package com.fxmagic.player;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class VideoPlayer extends BorderPane
{
    public MediaView mediaView;
    private FullScreenPlayer fullScreen;

    public VideoPlayer(final MediaPlayer mediaPlayer,Stage stage)
    {
        mediaView = new MediaView(mediaPlayer);
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(700);
        mediaView.setSmooth(true);
        mediaView.setId("cover-image");
        fullScreen=new FullScreenPlayer(mediaPlayer);
        
        mediaView.fitWidthProperty().bind(stage.widthProperty());
        
        mediaView.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent event)
            {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    fullScreen = new FullScreenPlayer(mediaPlayer);
                    fullScreen.setFullScreen(true);
                    fullScreen.show();
                }
            }
        });
        setCenter(mediaView);
    }    
}
