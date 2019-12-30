package com.halias.civilizationtiles;

import java.util.LinkedList;

import static com.badlogic.gdx.math.MathUtils.random;

public class GenerateTerrain
{
    private int[]   nextSpawn;
    LinkedList<WorldTile> tileList;
    int             nextDirection;
    int             x;
    int             y;
    int             worldX;
    int             worldY;

    public GenerateTerrain(char[][] worldMap, char tileType, int size, int firstX, int firstY, int totalX, int totalY)
    {
        x = firstX;
        y = firstY;
        worldX = totalX;
        worldY = totalY;
        nextSpawn = new int[4];
        tileList = new LinkedList<WorldTile>();
        for (int tilesNb = 0; tilesNb < size; tilesNb++)
        {
           findAvailableDirections(worldMap, tileType);
           do
           {
               nextDirection = random.nextInt(4);
           } while (nextSpawn[nextDirection] != 1);
           if (nextDirection == 0)
               worldMap[++y][x] = tileType;
           else if (nextDirection == 1)
               worldMap[--y][x] = tileType;
           else if (nextDirection == 2)
               worldMap[y][++x] = tileType;
           else if (nextDirection == 3)
               worldMap[y][--x] = tileType;
           tileList.addLast(new WorldTile(x, y));
        }
    }

    private void findAvailableDirections(char[][] worldMap, char tileType)
    {
        int check = 0;

        for (int reset = 0; reset < 4; reset++)
            nextSpawn[reset] = 0;
        do
        {
            if (y + 1 < worldY && worldMap[y + 1][x] == '0')
                nextSpawn[0] = 1;
            if (y - 1 >= 0 && worldMap[y - 1][x] == '0')
                nextSpawn[1] = 1;
            if (x + 1 < worldX && worldMap[y][x + 1] == '0')
                nextSpawn[2] = 1;
            if (x - 1 >= 0 && worldMap[y][x - 1] == '0')
                nextSpawn[3] = 1;
            for (int i = 0; i < 4; i++)
            {
                if (nextSpawn[i] != 0)
                    check++;
            }
            if (check == 0 && tileList.size() > 0)
            {
                x = tileList.get(tileList.size() - 1).getX();
                y = tileList.get(tileList.size() - 1).getY();
                tileList.removeLast();
            }
            else if (check == 0)
            {
                determineNewSpawnPoint(worldMap);
            }
        } while (check == 0);
    }

    private void determineNewSpawnPoint(char[][] worldMap)
    {
        do
        {
            System.out.println("JE PASSE ICIIII");
            x = random.nextInt(worldX);
            y = random.nextInt(worldY);
        } while (worldMap[y][x] != '0');
    }
}
