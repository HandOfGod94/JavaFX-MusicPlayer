package com.fxmagic.player;

import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Playlist extends Stage
{
    public ListView<String> playlistPane;
    
    public void showPlaylist(List<File> playlist, Stage primaryStage)
    {
        VBox playlistRoot = new VBox();
        Scene playlistScene = new Scene(playlistRoot, 400, 400);
        playlistPane = new ListView<>();
        ObservableList<String> list = FXCollections.observableArrayList();
        
        list.clear();
        for (int i = 0; i < playlist.size(); i++)
        {
            list.add(playlist.get(i).getName());
        }

        playlistPane.setItems(list);
        playlistPane.getSelectionModel().select(0);
        playlistRoot.getChildren().add(playlistPane);
        setScene(playlistScene);
        setTitle("Playlist");
        setX(primaryStage.getX() + primaryStage.getWidth());
        setY(primaryStage.getY());
    }
    
}
