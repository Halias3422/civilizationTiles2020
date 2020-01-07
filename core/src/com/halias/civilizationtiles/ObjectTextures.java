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

    private Sprite tree1;
    private Sprite peon;

    public ObjectTextures(int y, int x, int z, char[][][] worldMap)
    {
        worldX = x;
        worldY = y;
        worldZ = z;
        tileMap = worldMap;
        tree1 = new Sprite(new Texture("tree1.png"));
        peon = new Sprite(new Texture("peon.png"));
    }

    public void printTree1Sprite(SpriteBatch batch, int x, int y)
    {
        tree1.setPosition(x, y);
        tree1.draw(batch);
    }

    public void printPeonSprite(SpriteBatch batch, int x, int y)
    {
        peon.setPosition(x, y);
        peon.draw(batch);
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
    }*/

    public void disposeNecessary()
    {
        tree1.getTexture().dispose();
    }
}
