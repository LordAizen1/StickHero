package com.stickhero.stickhero;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.util.ArrayList;
import javafx.scene.Group;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

@RunWith(Enclosed.class)
public class Main extends Application implements EventHandler<ActionEvent> {
    private static ArrayList<Integer> scoreList = new ArrayList<Integer>();
    private int gameEnd = 0;
    private static Ninja ninja;
    private ArrayList<Pillar> Pillar0 = new ArrayList <Pillar>();
    static ArrayList<Rectangle> rect = new ArrayList<Rectangle>();
    //if cherry is added then some positive value and if cherry is not added then -1
    ArrayList<Integer> cherryPosition = new ArrayList<Integer>();
    ArrayList<ImageView> cherryImageView = new ArrayList<ImageView>();
    int pillarNo = 0;
    //private ArrayList <Stick> Stick0 = new ArrayList <Stick>();
    private Stick Stick0 = new Stick();
    private int running = 0;
    Scene gameStart, mainScreen;
    private Label numberLabel;
    Image cherry = new Image("cherry.png");

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Stick Hero- ripped");
        Image icon = new Image("icon.png");
        Image backgroundImage = new Image("background.png");

        stage.getIcons().add(icon);

        // Menu scene
        GridPane root = new GridPane();
        root.getStyleClass().add("grid-pane");
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
        Pane root1 = new Pane();
        Group setGroup = new Group();
        root1.getChildren().add(setGroup);
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root1.setBackground(new Background(background));
        gameStart = new Scene(root1, 404, 675);

        Media media = new Media(getClass().getResource("/Music/bg.mp3").toExternalForm());

        // Create a MediaPlayer to play the background music
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Set the MediaPlayer to continuously play
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        gameStart.setOnKeyTyped((KeyEvent ev) -> {
            // Check if the pressed key is 'Q'
            if (ev.getCharacter().equals("q")) {
                // Quit the program
                System.out.println("Quitting the program...\n");
                stage.close();
            }
        });

        gameStart.setOnMousePressed((MouseEvent event) -> {
            // Check if the clicked button is the primary button (left mouse button)
            if (event.getButton() == MouseButton.PRIMARY && gameEnd == 0) {
                if (running == 0) {
                    Stick0.createStick((int) rect.get(pillarNo).getWidth() + pillarNo * 200, ninja.getY() + 35, setGroup);
                    Stick0.increaseHeight(setGroup);
                }
                else{
                    //flip the ninja
                    ninja.setFlip();
                    if (cherryPosition.get(pillarNo) !=-1){
                        boolean pickedCherry = ninja.checkNinjaCherryIntersection(cherryImageView.get(pillarNo));
                        if (pickedCherry){
                            System.out.println("PICKED THE CHERRYYYY!!!\n");
                        }
                    }
                }
            }
        });

        gameStart.setOnMouseReleased((MouseEvent event) -> {
            // Check if the released button is the primary button (left mouse button)
            if (gameEnd == 0) {
                if (running ==0) {
                    Stick0.stopIncreaseHeight();

                    int end = 0;
                    int reqLength = 200 - (int) rect.get(pillarNo).getWidth();
                    Timeline StickFall;
                    //System.out.printf("Required Length: %d\nStickLength: %d \n", reqLength, Stick0.getLength());
                    //correct stick length
                    if (reqLength < Stick0.getLength() && Stick0.getLength() <= reqLength + (int) rect.get(pillarNo + 1).getWidth()) {
                        StickFall = Stick0.StickFall();
                        end = 0;
                    }
                    //not sufficient stick length
                    else {
                        StickFall = Stick0.StickFall();
                        ninja.moveAndFall(setGroup, Stick0.getLength());
                        end = 1;
                    }

                    int finalEnd = end;
                    StickFall.setOnFinished(ev -> {
                        if (finalEnd == 1) {
                            System.out.println("reached here?\n");
                            gameEnd = 1;
                            endGame(setGroup, stage, root1);
                        } else {

                            pillarNo += 1;

                            int pillarWidth = (int) rect.get(pillarNo).getWidth();
                            if (cherryImageView.get(pillarNo-1) != null) {
                                System.out.println("cherry present!\n");
                            }
                            else{
                                System.out.println("cherry absent!\n");
                            }
                            Ninja.AnimationResult animationResult = ninja.move(setGroup, reqLength, pillarWidth, cherryImageView.get(pillarNo-1), numberLabel);

                            TranslateTransition translate = animationResult.getTranslateTransition();
                            // Create a ParallelTransition to combine translation and image change
                            ParallelTransition parallelTransition = new ParallelTransition(
                                    animationResult.getTimeline(),
                                    translate
                            );

                            //TranslateTransition to move the screen to the right after the ninja completes walking
                            int screenTranslateX = 200;
                            TranslateTransition translateCamera = new TranslateTransition(Duration.seconds(1.5), setGroup);
                            translateCamera.setByX(-screenTranslateX);
                            translate.setOnFinished(e0 -> {

                                running = 0;
                                parallelTransition.stop();
                                ninja.putIdle(setGroup);

                                System.out.println("sup?\n");
                                System.out.println("Ninja X: " + ninja.getX() + "\n");
                                translateCamera.play();
                            });

                            parallelTransition.play();
                            running = 1;

                        }
                    });

                    StickFall.play();
                }


            }
        });

