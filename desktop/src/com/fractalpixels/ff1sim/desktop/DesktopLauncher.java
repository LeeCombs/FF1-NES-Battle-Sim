package com.fractalpixels.ff1sim.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fractalpixels.ff1sim.FF1Sim;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		// Setup configuration
		config.title = "FF1 NES Battle Sim";
		config.width = 800;
		config.height = 400;
		
		new LwjglApplication(new FF1Sim(), config);
	}
}
