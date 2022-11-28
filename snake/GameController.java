package com.snake;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.TilePane;

public class GameController implements Initializable {
    @FXML public TilePane rootCanvas;

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("SDAASASDS" + rootCanvas.getChildren().get(0));
        rootCanvas.setOnKeyPressed(e -> this.OnKeyPress(e.getCode()));
    }

    public void OnKeyPress(KeyCode code) {
        if (code == KeyCode.UP) {
            if (GameLoop.direction == Direction.DOWN)
                return;
            System.out.println("UP key was pressed");
            GameLoop.direction = Direction.UP;
        }
        else if (code == KeyCode.DOWN) {
            if (GameLoop.direction == Direction.UP)
                return;
            System.out.println("DOWN key was pressed");
            GameLoop.direction = Direction.DOWN;
        }
        else if (code == KeyCode.LEFT) {
            if (GameLoop.direction == Direction.RIGHT)
                return;
            System.out.println("LEFT key was pressed");
            GameLoop.direction = Direction.LEFT;
        }
        else if (code == KeyCode.RIGHT) {
            if (GameLoop.direction == Direction.LEFT)
                return;
            System.out.println("RIGHT key was pressed");
            GameLoop.direction = Direction.RIGHT;
        }
    }
}
