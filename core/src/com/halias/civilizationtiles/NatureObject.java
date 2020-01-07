package com.halias.civilizationtiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NatureObject {

    private int x;
    private int y;
    private int z;
    private int mapX;
    private int mapY;
    private int worldX;
    private int worldY;

    public String name;
    public int[] printTop;
    public int[] printBottom;
    public int[] printLeftTop;
    public int[] printLeftBottom;
    public int[] printRightTop;
    public int[] printRightBottom;

    public NatureObject(int totalX, int totalY, int posX, int posY, int posZ, String newName)
    {
          x = posX;
          y = posY;
          z = posZ;
          mapX = x * 16;
          mapY = y * 4 + z * 8 + 3;
          if ((y + 1) % 2 == 0)
              mapX += 8;
          name = newName;
    }

    public void addObject(String newName, char[][][] worldMap, int highestAltitude, int[][] altitude)
    {
        printTop = new int[highestAltitude + 1];
        printBottom = new int[highestAltitude + 1];
        printLeftTop = new int[highestAltitude + 1];
        printLeftBottom = new int[highestAltitude + 1];
        printRightTop = new int[highestAltitude + 1];
        printRightBottom = new int[highestAltitude + 1];

        name = newName;
        for (int alt = 0; alt < highestAltitude + 1; alt++)
        {
            printTop[alt] = 1;
            printBottom[alt] = 1;
            printLeftTop[alt] = 1;
            printLeftBottom[alt] = 1;
            printRightTop[alt] = 1;
            printRightBottom[alt] = 1;
            checkIfCenterIsPrintable(altitude, alt);
            if (printTop[alt] != -1)
            {
                checkIfLeftIsPrintable(altitude, alt);
                checkIfRightIsPrintable(altitude, alt);
            }
        }
    }

    private void checkIfCenterIsPrintable(int[][] altitude, int highestAltitude)
    {
        for (int check = y - 2; check <= highestAltitude && check > - 1; check += 2)
        {
            if (altitude[check][x] + check > y + z)
            {
                printTop[highestAltitude] = -1;
                printBottom[highestAltitude] = -1;
                printLeftTop[highestAltitude] = -1;
                printLeftBottom[highestAltitude] = -1;
                printRightTop[highestAltitude] = -1;
                printRightBottom[highestAltitude] = -1;
                return ;
            }
            else if (printTop[highestAltitude] != -1 && altitude[check][x] + check == y + z)
            {
                printTop[highestAltitude] = 1;
                printBottom[highestAltitude] = -1;
            }
            else if (printBottom[highestAltitude] != -1 && altitude[check][x] + check < y + z)
            {
                printTop[highestAltitude] = 1;
                printBottom[highestAltitude] = 1;
            }
        }
    }

    private void checkIfLeftIsPrintable(int[][] altitude, int highestAltitude)
    {
       if ((y + 1) % 2 != 0)
       {
            for (int check = y - 1; x > 0 && check <= highestAltitude && check > -1; check -= 2)
            {
                if (altitude[check][x - 1] + check > y + z)
                {
                    printLeftTop[highestAltitude] = -1;
                    printLeftBottom[highestAltitude] = -1;
                    return ;
                }
                else if (printLeftTop[highestAltitude] != -1 && altitude[check][x - 1] + check == y + z)
                {
                    printLeftTop[highestAltitude] = 1;
                    printLeftBottom[highestAltitude] = -1;
                }
                else if (printLeftBottom[highestAltitude] != -1 && altitude[check][x - 1] + check == y + z)
                {
                    printLeftTop[highestAltitude] = 1;
                    printLeftBottom[highestAltitude] = 1;
                }
            }
       }
       else
       {

           for (int check = y - 1; check <= highestAltitude && check > -1; check -= 2)
           {
               if (altitude[check][x] + check > y + z)
               {
                   printLeftTop[highestAltitude] = -1;
                   printLeftBottom[highestAltitude] = -1;
                   return ;
               }
               else if (printLeftTop[highestAltitude] != -1 && altitude[check][x] + check == y + z)
               {
                   printLeftTop[highestAltitude] = 1;
                   printLeftBottom[highestAltitude] = -1;
               }
               else if (printLeftBottom[highestAltitude] != -1 && altitude[check][x] + check == y + z)
               {
                   printLeftTop[highestAltitude] = 1;
                   printLeftBottom[highestAltitude] = 1;
               }
           }
       }
    }

    private void checkIfRightIsPrintable(int[][] altitude, int highestAltitude)
    {
        if ((y + 1) % 2 != 0)
        {
            for (int check = y - 1; check <= highestAltitude && check > -1; check -= 2)
            {
                if (altitude[check][x] + check > y + z)
                {
                    printRightTop[highestAltitude] = -1;
                    printRightBottom[highestAltitude] = -1;
                    return ;
                }
                else if (printRightTop[highestAltitude] != -1 && altitude[check][x] + check == y + z)
                {
                    printRightTop[highestAltitude] = 1;
                    printRightBottom[highestAltitude] = -1;
                }
                else if (printRightBottom[highestAltitude] != -1 && altitude[check][x] + check == y + z)
                {
                    printRightTop[highestAltitude] = 1;
                    printRightBottom[highestAltitude] = 1;

                }
            }
        }
        else
        {
            for (int check = y - 1; x + 1 < worldX && check <= highestAltitude && check > -1; check -= 2)
            {
                if (altitude[check][x + 1] + check > y + z)
                {
                    printRightTop[highestAltitude] = -1;
                    printRightBottom[highestAltitude] = -1;
                    return ;
                }
                else if (printRightTop[highestAltitude] != -1 && altitude[check][x + 1] + check == y + z)
                {
                    printRightTop[highestAltitude] = 1;
                    printRightBottom[highestAltitude] = -1;
                }
                else if (printRightBottom[highestAltitude] != -1 && altitude[check][x + 1] + check == y + z)
                {
                    printRightTop[highestAltitude] = 1;
                    printRightBottom[highestAltitude] = 1;

                }
            }
        }
    }

    public void printObject(SpriteBatch batch, ObjectTextures ObjectTextures, int altitude)
    {
        if (name.equals("tree1") && printTop[altitude] != -1)
            ObjectTextures.printTree1Sprite(batch, mapX, mapY, printTop[altitude], printBottom[altitude],
                    printLeftTop[altitude], printLeftBottom[altitude], printRightTop[altitude],
                    printRightBottom[altitude]);
    }
}
