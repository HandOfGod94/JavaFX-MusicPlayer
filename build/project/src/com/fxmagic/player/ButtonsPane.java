package com.fxmagic.player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class ButtonsPane extends VBox
{
    public Button play, stop, next, previous;
    public Slider seekBar;
    public Label time;
    
    public ButtonsPane()
    {
        setSpacing(15);
        setPadding(new Insets(0, 25, 25, 25));
        setAlignment(Pos.CENTER_RIGHT);
        
        FlowPane buttonsSpace=new FlowPane();
        buttonsSpace.setAlignment(Pos.BOTTOM_CENTER);
        buttonsSpace.setHgap(10);
        play = new Button("Play");
        stop = new Button("Stop");
        next = new Button("Next");
        previous = new Button("Prev");
        previous.setDisable(true);
        next.setDisable(true);
        buttonsSpace.getChildren().addAll(previous, play, stop, next);
        
        seekBar=new Slider();
        time=new Label("--:--/--:--");
        getChildren().addAll(time,seekBar,buttonsSpace);
    }
}
