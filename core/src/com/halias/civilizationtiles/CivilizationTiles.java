package com.halias.civilizationtiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CivilizationTiles extends ApplicationAdapter {
	private SpriteBatch 		  batch;
	private Sprite				  background;
	private OrthographicCamera	  camera;
	private Viewport			  viewport;
	final private float		  	  SCREEN_WIDTH = 320;
	final private float			  SCREEN_HEIGHT = 240;

	private int				   	  worldSizeX;
	private int					  worldSizeY;
	private int					  offsetX;
	private int				      offsetY;
	private float				  prevZoomView;
	private float				  zoomView;

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

		worldSizeY = 60 * 51;
		worldSizeX = 20 * 51;
		offsetX = 0;
		offsetY = 0;
//		camera.zoom = 2.2F;
		CharWorldMap = new CharWorldMap(worldSizeX, worldSizeY,
				SCREEN_WIDTH, SCREEN_HEIGHT, batch);
		NatureObjects = new NatureObjects(CharWorldMap.getCharWorldMap(), worldSizeX, worldSizeY,
				batch);
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
		if (Gdx.input.isKeyPressed(Input.Keys.W)/* && camera.zoom < 4*/)
		{
		    camera.zoom += 0.04;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.Q) && camera.zoom > 0.02)
		{
		    camera.zoom -= 0.04;
		}

		//System.out.println("altitudeLevel = " + altitudeLevel);
		camera.update();
		//batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(background, 0, 0);
		CharWorldMap.printMap(batch, (int)SCREEN_WIDTH, (int)SCREEN_HEIGHT);
		NatureObjects.printMap(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		background.getTexture().dispose();
		CharWorldMap.disposeNecessary();

	}

	@Override
	public void resize(int width, int height)
	{
		//viewport.update(width, height);
		camera.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
	}
}
