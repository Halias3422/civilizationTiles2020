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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class CharWorldMap
{
    private char[][][] worldMap;

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

    public CharWorldMap(int width, int length, int worldZ)
    {
        x = width;
        y = length;
        height = worldZ;
        worldMap = new char[height][y][x];
        initWorldMap();
        fillGenerationVariables(width, length);
        addNewTerrainToMap(forest, 'F', 1, 4);
        addNewTerrainToMap(dirt, 'D', 2, 6);
        addNewTerrainToMap(water, 'w', 2, 6);
        fillMapWithGrass();
        createAllWorldLevels();
        debugPrintWorldMap(x, y);
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
            System.out.println("z = " + z + "percentchance = " + percentChance + "nb added = " + nbAdded);
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
                (int)Math.round(tilesNumber * 0.75)) + (int)Math.round(tilesNumber * 0.75);
        water = tilesNumber - grass;
        forest = random.nextInt((int)Math.round(grass * 0.70) + 1 -
                (int)Math.round(grass * 0.40)) + (int)Math.round(grass * 0.40);
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

    public void printMap(SpriteBatch batch, NatureObjects NatureObjects, TileTextures TileTextures,
                         int offsetX, int offsetY, int zoomView)
    {
        for (int i = y - 1; i > - 1; i--)
        {
            for (int j = 0; j < x; j++)
            {
                for (int z = 0; z < height
                        && worldMap[z][i][j] != '0' && worldMap[z][i][j] != 'V'; z++)
                {
                    printTile(batch, worldMap[z][i][j], TileTextures, z, j, i, offsetX, offsetY, zoomView);
                    if (z < height - 1 && worldMap[z + 1][i][j] == '0')
                        NatureObjects.printObject(batch, TileTextures, worldMap, j, i, z, offsetX,
                                offsetY, zoomView);
                }
            }
        }
    }

    private void printTile(SpriteBatch batch, char tileType, TileTextures TileTextures,
                           int z, int posX, int posY, int offsetX, int offsetY, int zoomView)
    {
       WorldTile Current = new WorldTile(tileType, posX, posY, x, y, z, offsetX, offsetY, zoomView);
       Current.print(batch, worldMap, TileTextures, z, posX, posY, zoomView);
    }

    public char[][][] getCharWorldMap()
    {
        return (worldMap);
    }
}
