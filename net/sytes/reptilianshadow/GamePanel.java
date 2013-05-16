package net.sytes.reptilianshadow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
	
	//Dimensions
	public static int PanelHeight;
	public static int PanelWidth;
	
	public static int NumBrickWidth;
	public static int NumBrickHeight;
	
	public static int BrickPixelHeight = 25;
	public static int BrickPixelWidth;
	
	
	//Logic
	private boolean paused;

	//PaintStuff
	public Color bgColor = Color.LIGHT_GRAY;
	
	public ArrayList<Brick> bricks; //location internal to brick

	public ArrayList<Ball> balls; //location also internal to class
	
	public GamePanel(File levelFile){
		
		bricks = new ArrayList<Brick>();
		balls = new ArrayList<Ball>();
		
		Scanner sReader = null;
		try {
			sReader = new Scanner(levelFile);
		} catch (FileNotFoundException e) {
			System.out.println("Level File: " + levelFile + " could not be found");
			e.printStackTrace();
		}
		
		ArrayList<String> tempBricks = new ArrayList<String>();
		
		int brickCountY = 0;
		while(sReader.hasNextLine()){
			String[] row = sReader.nextLine().split(",");
			if (NumBrickWidth == 0){
				NumBrickWidth = row.length;
			}else if(row.length != NumBrickWidth){
				System.err.println("Varied row widths are not yet supported!\nExiting...");
				System.exit(1);
			}
			
			for (int brickCountX = 0; brickCountX < row.length; brickCountX++){
				bricks.add(new Brick(this, row[brickCountX], new Point(brickCountX, brickCountY)));
			}
			brickCountY++;
		}
		NumBrickHeight =brickCountY;
		
		Ball b1 = new Ball(this, 50, 400, 1, 1, 20);
		balls.add(b1);
		start.gt.stuffToUpdate.add(b1);
		
		Ball b2 = new Ball(this, 150, 400, 2, 2, 2);
		balls.add(b2);
		start.gt.stuffToUpdate.add(b2);
		
		Ball b3 = new Ball(this, 250, 400, 3, 3, 5);
		balls.add(b3);
		start.gt.stuffToUpdate.add(b3);
		
		Ball b4 = new Ball(this, 350, 400, 4, 4, 3);
		balls.add(b4);
		start.gt.stuffToUpdate.add(b4);
		
		Ball b5 = new Ball(this, 450, 400, 5, 5, 8);
		balls.add(b5);
		start.gt.stuffToUpdate.add(b5);
		
		Ball b6 = new Ball(this, 550, 400, 6, 6, 5);
		balls.add(b6);
		start.gt.stuffToUpdate.add(b6);
		
		Ball b7 = new Ball(this, 650, 400, 7, 7, 10);
		balls.add(b7);
		start.gt.stuffToUpdate.add(b7);
		
		Ball b8 = new Ball(this, 750, 400, 8, 8, 3);
		balls.add(b8);
		start.gt.stuffToUpdate.add(b8);
		
		Ball b9 = new Ball(this, 850, 400, 9, 9, 7);
		balls.add(b9);
		start.gt.stuffToUpdate.add(b9);
		
		this.setPreferredSize(new Dimension(900, 500));
		
	}
	
	public void pause(){
		paused = true;
	}

	public void play(){
		paused = false;
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		updateMetrics();
		
		g2.setColor(new Color(1.0f, 1.0f, 1.0f));
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (int i = 0; i < bricks.size(); i++){
			bricks.get(i).paint(g2);
		}
		for (int j = 0; j < balls.size(); j++){
			balls.get(j).paint(g2);
		}
		
	}
	
	public void updateMetrics(){
		PanelHeight = this.getHeight();
		PanelWidth = this.getWidth();
		
		BrickPixelWidth = PanelWidth / NumBrickWidth;
		
	}
	
	public boolean isPaused(){
		return paused;
	}

}
