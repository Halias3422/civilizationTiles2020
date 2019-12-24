package com.halias.civilizationtiles;

public class IntWorldMap
{
    private char[][] worldMap;

    private int prev;
    private int x;
    private int y;

    public IntWorldMap(int width, int length)
    {
        x = width;
        y = length;
        worldMap = new char[y][x];
        for (int i = 0; i < y; i++)
        {
            prev = 0;
            if (i % 2 == 0)
                prev = 1;
            for (int j = 0; j < x; j++)
            {
                if (prev == 0)
                    worldMap[i][j] = 'F';
                else if (prev == 1)
                    worldMap[i][j] = 'G';
            }
        }
        System.out.println("INT_WORLD_MAP CREATED");
        debugPrintWorldMap(x, y);
    }

    public char getTileContent(int x, int y)
    {
        return (worldMap[y][x]);
    }

    private void debugPrintWorldMap(int x, int y)
    {
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
                System.out.print(worldMap[i][j] + " ");
            System.out.println();
        }
    }
}
