package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ObjectTextures
{
    int worldX;
    int worldY;
    int worldZ;

    char[][][] tileMap;

    private Sprite tree1Top;
    private Sprite tree1LeftTop;
    private Sprite tree1LeftBottom;
    private Sprite tree1RightTop;
    private Sprite tree1RightBottom;
    private Sprite tree1Bottom;

    private Sprite peon;

    public ObjectTextures(int y, int x, int z, char[][][] worldMap)
    {
        worldX = x;
        worldY = y;
        worldZ = z;
        tileMap = worldMap;
        tree1Top = new Sprite(new Texture("tree1Top.png"));
        tree1LeftTop = new Sprite(new Texture("tree1LeftTop.png"));
        tree1LeftBottom = new Sprite(new Texture("tree1LeftBottom.png"));
        tree1RightTop = new Sprite(new Texture("tree1RightTop.png"));
        tree1RightBottom = new Sprite(new Texture("tree1RightBottom.png"));
        tree1Bottom = new Sprite(new Texture("tree1Bottom.png"));
        peon = new Sprite(new Texture("peon.png"));
    }

    public void printTree1Sprite(SpriteBatch batch, int x, int y, int printTop, int printBottom,
                                 int printLeftTop, int printLeftBottom, int printRightTop,
                                 int printRightBottom)
    {
       if (printTop == 1)
       {
        tree1Top.setPosition(x, y);
        tree1Top.draw(batch);
       }
       if (printBottom == 1)
       {
           tree1Bottom.setPosition(x, y);
           tree1Bottom.draw(batch);
       }
       if (printLeftTop == 1)
       {
           tree1LeftTop.setPosition(x, y);
           tree1LeftTop.draw(batch);
       }
       if (printLeftBottom == 1)
       {
           tree1LeftBottom.setPosition(x, y);
           tree1LeftBottom.draw(batch);
       }
       if (printRightTop == 1)
       {
           tree1RightTop.setPosition(x, y);
           tree1RightTop.draw(batch);
       }
       if (printRightBottom == 1)
       {
           tree1RightBottom.setPosition(x, y);
           tree1RightBottom.draw(batch);
       }
    }

    public void printPeonSprite(SpriteBatch batch, int x, int y)
    {
        peon.setPosition(x, y);
        peon.draw(batch);
    }

    public void disposeNecessary()
    {

        tree1Top.getTexture().dispose();
        tree1LeftTop.getTexture().dispose();
        tree1LeftBottom.getTexture().dispose();
        tree1RightTop.getTexture().dispose();
        tree1RightBottom.getTexture().dispose();
        tree1Bottom.getTexture().dispose();
    }
}
