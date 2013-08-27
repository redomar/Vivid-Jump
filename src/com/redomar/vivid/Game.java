package com.redomar.vivid;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	//Declaration of variables
	//Game Width and Height is are 4:3 
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 160;
	private static final int HEIGHT = (WIDTH / 4 * 3);
	private static final int SCALE = 3;
	private static final String version = "Alpha 1.0";
	private static final String title = "Vivid Jump"+" "+version;
	
	private static JFrame frame;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	private boolean running = false;
	private boolean buffer = false;
	private boolean buffered = false;
	
	public synchronized void start(){
		running = true;
		new Thread(this).start();
		requestFocus();
	}
	
	public synchronized void stop(){
		running = false;
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int ticks = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		//init();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
			}
			{
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				getFrame().setTitle("Frames: " + frames + " Ticks: " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	private void tick() {
		
	}

	private void render() {
		if(!buffer){
			consoleOuput("Buffering Graphics");
			buffer = true;
		}
		
		BufferStrategy bs = getBufferStrategy();
		if(bs==null){
			createBufferStrategy(3);
			return;
		}
		
		
		
		Graphics g = image.getGraphics();
		
		g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SCALE * WIDTH, SCALE * HEIGHT);
		g.dispose();
		bs.show();
		if(!buffered){
			consoleOuput("Rendering Graphics");
			buffered = true;
		}
	}

	public static String currentTime(){
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	return sdf.format(cal.getTime());
	}
	
	public static void consoleOuput(String msg){
		System.out.println("["+currentTime()+"][Console] "+msg);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(SCALE * WIDTH, SCALE * HEIGHT));
		game.setMinimumSize(new Dimension(SCALE * WIDTH, SCALE * HEIGHT));
		game.setMaximumSize(new Dimension(SCALE * WIDTH, SCALE * HEIGHT));
		
		
		setFrame(new JFrame(title));
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().add(game);
		getFrame().setResizable(false);
		getFrame().pack();
		getFrame().setVisible(true);
		getFrame().setLocationRelativeTo(null);
		
		consoleOuput("Java Frame Set");
		
		game.start();
		consoleOuput("Game Starting");
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public static JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		Game.frame = frame;
	}
}