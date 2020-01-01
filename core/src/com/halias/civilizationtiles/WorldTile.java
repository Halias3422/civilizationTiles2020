package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class WorldTile
{
    int             x;
    int             y;
    char            tileType;

    public WorldTile(char intTileType, int posX, int posY, int worldX, int worldY, int worldZ)
    {
        tileType = intTileType;
        x = posX * 16;
        y = posY * 4 + worldZ * 6;
        if (tileType == 'H')
            y += 6;
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

    public void print(SpriteBatch batch, char[][][] worldMap, TileTextures TileTextures,
                      int z, int tabX, int tabY)
    {
        if (tileType == 'V')
            return ;
        else if (tileType == 'G')
            TileTextures.printGrassSprite(batch, worldMap, x, y, z, tabX, tabY);
        else if (tileType == 'F')
            TileTextures.printForestSprite(batch, worldMap, x, y, z, tabX, tabY);
        else if (tileType == 'W')
            TileTextures.printDeepWaterSprite(batch, x, y);
        else if (tileType == 'w')
            TileTextures.printShallowWaterSprite(batch, x, y);
        else if (tileType == 'D')
            TileTextures.printDirtSprite(batch, worldMap, x , y, z, tabX, tabY);
        else if (tileType == 'H')
            TileTextures.printHiddenTile(batch, worldMap, x, y, z, tabX, tabY);
    }
}
