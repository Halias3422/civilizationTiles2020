package com.halias.civilizationtiles;

import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

import static com.badlogic.gdx.math.MathUtils.random;

public class Altitude
{
    int[][] altitude;
    LinkedList<int[]> posList;

    int     mountainPoints;
    int[]   mountainSize;
    int     totalMountainTiles;
    int     addedSize;
    int     randX;
    int     randY;
    int     nextSpawn[];

    int     worldX;
    int     worldY;
    int     worldZ;
    int     highestAltitude;

    public Altitude(int x, int y, int height, char[][][] worldMap)
    {
        worldX = x;
        worldY = y;
        worldZ = height;
        initAltitudeVars();

        int count = 0;

        for (int i = 0; i < mountainPoints; i++)
        {
            //Set mountain spawn origin
            do
            {
                randX = random.nextInt(x);
                randY = random.nextInt(y);
            } while (altitude[randY][randX] != 0);

            if (height > 1)
                altitude[randY][randX] = random.nextInt(height - (height / 2)) + (height / 2);
            else
                altitude[randY][randX] = 1;
            posList.addLast(new int[] {randX, randY});
            for (int j = 0; j < mountainSize[i]; j++)
            {
                int actualHeight = altitude[randY][randX];
                int maxNextHeight = actualHeight;
                int minNextHeight = actualHeight - 4 < 1 ? 1 : actualHeight - 4;
                addNewAltitudePoint(random.nextInt(maxNextHeight + 1 - minNextHeight) + minNextHeight, worldMap);
                posList.addLast(new int[] {randX, randY});
            }

        }
        LinkedList<int[]> ignore = new LinkedList<int[]>();
        finalAltitudeEgalization(worldMap, ignore);
    }

    public int findHighestAltitude()
    {
        highestAltitude = 0;

        for (int i = 0; i < worldY; i++)
        {
            for (int j = 0; j < worldX; j++)
            {
                if (altitude[i][j] > highestAltitude)
                    highestAltitude = altitude[i][j];
            }
        }
        return (highestAltitude);
    }

    private void finalAltitudeEgalization(char[][][] worldMap, LinkedList<int[]> ignore)
    {
        int check = 0;

        for (int i = 0; i < worldY; i++)
        {
            for (int j = 0; j < worldX; j++)
            {
                if (worldMapValidForAltitude(worldMap, i, j) == 1)
                {
                    int currAlt = (altitude[i][j] * 2 + 1) + (altitude[i][j] / 2 + 1);
                    if ((i + 1) % 2 != 0)
                    {
                        if ((i > 0  && ((j > 0 && currAlt < altitude[i - 1][j - 1]) || currAlt < altitude[i - 1][j]))
                        || (i < worldY - 1 && ( j > 0 && currAlt < altitude[i + 1][j - 1]) ||
                                (i < worldY - 1 && currAlt < altitude[i + 1][j])))
                        {
                            if (random.nextInt(10) < 5)
                                altitude[i][j] = altitude[i][j] * 2 + 1;
                            else
                                altitude[i][j] += 4;
                            check++;
                        }
                    }
                    else
                    {
                        if ((i > 0 && (currAlt < altitude[i - 1][j]) || (j < worldX - 1 && currAlt < altitude[i - 1][j + 1]))
                                || (i < worldY - 1 && (currAlt < altitude[i + 1][j]) || (j < worldX - 1 && currAlt < altitude[i + 1][j + 1])))
                        {
                            if (random.nextInt(10) < 5)
                                altitude[i][j] = altitude[i][j] * 2 + 1;
                            else
                                altitude[i][j] += 4;
                            check++;
                        }
                    }
                }
                if (altitude[i][j] >= worldZ)
                    altitude[i][j] = worldZ - 1;
            }
        }
        if (check > 0)
            finalAltitudeEgalization(worldMap, ignore);
    }

