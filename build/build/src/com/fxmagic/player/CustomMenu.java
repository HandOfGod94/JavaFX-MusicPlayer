package com.fxmagic.player;

import java.io.File;
import java.util.List;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CustomMenu extends MenuBar
{
    private List<File> openFiles;
    public Menu fileMenu, viewMenu, aboutMenu;
    public MenuItem open, exit, viewFullScreen, about;
    public CheckMenuItem viewPlaylist;

    public CustomMenu(final Stage stage)
    {

        fileMenu = new Menu("File");
        viewMenu = new Menu("View");
        aboutMenu = new Menu("About");

        open = new MenuItem("Open");
        exit = new MenuItem("Exit");

        viewPlaylist = new CheckMenuItem("Playlist");
        viewFullScreen = new MenuItem("FullScreen");
        viewPlaylist.setDisable(true);
        viewFullScreen.setDisable(true);

        MenuItem about = new MenuItem("About");

        fileMenu.getItems().addAll(open, exit);
        viewMenu.getItems().addAll(viewPlaylist, viewFullScreen);
        aboutMenu.getItems().add(about);

        getMenus().addAll(fileMenu, viewMenu, aboutMenu);
    }

    public List<File> openMenuClicked(Stage stage, List<File> playlist)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files (MP3/WAV/AAC)", "*.mp3", "*.wav", "*.aac"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files (MP4/FLV)", "*.mp4", "*.flv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files (*.*)", "*.*"));
        if(playlist!=null)
            fileChooser.setInitialDirectory(playlist.get(0).getParentFile());
        openFiles = fileChooser.showOpenMultipleDialog(stage);
        return openFiles;
    }

}
