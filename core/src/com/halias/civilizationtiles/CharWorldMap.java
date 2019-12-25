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
    100 - WATER % IS GRASS
    30 - 60 % OF GRASS IS FOREST
    5 - 15 % OF GRASS IS DIRT

*/

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    private int water;
    private int grass;
    private int forest;
    private int dirt;
    private int sand;

    public CharWorldMap(int width, int length)
    {
        fillGenerationVariables(width, length);
        worldMap = new char[y][x];
        initWorldMap();
        addWaterToMap();
        tmpAddGrassToMap();
        System.out.println("INT_WORLD_MAP CREATED");
        debugPrintWorldMap(x, y);
    }

    private void tmpAddGrassToMap()
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
                if ((j == x - 1 || i == y - 1 ) && i % 2 != 0)
                    worldMap[i][j] = 'V';
                else
                    worldMap[i][j] = '0';
            }
        }
    }

    // random.nextInt(max + 1 - min) + min;

    private void fillGenerationVariables(int width, int length)
    {
        x = width;
        y = length;
        tilesNumber = width * length;
        water = random.nextInt((int)Math.round(tilesNumber * 0.25) + 1 -
                (int)Math.round(tilesNumber * 0.10)) + (int)Math.round(tilesNumber * 0.10);
        grass = tilesNumber - water;
        forest = random.nextInt((int)Math.round(grass * 0.60) + 1 -
                (int)Math.round(grass * 0.30)) + (int)Math.round(grass * 0.30);
        grass -= forest;
        dirt = random.nextInt((int)Math.round(grass * 0.15) + 1 -
                (int)Math.round(grass * 0.05)) + (int)Math.round(grass * 0.05);
    }

    private void addWaterToMap()
    {
        int waterOriginsNb = random.nextInt(6 - 2) + 2;
        int[] waterPoints = new int[waterOriginsNb];
        int addedSizes = 0;
        int randY;
        int randX;
        GenerateTerrain GenerateWater;

        //Deciding WaterPoints number and each respective size

        for (int i = 0; i < waterOriginsNb - 1; i++)
        {
            waterPoints[i] = random.nextInt((int) Math.round((water - addedSizes) * 0.90) + 1 -
                    (int) Math.round((water - addedSizes) * 0.10)) + (int) Math.round((water - addedSizes) * 0.10);
            addedSizes += waterPoints[i];
        }
        waterPoints[waterOriginsNb - 1] = water - addedSizes;
        System.out.println("TOTAL WATER ATTENDU = " + (addedSizes + waterPoints[waterOriginsNb - 1]));

        //Positioning WaterPoints on map according to size

        for (int i = 0; i < waterOriginsNb; i++)
        {
            do
            {
                randX = random.nextInt(x);
                randY = random.nextInt(y);
            } while (worldMap[randY][randX] == 'w' || worldMap[randY][randX] == 'V');
            worldMap[randY][randX] = 'w';
           GenerateWater = new GenerateTerrain(worldMap, 'w', waterPoints[i], randX, randY, x, y);
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
        for (int i = y - 1; i >= 0; i--)
        {
            for (int j = 0; j < x; j++)
                System.out.print(worldMap[i][j] + " ");
            System.out.println();
        }
        System.out.println("TOTAL TILES = " + tilesNumber);
        System.out.println("WATER = " + water + " (" + (100 * water) / tilesNumber + "%)");
        System.out.println("GRASS = " + grass + " (" + (100 * grass) / tilesNumber + "%)");
        System.out.println("FOREST = " + forest + " (" + (100 * forest) / tilesNumber + "%)");
        System.out.println("DIRT = " + dirt + " (" + (100 * dirt) / tilesNumber + "%)");
        int countWater = 0;
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                if (worldMap[i][j] == 'w')
                    countWater++;
            }
        }
        System.out.println("THERE ARE " + countWater + " WATER TILES");
    }

    public void printMap(SpriteBatch batch, LinkedList<WorldTile> listWorldMap, TileTextures TileTextures)
    {
        printTileType(batch, 'G', listWorldMap, TileTextures);
        printTileType(batch, 'w', listWorldMap, TileTextures);
        printTileType(batch, 'W', listWorldMap, TileTextures);
    }

    public void printTileType(SpriteBatch batch, char tileType, LinkedList<WorldTile> listWorldMap,
    TileTextures TileTextures)
    {
        ListIterator<WorldTile> iterator;
        WorldTile Current;

        iterator = listWorldMap.listIterator(0);
        while (iterator.hasNext())
        {
            Current = iterator.next();
            if (Current.getTileType() == tileType)
                Current.print(batch, TileTextures);
        }
    }
}
