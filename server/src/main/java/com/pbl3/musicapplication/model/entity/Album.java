package com.pbl3.musicapplication.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.pbl3.musicapplication.model.model.AlbumModel;
import com.pbl3.musicapplication.model.model.SongModel;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;


@AllArgsConstructor@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Album {
    @Id
    @TableGenerator(name = "AlbumId_Gen", initialValue = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AlbumId_Gen")
    @Setter(AccessLevel.PRIVATE)
    private Integer albumId;

    @Nonnull
    private String albumName;

    @Nonnull
    @OneToOne(targetEntity = Artist.class)
    private Artist artist;

    @Nullable
    @OneToMany(targetEntity = Song.class)
    private List<Song> songsAlbum;
    
    public Album (AlbumModel albumModel) {
        this.artist = albumModel.getArtist();

        if (albumModel.getSongModels() != null) {
            List<Song> tmp = new ArrayList<>();
            for (SongModel songModel : albumModel.getSongModels()) {
                tmp.add(new Song(songModel));
            }
            this.songsAlbum = tmp;
        }
    }
    public void setSongsAlbum(@Nonnull List<SongModel> songModels){
        List<Song> tmp = new ArrayList<>();
        for (SongModel songModel : songModels) {
            tmp.add(new Song(songModel));
        }
        this.songsAlbum = tmp;
    }
    public boolean isValid() {
        return !(artist == null || !artist.isValid() || albumName.isEmpty() || albumName == null);
    }
}