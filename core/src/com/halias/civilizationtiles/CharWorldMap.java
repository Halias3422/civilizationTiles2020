package com.halias.civilizationtiles;


/*
    TILES TYPES:
    F - FOREST
    G - GRASS
    S - SAND
    w - SHALLOW WATER
    W - SEA WATER
    V - VOID TILE

    WORLD CREATION RULES:
    10 - 25 % IS WATER
    75 - 90 % IS GRASS
    30 - 60 % OF GRASS IS FOREST
    5 - 15 % OF GRASS IS DIRT

*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.World;

import java.io.BufferedReader;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class CharWorldMap
{
    private char[][][] worldMap;
    private TileTextures TileTextures;
    private NatureObjects NatureObjects;
    private FrameBuffer   FB;
    private Texture       BufferedMap;

    private int x;
    private int y;
    private int height;
    private int tilesNumber;
    private int voidTiles;
    private int water;
    private int grass;
    private int forest;
    private int dirt;
    private int sand;

    public CharWorldMap(int width, int length, int worldZ, float SCREEN_WIDTH, float SCREEN_HEIGHT,
                        SpriteBatch batch)
    {
        FB = new FrameBuffer(Pixmap.Format.RGBA8888, width * 16 + 8,
                length * 8 + 8, false);
        x = width;
        y = length;
        height = worldZ;
        worldMap = new char[height][y][x];
        initWorldMap();
        fillGenerationVariables(width, length);
        addNewTerrainToMap(water, 'w', 4, 12);
        addNewTerrainToMap(forest, 'F', 2, 8);
        addNewTerrainToMap(dirt, 'D', 4, 12);
        fillMapWithGrass();
        createAllWorldLevels();
        TileTextures = new TileTextures(length, width);
        NatureObjects = new NatureObjects(worldMap, width, length, worldZ);
        debugPrintWorldMap(x, y);
        storeWorldMapIntoFrameBuffer(batch, 0.05F);
    }


    private void createAllWorldLevels()
    {
        int nbAdded;
        int percentChance = 30;
        for (int z = 1; z < height; z++)
        {
            nbAdded = 0;
            for (int i = 0; i < y; i++)
            {
                for (int j = 0; j < x; j++)
                {
                    if ((worldMap[z - 1][i][j] == 'G' || worldMap[z - 1][i][j] == 'F' ||
                            worldMap[z - 1][i][j] == 'D') && random.nextInt(100) < percentChance)
                    {
                        worldMap[z][i][j] = worldMap[z - 1][i][j];
                        nbAdded++;
                    }
                }
            }
            if (percentChance < 70)
                percentChance += 5;
        }
    }

    private void fillMapWithGrass()
    {
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                if (worldMap[0][i][j] == '0')
                    worldMap[0][i][j] = 'G';
            }
        }
    }

    private void initWorldMap()
    {
        voidTiles = 0;
        for (int z = 0; z < height; z++)
        {
            for (int i = 0; i < y; i++)
            {
                for (int j = 0; j < x; j++)
                {
                    if ((j == x - 1 || i == y - 1) && i % 2 != 0)
                    {
                        if (z == 0)
                            voidTiles++;
                        worldMap[z][i][j] = 'V';
                    } else
                        worldMap[z][i][j] = '0';
                }
            }
        }

    }

    // random.nextInt(max + 1 - min) + min;

    private void fillGenerationVariables(int width, int length)
    {
        tilesNumber = (width * length) - voidTiles;
        grass = random.nextInt((int)Math.round(tilesNumber * 0.9) + 1 -
                (int)Math.round(tilesNumber * 0.9)) + (int)Math.round(tilesNumber * 0.9);
        water = tilesNumber - grass;
        forest = random.nextInt((int)Math.round(grass * 0.80) + 1 -
                (int)Math.round(grass * 0.60)) + (int)Math.round(grass * 0.60);
        dirt = random.nextInt((int)Math.round(grass * 0.10) + 1 -
                (int)Math.round(grass * 0.02)) + (int)Math.round(grass * 0.02);
        grass -= forest + dirt;
        debugPrintWorldMap(x, y);
    }

    private void addNewTerrainToMap(int terrainSize, char tileType, int originMin, int originMax)
    {
        int terrainOriginsNb = random.nextInt(originMax - originMin) + originMin;
        int[] terrainPoints = new int[terrainOriginsNb];
        int addedSizes = 0;
        int randY;
        int randX;
        GenerateTerrain GenerateTerrain;

        //Deciding Texture Origin Points number and each respective size

        for (int i = 0; i < terrainOriginsNb; i++)
        {
            terrainPoints[i] = random.nextInt((int) Math.round((terrainSize - addedSizes) * 0.90) + 1 -
                    (int) Math.round((terrainSize - addedSizes) * 0.10)) + (int) Math.round((terrainSize - addedSizes) * 0.10);
            addedSizes += terrainPoints[i];
            System.out.println("terrain[ " + i + "] = " + terrainPoints[i]);
        }
        terrainPoints[terrainOriginsNb - 1] = terrainSize - addedSizes;

        //Positioning Texture on map according to size

        System.out.println("OriginNB of " + tileType + " = " + terrainOriginsNb);
        for (int i = 0; i < terrainOriginsNb; i++)
        {
            do
            {
                randX = random.nextInt(x);
                randY = random.nextInt(y);
            } while (worldMap[0][randY][randX] != '0' || worldMap[0][randY][randX] == 'V');
            worldMap[0][randY][randX] = tileType;
           GenerateTerrain = new GenerateTerrain(worldMap[0], tileType, terrainPoints[i], randX, randY, x, y);
        }
        if (tileType == 'w')
        addDeepWater();
    }

    private void addDeepWater()
    {
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                if (worldMap[0][i][j] == 'w' && checkIfSurroundedByWater(i, j) == 1)
                    worldMap[0][i][j] = 'W';
            }
        }
    }

    private int checkIfSurroundedByWater(int i, int j)
    {
        int check = 0;
        if ((i + 1) % 2 != 0)
        {
            if (i == 0 ||
                    ((j == 0 || worldMap[0][i - 1][j - 1] == 'w' || worldMap[0][i - 1][j - 1] == 'W'
                            || worldMap[0][i - 1][j - 1] == 'V') &&
                    (worldMap[0][i - 1][j] == 'w' || worldMap[0][i - 1][j] == 'W' ||
                            worldMap[0][i - 1][j] == 'V')))
                check++;
            if (i == y - 1 ||
                    ((j == 0 || worldMap[0][i + 1][j - 1] == 'w' || worldMap[0][i + 1][j - 1] == 'W'
                            || worldMap[0][i + 1][j - 1] == 'V') &&
                    (worldMap[0][i + 1][j] == 'w' || worldMap[0][i + 1][j] == 'W' ||
                            worldMap[0][i + 1][j] == 'V')))
                check++;
        }
        else
        {
            if (i == 0 ||
                    ((worldMap[0][i - 1][j] == 'w' || worldMap[0][i - 1][j] == 'W' ||
                            worldMap[0][i - 1][j] == 'V') &&
                    (j == x - 1 || worldMap[0][i - 1][j + 1] == 'w' ||
                            worldMap[0][i - 1][j + 1] == 'W' || worldMap[0][i - 1][j + 1] == 'V')))
                check++;
            if (i == y - 1 ||
                    ((worldMap[0][i + 1][j] == 'w' || worldMap[0][i + 1][j] == 'W' ||
                            worldMap[0][i + 1][j] == 'V') &&
                    (j == x - 1 || worldMap[0][i + 1][j + 1] == 'w' ||
                            worldMap[0][i + 1][j + 1] == 'W' || worldMap[0][i + 1][j + 1] == 'V')))
                check++;
        }
        if (check == 2)
            return (1);
        return (0);
    }

    public char getTileContent(int x, int y, int z)
    {
        return (worldMap[z][y][x]);
    }

    private void debugPrintWorldMap(int x, int y)
    {
        /*for (int i = y - 1; i >= 0; i--)
        {
            for (int j = 0; j < x; j++)
                System.out.print(worldMap[0][i][j] + " ");
            System.out.println();
        }*/
        System.out.println("TOTAL TILES = " + tilesNumber);
        System.out.println("WATER = " + water + " (" + (100 * water) / tilesNumber + "%)");
        System.out.println("GRASS = " + grass + " (" + (100 * grass) / tilesNumber + "%)");
        System.out.println("FOREST = " + forest + " (" + (100 * forest) / tilesNumber + "%)");
        System.out.println("DIRT = " + dirt + " (" + (100 * dirt) / tilesNumber + "%)");
        int countWater = 0;
        /*for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                if (worldMap[i][j] == 'w')
                    countWater++;
            }
        }*/
        //System.out.println("THERE ARE " + countWater + " WATER TILES");
    }

    public void storeWorldMapIntoFrameBuffer(SpriteBatch batch, float zoomView)
    {
  //      FB.dispose();
    //    FB = new FrameBuffer(Pixmap.Format.RGBA8888, Math.round((x * 16 + 8) * zoomView),
      //          Math.round((y * 8 + 8) * zoomView), false);
        FB.begin();
        batch.begin();
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, x * 16 + 8, y * 8 + 8);
        batch.setProjectionMatrix(matrix);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (int i = y - 1; i > - 1; i--)
        {
            for (int j = 0; j < x; j++)
            {
                for (int z = 0; z < height
                        && worldMap[z][i][j] != '0' && worldMap[z][i][j] != 'V'; z++)
                {
                    printTile(batch, worldMap[z][i][j], TileTextures, z, j, i);
                    if (z < height - 1 && worldMap[z + 1][i][j] == '0')
                        NatureObjects.printObject(batch, TileTextures, worldMap, j, i, z);
                }
            }
        }
        batch.end();
        FB.end();
        BufferedMap = FB.getColorBufferTexture();
        BufferedMap.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    public void printMap(SpriteBatch batch, int SCREEN_WIDTH, int SCREEN_HEIGHT)
    {
        batch.draw(BufferedMap, 0, 0, Math.round((x * 16 + 8)), Math.round((y * 8 + 8)),
                0, 0, x * 16 + 8, y * 8 + 8, false, true);
    }

    private void printTile(SpriteBatch batch, char tileType, TileTextures TileTextures,
                           int z, int posX, int posY)
    {
       WorldTile Current = new WorldTile(tileType, posX, posY, x, y, z);
       Current.print(batch, worldMap, TileTextures, z, posX, posY);
    }

    public char[][][] getCharWorldMap()
    {
        return (worldMap);
    }
}
