package com.example.clound.REPOSITORY;

import com.example.clound.MODELO.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Playlist findByName(String name);
}
