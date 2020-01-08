package com.halias.civilizationtiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;

import static com.badlogic.gdx.math.MathUtils.random;

public class NatureObjects {

    private char[][] worldMap;
    private String[][] natureMap;
    private FrameBuffer FB;
    private ObjectTextures ObjectTextures;
    private int x;
    private int y;

    public NatureObjects(char[][] charWorldMap, int worldX, int worldY, SpriteBatch batch)
    {
        worldMap = charWorldMap;
        x = worldX;
        y = worldY;
        natureMap = new String[y][x];
        ObjectTextures = new ObjectTextures(y, x, worldMap);
        placeNatureObjectsOnMap(batch);
    }

    private void placeNatureObjectsOnMap(SpriteBatch batch)
    {
        int rand;

        FB = new FrameBuffer(Pixmap.Format.RGBA8888, x * 16, y * 4 + 32, false, true);
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, x * 16, y * 4 + 32);
        FB.begin();
        batch.begin();
        batch.setProjectionMatrix(matrix);
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (int i = y - 1; i > -1; i--)
        {
            for (int j = 0; j < x; j++)
            {
                rand = random.nextInt(1000);
                if (worldMap[i][j] == 'F' && rand < 600)
                {
                    if (rand > 100)
                        natureMap[i][j] = "tree1";
                    else if (rand > 2)
                        natureMap[i][j] = "bush1";
                    else
                        natureMap[i][j] = "tree2";
                }
                else if (worldMap[i][j] == 'G' && rand < 5)
                {
                    if (rand > 2)
                        natureMap[i][j] = "tree3";
                    else if (rand > 0)
                        natureMap[i][j] = "rock2";
                    else if (rand == 0)
                        natureMap[i][j] = "bush2";
                }
                else if (worldMap[i][j] == 'D' && rand < 10)
                {
                        natureMap[i][j] = "rock1";
                }
                else
                    natureMap[i][j] = "empty";
                printTile(batch, natureMap[i][j], ObjectTextures, i, j);
            }
        }
        batch.end();
        FB.end();
        FB.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    private void printTile(SpriteBatch batch, String tileType, ObjectTextures ObjectTextures, int row, int tile)
    {
        int posX;
        int posY;

        posX = tile * 16;
        posY = row * 4 + 8;
        if ((row + 1) % 2 == 0)
            posX += 8;
        if (tileType.equals("tree1"))
            ObjectTextures.printTree1Sprite(batch, posX, posY);
        else if (tileType.equals("tree2"))
            ObjectTextures.printTree2Sprite(batch, posX, posY);
        else if (tileType.equals("tree3"))
            ObjectTextures.printTree3Sprite(batch, posX, posY);
        else if (tileType.equals("bush1"))
            ObjectTextures.printBush1Sprite(batch, posX, posY);
        else if (tileType.equals("bush2"))
            ObjectTextures.printBush2Sprite(batch, posX, posY);
        else if (tileType.equals("rock1"))
            ObjectTextures.printRock1Sprite(batch, posX, posY);
        else if (tileType.equals("rock2"))
            ObjectTextures.printRock2Sprite(batch, posX, posY);
    }

    public void printMap(SpriteBatch batch)
    {
        batch.draw(FB.getColorBufferTexture(), 0, 0, x * 16, y * 4 + 32, 0, 0,
                x * 16, y * 4 + 32, false, true);

    }

    public void disposeNecessary()
    {
        FB.dispose();
    }
}
