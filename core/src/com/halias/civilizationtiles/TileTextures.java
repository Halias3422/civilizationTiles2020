package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileTextures
{
   private Sprite grass;
   private Sprite elevatedGrass;
   private Sprite elevatedGrassRight;
   private Sprite elevatedGrassLeft;
   private Sprite elevatedGrassAll;

   private Sprite forest;
   private Sprite elevatedForest;
   private Sprite elevatedForestRight;
   private Sprite elevatedForestLeft;
   private Sprite elevatedForestAll;

   private Sprite tree;
   private Sprite deepWater;
   private Sprite shallowWater;
   private Sprite sand;

   private Sprite dirt;
   private Sprite elevatedDirt;
   private Sprite elevatedDirtRight;
   private Sprite elevatedDirtLeft;
   private Sprite elevatedDirtAll;

   private int    worldY;
   private int    worldX;

    public TileTextures(int y, int x)
    {
        worldX = x;
        worldY = y;
        grass = new Sprite(new Texture("grass.png"));
        grass.setSize(16, 8);
        elevatedGrass = new Sprite(new Texture("elevatedGrass.png"));
        elevatedGrass.setSize(16, 12);
        elevatedGrassRight = new Sprite(new Texture("elevatedGrassTop.png"));
        elevatedGrassRight.setSize(16, 12);
        elevatedGrassLeft = new Sprite(new Texture("elevatedGrassLeft.png"));
        elevatedGrassLeft.setSize(16, 12);
        elevatedGrassAll = new Sprite(new Texture("elevatedGrassAll.png"));
        elevatedGrassAll.setSize(16, 12);

        forest = new Sprite(new Texture("forest.png"));
        forest.setSize(16, 8);
        elevatedForest = new Sprite(new Texture("elevatedForest.png"));
        elevatedForest.setSize(16, 12);
        elevatedForestRight = new Sprite(new Texture("elevatedForestRight.png"));
        elevatedForestRight.setSize(16, 12);
        elevatedForestLeft = new Sprite(new Texture("elevatedForestLeft.png"));
        elevatedForestLeft.setSize(16, 12);
        elevatedForestAll = new Sprite(new Texture("elevatedForestAll.png"));
        elevatedForestAll.setSize(16, 12);

        tree = new Sprite(new Texture("tree.png"));
        tree.setSize(16, 16);

        sand = new Sprite(new Texture("sand.png"));
        sand.setSize(16, 8);

        shallowWater = new Sprite(new Texture("shallowWater.png"));
        shallowWater.setSize(16, 8);

        deepWater = new Sprite(new Texture("DeepWater.png"));
        deepWater.setSize(16, 8);

        dirt = new Sprite(new Texture("dirt.png"));
        dirt.setSize(16, 8);
        elevatedDirt = new Sprite(new Texture("elevatedDirt.png"));
        elevatedDirt.setSize(16, 12);
        elevatedDirtRight = new Sprite(new Texture("elevatedDirtRight.png"));
        elevatedDirtRight.setSize(16, 12);
        elevatedDirtLeft = new Sprite(new Texture("elevatedDirtLeft.png"));
        elevatedDirtLeft.setSize(16, 12);
        elevatedDirtAll = new Sprite(new Texture("elevatedDirtAll.png"));
        elevatedDirtAll.setSize(16, 12);
    }

    public void printGrassSprite(SpriteBatch batch, char[][][] worldMap, int x, int y, int z,
                                 int tabX, int tabY, int zoomView)
    {
        if (z == 0)
        {
            grass.setSize(16 + zoomView, 8 + zoomView / 2);
            grass.setPosition(x, y);
            grass.draw(batch);
        }
        else if (isTopLeft(worldMap, tabX, tabY, z) == 1 && isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedGrassAll.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedGrassAll.setPosition(x, y);
            elevatedGrassAll.draw(batch);
        }
        else if (isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedGrassRight.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedGrassRight.setPosition(x, y);
            elevatedGrassRight.draw(batch);
        }
        else if (isTopLeft(worldMap, tabX, tabY, z) == 1)
        {
            elevatedGrassLeft.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedGrassLeft.setPosition(x, y);
            elevatedGrassLeft.draw(batch);
        }
        else
        {
            elevatedGrass.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedGrass.setPosition(x, y);
            elevatedGrass.draw(batch);
        }
    }

    public void printForestSprite(SpriteBatch batch, char[][][] worldMap, int x, int y, int z,
                                  int tabX, int tabY, int zoomView)
    {
        if (z == 0)
        {
            forest.setSize(16 + zoomView, 8 + zoomView / 2);
            forest.setPosition(x, y);
            forest.draw(batch);
        }
        else if (isTopLeft(worldMap, tabX, tabY, z) == 1 && isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedForestAll.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedForestAll.setPosition(x, y);
            elevatedForestAll.draw(batch);
        }
        else if (isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedForestRight.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedForestRight.setPosition(x, y);
            elevatedForestRight.draw(batch);
        }
        else if (isTopLeft(worldMap, tabX, tabY, z) == 1)
        {
            elevatedForestLeft.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedForestLeft.setPosition(x, y);
            elevatedForestLeft.draw(batch);
        }
        else
        {
            elevatedForest.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedForest.setPosition(x, y);
            elevatedForest.draw(batch);
        }
    }

    public void printTreeSprite(SpriteBatch batch, int x, int y, int zoomView)
    {
        tree.setSize(16 + zoomView, 16 + zoomView);
        tree.setPosition(x, y);
        tree.draw(batch);
    }

    public void printSandSprite(SpriteBatch batch, int x, int y, int zoomView)
    {
        sand.setSize(16 + zoomView, 8 + zoomView / 2);
        sand.setPosition(x, y);
        sand.draw(batch);
    }

    public void printShallowWaterSprite(SpriteBatch batch, int x, int y, int zoomView)
    {
        shallowWater.setSize(16 + zoomView, 8 + zoomView / 2);
        shallowWater.setPosition(x, y);
        shallowWater.draw(batch);
    }

    public void printDeepWaterSprite(SpriteBatch batch, int x, int y, int zoomView)
    {
        deepWater.setSize(16 + zoomView, 8 + zoomView / 2);
        deepWater.setPosition(x, y);
        deepWater.draw(batch);
    }

    public void printDirtSprite(SpriteBatch batch, char[][][] worldMap, int x, int y, int z,
                                  int tabX, int tabY, int zoomView)
    {
        if (z == 0)
        {
            dirt.setSize(16 + zoomView, 8 + zoomView / 2);
            dirt.setPosition(x, y);
            dirt.draw(batch);
        }
        else if (isTopLeft(worldMap, tabX, tabY, z) == 1 && isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedDirtAll.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedDirtAll.setPosition(x, y);
            elevatedDirtAll.draw(batch);
        }
        else if (isTopRight(worldMap, tabX, tabY, z) == 1)
        {
            elevatedDirtRight.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedDirtRight.setPosition(x, y);
            elevatedDirtRight.draw(batch);
        }
        else if (isTopLeft(worldMap, tabX, tabY, z) == 1)
        {
            elevatedDirtLeft.setSize(16 + zoomView, 12 + zoomView / 2);
            elevatedDirtLeft.setPosition(x, y);
            elevatedDirtLeft.draw(batch);
        }
        else
        {
            elevatedDirt.setSize(16 + zoomView, 12 + zoomView / 2);
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
}
