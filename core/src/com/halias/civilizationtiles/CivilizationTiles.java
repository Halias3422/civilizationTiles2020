package com.halias.civilizationtiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.LinkedList;
import java.util.ListIterator;

public class CivilizationTiles extends ApplicationAdapter {
	private SpriteBatch 		  batch;
	private Sprite				  background;
	private OrthographicCamera	  camera;
	private Viewport			  viewport;
	final private float		  	  SCREEN_WIDTH = 320;
	final private float			  SCREEN_HEIGHT = 240;
	private GestureDetector		  gesture;

	private int				   	  worldSizeX;
	private int					  worldSizeY;
	private int					  worldSizeZ;
	private int					  offsetX;
	private int				      offsetY;
	private float				  prevZoomView;
	private float				  zoomView;

	private TileTextures		  TileTextures;
	private CharWorldMap		  CharWorldMap;
	private NatureObjects		  NatureObjects;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Sprite(new Texture("blackBackground.png"));
		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		//SCREEN_WIDTH = Gdx.graphics.getWidth();
		//SCREEN_HEIGHT = Gdx.graphics.getHeight();
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);

		worldSizeY = 60 * 20;
		worldSizeX = 20 * 20;
		worldSizeZ = 100;
		offsetX = 0;
		offsetY = 0;
		zoomView = 0.10F;
		prevZoomView = 0.10F;
		CharWorldMap = new CharWorldMap(worldSizeX, worldSizeY, worldSizeZ,
				SCREEN_WIDTH, SCREEN_HEIGHT, batch);
	}

	@Override
	public void render () {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) /*&& offsetX > -10*/)
		{
			camera.position.x -= 2 * camera.zoom;
			//offsetX--;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) /*&& offsetX < worldSizeX - 10*/)
		{
			camera.position.x += 2 * camera.zoom;
			//offsetX++;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) /*&& offsetY > -30*/)
		{
			camera.position.y -= 2 * camera.zoom;
			//offsetY -= 3;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) /*&& offsetY < worldSizeY - 30*/)
		{
			camera.position.y += 2 * camera.zoom;
			//offsetY += 3;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W) && camera.zoom < 2)
		{
		    camera.zoom += 0.005;
		    System.out.println("zoom = " + camera.zoom);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.Q) && camera.zoom > 0.02)
		{
		    camera.zoom -= 0.005;
			System.out.println("zoom = " + camera.zoom);
		}
		if (prevZoomView != zoomView)
		{
			CharWorldMap.storeWorldMapIntoFrameBuffer(batch, zoomView);
			prevZoomView = zoomView;
			System.out.println("zoomView = " + zoomView);
		}
		camera.update();
		//batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(background, 0, 0);
		CharWorldMap.printMap(batch, (int)SCREEN_WIDTH, (int)SCREEN_HEIGHT,
				zoomView);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		background.getTexture().dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		//viewport.update(width, height);
		camera.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
	}
}
