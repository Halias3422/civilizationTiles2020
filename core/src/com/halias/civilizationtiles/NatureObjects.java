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
    private String[][][] natureObjects;

    public NatureObjects(char[][][] worldMap, int worldX, int worldY, int worldZ)
    {
       natureObjects = new String[worldZ][worldY][worldX];
       initNatureObjects(worldX, worldY, worldZ);
       for (int i = worldY - 1; i > -1; i--)
       {
           for (int j = 0; j < worldX; j++)
           {
               z = 0;
               while (z < worldZ - 1 && worldMap[z + 1][i][j] != '0' && worldMap[z + 1][i][j] != 'V')
                   z++;
              if (worldMap[z][i][j] == 'F' && random.nextInt(100) < 70)
              {
                  natureObjects[z][i][j] = "tree1";
              }
           }
       }
    }

    private void initNatureObjects(int worldX, int worldY, int worldZ)
    {
        x = worldX;
        y = worldY;
        z = worldZ;
        for (int z = 0; z < worldZ; z++)
        {
            for (int i = 0; i < worldY; i++)
            {
                for (int j = 0; j < worldX; j++)
                {
                    if ((j == worldX - 1 || i == worldY - 1) && i % 2 != 0)
                        natureObjects[z][i][j] = "V";
                    else
                        natureObjects[z][i][j] = "0";
                }
            }
        }
    }

/*    public void printMap(SpriteBatch batch, ObjectTextures ObjectTextures, int[][] altitude,
                         int altitudeLevel)
    {
        int posX;
        int posY;
        for (int alt = 0; alt <= altitudeLevel; alt++)
        {
            for (int row = y - 1; row > -1; row--)
            {
                for (int tile = 0; tile < x; tile++)
                {
                    if (natureObjects[alt][row][tile] != "0")
                    {
                        posX = tile * 16;
                        posY = row * 4 + alt * 6 + 6;
                        if ((row + 1) % 2 == 0)
                            posX += 8;
                        if (natureObjects[alt][row][tile].equals("tree1"))
                            ObjectTextures.printTree1Sprite(batch, row, tile, posX, posY, alt,
                                    altitude, altitudeLevel);
                    }
                }
            }
        }
    }
*/
    public void printRow(SpriteBatch batch, ObjectTextures ObjectTextures, int[][] altitude,
                            int mapY, int mapZ)
    {
        int posX;
        int posY;
        for (int mapX = 0; mapX < x; mapX++)
        {
            if (altitude[mapY][mapX] <= mapZ && natureObjects[altitude[mapY][mapX]][mapY][mapX] != "0")
            {
                posX = mapX * 16;
                posY = mapY * 4 + altitude[mapY][mapX] * 8 + 8;
                if ((mapY + 1) % 2 == 0)
                    posX += 8;
                if (natureObjects[altitude[mapY][mapX]][mapY][mapX] == "tree1")
                 ObjectTextures.printTree1Sprite(batch, posX, posY);
            }
        }
    }

/*    public void printObject(SpriteBatch batch, TileTextures TileTextures, char[][][] worldMap,
                            int posX, int posY, int posZ)
    {
        if (natureObjects[posZ][posY][posX] != '0' && natureObjects[posZ][posY][posX] != 'V')
        {
            x = posX * 16;
            y = posY * 4;
            if ((posY + 1) % 2 == 0)
                x += 8;
            if (posZ > 0)
                y += (posZ * 3) + 8;
            ObjectTextures.printTree1Sprite(batch, x, y);
        }
    }*/
}
