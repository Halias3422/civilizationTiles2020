package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileTextures
{
   private Sprite elevatedGrass;
   private Sprite elevatedGrassRight;
   private Sprite elevatedGrassLeft;
   private Sprite elevatedGrassAll;

   private Sprite elevatedForest;
   private Sprite elevatedForestRight;
   private Sprite elevatedForestLeft;
   private Sprite elevatedForestAll;

   private Sprite deepWater;
   private Sprite shallowWater;

   private Sprite elevatedDirt;
   private Sprite elevatedDirtRight;
   private Sprite elevatedDirtLeft;
   private Sprite elevatedDirtAll;

   private Sprite hiddenZero;

   private int    worldY;
   private int    worldX;

    public TileTextures(int y, int x)
    {
        worldX = x;
        worldY = y;
        elevatedGrass = new Sprite(new Texture("elevatedGrass.png"));
        elevatedGrassRight = new Sprite(new Texture("elevatedGrassTop.png"));
        elevatedGrassLeft = new Sprite(new Texture("elevatedGrassLeft.png"));
        elevatedGrassAll = new Sprite(new Texture("elevatedGrassAll.png"));

        elevatedForest = new Sprite(new Texture("elevatedForest.png"));
        elevatedForestRight = new Sprite(new Texture("elevatedForestRight.png"));
        elevatedForestLeft = new Sprite(new Texture("elevatedForestLeft.png"));
        elevatedForestAll = new Sprite(new Texture("elevatedForestAll.png"));

        shallowWater = new Sprite(new Texture("shallowWater.png"));

        deepWater = new Sprite(new Texture("DeepWater.png"));

        elevatedDirt = new Sprite(new Texture("elevatedDirt.png"));
        elevatedDirtRight = new Sprite(new Texture("elevatedDirtRight.png"));
        elevatedDirtLeft = new Sprite(new Texture("elevatedDirtLeft.png"));
        elevatedDirtAll = new Sprite(new Texture("elevatedDirtAll.png"));

        hiddenZero = new Sprite(new Texture("hiddenZero.png"));
    }

    public void printHiddenTile(SpriteBatch batch, char[][][] worldMap, int x, int y, int z,
                                int tabX, int tabY)
    {
            hiddenZero.setPosition(x, y);
            hiddenZero.draw(batch);
    }

    public void printGrassSprite(SpriteBatch batch, char[][][] worldMap, int x, int y, int z,
                                 int tabX, int tabY)
    {
        if (isTopLeft(worldMap, tabX, tabY, z) == 1 && isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedGrassAll.setPosition(x, y);
            elevatedGrassAll.draw(batch);
        }
        else if (isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedGrassRight.setPosition(x, y);
            elevatedGrassRight.draw(batch);
        }
        else if (isTopLeft(worldMap, tabX, tabY, z) == 1)
        {
            elevatedGrassLeft.setPosition(x, y);
            elevatedGrassLeft.draw(batch);
        }
        else
        {
            elevatedGrass.setPosition(x, y);
            elevatedGrass.draw(batch);
        }
    }

    public void printForestSprite(SpriteBatch batch, char[][][] worldMap, int x, int y, int z,
                                  int tabX, int tabY)
    {
        if (isTopLeft(worldMap, tabX, tabY, z) == 1 && isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedForestAll.setPosition(x, y);
            elevatedForestAll.draw(batch);
        }
        else if (isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedForestRight.setPosition(x, y);
            elevatedForestRight.draw(batch);
        }
        else if (isTopLeft(worldMap, tabX, tabY, z) == 1)
        {
            elevatedForestLeft.setPosition(x, y);
            elevatedForestLeft.draw(batch);
        }
        else
        {
            elevatedForest.setPosition(x, y);
            elevatedForest.draw(batch);
        }
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

    public void printDirtSprite(SpriteBatch batch, char[][][] worldMap, int x, int y, int z,
                                  int tabX, int tabY)
    {
        if (isTopLeft(worldMap, tabX, tabY, z) == 1 && isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedDirtAll.setPosition(x, y);
            elevatedDirtAll.draw(batch);
        }
        else if (isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedDirtRight.setPosition(x, y);
            elevatedDirtRight.draw(batch);
        }
        else if (isTopLeft(worldMap, tabX, tabY, z) == 1)
        {
            elevatedDirtLeft.setPosition(x, y);
            elevatedDirtLeft.draw(batch);
        }
        else
        {
            elevatedDirt.setPosition(x, y);
            elevatedDirt.draw(batch);
        }
    }

    private int isTopLeft(char[][][] worldMap, int tabX, int tabY, int z)
    {
        if ((tabY + 1) % 2 != 0)
        {
            if (tabY == worldY - 1
                    || (tabX == 0 || worldMap[z][tabY + 1][tabX - 1] == '0'))
                return (1);
        }
        else
        {
            if (tabY == worldY - 1
                || worldMap[z][tabY + 1][tabX] == '0')
                return (1);
        }
        return (0);
    }

    private int isTopRight(char[][][] worldMap, int tabX, int tabY, int z)
    {
        if ((tabY + 1) % 2 != 0)
        {
            if (tabY == worldY - 1
                || worldMap[z][tabY + 1][tabX] == '0')
                return (1);
        }
        else
        {
            if (tabY == worldY - 1
                || (tabX == worldX - 1 || worldMap[z][tabY + 1][tabX + 1] == '0'))
                return (1);
        }
        return (0);
    }

    public void disposeNecessary()
    {
        elevatedGrass.getTexture().dispose();
        elevatedGrassRight.getTexture().dispose();
        elevatedGrassLeft.getTexture().dispose();
        elevatedGrassAll.getTexture().dispose();
        elevatedForest.getTexture().dispose();
        elevatedForestRight.getTexture().dispose();
        elevatedForestLeft.getTexture().dispose();
        elevatedForestAll.getTexture().dispose();
        deepWater.getTexture().dispose();
        shallowWater.getTexture().dispose();
        elevatedDirt.getTexture().dispose();
        elevatedDirtRight.getTexture().dispose();
        elevatedDirtLeft.getTexture().dispose();
        elevatedDirtAll.getTexture().dispose();
        hiddenZero.getTexture().dispose();
    }
}
