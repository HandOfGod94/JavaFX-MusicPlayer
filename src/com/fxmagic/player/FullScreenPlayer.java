package com.fxmagic.player;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FullScreenPlayer extends Stage
{
    public FullScreenPlayer(final MediaPlayer mediaPlayer)
    {
        final MediaView mediaView=new MediaView(mediaPlayer);
        StackPane fullScreenPlayer = new StackPane();
        Scene fullScreenScene = new Scene(fullScreenPlayer);
        fullScreenScene.setFill(Color.BLACK);
        //ButtonsPane overlayControl=new ButtonsPane();
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
        fullScreenPlayer.getChildren().addAll(mediaView);
        setScene(fullScreenScene);
        mediaView.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent arg0)
            {
                if (arg0.getButton() == MouseButton.PRIMARY && arg0.getClickCount() == 2)
                {
                    mediaView.setFitWidth(700);
                    close();
                }
            }
        });

        fullScreenScene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {

            @Override
            public void handle(KeyEvent event)
            {
                if (event.getCode().equals(KeyCode.ESCAPE))
                {
                    mediaView.setFitWidth(700);
                    close();   
                }
            }
        });
    }
}
