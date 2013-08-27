package com.redomar.vivid.level.tiles;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage image;
	private int type;
	
	public static final int BLOCKED = 0;
	public static final int NORMAL = 1;
	
	public Tile(BufferedImage image, int type){
		this.image = image;
		this.type = type;
	}
	
	public int getType(){
		return type;
	}

	public BufferedImage getImage() {
		return image;
	}
}
