package com.stickhero.stickhero;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application implements EventHandler<ActionEvent> {
    boolean isPressed = false;
    private int points = 0;
    private int Flag = 0;
    private boolean End = false;
    private String Player;
    private int TopScore = 0;
    private String TopPlayer;
    private Ninja ninja = new Ninja(50, 100);
    private ArrayList<Pillar> Pillar0 = new ArrayList <Pillar>();
    private ArrayList <Stick> Stick0 = new ArrayList <Stick>();

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Stick Hero- ripped");
        Image icon = new Image("icon.png");
        Image backgroundImage = new Image("background.png");
        Image cherry = new Image("cherry.png");
        stage.getIcons().add(icon);

        Label label = new Label();

        GridPane root = new GridPane();
        root.getChildren().add(label);

        Scene scene = new Scene(root, 405, 675);
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root.setBackground(new Background(background));

        ImageView cherryImage = new ImageView(cherry);
        GridPane.setMargin(cherryImage, new Insets(10, 10, 0, 0));
        //StackPane.setAlignment(cherryImage, Pos.TOP_LEFT);
        //StackPane.setMargin(cherryImage, new Insets(10, 10, 0, 0));
        //root.getChildren().add(cherryImage);

        Label numberLabel = new Label(String.valueOf(points));
        numberLabel.setStyle("-fx-text-fill: #FF3700; -fx-font-size: 18; -fx-font-weight: bold;");
        GridPane.setMargin(numberLabel, new Insets(20, 10, 0, 0));
        root.add(cherryImage, 0, 0);
        root.add(numberLabel, 1, 0);

        // Create a new StackPane to overlay the image and label
        //StackPane overlayPane = new StackPane();
        //overlayPane.getChildren().addAll(cherryImage, numberLabel);

        //root.getChildren().add(overlayPane);

        scene.setOnKeyTyped((KeyEvent event) -> {
            // Check if the pressed key is 'Q'
            if (event.getCode() == KeyCode.Q) {
                // Quit the program
                System.out.println("Quitting the program...\n");
                stage.close();
            }
        });

        scene.setOnMouseClicked((MouseEvent event) -> {
            // Check if the clicked button is the primary button (left mouse button)
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("Left mouse button pressed.\n");
                isPressed = true;
            }
        });

        scene.setOnMouseReleased((MouseEvent event) -> {
            // Check if the released button is the primary button (left mouse button)
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("Left mouse button released.\n");
                isPressed = false;
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Game("Player1");
        launch();
    }

    public static void Game(String PlayerName) {
        //handle ui and listeners
        //do Stick generation and walk
        //handle points
    }

    public void CherryGenerator(){
        //randomly generate cherries at different locations between the walls
    }

    public void TopPlayer(){
        //Save top player name and score
        //else any other player will be forgotten
    }

    public void Walk() {
        //Walk the character and trigger different outcomes based on the length of the stick
        //Also if the player presses and correctly picks cherry, then increase point
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}