package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldTile
{
    private Sprite tile;
    int             x;
    int             y;

    public WorldTile(char tileType, int posX, int posY)
    {
        if (tileType == 'G')
            tile = new Sprite(new Texture("grass.png"));
        else if (tileType == 'F')
            tile = new Sprite(new Texture("forest.png"));
        else if (tileType == 'S')
            tile = new Sprite(new Texture("sand.png"));
        else if (tileType == 'W')
            tile = new Sprite(new Texture("shallowWater.png"));
        x = posX * 32;
        y = posY * 8;
        if ((posY + 1) % 2 == 0)
            x += 16;
    }

    public void print(SpriteBatch batch, int width)
    {
        batch.draw(tile, x, y);
    }
}
