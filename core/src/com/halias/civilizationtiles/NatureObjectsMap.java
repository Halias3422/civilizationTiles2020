package com.halias.civilizationtiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;

import static com.badlogic.gdx.math.MathUtils.random;

/*
        OBJECTS TYPES
        'T' = tree
*/

public class NatureObjectsMap
{
    private int z;
    private int x;
    private int y;
    private NatureObject[][][] NatureObjectsMap;
    private FrameBuffer[] FB;
    private ObjectTextures ObjectTextures;

    public NatureObjectsMap(char[][][] worldMap, int worldX, int worldY, int worldZ,
                            int highestAltitude, int[][]altitude, SpriteBatch batch)
    {
       int alt;

       ObjectTextures = new ObjectTextures(worldY, worldX, worldZ, worldMap);
       x = worldX;
       y = worldY;
       z = highestAltitude;
       NatureObjectsMap = new NatureObject[worldZ][worldY][worldX];
       initNatureObjects(worldX, worldY, worldZ);
       for (int i = worldY - 1; i > -1; i--)
       {
           for (int j = 0; j < worldX; j++)
           {
               alt = 0;
               while (alt < worldZ - 1 && worldMap[alt + 1][i][j] != '0' && worldMap[alt + 1][i][j] != 'V')
                   alt++;
              if (worldMap[alt][i][j] == 'F' && random.nextInt(100) < 70)
              {
                  NatureObjectsMap[alt][i][j].addObject("tree1", worldMap, highestAltitude,
                          altitude);
              }
           }
       }
       storeMapIntoFrameBuffer(highestAltitude, batch);
    }

    private void storeMapIntoFrameBuffer(int highestAltitude, SpriteBatch batch)
    {
        FB = new FrameBuffer[highestAltitude + 1];
        Matrix4 matrix = new Matrix4();

        for (int alt = 0; alt < highestAltitude + 1; alt++)
        {
            FB[alt] = new FrameBuffer(Pixmap.Format.RGBA8888, x * 16,
                    (y * 4 + 12) + alt * 8 + 8, false, true);
            FB[alt].begin();
            matrix.setToOrtho2D(0, 0, x * 16, (y * 4 + 12) + alt * 8 + 8);
            batch.begin();
            batch.setProjectionMatrix(matrix);
            Gdx.gl.glClearColor(1, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            for (int prevAlt = 0; prevAlt < alt; prevAlt++)
            {
                for (int row = y - 1; row > -1; row--)
                {
                    System.out.println("prevAlt = " + prevAlt + " row = " + row);
                    for (int tile = 0; tile < x; tile++)
                    {
                        if (!(NatureObjectsMap[prevAlt][row][tile].name.equals("empty")) &&
                                !(NatureObjectsMap[prevAlt][row][tile].name.equals("void")))
                            NatureObjectsMap[prevAlt][row][tile].printObject(batch, ObjectTextures, prevAlt);
                    }
                }
            }
            batch.end();
            FB[alt].end();
            FB[alt].getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
    }

    public void printMap(SpriteBatch batch, int altitudeLevel)
    {
        batch.draw(FB[altitudeLevel].getColorBufferTexture(), 0, 0, x * 16, (y * 4 + 12) + altitudeLevel * 8 + 8,
        0, 0, x * 16, (y * 4 + 12) + altitudeLevel * 8 + 8, false, true);
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
                        NatureObjectsMap[z][i][j] = new NatureObject(x, y, j, i, z, "void");
                    else
                        NatureObjectsMap[z][i][j] = new NatureObject(x, y, j, i, z, "empty");
                }
            }
        }
    }
}
