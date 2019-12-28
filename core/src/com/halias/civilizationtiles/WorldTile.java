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

    public WorldTile(char intTileType, int posX, int posY, int worldX, int worldY, int worldZ,
                     int offsetX, int offsetY, int zoomView)
    {
        tileType = intTileType;
            x = (posX - offsetX) * (16 + zoomView);
            y = (posY - offsetY) * (4 + zoomView / 4);
        if ((posY + 1) % 2 == 0)
            x += (8 + zoomView / 2);
        if (worldZ > 1)
            y += worldZ;
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

    public void print(SpriteBatch batch, char[][][] worldMap, TileTextures TileTextures,
                      int z, int tabX, int tabY, int zoomView)
    {
        if (tileType == 'V')
            return ;
        else if (tileType == 'G')
            TileTextures.printGrassSprite(batch, worldMap, x, y, z, tabX, tabY, zoomView);
        else if (tileType == 'F')
            TileTextures.printForestSprite(batch, worldMap, x, y, z, tabX, tabY, zoomView);
        else if (tileType == 'S')
            TileTextures.printSandSprite(batch,x , y, zoomView);
        else if (tileType == 'W')
            TileTextures.printDeepWaterSprite(batch, x, y, zoomView);
        else if (tileType == 'w')
            TileTextures.printShallowWaterSprite(batch, x, y, zoomView);
        else if (tileType == 'D')
            TileTextures.printDirtSprite(batch, worldMap, x , y, z, tabX, tabY, zoomView);
    }
    public void printObject(SpriteBatch batch, String object, TileTextures TileTextures, int zoomView)
    {
        if (object.equals("tree"))
            TileTextures.printTreeSprite(batch, x, y, zoomView);
    }
}
