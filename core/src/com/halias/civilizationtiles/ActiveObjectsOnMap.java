package com.halias.civilizationtiles;

public class ActiveObjectsOnMap
{
    private String name;
    private int    x;
    private int    y;
    private int    z;

    public ActiveObjectsOnMap(String newName, int posX, int posY, int posZ)
    {
        name = newName;
        x = posX;
        y = posY;
        z = posZ;
    }

    public String getName()
    {
        return (name);
    }

    public int getX()
    {
        return (x);
    }

    public int getY()
    {
        return (y);
    }

    public int getZ()
    {
        return (z);
    }
}

