package com.fxmagic.player;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MusicPlayer extends VBox
{
    public ImageView cover;
    public Text title, artist, album;
    
    public MusicPlayer()
    {
        setAlignment(Pos.CENTER);
        setSpacing(15);
        
        cover = new ImageView();
        cover.setFitWidth(300);
        cover.setPreserveRatio(true);
        cover.setSmooth(true);

        title = new Text();
        artist = new Text();
        album = new Text();
        title.setId("general-text");
        artist.setId("general-text");
        album.setId("general-text");
        
        getChildren().addAll(cover,title,artist,album);
    }
    
    public void setMetadata(Image albumArt,String titleValue, String artistValue, String albumValue)
    {
        cover.setId("cover-image");
        cover.setImage(albumArt);
        title.setText("\nTitle: "+titleValue);
        artist.setText("Artist: "+artistValue);
        album.setText("Album: "+albumValue);
    }
}
