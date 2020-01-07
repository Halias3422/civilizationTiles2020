package com.halias.civilizationtiles;


/*
    TILES TYPES:
    F - FOREST
    G - GRASS
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.awt.Frame;
import java.io.BufferedReader;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class CharWorldMap
{
    private char[][][] worldMap;
    private int[][]   altitude;
    private TileTextures TileTextures;
    private ObjectTextures ObjectTextures;
    private NatureObjects NatureObjects;
    private Altitude      Altitude;
    private FrameBuffer[]   FB;
    private FrameBuffer[]   finalFB;

    private int x;
    private int y;
    private int height;
    private int mapSquares;
    private int tilesNumber;
    private int voidTiles;
    private int water;
    private int grass;
    private int forest;
    private int dirt;
    private int sand;

    private int highestAltitude;

    public CharWorldMap(int width, int length, int worldZ, float SCREEN_WIDTH, float SCREEN_HEIGHT,
                        SpriteBatch batch)
    {
        x = width;
        y = length;
        height = worldZ;
        mapSquares = length;
        while (mapSquares % 12 != 0)
            mapSquares++;
        mapSquares = mapSquares / 12;
        worldMap = new char[height][y][x];
        System.out.println("world map vars INIT");
        initWorldMap();
        fillGenerationVariables(width, length);
        addNewTerrainToMap(water, 'w', 4, 12);
        System.out.println("water added");
        addNewTerrainToMap(dirt, 'D', 8, 20);
        System.out.println("dirt added");
        addNewTerrainToMap(forest, 'F', 2, 8);
        System.out.println("forest added");
        fillMapWithGrass();
        System.out.println("grass added");
        System.out.println("level 0 FILLED");
        Altitude = new Altitude(x, y, height, worldMap);
        System.out.println("altitude done");
        altitude = Altitude.retreiveIntTab();
        highestAltitude = Altitude.findHighestAltitude();
        System.out.println("HIGHEST ALTITUDE = " + highestAltitude);
        System.out.println("WORLDZ = " + worldZ);
        createAllWorldLevels();
        System.out.println("world map filled with ALL ALTITUDES");
        TileTextures = new TileTextures(length, width);
        ObjectTextures = new ObjectTextures(length, width, height, worldMap);
        NatureObjects = new NatureObjects(worldMap, width, length, worldZ);
        System.out.println("TileTextures and NatureObjects INIT");
    //    debugPrintWorldMap(x, y);
        storeWorldMapIntoFrameBuffer(batch, 0.05F);
        System.out.println("map stored into FRAMEBUFFER");
    }


    private void createAllWorldLevels()
    {
        for (int i = 0; i < y; i++)
          {
            for (int j = 0; j < x; j++)
            {
                for (int currHeight = 1; currHeight <= altitude[i][j]; currHeight++)
                {
                    if (worldMap[currHeight - 1][i][j] != 'w' && worldMap[currHeight - 1][i][j] != 'W')
                        worldMap[currHeight][i][j] = worldMap[currHeight - 1][i][j];
                }
            }
        }
        /*int nbAdded;
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
        }*/
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

        for (int i = 0; i < terrainOriginsNb - 1; i++)
        {
            terrainPoints[i] = random.nextInt((int) Math.round((terrainSize - addedSizes) * 0.90) + 1 -
                    (int) Math.round((terrainSize - addedSizes) * 0.10)) + (int) Math.round((terrainSize - addedSizes) * 0.10);
            addedSizes += terrainPoints[i];
        }
        terrainPoints[terrainOriginsNb - 1] = terrainSize - addedSizes;

        //Positioning Texture on map according to size

        for (int i = 0; i < terrainOriginsNb; i++)
        {
            do
            {
                randX = random.nextInt(x);
                randY = random.nextInt(y);
            } while (worldMap[0][randY][randX] != '0' || worldMap[0][randY][randX] == 'V');
            worldMap[0][randY][randX] = tileType;
           GenerateTerrain = new GenerateTerrain(worldMap[0], tileType, terrainPoints[i], randX, randY, x, y);
           if (GenerateTerrain.checkIfDone() == 1)
               break ;
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
        FB = new FrameBuffer[highestAltitude + 1];
        finalFB = new FrameBuffer[highestAltitude + 1];

        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0,x * 16, y * 5);
        batch.begin();
        batch.setProjectionMatrix(matrix);
        for (int alt = 0; alt <highestAltitude + 1; alt++)
        {
            FB[alt] = new FrameBuffer(Pixmap.Format.RGBA8888, x * 16, y * 5,
                    false, true);
            FB[alt].begin();
            Gdx.gl.glClearColor(1, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            for (int row = y - 1; row > -1; row--)
            {
                for (int tile = 0; tile < x; tile++)
                {
                    if (worldMap[alt][row][tile] != '0' && worldMap[alt][row][tile] != 'V')
                        printTile(batch, worldMap[alt][row][tile], TileTextures, alt, tile, row);
                }
            }
        FB[alt].end();
        FB[alt].getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
        for (int reprint = 0; reprint < highestAltitude + 1; reprint++)
        {
            finalFB[reprint] = new FrameBuffer(Pixmap.Format.RGBA8888, x * 16, y * 5 + reprint * 6,
                    false, true);
            finalFB[reprint].begin();
            matrix.setToOrtho2D(0, 0, x * 16, y * 5 + reprint * 6);
            batch.setProjectionMatrix(matrix);
            Gdx.gl.glClearColor(1, 0,0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            for (int currAlt = 0; currAlt <= reprint; currAlt++)
                batch.draw(FB[currAlt].getColorBufferTexture(), 0, 0, x * 16, y * 5 + reprint * 6, 0, 0, x * 16,
                    y * 5 + reprint * 6, false, true);
            finalFB[reprint].end();
            finalFB[reprint].getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
        batch.end();
        for (int i = 0; i < highestAltitude + 1; i++)
        {
            FB[i].dispose();
        }
    }

    public void printMap(SpriteBatch batch, int SCREEN_WIDTH, int SCREEN_HEIGHT, int altitudeLevel)
    {
            batch.draw(finalFB[altitudeLevel].getColorBufferTexture(), 0, 0, x * 16, y * 5 + altitudeLevel * 6, 0, 0, x * 16,
                    y * 5 + altitudeLevel * 6, false, true);
    }

    int updated = 0;

    private void printTile(SpriteBatch batch, char tileType, TileTextures TileTextures,
                           int z, int posX, int posY)
    {
        WorldTile Current;
        Current = new WorldTile(tileType, posX, posY, x, y, z);
        Current.print(batch, worldMap, TileTextures, z, posX, posY);
        if (z < height - 1 && worldMap[z + 1][posY][posX] != '0')
        {
            Current = new WorldTile('H', posX, posY, x, y, z);
            Current.print(batch, worldMap, TileTextures, x, posX, posY);
        }
    }

    public char[][][] getCharWorldMap()
    {
        return (worldMap);
    }

    public int retreiveHighestAltitude()
    {
        return (highestAltitude);
    }

    public void disposeNecessary()
    {
            for (int i = 0; i < highestAltitude + 1; i++)
            {
                finalFB[i].dispose();
            }
        TileTextures.disposeNecessary();
        ObjectTextures.disposeNecessary();
    }
}
