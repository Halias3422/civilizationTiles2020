package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ObjectTextures
{
    int worldX;
    int worldY;
    int worldZ;
    int drawnRectangles;

    Vector2 srcOffset;
    char[][][] tileMap;

    private Sprite tree1FullLeft;
    private Sprite tree1FullRight;
    private Sprite tree1RightHidden1;
    private Sprite tree1RightHidden2;
    private Sprite tree1LeftHidden1;
    private Sprite tree1LeftHidden2;

    public ObjectTextures(int y, int x, int z, char[][][] worldMap)
    {
        worldX = x;
        worldY = y;
        worldZ = z;
        tileMap = worldMap;
        srcOffset = new Vector2();
        tree1FullLeft = new Sprite(new Texture("tree1FullLeft.png"));
        tree1FullLeft.setSize(16, 16);
        tree1FullRight = new Sprite(new Texture("tree1FullRight.png"));
        tree1FullRight.setSize(16, 16);
        tree1LeftHidden1 = new Sprite(new Texture("tree1LeftHidden1.png"));
        tree1LeftHidden1.setSize(16, 16);
        tree1RightHidden1 = new Sprite(new Texture("tree1RightHidden1.png"));
        tree1RightHidden1.setSize(16, 16);
        tree1LeftHidden2 = new Sprite(new Texture("tree1LeftHidden2.png"));
        tree1LeftHidden2.setSize(16, 16);
        tree1RightHidden2 = new Sprite(new Texture("tree1RightHidden2.png"));
        tree1RightHidden2.setSize(16, 16);
    }

    public void printTree1Sprite(SpriteBatch batch, int mapY, int mapX, int x, int y, int z,
                                 int[][] altitude, int altitudeLevel)
    {
        if ((mapY + 1) % 2 != 0)
        {
            if (mapY == 0 || mapX == 0 || altitude[mapY - 1][mapX - 1] <= altitude[mapY][mapX] || altitudeLevel == altitude[mapY][mapX])
            {
                tree1FullLeft.setPosition(x, y);
                tree1FullLeft.draw(batch);
            }
            else if (mapY > 0 && mapX > 0 && altitude[mapY - 1][mapX - 1] == altitude[mapY][mapX] + 1)
            {
                tree1LeftHidden1.setPosition(x, y);
                tree1LeftHidden1.draw(batch);
            }
            else if (mapY > 0 && mapX > 0 && altitude[mapY - 1][mapX - 1] == altitude[mapY][mapX] + 2)
            {
                tree1LeftHidden2.setPosition(x, y);
                tree1LeftHidden2.draw(batch);
            }
            if (mapY == 0 || altitude[mapY - 1][mapX] <= altitude[mapY][mapX] || altitudeLevel == altitude[mapY][mapX])
            {
                tree1FullRight.setPosition(x, y);
                tree1FullRight.draw(batch);
            }
            else if (mapY > 0 && altitude[mapY - 1][mapX] == altitude[mapY][mapX] + 1)
            {
                tree1RightHidden1.setPosition(x, y);
                tree1RightHidden1.draw(batch);
            }
            else if (mapY > 0 && altitude[mapY - 1][mapX] == altitude[mapY][mapX] + 2)
            {
                tree1RightHidden2.setPosition(x, y);
                tree1RightHidden2.draw(batch);
            }

        }
        else
        {
            if (mapY == 0 || altitude[mapY - 1][mapX] <= altitude[mapY][mapX] || altitudeLevel == altitude[mapY][mapX])
            {
                tree1FullLeft.setPosition(x, y);
                tree1FullLeft.draw(batch);
            }
            else if (mapY > 0 && altitude[mapY - 1][mapX] == altitude[mapY][mapX] + 1)
            {
                tree1LeftHidden1.setPosition(x, y);
                tree1LeftHidden1.draw(batch);
            }
            else if (mapY > 0 && altitude[mapY - 1][mapX] == altitude[mapY][mapX] + 2)
            {
                tree1LeftHidden2.setPosition(x, y);
                tree1LeftHidden2.draw(batch);
            }
            if (mapY == 0 || mapX == worldX - 1 || altitude[mapY - 1][mapX + 1] <= altitude[mapY][mapX] ||
             altitudeLevel == altitude[mapY][mapX])
            {
                tree1FullRight.setPosition(x, y);
                tree1FullRight.draw(batch);
            }
            else if (mapY > 0 && mapX < worldX - 1 && altitude[mapY - 1][mapX + 1] == altitude[mapY][mapX] + 1)
            {
                tree1RightHidden1.setPosition(x, y);
                tree1RightHidden1.draw(batch);
            }
            else if (mapY > 0 && mapX < worldX - 1 && altitude[mapY - 1][mapX + 1] == altitude[mapY][mapX] + 2)
            {
                tree1RightHidden2.setPosition(x, y);
                tree1RightHidden2.draw(batch);
            }
        }
    }

   /* public void printTree1Sprite(SpriteBatch batch, int x, int y, int mapX, int mapY)
    {
        drawnRectangles = determineNecessaryDrawnRectangles((int)tree1.getWidth());
        for (int i = 0; i < drawnRectangles; i++)
        {
            srcOffset.y = 0;
            srcOffset.x = tree1.getWidth() / drawnRectangles * i;
            if (i != 0 && i / 2 == 0 && mapX < worldX - 1)
            {
                mapX++;
                x += 16;
            }
            if (mapY > 0 && worldZ > 0 && tileMap[1][mapY - 1][mapX] != '0')
                FindSrcOffset(tree1, x, y, mapX, mapY, drawnRectangles);
            //System.out.println("offset y = " + srcOffset.y + " offsetx = " + srcOffset.x);
           // batch.draw(tree1.getTexture(), (float)x + srcOffset.x, (float)y + srcOffset.y, (int)srcOffset.x, 0,
             //          (int)(tree1.getWidth() - srcOffset.x),
               //        (int)(srcOffset.y));
            if (srcOffset.y > -1)
            {
                if ((i + 1) % 2 != 0)
                   batch.draw(tree1.getTexture(), x, y, 0, (int)srcOffset.y, (int)(tree1.getWidth() / 2), (int)(tree1.getHeight() - srcOffset.y));
                if ((i + 1) % 2 == 0)
                    batch.draw(tree1.getTexture(), (float)(x + srcOffset.x), (float)y, (int)srcOffset.x, 0, (int)(tree1.getWidth() - srcOffset.x), (int)tree1.getHeight());
            }
        }
        //batch.draw(tree1, x, y);
    }

    private void FindSrcOffset(Sprite objectSprite, int x, int y, int mapX, int mapY,
                                  int drawnRectangles)
    {
        int objectStartY = y;
        int objectEndY = y + (int)objectSprite.getHeight();
        int inFrontTilesEnd = 0;

        if (((mapY + 1) % 2 != 0 && (drawnRectangles + 1) % 2 == 0) ||
                ((mapY + 1) % 2 == 0 && (drawnRectangles + 1) % 2 != 0))
        {
            while (inFrontTilesEnd < worldZ && tileMap[inFrontTilesEnd][mapY - 1][mapX] != '0')
                inFrontTilesEnd++;
        }
        else if ((mapY + 1) % 2 != 0 && mapX > 0)
        {
            while (inFrontTilesEnd < worldZ && tileMap[inFrontTilesEnd][mapY - 1][mapX - 1] != '0')
                inFrontTilesEnd++;
        }
        else if ((mapY + 1) % 2 == 0 && mapX < worldX - 1)
        {
            while (inFrontTilesEnd < worldZ && tileMap[inFrontTilesEnd][mapY - 1][mapX + 1] != '0')
                inFrontTilesEnd++;
        }
        inFrontTilesEnd = (mapY - 1) * 4 + inFrontTilesEnd * 3;
        if (inFrontTilesEnd > objectStartY)
            srcOffset.y = inFrontTilesEnd - objectStartY;
        if (srcOffset.y + objectStartY > objectEndY)
            srcOffset.y = -1;
        //check [y - 1][x - 1] && [y - 1][x]
        // if ((mapY + 1) % 2 != 0)
        //      [y - 1][x - 1]
        // else
        //      [y - 1][x + 1]

    }

    private int determineNecessaryDrawnRectangles(int objWidth)
    {
        int count = 0;

        for (int i = objWidth; i > 0; i -= 8)
            count++;
        return (count);
    }*/

    public void disposeNecessary()
    {
        tree1FullLeft.getTexture().dispose();
        tree1FullRight.getTexture().dispose();
        tree1RightHidden1.getTexture().dispose();
        tree1RightHidden2.getTexture().dispose();
        tree1LeftHidden1.getTexture().dispose();
        tree1LeftHidden2.getTexture().dispose();
    }
}
