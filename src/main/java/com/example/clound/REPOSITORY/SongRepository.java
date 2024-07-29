package com.example.clound.REPOSITORY;

import com.example.clound.MODELO.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
