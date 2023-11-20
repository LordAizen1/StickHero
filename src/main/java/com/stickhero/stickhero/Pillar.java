package com.stickhero.stickhero;

import javafx.scene.Node;

public class Pillar extends Node {

    private final int Height = 230;

    int width;

    int x;

    public Pillar()
    {	//randomize width and position
    }

    public void addPosition(int x)
    {
        x+=x;
    }

    public int getWidth()
    {
        return width;
    }

    public int getPos()
    {
        return x;
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }
}