package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ActiveObjectsOnMap
{
    public String name;
    public int    posX;
    public int    posY;
    public int    posZ;
    private int    worldX;
    private int    worldY;
    private int    worldZ;
    private char[][][] tileMap;
    private Sprite player;

    public ActiveObjectsOnMap(String newName, char[][][] worldMap, int x, int y, int z)
    {
        worldX = x;
        worldY = y;
        worldZ = z;
        name = newName;
        tileMap = worldMap;
        posX = 0;
        posY = 0;
        player = new Sprite(new Texture("peon.png"));
        int tmpZ = 0;
        while (tmpZ < z - 1 && worldMap[tmpZ + 1][0][0] != '0')
            tmpZ++;
        posZ = tmpZ;
    }

    public void print(SpriteBatch batch)
    {
        int printX = posX * 16;
        int printY = posY * 4 + posZ * 6 + 6;
        if ((posY + 1) % 2 == 0)
            printX += 8;
        player.setPosition(printX, printY);
        player.draw(batch);
        System.out.println("PLAYER PRINTED");
    }

    public String getName()
    {
        return (name);
    }

    public int getX()
    {
        return (worldX);
    }

    public int getY()
    {
        return (worldY);
    }

    public int getZ()
    {
        return (worldZ);
    }
}

