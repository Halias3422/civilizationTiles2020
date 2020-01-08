package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ObjectTextures
{
    int worldX;
    int worldY;

    char[][] tileMap;

    private Sprite tree1;
    private Sprite tree2;
    private Sprite tree3;

    private Sprite bush1;
    private Sprite bush2;

    private Sprite rock1;
    private Sprite rock2;

    private Sprite peon;

    public ObjectTextures(int y, int x, char[][] worldMap)
    {
        worldX = x;
        worldY = y;
        tileMap = worldMap;
        tree1 = new Sprite(new Texture("tree1.png"));
        tree2 = new Sprite(new Texture("tree2.png"));
        tree3 = new Sprite(new Texture("tree3.png"));
        bush1 = new Sprite(new Texture("bush1.png"));
        bush2 = new Sprite(new Texture("bush2.png"));
        rock1 = new Sprite(new Texture("rock1.png"));
        rock2 = new Sprite(new Texture("rock2.png"));
        peon = new Sprite(new Texture("peon.png"));
    }

    public void printTree1Sprite(SpriteBatch batch, int x, int y)
    {
        tree1.setPosition(x, y);
        tree1.draw(batch);
    }

    public void printTree2Sprite(SpriteBatch batch, int x, int y)
    {
        tree2.setPosition(x, y);
        tree2.draw(batch);
    }

    public void printTree3Sprite(SpriteBatch batch, int x, int y)
    {
        tree3.setPosition(x, y);
        tree3.draw(batch);
    }

    public void printBush1Sprite(SpriteBatch batch, int x, int y)
    {
        bush1.setPosition(x, y);
        bush1.draw(batch);
    }

    public void printBush2Sprite(SpriteBatch batch, int x, int y)
    {
        bush2.setPosition(x, y);
        bush2.draw(batch);
    }

    public void printRock1Sprite(SpriteBatch batch, int x, int y)
    {
        rock1.setPosition(x, y);
        rock1.draw(batch);
    }

    public void printRock2Sprite(SpriteBatch batch, int x, int y)
    {
        rock2.setPosition(x, y);
        rock2.draw(batch);
    }

    public void printPeonSprite(SpriteBatch batch, int x, int y)
    {
        peon.setPosition(x, y);
        peon.draw(batch);
    }

    public void disposeNecessary()
    {

        tree1.getTexture().dispose();
        peon.getTexture().dispose();
    }
}
