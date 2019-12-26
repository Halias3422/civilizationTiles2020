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
    private char[][] worldMap;

    private int x;
    private int y;
    private int tilesNumber;
    private int voidTiles;
    private int water;
    private int grass;
    private int forest;
    private int dirt;
    private int sand;

    public CharWorldMap(int width, int length)
    {
        x = width;
        y = length;
        worldMap = new char[y][x];
        initWorldMap();
        fillGenerationVariables(width, length);
        addNewTerrainToMap(forest, 'F', 1, 4);
        addNewTerrainToMap(dirt, 'D', 2, 6);
        addNewTerrainToMap(water, 'w', 2, 6);
        fillMapWithGrass();
        for (int i = 0; i < y; i++)
            System.out.println(worldMap[i]);
        System.out.println("INT_WORLD_MAP CREATED");
        debugPrintWorldMap(x, y);
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
        voidTiles = 0;
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                if ((j == x - 1 || i == y - 1 ) && i % 2 != 0)
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
                (int)Math.round(tilesNumber * 0.75)) + (int)Math.round(tilesNumber * 0.75);
        water = tilesNumber - grass;
        forest = random.nextInt((int)Math.round(grass * 0.60) + 1 -
                (int)Math.round(grass * 0.30)) + (int)Math.round(grass * 0.30);
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

        //Deciding WaterPoints number and each respective size

        for (int i = 0; i < terrainOriginsNb - 1; i++)
        {
            terrainPoints[i] = random.nextInt((int) Math.round((terrainSize - addedSizes) * 0.90) + 1 -
                    (int) Math.round((terrainSize - addedSizes) * 0.10)) + (int) Math.round((terrainSize - addedSizes) * 0.10);
            addedSizes += terrainPoints[i];
        }
        terrainPoints[terrainOriginsNb - 1] = terrainSize - addedSizes;
        System.out.println("TOTAL " + tileType + " ATTENDU = " + (addedSizes + terrainPoints[terrainOriginsNb - 1]));

        //Positioning WaterPoints on map according to size

        for (int i = 0; i < terrainOriginsNb; i++)
        {
            do
            {
                randX = random.nextInt(x);
                randY = random.nextInt(y);
            } while (worldMap[randY][randX] != '0' || worldMap[randY][randX] == 'V');
            worldMap[randY][randX] = tileType;
           GenerateTerrain = new GenerateTerrain(worldMap, tileType, terrainPoints[i], randX, randY, x, y);
           System.out.println();
        }
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
                    ((j == 0 || worldMap[i - 1][j - 1] == 'w' || worldMap[i - 1][j - 1] == 'W' || worldMap[i - 1][j - 1] == 'V') &&
                    (worldMap[i - 1][j] == 'w' || worldMap[i - 1][j] == 'W' || worldMap[i - 1][j] == 'V')))
                check++;
            if (i == y - 1 ||
                    ((j == 0 || worldMap[i + 1][j - 1] == 'w' || worldMap[i + 1][j - 1] == 'W' || worldMap[i + 1][j - 1] == 'V') &&
                    (worldMap[i + 1][j] == 'w' || worldMap[i + 1][j] == 'W' || worldMap[i + 1][j] == 'V')))
                check++;
        }
        else
        {
            if (i == 0 ||
                    ((worldMap[i - 1][j] == 'w' || worldMap[i - 1][j] == 'W' || worldMap[i - 1][j] == 'V') &&
                    (j == x - 1 || worldMap[i - 1][j + 1] == 'w' || worldMap[i - 1][j + 1] == 'W' || worldMap[i - 1][j + 1] == 'V')))
                check++;
            if (i == y - 1 ||
                    ((worldMap[i + 1][j] == 'w' || worldMap[i + 1][j] == 'W' || worldMap[i + 1][j] == 'V') &&
                    (j == x - 1 || worldMap[i + 1][j + 1] == 'w' || worldMap[i + 1][j + 1] == 'W' || worldMap[i + 1][j + 1] == 'V')))
                check++;
        }
        if (check == 2)
            return (1);
        return (0);

    }

    public char getTileContent(int x, int y)
    {
        return (worldMap[y][x]);
    }

    private void debugPrintWorldMap(int x, int y)
    {
        /*for (int i = y - 1; i >= 0; i--)
        {
            for (int j = 0; j < x; j++)
                System.out.print(worldMap[i][j] + " ");
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

    public void printMap(SpriteBatch batch, LinkedList<WorldTile> listWorldMap, TileTextures TileTextures)
    {
        printTileType(batch, 'G', listWorldMap, TileTextures);
        printTileType(batch, 'w', listWorldMap, TileTextures);
        printTileType(batch, 'W', listWorldMap, TileTextures);
        printTileType(batch, 'D', listWorldMap, TileTextures);
        printTileType(batch, 'F', listWorldMap, TileTextures);

        printObject(batch, "tree", listWorldMap, TileTextures);
    }

    public void printObject(SpriteBatch batch, String object, LinkedList<WorldTile> listWorldMap,
    TileTextures TileTextures)
    {
        ListIterator<WorldTile> iterator;
        WorldTile Current;
        iterator = listWorldMap.listIterator(listWorldMap.size());
        while (iterator.hasPrevious())
        {
            Current = iterator.previous();
            if (object.equals("tree") && Current.getTileType() == 'F' && Current.getIfTree() == 1)
                Current.printObject(batch, object, TileTextures);
        }


    }

    public void printTileType(SpriteBatch batch, char tileType, LinkedList<WorldTile> listWorldMap,
    TileTextures TileTextures)
    {
        ListIterator<WorldTile> iterator;
        WorldTile Current;
        iterator = listWorldMap.listIterator(listWorldMap.size());
        //iterator = listWorldMap.listIterator(0);
        while (iterator.hasPrevious())
        {
            Current = iterator.previous();
            if (Current.getTileType() == tileType)
                Current.print(batch, TileTextures);
        }
    }
}
