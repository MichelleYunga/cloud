package com.example.clound.CONTROLLER;

import com.example.clound.MODELO.Playlist;
import com.example.clound.MODELO.Song;
import com.example.clound.REPOSITORY.PlaylistRepository;
import com.example.clound.REPOSITORY.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/playlists")
@CrossOrigin(origins ="*")
public class PlaylistController {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

   @PostMapping("/crear")
public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
    if (playlist.getName() == null) {
        return ResponseEntity.badRequest().build();
    }
    Playlist savedPlaylist = playlistRepository.save(playlist);
    URI location = URI.create("/playlists/" + savedPlaylist.getId());
    return ResponseEntity.created(location).body(savedPlaylist);
}


    @GetMapping("/lists")
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @GetMapping("/{listName}")
    public ResponseEntity<Playlist> getPlaylistByName(@PathVariable String listName) {
        Playlist playlist = playlistRepository.findByName(listName);
        if (playlist == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playlist);
    }

    @PutMapping("/{listName}/actualizar")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable String listName, @RequestBody Playlist playlistDetails) {
        Playlist playlist = playlistRepository.findByName(listName);
        if (playlist == null) {
            return ResponseEntity.notFound().build();
        }
        if (!playlist.getName().equals(playlistDetails.getName())) {
            return ResponseEntity.status(409).build();
        }
        playlist.setDescription(playlistDetails.getDescription());
        playlist.setSongs(playlistDetails.getSongs());
        playlistRepository.save(playlist);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{listName}/addSong")
    public ResponseEntity<Playlist> addSongToPlaylist(@PathVariable String listName, @RequestBody Song song) {
        Playlist playlist = playlistRepository.findByName(listName);
        if (playlist == null) {
            return ResponseEntity.notFound().build();
        }
        playlist.addSong(song);
        songRepository.save(song); // Optional: Save song to the repository
        playlistRepository.save(playlist);
        return ResponseEntity.ok(playlist);
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable String listName) {
        Playlist playlist = playlistRepository.findByName(listName);
        if (playlist == null) {
            return ResponseEntity.notFound().build();
        }
        playlistRepository.delete(playlist);
        return ResponseEntity.noContent().build();
    }
}
