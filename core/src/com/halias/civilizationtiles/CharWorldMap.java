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
    private char[][] worldMap;
    private TileTextures TileTextures;
    private ObjectTextures ObjectTextures;
    private FrameBuffer   FB;

    private int x;
    private int y;
    private int mapSquares;
    private int tilesNumber;
    private int water;
    private int grass;
    private int forest;
    private int dirt;
    private int voidTiles;
    private int sand;


    public CharWorldMap(int width, int length, float SCREEN_WIDTH, float SCREEN_HEIGHT,
                        SpriteBatch batch)
    {
        x = width;
        y = length;
        voidTiles = 0;
        mapSquares = length;
        while (mapSquares % 12 != 0)
            mapSquares++;
        mapSquares = mapSquares / 12;
        worldMap = new char[y][x];
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
        TileTextures = new TileTextures(length, width);
        ObjectTextures = new ObjectTextures(length, width, worldMap);
    //    debugPrintWorldMap(x, y);
        storeWorldMapIntoFrameBuffer(batch, 0.05F);
        System.out.println("map stored into FRAMEBUFFER");
    }

    private void fillMapWithGrass()
    {
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                if (worldMap[i][j] == '0')
                    worldMap[i][j] = 'G';
            }
        }
    }

    private void initWorldMap()
    {
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                if ((j == x - 1 || i == y - 1) && i % 2 != 0)
                {
                    voidTiles++;
                    worldMap[i][j] = 'V';
                }
                else
                    worldMap[i][j] = '0';
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
            } while (worldMap[randY][randX] != '0' || worldMap[randY][randX] == 'V');
            worldMap[randY][randX] = tileType;
           GenerateTerrain = new GenerateTerrain(worldMap, tileType, terrainPoints[i], randX, randY, x, y);
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
                if (worldMap[i][j] == 'w' && checkIfSurroundedByWater(i, j) == 1)
                    worldMap[i][j] = 'W';
            }
        }
    }

    private int checkIfSurroundedByWater(int i, int j)
    {
        int check = 0;
        if ((i + 1) % 2 != 0)
        {
            if (i == 0 ||
                    ((j == 0 || worldMap[i - 1][j - 1] == 'w' || worldMap[i - 1][j - 1] == 'W'
                            || worldMap[i - 1][j - 1] == 'V') &&
                    (worldMap[i - 1][j] == 'w' || worldMap[i - 1][j] == 'W' ||
                            worldMap[i - 1][j] == 'V')))
                check++;
            if (i == y - 1 ||
                    ((j == 0 || worldMap[i + 1][j - 1] == 'w' || worldMap[i + 1][j - 1] == 'W'
                            || worldMap[i + 1][j - 1] == 'V') &&
                    (worldMap[i + 1][j] == 'w' || worldMap[i + 1][j] == 'W' ||
                            worldMap[i + 1][j] == 'V')))
                check++;
        }
        else
        {
            if (i == 0 ||
                    ((worldMap[i - 1][j] == 'w' || worldMap[i - 1][j] == 'W' ||
                            worldMap[i - 1][j] == 'V') &&
                    (j == x - 1 || worldMap[i - 1][j + 1] == 'w' ||
                            worldMap[i - 1][j + 1] == 'W' || worldMap[i - 1][j + 1] == 'V')))
                check++;
            if (i == y - 1 ||
                    ((worldMap[i + 1][j] == 'w' || worldMap[i + 1][j] == 'W' ||
                            worldMap[i + 1][j] == 'V') &&
                    (j == x - 1 || worldMap[i + 1][j + 1] == 'w' ||
                            worldMap[i + 1][j + 1] == 'W' || worldMap[i + 1][j + 1] == 'V')))
                check++;
        }
        if (check == 2)
            return (1);
        return (0);
    }

    public char getTileContent(int x, int y, int z)
    {
        return (worldMap[y][x]);
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
            Matrix4 matrix = new Matrix4();
            FB = new FrameBuffer(Pixmap.Format.RGBA8888, x * 16, (y * 4 + 12),
                    false, true);
            FB.begin();
            matrix.setToOrtho2D(0, 0,x * 16, (y * 4 + 12));
            batch.begin();
            batch.setProjectionMatrix(matrix);
            Gdx.gl.glClearColor(1, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            for (int row = y - 1; row > -1; row--)
            {
                for (int tile = 0; tile < x; tile++)
                {
                    if (worldMap[row][tile] != '0' && worldMap[row][tile] != 'V')
                        printTile(batch, worldMap[row][tile], TileTextures, tile, row);
                }
            }
            batch.end();
            FB.end();
            FB.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    public void printMap(SpriteBatch batch, int SCREEN_WIDTH, int SCREEN_HEIGHT)
    {
        batch.draw(FB.getColorBufferTexture(), 0, 0, x * 16, (y * 4 + 12), 0, 0, x * 16,
                (y * 4 + 12), false, true);
    }

    int updated = 0;

    private void printTile(SpriteBatch batch, char tileType, TileTextures TileTextures,
                           int posX, int posY)
    {
        WorldTile Current;
        Current = new WorldTile(tileType, posX, posY, x, y);
        Current.print(batch, worldMap, TileTextures, posX, posY);
    }

    public char[][] getCharWorldMap()
    {
        return (worldMap);
    }


    public void disposeNecessary()
    {
        FB.dispose();
        TileTextures.disposeNecessary();
        ObjectTextures.disposeNecessary();
    }
}
