package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileTextures
{
   private Sprite grass;
   private Sprite forest;
   private Sprite deepWater;
   private Sprite shallowWater;
   private Sprite sand;
   private Sprite dirt;

    public TileTextures()
    {
        grass = new Sprite(new Texture("grass.png"));
        grass.setSize(16, 8);
        forest = new Sprite(new Texture("forest.png"));
        forest.setSize(16, 8);
        sand = new Sprite(new Texture("sand.png"));
        sand.setSize(16, 8);
        shallowWater = new Sprite(new Texture("shallowWater.png"));
        shallowWater.setSize(16, 8);
        deepWater = new Sprite(new Texture("DeepWater.png"));
        deepWater.setSize(16, 8);
        dirt = new Sprite(new Texture("dirt.png"));
        dirt.setSize(16, 8);
    }

    public void printGrassSprite(SpriteBatch batch, int x, int y)
    {
        grass.setPosition(x, y);
        grass.draw(batch);
    }

    public void printForestSprite(SpriteBatch batch, int x, int y)
    {
        forest.setPosition(x, y);
        forest.draw(batch);
    }

    public void printSandSprite(SpriteBatch batch, int x, int y)
    {
        sand.setPosition(x, y);
        sand.draw(batch);
    }

    public void printShallowWaterSprite(SpriteBatch batch, int x, int y)
    {
        shallowWater.setPosition(x, y);
        shallowWater.draw(batch);
    }

    public void printDeepWaterSprite(SpriteBatch batch, int x, int y)
    {
        deepWater.setPosition(x, y);
        deepWater.draw(batch);
    }

    public void printDirtSprite(SpriteBatch batch, int x, int y)
    {
        dirt.setPosition(x, y);
        dirt.draw(batch);
    }
}
