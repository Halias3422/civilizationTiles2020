package com.halias.civilizationtiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.LinkedList;
import java.util.ListIterator;

public class CivilizationTiles extends ApplicationAdapter {
	private SpriteBatch 		  batch;
	private Sprite				  background;
	private Camera				  camera;
	private Viewport			  viewport;
	final private float		  	  SCREEN_WIDTH = 320;
	final private float			  SCREEN_HEIGHT = 240;

	private int				   	  worldSizeX;
	private int					  worldSizeY;
	private int					  worldSizeZ;

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

		worldSizeY = 60;
		worldSizeX = 20;
		worldSizeZ = 100;
		TileTextures = new TileTextures(worldSizeY, worldSizeX);
		CharWorldMap = new CharWorldMap(worldSizeX, worldSizeY, worldSizeZ);
		NatureObjects = new NatureObjects(CharWorldMap.getCharWorldMap(), worldSizeX, worldSizeY, worldSizeZ);
	}

	@Override
	public void render () {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(background, 0, 0);
		CharWorldMap.printMap(batch, NatureObjects, TileTextures);
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
		viewport.update(width, height);
		camera.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
	}
}
