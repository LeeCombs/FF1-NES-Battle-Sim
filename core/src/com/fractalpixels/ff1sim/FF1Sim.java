package com.fractalpixels.ff1sim;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class FF1Sim extends ApplicationAdapter {
	
	// Testing
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	
	// Camera and Sprite Batch
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle bucket;
	
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	
	@Override
	public void create () {
		// load images for droplet and bucket at 64x64 each
		dropImage = new Texture(Gdx.files.internal("images/droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("images/bucket.png"));
		
		// Load music and sounds
		dropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/AttackSpell.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("music/BattleScene.ogg"));
		
		// start music
		rainMusic.setLooping(true);;
		rainMusic.play();
		rainMusic.setVolume(0.1f);
		
		// Camera and Sprite Batch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
		
		// Setup raindrops
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
				
	}

	@Override
	public void render () {
		
		// TODO: Why is this in this method?
		
		// Move bucket on click
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		
		// Move bucket on key presses
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			bucket.x += 200 * Gdx.graphics.getDeltaTime();
		
		// Keep bucket within screen limits
		if(bucket.x < 0)
			bucket.x = 0;
		if(bucket.x > 800 - 64)
			bucket.x = 800 - 64;
		
		// Drop raindrops
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();
		
		// Make raindrops drop
		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
		   Rectangle raindrop = iter.next();
		   raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
		   if(raindrop.y + 64 < 0)
			   iter.remove();
		   
		   // Destroy drop on bucket overlap
		   if(raindrop.overlaps(bucket)) {
				dropSound.play(0.1f);
				iter.remove();
			}
		}
		
		
		
		// Draw stuff
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop: raindrops) {
		   batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		
		batch.end();
	}
	
	@Override
	public void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
	
	/////////////////
	// Own Methods //
	/////////////////
	
	private void spawnRaindrop() {
	   Rectangle raindrop = new Rectangle();
	   raindrop.x = MathUtils.random(0, 800-64);
	   raindrop.y = 480;
	   raindrop.width = 64;
	   raindrop.height = 64;
	   raindrops.add(raindrop);
	   lastDropTime = TimeUtils.nanoTime();
	}
}
