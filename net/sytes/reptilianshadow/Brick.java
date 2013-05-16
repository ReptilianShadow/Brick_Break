package net.sytes.reptilianshadow;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics2D;
import java.awt.Point;

public class Brick {

	private GamePanel panel;
	
	public Point coords;//not pixel point
	//specifies row and coloumn
	
	private String type; 
	private int toughness;
	/*
	 * Types:
	 * . - None		- Has No Special Ability
	 * # - MultiBall- Spawns 2 Other Balls at Random Angles
	 * + - Long Bar - Increases Bounce Bar Width
	 * - - Short Bar- Decreases Bounce Bar Width
	 * 
	 * Toughness:
	 * 0 - Invis	- Does not exist
	 * 1 - Red		- 1 Hit Break
	 * 2 - Orange	- 2 Hit Break
	 * 3 - Yellow	- 3 Hit Break
	 * 4 - Lime		- 4 Hit Break
	 * 5 - Green	- 5 Hit Break
	 * 6 - Sky Blue	- 6 Hit Break
	 * 7 - Blue		- 7 Hit Break
	 * 8 - Magenta	- 8 Hit Break
	 * 9 - Black	- Unbreakable
	 * 
	 */
	private PowerUp powerUp;

	public Brick(GamePanel panel, String seedThing, Point coords){ //2 character string of format (type)(toughness)
		type = seedThing.substring(0,1);
		toughness = Integer.parseInt(seedThing.substring(1,2));
		this.coords = coords;
		this.panel = panel;
	}

	public int getToughness(){
		return toughness;
	}
	public String getType(){
		return type;
	}
	
	public void paint(Graphics2D g){
		
		
		switch(toughness){
		case 0: //invis
			
			break;
		case 1: //red
			g.setColor(Color.RED);
			break;
		case 2: //orange
			g.setColor(Color.ORANGE);
			break;
		case 3: //yellow
			g.setColor(Color.YELLOW);
			break;
		case 4: //lime
			g.setColor(Color.PINK);
			break;
		case 5: //green
			g.setColor(Color.GREEN);
			break;
		case 6: //sky blue
			g.setColor(Color.CYAN);
			break;
		case 7: //blue
			g.setColor(Color.BLUE);
			break;
		case 8: //magenta
			g.setColor(Color.MAGENTA);
			break;
		case 9: //black
			g.setColor(Color.BLACK);
			break;
		}
		
		
		
		if(toughness != 0){
			g.fill3DRect(
					coords.x * panel.BrickPixelWidth,
					coords.y * panel.BrickPixelHeight,
					panel.BrickPixelWidth,
					panel.BrickPixelHeight,
					true);
		}
//		System.out.println(panel.BrickPixelWidth + "*" + panel.BrickPixelHeight
//				+ "@\t(" + coords.x * panel.BrickPixelWidth + "," + coords.y * panel.BrickPixelHeight + ")");
		
	}
	
	public void decay(){
		if (toughness < 9 )toughness--;
	}
	
	
	public Dimension getSize(){
		return new Dimension( panel.BrickPixelWidth, panel.BrickPixelHeight );
	}
	
	public Point getLocation(){
		return new Point(coords.x * panel.BrickPixelWidth, coords.y * panel.BrickPixelHeight);
	}
	
	public String toString(){
		return type + toughness;
	}
}