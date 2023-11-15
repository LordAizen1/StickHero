package com.stickhero.stickhero;

public class Pillar {

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
}