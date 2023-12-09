package com.stickhero.stickhero;

import java.util.Objects;

import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;


public class Ninja {

    private static Ninja gen = null;
    private int points = 0;
    private boolean picked = false;
    private int x = 104;
    private final int  y = 440;
    private boolean flipped = false;

    private int currentImageIndex = 0;
    private ImageView imageView;
    private int walkTimer=0;
    private Ninja(){
    }

    public void NinjaAdder(Group setGroup, int setX){

        Image image = new Image(getClass().getResource("/idle/1.png").toExternalForm());
        imageView = new ImageView(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(40);
        imageView.setX(setX);
        imageView.setLayoutY(getY() -5);
        //System.out.printf("ninja x pos: %f\n",imageView.getX());
        setGroup.getChildren().add(imageView);
    }

    public static Ninja getInstance(){
        if (gen == null) {
            gen = new Ninja();
        }
        return gen;
    }

    public int getNinjaX(Group setGroup){
        return (int) imageView.localToScene(imageView.getX(), imageView.getY()).getX();
    }
    public void putIdle(Group setGroup){

        Image image = new Image(getClass().getResource("/idle/1.png").toExternalForm());
        imageView.setImage(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(40);
        imageView.setLayoutY(getY() -5);
    }

    public void getRevived() {
//        System.out.printf("no of pillars behind: %d\n nextPillar Width: %d\n",PillarNo,nextPillarWidth);
//        Image image = new Image(Objects.requireNonNull(getClass().getResource("/idle/1.png")).toExternalForm());
//        imageView.setImage(image);
//        imageView.setFitWidth(25);
//        imageView.setFitHeight(40);
//        int revivedX = (200 * PillarNo) + nextPillarWidth;
//        imageView.setX(revivedX);
//        System.out.println("X: "+ revivedX+"\n");
//        imageView.setLayoutY(getY() -5);

        //deduct 5 points
        points -= 5;
    }

    private static final String[] IMAGE_PATHS = {
            "/walk/1.png",
            "/walk/2.png",
            "/walk/3.png",
            "/walk/4.png",
            "/walk/5.png"
    };

    public AnimationResult move(Group setGroup, int distance, int pillarWidth, ImageView cherryImage, Label points)
    {
        walkTimer = 0;
        this.picked = false;

        imageView.setFitWidth(60);
        imageView.setFitHeight(40);

        javafx.util.Duration fallDuration = javafx.util.Duration.seconds(1.5);
        TranslateTransition fall = new TranslateTransition(fallDuration, imageView);
        fall.setByY(237); // Adjust the translation distance;

        javafx.util.Duration walkDuration = javafx.util.Duration.seconds(2);
        TranslateTransition translateTransition = new TranslateTransition(walkDuration, imageView);
        translateTransition.setByX(distance + pillarWidth); // Adjust the translation distance

        // Create a Timeline with KeyFrames to switch between images
        javafx.util.Duration duration = javafx.util.Duration.seconds(0.1); // Set the duration for each image
        Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(duration, event -> {
                    nextImage();
                    if (flipped){
                        updateFlippedImageView(setGroup, cherryImage, points);
                    }
                    else {
                        updateImageView();
                    }
                    //System.out.println("timer: "+walkTimer+"\n");
                    if (flipped && walkTimer>=18){
                        //translateTransition.stop();
                        fall.play();

                        Media media = new Media(getClass().getResource("/Music/fall.mp3").toExternalForm());
                        MediaPlayer mediaPlayerFall =  new MediaPlayer(media);
                        mediaPlayerFall.play();
                    }

                    fall.setOnFinished(ev -> {
                        fall.stop();
                    });

                    walkTimer += 1;
                })
        );

        timeline.setCycleCount(javafx.animation.Timeline.INDEFINITE); // Repeat the animation indefinitely

        return new AnimationResult(timeline, translateTransition);
    }


    public void moveAndFall(Group setGroup, int StickLength){
        imageView.setFitWidth(60);
        imageView.setFitHeight(40);

        // Create a Timeline with KeyFrames to switch between images
        javafx.util.Duration duration = javafx.util.Duration.seconds(0.1); // Set the duration for each image
        Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(duration, event -> {
                    nextImage();
                    updateImageView();
                })
        );

        timeline.setCycleCount(javafx.animation.Timeline.INDEFINITE); // Repeat the animation indefinitely
        javafx.util.Duration walkDuration = javafx.util.Duration.seconds(2);
        TranslateTransition translateTransition = new TranslateTransition(walkDuration, imageView);
        translateTransition.setByX(StickLength); // Adjust the translation distance

        javafx.util.Duration fallDuration = javafx.util.Duration.seconds(1.5);
        TranslateTransition fall = new TranslateTransition(fallDuration, imageView);
        fall.setByY(237); // Adjust the translation distance;

        ParallelTransition walkingAndSwitching = new ParallelTransition(
                translateTransition,
                timeline
        );

        // Set up event handlers for transitions
        translateTransition.setOnFinished(event -> {
            // Start the falling transition when walking and switching are finished
            walkingAndSwitching.stop();
            fall.play();

            Media media = new Media(getClass().getResource("/Music/fall.mp3").toExternalForm());
            MediaPlayer mediaPlayerFall =  new MediaPlayer(media);
            mediaPlayerFall.play();

        });
        fall.setOnFinished(ev -> {
            fall.stop();
        });

        walkingAndSwitching.play();
    }

