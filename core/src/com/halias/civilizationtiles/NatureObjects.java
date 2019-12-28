package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.math.MathUtils.random;

/*
        OBJECTS TYPES
        'T' = tree
*/

public class NatureObjects
{
    private int z;
    private int x;
    private int y;
    private char[][][] natureObjects;

    public NatureObjects(char[][][] worldMap, int worldX, int worldY, int worldZ)
    {
       natureObjects = new char[worldZ][worldY][worldX];
       initNatureObjects(worldX, worldY, worldZ);
       for (int i = worldY - 1; i > -1; i--)
       {
           for (int j = 0; j < worldX; j++)
           {
               z = 0;
               while (z < worldZ - 1 && worldMap[z + 1][i][j] != '0' && worldMap[z + 1][i][j] != 'V')
                   z++;
              if (worldMap[z][i][j] == 'F' && random.nextInt(100) < 70)
                  natureObjects[z][i][j] = 'T';

           }
       }
    }

    private void initNatureObjects(int worldX, int worldY, int worldZ)
    {
        for (int z = 0; z < worldZ; z++)
        {
            for (int i = 0; i < worldY; i++)
            {
                for (int j = 0; j < worldX; j++)
                {
                    if ((j == worldX - 1 || i == worldY - 1) && i % 2 != 0)
                        natureObjects[z][i][j] = 'V';
                    else
                        natureObjects[z][i][j] = '0';
                }
            }
        }
    }

    public void printObject(SpriteBatch batch, TileTextures TileTextures, char[][][] worldMap,
                            int posX, int posY, int posZ, int offsetX, int offsetY, int zoomView)
    {
        if (natureObjects[posZ][posY][posX] != '0' && natureObjects[posZ][posY][posX] != 'V')
        {
            x = (posX - offsetX) * (16 + zoomView);
            y = (posY - offsetY) * (4 + zoomView / 4);
            if ((posY + 1) % 2 == 0)
                x += (8 + zoomView / 2);
            if (posZ > 0)
                y += posZ + 4;
            if (worldMap[posZ][posY][posX] != 'F')
                System.out.println("WTF??");
            TileTextures.printTreeSprite(batch, x, y, zoomView);
        }
    }
}
