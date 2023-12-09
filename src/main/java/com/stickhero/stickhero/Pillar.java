package com.stickhero.stickhero;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.Random;

public class Pillar extends Node {

    private final int Height = 200;
    private final int y = 476 ;
    int width;
    int x;
    private Rectangle pillarRectangle;

    public  Pillar(){
    }

    public Pillar(int x, int width)
    {	//for starting pillar
        this.x =x;
        this.width = width;
    }

    //prevPillar width
//    public Rectangle setRandom(int prevPillar){
//        //randomize width and position
//        int randomWidth = getRandom(120,20);
//        this.width = randomWidth;
//
//        Rectangle pillar = new Rectangle(randomWidth, 200, Color.web("#171717")); // Customize size and color
//        System.out.println("RandomWidth: "+randomWidth+"\nPrevPillarPos: " + prevPillar+"\n");
//        //setPos
//        this.x = getRandom(390,prevPillar);
//
//        //this.x = random.nextInt((404-randomWidth)  - 5 + 1) + prevPillarPos;
//        return pillar;
//    }

    //need gap of 200 from one pillar to another and randomise only the width
    public Rectangle setRandom(int pillarNo){
        //randomize width and position
        int randomWidth = getRandom(100,20);
        this.width = randomWidth;

        Rectangle pillar = new Rectangle(randomWidth, 200, Color.web("#171717")); // Customize size and color
        //System.out.println("RandomWidth: "+randomWidth+"\nPrevPillarPos: " + prevPillar+"\n");
        //setPos
        this.x = pillarNo *200;

        pillar.setX(pillarNo*200);
        pillar.setY(475);
        //this.x = random.nextInt((404-randomWidth)  - 5 + 1) + prevPillarPos;
        return pillar;
    }

    private int getRandom(int max, int min){
        return (int) ((Math.random() * (max - min)) + min);
    }

    public int lengthRequired(ArrayList<Pillar> pillars){
        return pillars.get(pillars.size()).getPos() - pillars.get(pillars.size() -1).getPos();
    }

    public int getWidth()
    {
        return width;
    }

    public int getPos()
    {
        return x;
    }

    public void setPillarRectangle(Rectangle rectangle){
        this.pillarRectangle = rectangle;
    }
    public Rectangle getPillarRectangle(){
        return this.pillarRectangle;
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}