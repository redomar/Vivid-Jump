package com.redomar.vivid.level;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.redomar.vivid.level.tiles.Tile;

public class LevelHandler {

	private int x, y;
	private int tileSize;
	private int[][] map;
	private int mapWidth, mapHeight;
	
	private BufferedImage tileSheet;
	private int sheetTileWidth;
	private Tile[][] tiles;
	
	public LevelHandler(int tileSize, String path){
		this.tileSize = tileSize;
		try{
			InputStream in = getClass().getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int[mapWidth][mapHeight];
			
			String spacers = "\\s+";
			for(int row = 0; row < mapHeight; row++){
				String line = br.readLine();
				String[] tokens = line.split(spacers);
				for(int col = 0; col < mapWidth; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadSheet(String path){
		try{
			tileSheet = ImageIO.read(getClass().getResourceAsStream(path));
			sheetTileWidth = tileSheet.getWidth() / tileSize;
			tiles = new Tile[2][sheetTileWidth];
			
			BufferedImage outImage;
			for(int col = 0; col < sheetTileWidth; col++){
				outImage = tileSheet.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(outImage, Tile.BLOCKED);
				outImage = tileSheet.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(outImage, Tile.NORMAL);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g){
		for(int row = 0; row < mapHeight; row++){
			for(int col = 0; col < mapHeight; col++){
				int rc = map[row][col];
				int r = rc / sheetTileWidth;
				int c = rc % sheetTileWidth;
				
				g.drawImage(tiles[r][c].getImage(), x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);
			}
		}
	}
	
}
