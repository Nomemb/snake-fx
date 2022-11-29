package com.snake;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * JavaFX App
 */


public class App extends Application {
	static Stage stage;
	static Scene mainScene, gameScene;
	static Timer gameTimer;
	static TimerTask gameLoop;
	
    @Override
    public void start(Stage stage) throws IOException {

    	// 타이틀
    	Label mainLabel = new Label("Snake Game");
    	mainLabel.setFont(Font.font("Arial", FontWeight.BOLD, 45));
    	HBox top = new HBox();
    	top.setPadding(new Insets(60));
    	top.getChildren().addAll(mainLabel);
    	

    	// 버튼 설정
    	Button gameStartBtn = new Button("Start");
    	VBox center = new VBox();
    	center.setPadding(new Insets(10, 60, 60, 150));
    	gameStartBtn.setPrefSize(100, 50);
    	gameStartBtn.setFont(Font.font("Arial", 25));
    	center.getChildren().addAll(gameStartBtn);
    	
    	// 메인 화면 설정
    	BorderPane mainPane = new BorderPane();
    	mainPane.setTop(top);
    	mainPane.setCenter(center);
    	
    	mainScene = new Scene(mainPane, 400, 400);
    	
    	// 게임 화면 설정
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("board.fxml"));
		gameScene = new Scene(fxmlLoader.load(), 400, 400);
		
		// 초기 시작 상태
		stage.setTitle("Snake Game");
		stage.setScene(mainScene);
		stage.show();

		// 게임 내부 설정
		GameController controller = fxmlLoader.getController();
		
		gameTimer = new Timer(true);
		
		fxmlLoader.setController(fxmlLoader);
		gameScene.setOnKeyPressed(e -> controller.OnKeyPress(e.getCode()));
		stage.setOnCloseRequest(e -> gameTimer.cancel());
		
		Snake.pos.add(new Point(6, 10));
		Snake.pos.add(new Point(7, 10));
		Snake.pos.add(new Point(8, 10));
		Snake.pos.add(new Point(9, 10));
		Snake.pos.add(new Point(10, 10));
		Board.seed = new Point(0, 0);
		
		GameLoop.putSeed(controller.rootCanvas);
		Snake.headPos = new Point(10, 10);
		
		gameLoop = new TimerTask() {
			@Override
			public void run() {
				try {
					GameLoop.Update(controller.rootCanvas);
				} catch (IOException e) { }
			}
		};
		
    	// 버튼 동작 ( 게임 씬으로 변경 )
    	gameStartBtn.setOnAction(e->{
    		stage.setScene(gameScene);
    	});
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing. Length: " + Snake.length);
    }

    public static void main(String[] args) {
        launch();
    }
 }