        button1.setOnAction(e -> {
            stage.setScene(gameStart);
            mediaPlayer.play();

            addRandomPillars(setGroup);

            createNinja(setGroup);
            addCherry(setGroup);

            ImageView cherryImage = new ImageView(cherry);
            GridPane.setMargin(cherryImage, new Insets(10, 10, 0, 0));

            numberLabel = new Label(String.valueOf(ninja.getPoints()));
            numberLabel.setStyle("-fx-text-fill: #FF3700; -fx-font-size: 25; -fx-font-weight: bold;");

            cherryImage.setLayoutX(360);
            cherryImage.setLayoutY(0);

            numberLabel.setLayoutX(335);
            numberLabel.setLayoutY(2);

            root1.getChildren().addAll(cherryImage, numberLabel);

            Result result = JUnitCore.runClasses(JUnitTest.class);
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
            if (!result.wasSuccessful()){
                stage.close();
                System.out.println(result.wasSuccessful());
            }
            System.out.println(result.wasSuccessful());
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
        //Game("Player1");
        launch();
    }

    private void GameStart(Stage stage, Scene gameStart, Group setGroup, Pane root1) {
        stage.setScene(gameStart);
        pillarNo = 0;

        for (int i=0; i<100; i++){
            setGroup.getChildren().remove(rect.get(i));

        }
        rect.clear();
        addRandomPillars(setGroup);

        createNinja(setGroup);

        for (int i=0; i<100; i++){
            setGroup.getChildren().remove(cherryImageView.get(i));
        }
        cherryImageView.clear();
        cherryPosition.clear();
        addCherry(setGroup);
        numberLabel.setText(String.valueOf(ninja.getPoints()));

        TranslateTransition translateCamera = new TranslateTransition(Duration.seconds(1.5), setGroup);
        translateCamera.setToX(0);
        translateCamera.play();
    }

    public static class JUnitTest{
        @Test
        //test that all pillars are correctly added
        public void testPillars(){
            for (int i=0; i<100; i++){
               assertNotNull(rect.get(i));;
            }
        }

        @Test
        public void ninjaPlaced(){
            assert ninja.getX() > 5;
        }

    }


    public void TopPlayer(){
        //Save top player name and score
        //else any other player will be forgotten
    }

    private void addCherry(Group group) {

        for (int i=0; i<100; i++) {
            int distanceBetween = 200 - (int) rect.get(i).getWidth();
            if (distanceBetween < 45) {
                cherryPosition.add(-1);
                cherryImageView.add(null);
                continue;
            }

            Random random = new Random();
            //randomise whether cherry will be added or not
            if (random.nextBoolean()) {
                cherryImageView.add(new ImageView(new Image("cherry.png")));

                int calculatedX = (int) (((Math.random() * ((distanceBetween - 37) - 40))) + rect.get(i).getWidth()) + 200 * i;
                cherryImageView.get(i).setX(calculatedX);
                cherryImageView.get(i).setY(476);
                group.getChildren().add(cherryImageView.get(i));

                cherryPosition.add(calculatedX);
            }
            else {
                cherryPosition.add(-1);
                cherryImageView.add(null);
            }
        }
    }

    //singleton design-pattern
    private void createNinja(Group group) {
        ninja = Ninja.getInstance();
        ninja.NinjaAdder(group, (int) rect.get(pillarNo ).getWidth() -20);
    }

    private void addRandomPillars(Group setGroup) {

        for (int i=0;i<100; i++ ){
            //Pillar p = new Pillar();
            Pillar0.add(new Pillar());
            rect.add(Pillar0.get(i).setRandom(i));
            setGroup.getChildren().add( rect.get(i));
        }

    }

    private void endGame(Group setGroup, Stage stage, Pane root1) {

        loadScoreList();

        Rectangle endMenu = new Rectangle(300, 250);
        endMenu.setArcWidth(20);
        endMenu.setArcHeight(20);

        // Set the fill color with transparency
        Color fillColor = Color.rgb(255, 255, 255, 0.7); // R, G, B, Opacity
        endMenu.setFill(fillColor);

        endMenu.setX(200*pillarNo +47);
        endMenu.setY(200);

        // GAME ENDED text
        Text endtext = new Text("GAME ENDED!\n          😭");
        endtext.setLayoutX(200*pillarNo + 90); // Set the X coordinate of the text
        endtext.setLayoutY(260); // Set the Y coordinate of the text
        Font font = Font.font("Arial", FontWeight.BOLD, 30);
        endtext.setFont(font); // Set the font size

        //high score text
        scoreList.add(ninja.getPoints());
        Text highScore = new Text("HIGH SCORE: "+HighestScore(scoreList));
        highScore.setLayoutX(200*pillarNo + 120); // Set the X coordinate of the text
        highScore.setLayoutY(340); // Set the Y coordinate of the text
        Font font1 = Font.font("Arial", 20);
        highScore.setFont(font1);

        //quit button
        Button endButton= new Button("Quit");
        endButton.setLayoutX(200*pillarNo + 220);
        endButton.setLayoutY(370);

        endButton.setPrefWidth(120); // Set your desired width
        endButton.setPrefHeight(50); // Set your desired height
        endButton.setStyle("-fx-background-color: #F2F2F2;");
        endButton.setFont(new Font(12));


        //revive button
        Button reviveButton= new Button("Second Life \n      -5 🍒");
        reviveButton.setLayoutX(200*pillarNo + 80);
        reviveButton.setLayoutY(370);

        reviveButton.setPrefWidth(120); // Set your desired width
        reviveButton.setPrefHeight(50); // Set your desired height
        reviveButton.setStyle("-fx-background-color: #F2F2F2;");
        reviveButton.setFont(new Font(12));

        Group endMenuGroup = new Group();
        endMenuGroup.getChildren().addAll( endMenu, endtext, highScore);

        if (ninja.getPoints() <5){
            reviveButton.setStyle("-fx-background-color: #DEDEDE;");
            Text sad = new Text("Not enough cherries");
            sad.setFill(Color.web("#FF6D71"));
            sad.setX(10);
            sad.setLayoutX(200*pillarNo + 77); // Set the X coordinate of the text
            sad.setLayoutY(432); // Set the Y coordinate of the text
            endMenuGroup.getChildren().add(sad);
        }

        //end button function
        endButton.setOnAction(e -> {
            writeIntArrayToFile();

            stage.close();
        });

        //revive function not working
        reviveButton.setOnAction(e -> {
            if (ninja.getPoints() >5){
                ninja.getRevived();
                GameStart(stage, gameStart, setGroup, root1);
                int screenTranslateX = 200;
                TranslateTransition translateCamera = new TranslateTransition(Duration.seconds(1.5), setGroup);
                translateCamera.setByX(-screenTranslateX);

                setGroup.getChildren().remove(endMenuGroup);
                //translateCamera.play();
                gameEnd = 0;
            }
        });

        endMenuGroup.getChildren().addAll(endButton, reviveButton);
        setGroup.getChildren().add(endMenuGroup);
    }

    private void loadScoreList() {

    }

    public void writeIntArrayToFile() {
        try (FileWriter writer = new FileWriter(new File(System.getProperty("user.dir")+"\\score.txt"));
        //try (FileWriter writer = new FileWriter(new File(getClass().getResource("score.txt").toExternalForm()));
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            for (int num : scoreList) {
                bufferedWriter.write(Integer.toString(num));
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readIntegersFromFile() {

        try (FileReader reader = new FileReader(new File(System.getProperty("user.dir") + "\\score.txt"));
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int num = Integer.parseInt(line.trim());
                scoreList.add(num);
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public int HighestScore(ArrayList<Integer> scoreList) {
        //load the scoreList with the data in the txt file
        readIntegersFromFile();

        if (scoreList == null || scoreList.isEmpty()) {
            return ninja.getPoints();
        }

        int max = scoreList.get(0);

        for (int number : scoreList) {
            if (number > max) {
                max = number;
            }
        }
        return max;
    }



    @Override
    public void handle(ActionEvent actionEvent) {

    }
}