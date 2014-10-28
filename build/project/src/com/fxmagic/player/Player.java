package com.fxmagic.player;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Player extends VBox
{
    String extension;
    MusicPlayer musicPlayer;
    VideoPlayer videoPlayer;
    public Player(String extension, MediaPlayer mediaPlayer,Stage stage)
    {
        this.extension=extension;
        setAlignment(Pos.CENTER);
        setSpacing(15);
        if(!(extension.equals(".mp4")||extension.equals(".flv")))
        {
            musicPlayer=new MusicPlayer();
            getChildren().add(musicPlayer);
        }
        else
        {
            videoPlayer=new VideoPlayer(mediaPlayer,stage);
            getChildren().add(videoPlayer);
        }
    }
    
    public void setMetadata(Image albumArt,String titleValue, String artistValue, String albumValue)
    {
        if(!(extension.equals(".mp4")||extension.equals(".flv")))
        {
            if(albumArt==null)
                albumArt=new Image("file:default-album-art.jpg");
            if(titleValue==null)
                titleValue="Unknown";
            if(artistValue==null)
                artistValue="Unknown";
            if(albumValue==null)
                albumValue="Unknown";
            musicPlayer.setMetadata(albumArt, titleValue, artistValue, albumValue);
        }
    }
}
