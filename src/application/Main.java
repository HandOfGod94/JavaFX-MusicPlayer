package application;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import com.fxmagic.player.ButtonsPane;
import com.fxmagic.player.CustomMenu;
import com.fxmagic.player.FullScreenPlayer;
import com.fxmagic.player.Player;
import com.fxmagic.player.Playlist;

public class Main extends Application
{
    public List<File> playlist, tempOpenWindow;
    public int currentFileNo;
    public File fileToPlay;
    String extension;

    Media currentMedia;
    MediaPlayer mediaPlayer;

    ButtonsPane buttonsPane;
    CustomMenu menuBar;
    boolean seekBarValueChanged = false;

    Playlist nowPlaying;
    Stage currentStage;
    
    @Override
    public void start(final Stage primaryStage)
    {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 700);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Media Player");
        primaryStage.getIcons().add(new Image("file:WMP.png"));
        primaryStage.show();
        currentStage=primaryStage;
        buttonsPane = new ButtonsPane();
        root.setBottom(buttonsPane);

        menuBar = new CustomMenu(primaryStage);
        root.setTop(menuBar);

        menuClicks(menuBar, primaryStage, root);
        buttonClicks(buttonsPane, root);

        primaryStageProperties(primaryStage);
        
        nowPlaying = new Playlist();
    }

    private void menuClicks(final CustomMenu menuBar, final Stage primaryStage, final BorderPane root)
    {
        menuBar.open.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {
                tempOpenWindow = menuBar.openMenuClicked(primaryStage,playlist);
                
                if (tempOpenWindow != null)
                {
                    playlist=tempOpenWindow;
                    currentFileNo = 0;
                    if (buttonsPane.play.getText().equals("Pause"))
                        mediaPlayer.stop();
                    if (nowPlaying.isShowing())
                    {
                        nowPlaying.showPlaylist(playlist, primaryStage);
                        onPlaylistClick(nowPlaying, root);
                    }
                    createMediaPlayer(root);
                    menuBar.viewPlaylist.setDisable(false);
                    mediaPlayer.play();
                }
            }
        });

        menuBar.exit.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent arg0)
            {
                System.exit(0);
            }
        });

        menuBar.viewPlaylist.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {
                if (menuBar.viewPlaylist.isSelected())
                {
                    nowPlaying.showPlaylist(playlist, primaryStage);
                    nowPlaying.show();
                }
                else
                {
                    nowPlaying.close();
                }
                onPlaylistClick(nowPlaying, root);
                onPlaylistClosed(nowPlaying);

            }
        });
        
        menuBar.viewFullScreen.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent arg0)
            {
                FullScreenPlayer player=new FullScreenPlayer(mediaPlayer);
                player.setFullScreen(true);
                player.show();
            }
        });
        
    }

    private void onPlaylistClick(final Playlist nowPlaying, final BorderPane root)
    {
        nowPlaying.playlistPane.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent event)
            {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    currentFileNo = nowPlaying.playlistPane.getSelectionModel().getSelectedIndex();
                    mediaPlayer.stop();
                    createMediaPlayer(root);
                    mediaPlayer.play();

                }

            }
        });
    }

    private void onPlaylistClosed(Playlist nowPlaying)
    {
        nowPlaying.setOnCloseRequest(new EventHandler<WindowEvent>()
        {

            @Override
            public void handle(WindowEvent event)
            {
                if (menuBar.viewPlaylist.isSelected())
                    menuBar.viewPlaylist.setSelected(false);
            }
        });

    }

    private void buttonClicks(final ButtonsPane buttonsPane, final BorderPane root)
    {
        final Button playButton = buttonsPane.play;
        final Button nextButton = buttonsPane.next;
        final Button stopButton = buttonsPane.stop;
        final Button previousButton = buttonsPane.previous;

        playButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (playButton.getText().equals("Pause"))
                {
                    mediaPlayer.pause();
                    playButton.setText("Play");
                }
                else
                {
                    mediaPlayer.play();
                    playButton.setText("Pause");
                }
            }
        });

        stopButton.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent arg0)
            {
                if (playButton.getText().equals("Pause"))
                {
                    mediaPlayer.stop();
                    playButton.setText("Play");
                }
            }
        });

        nextButton.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent arg0)
            {
                mediaPlayer.stop();
                currentFileNo++;
                createMediaPlayer(root);
                mediaPlayer.play();
            }
        });

        previousButton.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent arg0)
            {
                mediaPlayer.stop();
                currentFileNo--;
                createMediaPlayer(root);
                mediaPlayer.play();
            }
        });

    }

    private void createMediaPlayer(final BorderPane root)
    {
        fileToPlay = playlist.get(currentFileNo);
        extension = getExtension(fileToPlay.getAbsoluteFile().toString());
        if(extension.equals(".mp4")||extension.equals(".flv"))
        {
            menuBar.viewFullScreen.setDisable(false);
        }
        else
        {
            menuBar.viewFullScreen.setDisable(true);
        }
        currentMedia = new Media(fileToPlay.toURI().toString());
        mediaPlayer = new MediaPlayer(currentMedia);
        Player player = new Player(extension, mediaPlayer,currentStage);
        setUpMediaPlayer(player, root);
        root.setCenter(player);
    }

    private void setUpMediaPlayer(final Player player, final BorderPane root)
    {
        mediaPlayer.setOnReady(new Runnable()
        {
            String titleValue, artistValue, albumValue;
            Image albumArt;
            Duration duration;

            @Override
            public void run()
            {
                albumArt = (Image) mediaPlayer.getMedia().getMetadata().get("image");
                titleValue = (String) mediaPlayer.getMedia().getMetadata().get("title");
                artistValue = (String) mediaPlayer.getMedia().getMetadata().get("artist");
                albumValue = (String) mediaPlayer.getMedia().getMetadata().get("album");
                duration = mediaPlayer.getMedia().getDuration();
                player.setMetadata(albumArt, titleValue, artistValue, albumValue);
                seekBarValue(duration);
            }
        });

        mediaPlayer.setOnPlaying(new Runnable()
        {
            @Override
            public void run()
            {
                currentStage.setTitle(fileToPlay.getName());
                buttonsPane.play.setText("Pause");
                buttonsPane.next.setDisable(false);
                buttonsPane.previous.setDisable(false);
                if (currentFileNo == 0)
                    buttonsPane.previous.setDisable(true);
                if (currentFileNo == playlist.size() - 1)
                    buttonsPane.next.setDisable(true);
            }
        });

        mediaPlayer.setOnStopped(new Runnable()
        {
            String timeText;
            Duration duration;

            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                buttonsPane.seekBar.setValue(0);
                duration = mediaPlayer.getMedia().getDuration();
                timeText = new DecimalFormat("00").format((int) duration.toMinutes()) + ":"
                        + new DecimalFormat("00").format((int) ((duration.toMinutes() - (int) duration.toMinutes()) * 60));
                buttonsPane.time.setText("00:00/" + timeText);
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable()
        {
            String timeText;
            Duration duration;

            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                buttonsPane.seekBar.setValue(0);
                duration = mediaPlayer.getMedia().getDuration();
                timeText = new DecimalFormat("00").format((int) duration.toMinutes()) + ":"
                        + new DecimalFormat("00").format((int) ((duration.toMinutes() - (int) duration.toMinutes()) * 60));
                buttonsPane.time.setText("00:00/" + timeText);
                buttonsPane.play.setText("Play");
                if (currentFileNo < playlist.size() - 1)
                {
                    currentFileNo++;
                    createMediaPlayer(root);
                    mediaPlayer.play();
                }
            }
        });
    }

    private void primaryStageProperties(final Stage primaryStage)
    {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {

            @Override
            public void handle(WindowEvent arg0)
            {
                if (nowPlaying.isShowing())
                    nowPlaying.close();
            }
        });

        primaryStage.iconifiedProperty().addListener(new InvalidationListener()
        {

            @Override
            public void invalidated(Observable observable)
            {
                if (primaryStage.isIconified())
                {
                    if (nowPlaying.isShowing())
                    {
                        nowPlaying.setIconified(true);
                    }
                }
                else
                {
                    if (nowPlaying.isIconified())
                        nowPlaying.setIconified(false);
                }
            }
        });

    }

    private void seekBarValue(final Duration duration)
    {
        updateSeekBar(duration);
        buttonsPane.seekBar.valueProperty().addListener(new InvalidationListener()
        {
            @Override
            public void invalidated(Observable ov)
            {
                if (buttonsPane.seekBar.isValueChanging())
                {
                    mediaPlayer.setMute(true);
                    mediaPlayer.seek(duration.multiply(buttonsPane.seekBar.getValue() / 100.0));
                }

                else
                {
                    buttonsPane.seekBar.setOnMousePressed(new EventHandler<Event>()
                    {

                        @Override
                        public void handle(Event event)
                        {
                            seekBarValueChanged = true;
                            mediaPlayer.seek(duration.multiply(buttonsPane.seekBar.getValue() / 100.0));
                        }
                    });

                    buttonsPane.seekBar.setOnMouseReleased(new EventHandler<Event>()
                    {

                        @Override
                        public void handle(Event event)
                        {
                            seekBarValueChanged = false;
                        }
                    });
                }
            }
        });

    }

    protected void updateSeekBar(final Duration duration)
    {
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener()
        {
            Duration currentTime;
            String currentDuration, totalDuration, elapsedTime;

            @Override
            public void invalidated(Observable observable)
            {
                if (mediaPlayer.getCurrentTime().toMillis() != 0)
                {
                    Platform.runLater(new Runnable()
                    {

                        @Override
                        public void run()
                        {

                            currentTime = mediaPlayer.getCurrentTime();

                            if (!seekBarValueChanged && !buttonsPane.seekBar.isValueChanging())
                            {
                                mediaPlayer.setMute(false);
                                buttonsPane.seekBar.adjustValue((currentTime.toMillis() / duration.toMillis()) * 100.0);
                                currentDuration = new DecimalFormat("00").format((int) currentTime.toMinutes()) + ":"
                                        + new DecimalFormat("00").format((int) ((currentTime.toMinutes() - (int) currentTime.toMinutes()) * 60));
                                totalDuration = new DecimalFormat("00").format((int) duration.toMinutes()) + ":"
                                        + new DecimalFormat("00").format((int) ((duration.toMinutes() - (int) duration.toMinutes()) * 60));
                                elapsedTime = currentDuration + "/" + totalDuration;
                                buttonsPane.time.setText(elapsedTime);
                            }
                        }
                    });
                }
            }
        });
    }

    private String getExtension(String fileName)
    {
        String ext;
        ext = fileToPlay.getAbsolutePath().substring(fileName.lastIndexOf("."), fileName.length());
        return ext;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