    private void addNewAltitudePoint(int newAltitudePointHeight, char[][][] worldMap)
    {
        int nextDirection;

        for (int i = 0; i < 4; i++)
            nextSpawn[i] = 0;
        findAvailableSpawnPoint(worldMap);
        do
        {
            nextDirection = random.nextInt(4);
        } while (nextSpawn[nextDirection] != 1);
        if ((randY + 1) % 2 != 0)
        {
            if (nextDirection == 0)
                altitude[++randY][--randX] = newAltitudePointHeight;
            else if (nextDirection == 1)
                altitude[--randY][--randX] = newAltitudePointHeight;
        }
        else
        {
            if (nextDirection == 0)
                altitude[++randY][++randX] = newAltitudePointHeight;
            else if (nextDirection == 1)
                altitude[--randY][++randX] = newAltitudePointHeight;
        }
        if (nextDirection == 2)
            altitude[--randY][randX] = newAltitudePointHeight;
        else if (nextDirection == 3)
            altitude[++randY][randX] = newAltitudePointHeight;
    }

    private void findAvailableSpawnPoint(char[][][] worldMap)
    {
        int check = 0;
        int[] lastLink;
        do
        {
            if ((randY + 1) % 2 != 0)
            {
                if (randY + 1 < worldY && randX > 0 && altitude[randY + 1][randX - 1] == 0
                        && worldMapValidForAltitude(worldMap, randY + 1, randX - 1) == 1)
                    nextSpawn[0] = 1;
                if (randY - 1 >= 0 && randX > 0 && altitude[randY - 1][randX - 1] == 0
                    && worldMapValidForAltitude(worldMap, randY - 1, randX - 1) == 1)
                    nextSpawn[1] = 1;
            }
            else
            {
                if (randY + 1 < worldY && randX + 1 < worldX && altitude[randY + 1][randX + 1] == 0
                    && worldMapValidForAltitude(worldMap, randY + 1, randX + 1) == 1)
                    nextSpawn[0] = 1;
                if (randY - 1 >= 0 && randX + 1 < worldX && altitude[randY - 1][randX + 1] == 0 &&
                        worldMapValidForAltitude(worldMap, randY - 1, randX + 1) == 1)
                    nextSpawn[1] = 1;
            }
            if (randY - 1 >= 0 && altitude[randY - 1][randX] == 0
                    && worldMapValidForAltitude(worldMap, randY - 1, randX) == 1)
                nextSpawn[2] = 1;
            if (randY + 1 < worldY && altitude[randY + 1][randX] == 0
                    && worldMapValidForAltitude(worldMap, randY + 1, randX) == 1)
                nextSpawn[3] = 1;


            for (int i = 0; i < 4; i++)
            {
                if (nextSpawn[i] != 0)
                    check++;
            }
            if (check == 0 && posList.size() > 0)
            {
                lastLink = posList.get(posList.size() - 1);
                randX = lastLink[0];
                randY = lastLink[1];
                posList.removeLast();
            }
            else if (check == 0)
                determineNewSpawnPoint();
        } while (check == 0);
    }

    private void determineNewSpawnPoint()
    {
        do
        {
            randX = random.nextInt(worldX);
            randY = random.nextInt(worldY);
        } while (altitude[randY][randX] != 0);
    }

    private int worldMapValidForAltitude(char[][][] worldMap, int y, int x)
    {
        if (worldMap[0][y][x] != 'w' && worldMap[0][y][x] != 'W' && worldMap[0][y][x] != 'V')
            return (1);
        return (0);
    }


    private void initAltitudeVars()
    {
        altitude = new int[worldY][worldX];
        posList = new LinkedList<int[]>();
        mountainPoints = random.nextInt(10 - 4) + 4;
        mountainSize = new int[mountainPoints];
        totalMountainTiles = random.nextInt((int) Math.round((worldX * worldY * 0.6) -
                (int) Math.round(worldX * worldY * 0.4)) + (int) Math.round(worldX * worldY * 0.4));
        addedSize = 0;
        nextSpawn = new int[4];

        for (int i = 0; i < worldY; i++)
        {
            for (int j = 0; j < worldX; j++)
            {
                altitude[i][j] = 0;
            }
        }
        for (int i = 0; i < mountainPoints - 1; i++)
        {
            addedSize = random.nextInt(totalMountainTiles);
            mountainSize[i] = addedSize;
            totalMountainTiles -= addedSize;
            if (totalMountainTiles <= 0)
                break ;
        }
        mountainSize[mountainPoints - 1] = totalMountainTiles;
    }

    public int[][] retreiveIntTab()
    {
        return (altitude);
    }
}
