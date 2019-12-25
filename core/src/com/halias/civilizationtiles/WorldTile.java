package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldTile
{
    int             x;
    int             y;
    char            tileType;

    public WorldTile(char intTileType, int posX, int posY)
    {
        tileType = intTileType;
            x = posX * 32;
            y = posY * 8;
        if ((posY + 1) % 2 == 0)
            x += 16;
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
            TileTextures.printShallowWaterSprite(batch, x, y);
        else if (tileType == 'w')
            TileTextures.printDeepWaterSprite(batch, x, y);
        else if (tileType == 'D')
            TileTextures.printDirtSprite(batch, x, y);

    }
}
