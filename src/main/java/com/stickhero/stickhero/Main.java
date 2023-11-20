package com.stickhero.stickhero;

import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    Scene gameStart, mainScreen;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Stick Hero- ripped");
        Image icon = new Image("icon.png");
        Image backgroundImage = new Image("background.png");
        Image cherry = new Image("cherry.png");
        stage.getIcons().add(icon);

        // Menu scene
        //Label mainLabel = new Label();
        GridPane root = new GridPane();
        root.getStyleClass().add("grid-pane");
        //root.getChildren().add(mainLabel);
        BackgroundImage bg = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root.setBackground(new Background(bg));
        Button button1= new Button("PLAY");

        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        mainScreen = new Scene(root, 405, 675);

        // Game Scene
        //Label gameLabel = new Label();
        Pane root1 = new Pane();
        //root1.getChildren().add(mainLabel);
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root1.setBackground(new Background(background));
        gameStart = new Scene(root1, 404, 675);
        ImageView cherryImage = new ImageView(cherry);
        GridPane.setMargin(cherryImage, new Insets(10, 10, 0, 0));

        Label numberLabel = new Label(String.valueOf(points));
        numberLabel.setStyle("-fx-text-fill: #FF3700; -fx-font-size: 25; -fx-font-weight: bold;");
        //GridPane.setMargin(numberLabel, new Insets(20, 10, 0, 0));

        cherryImage.setLayoutX(360);
        cherryImage.setLayoutY(0);

        numberLabel.setLayoutX(335);
        numberLabel.setLayoutY(2);

        //root1.add(cherryImage, 0, 0);
        //root1.add(numberLabel, 1, 0);
        root1.getChildren().addAll(cherryImage, numberLabel);

        gameStart.setOnKeyTyped((KeyEvent event) -> {
            // Check if the pressed key is 'Q'
            if (event.getCode() == KeyCode.Q) {
                // Quit the program
                System.out.println("Quitting the program...\n");
                stage.close();
            }
        });

        gameStart.setOnMouseClicked((MouseEvent event) -> {
            // Check if the clicked button is the primary button (left mouse button)
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("Left mouse button pressed.\n");
                isPressed = true;
            }
        });

        gameStart.setOnMouseReleased((MouseEvent event) -> {
            // Check if the released button is the primary button (left mouse button)
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("Left mouse button released.\n");
                isPressed = false;
            }
        });

        button1.setOnAction(e -> {
            stage.setScene(gameStart);
            addPillar(root1);
            addStartingPillar(root1);
            createNinja(root1);
            addCherry(root1);
        });

        button1.getStyleClass().add("styled-button");
        GridPane.setHalignment(button1, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(button1, javafx.geometry.VPos.CENTER);

        // Create a timeline for the button animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(button1.prefWidthProperty(), 100),
                        new KeyValue(button1.prefHeightProperty(), 100)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(button1.prefWidthProperty(), 107),
                        new KeyValue(button1.prefHeightProperty(), 107)
                ),
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(button1.prefWidthProperty(), 100),
                        new KeyValue(button1.prefHeightProperty(), 100, Interpolator.LINEAR)
                )
        );

        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat the animation indefinitely
        timeline.setAutoReverse(true); // Reverse the animation on each cycle

        // Start the animation when the scene is shown
        stage.setOnShown(event -> timeline.play());

        root.add(button1, 2, 2, 3, 3);

        stage.setScene(mainScreen);
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

    private void addCherry(Pane root1) {
        ImageView cherryImage = new ImageView(new Image("cherry.png"));
        cherryImage.setLayoutX(246);
        cherryImage.setLayoutY(476);
        root1.getChildren().addAll(cherryImage);
    }

    private void createNinja(Pane root1) {
        Rectangle pillar = new Rectangle(20, 35, Color.BLACK);
        pillar.setLayoutX(119);
        pillar.setLayoutY(441);
        root1.getChildren().addAll(pillar);
    }
    private void addStartingPillar(Pane root1) {
        Rectangle pillar = new Rectangle(120, 200, Color.web("#171717"));
        pillar.setLayoutX(20);
        pillar.setLayoutY(476);
        root1.getChildren().addAll(pillar);
    }
    private void addPillar(Pane root1) {
        Rectangle pillar = new Rectangle(40, 200, Color.web("#171717")); // Customize size and color
        //int lastRowIndex = root1.getRowConstraints().size() - 1;
        pillar.setLayoutX(350);
        pillar.setLayoutY(476);
        root1.getChildren().addAll(pillar);
        //GridPane.setValignment(uprightRectangle, javafx.geometry.VPos.BOTTOM);
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}