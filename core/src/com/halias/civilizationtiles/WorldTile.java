package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.math.MathUtils.random;

public class WorldTile
{
    int             x;
    int             y;
    char            tileType;
    int             hasTree;
    int             rand;

    public WorldTile(char intTileType, int posX, int posY, int worldX, int worldY)
    {
        hasTree = 0;
        if (intTileType == 'F' && random.nextInt(11) < 8 && posY < worldY - 3)
        {
            System.out.println("posY = " + posY + "worldY = " + worldY);
            hasTree = 1;
        }
        tileType = intTileType;
            x = posX * 16;
            y = posY * 4;
        if ((posY + 1) % 2 == 0)
            x += 8;
    }
    public WorldTile(int posX, int posY)
    {
        x = posX;
        y = posY;
    }

    public int getX()
    {
        return (x);
    }

    public int getY()
    {
        return (y);
    }

    public char getTileType()
    {
        return (tileType);
    }

    public int  getIfTree()
    {
        return (hasTree);
    }

    public void print(SpriteBatch batch, TileTextures TileTextures)
    {
        if (tileType == 'V')
            return ;
        else if (tileType == 'G')
            TileTextures.printGrassSprite(batch, x, y);
        else if (tileType == 'F')
            TileTextures.printForestSprite(batch,x , y);
        else if (tileType == 'S')
            TileTextures.printSandSprite(batch,x , y);
        else if (tileType == 'W')
            TileTextures.printDeepWaterSprite(batch, x, y);
        else if (tileType == 'w')
            TileTextures.printShallowWaterSprite(batch, x, y);
        else if (tileType == 'D')
            TileTextures.printDirtSprite(batch, x, y);
    }
    public void printObject(SpriteBatch batch, String object, TileTextures TileTextures)
    {
        if (object.equals("tree"))
            TileTextures.printTreeSprite(batch, x, y);
    }
}