    public void setX(int x){  this.x = x;}

    private void nextImage() {
        currentImageIndex = (currentImageIndex + 1) % IMAGE_PATHS.length;
    }

    private void updateImageView() {
        String imagePath = IMAGE_PATHS[currentImageIndex];
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        imageView.setImage(image);
        imageView.setLayoutY(getY() -5);
    }
    private void updateFlippedImageView(Group setGroup, ImageView cherryImage, Label pointsLabel) {
        String imagePath = IMAGE_PATHS[currentImageIndex];
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        WritableImage snapshot = flipImageVertically(image);
        imageView.setImage(snapshot);
        imageView.setLayoutY(getY() +37);

        try {
            boolean picked = checkNinjaCherryIntersection(cherryImage);
            System.out.println("cherry picked: "+ picked +"\n");
            if (picked){
                if (setGroup.getChildren().contains(cherryImage)){
                    points += 1;
                    pointsLabel.setText(String.valueOf(points));
                }
                setGroup.getChildren().remove(cherryImage);
            }
        }
        catch (Exception e){
            System.out.println("cherry not there.\n");
        }
    }

    public boolean checkNinjaCherryIntersection(ImageView cherryImageView){
        // Get the layout bounds of each ImageView
        Bounds bounds1 = imageView.getBoundsInParent();
        Bounds bounds2 = cherryImageView.getBoundsInParent();

        // Check for intersection by comparing bounds
        return bounds1.intersects(bounds2);
    }

    private WritableImage flipImageVertically(Image originalImage) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();

        // Create a WritableImage with the same dimensions and PixelFormat as the original image
        WritableImage flippedImage = new WritableImage(originalImage.getPixelReader(), width, height);

        // Get PixelReader for the original image
        PixelReader pixelReader = originalImage.getPixelReader();

        // Get PixelWriter for the flipped image
        PixelWriter pixelWriter = flippedImage.getPixelWriter();

        // Flip the image vertically
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, height - 1 - y);
                pixelWriter.setColor(x, y, color);
            }
        }
        return flippedImage;
    }

    public class AnimationResult {
        private final Timeline timeline;
        private final TranslateTransition translateTransition;

        public AnimationResult(Timeline timeline, TranslateTransition translateTransition) {
            this.timeline = timeline;
            this.translateTransition = translateTransition;
        }

        public Timeline getTimeline() {
            return timeline;
        }

        public TranslateTransition getTranslateTransition() {
            return translateTransition;
        }
    }

    public void setFlip(){this.flipped = !this.flipped;}
    public int getPoints(){
        return points;
    }

    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
}
