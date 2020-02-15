package com.fractalpixels.ff1sim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FF1Sim extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	// Testing
	private Texture dropImage, bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		// load images for droplet and bucket at 64x64 each
		dropImage = new Texture(Gdx.files.internal("images/droplet.png"));
		bucketImage= new Texture(Gdx.files.internal("images/bucket.png"));
		
		// Load music and sounds
		dropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/AttackSpell.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("music/BattleScene.ogg"));
		
		// start music
		rainMusic.setLooping(true);;
		rainMusic.play();
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
