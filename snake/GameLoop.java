package com.snake;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class GameLoop {
    static Direction direction = Direction.RIGHT;
    static GameController gc = new GameController();

    public static void Update(TilePane rootCanvas) throws IOException {

        if (direction == Direction.UP)
            Snake.headPos.y--;
        else if (direction == Direction.DOWN)
            Snake.headPos.y++;
        else if (direction == Direction.LEFT)
            Snake.headPos.x--;
        else if (direction == Direction.RIGHT)
            Snake.headPos.x++;

        if (isEnd(rootCanvas))
        {
        	App.stage.setScene(App.mainScene);
//        	Platform.exit();
        }
        else {
            Snake.pos.add(new Point(Snake.headPos.x, Snake.headPos.y));
            fillCell(rootCanvas, Snake.headPos, Snake.tailPos = Snake.pos.poll(), "#CCCCCC");
        }
        
        if (isEat(rootCanvas)) eat();
    }

    
    private static String auto(){
    	try {
			return new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println("empty");
			return "";
		}
    }
    
    private static void fillCell(TilePane rootCanvas, Point _new, Point _old, String _color) {
        rootCanvas.getChildren().get(_new.y * 20 + _new.x).setStyle("-fx-background-color: " + _color +";");
        if (_old != null)
            rootCanvas.getChildren().get(_old.y * 20 + _old.x).setStyle("-fx-background-color: #333333; -fx-border-color: #444444;");
    }

    private static boolean isEnd(TilePane _rootCanvas) throws IOException {
        if (Snake.headPos.x < 0 || Snake.headPos.y < 0 || Snake.headPos.x > 19 || Snake.headPos.y > 19) {
            System.out.println("Crashed to wall");
            return true;
        }
        if (isExist(Snake.headPos.x, Snake.headPos.y, false)) {
            System.out.println("Crashed to body");
            return true;
        }
        return false;        
    }

    private static boolean isEat(TilePane _rootCanvas) {
        if (Snake.headPos.x == Board.seed.x && Snake.headPos.y == Board.seed.y) {
            putSeed(_rootCanvas);
            return true;
        }
        return false;
    }

    private static boolean isExist(int _x, int _y, boolean isHeadInclude) {
        for (Point items: Snake.pos) {
            if (items == Snake.headPos && isHeadInclude == false) continue;
            if (items.x == _x && items.y == _y) {
                return true;
            }
        }
        return false;
    }

    private static void eat() {
        Snake.length++;
        Snake.pos.add(Snake.tailPos);
    }

    public static void putSeed(TilePane _rootCanvas) {
        Random r = new Random();
        int x = r.nextInt(20);
        int y = r.nextInt(20);
        while (isExist(x, y, true)) {
            x = r.nextInt(20);
            y = r.nextInt(20);
        }
        Board.seed.x = x;
        Board.seed.y = y;
        System.out.println(Board.seed.x + ", " + Board.seed.y);
        fillCell(_rootCanvas, Board.seed, null, "#00C896");
    }
}
