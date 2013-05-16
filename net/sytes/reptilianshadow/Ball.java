package net.sytes.reptilianshadow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

public class Ball implements Updatable{

	private DoublePoint position;//center
	
	public double xVelocity; //pixels/tick
	public double yVelocity; //pixels/tick

	public int radius; //pixels

	private GamePanel panel;

	public Ball(GamePanel gp, int x, int y, double xVelocity, double yVelocity, int radius){
		panel = gp;
		position = new DoublePoint(x, y);
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.radius = radius;
	}
	
	public Ball(GamePanel gp, int x, int y, double AnyDirSpeed, int radius){
		panel = gp;
		position = new DoublePoint(x, y);
		this.radius = radius;
		
		//hypotenuse speed == anydirspeed
		//otherwise random x and y velocities
		
		//values for x and y velocities (random which)
		double v1 = 0, v2 = 0;
		
		Random rnd = new Random(System.currentTimeMillis()); //just some seed value
		
		v1 = (rnd.nextDouble() * (AnyDirSpeed - 1)) + 1; // [ 1, AnyDirSpeed ) 
		
		//solve for the other leg velocity
		//b = sqrt( c^2 - a^2 );
		v2 = Math.sqrt(  Math.pow(AnyDirSpeed, 2) - Math.pow(v1, 2)  ); 
		
		
		//invert v1?
		if (rnd.nextInt(2) == 0){
			v1 *= -1;
		}
		//invert v2?
		if (rnd.nextInt(2) == 0){
			v2 *= -1;
		}
		
		//randomly set velocities to values
		if (rnd.nextInt(2) == 0){
			xVelocity = v1;
			yVelocity = v2;
		}else{
			xVelocity = v2;
			yVelocity = v1;
		}
		
	}
	

	public static boolean isBrickCollide(Ball ball, Brick brick){

		Dimension brickSize = brick.getSize();
		Point brickLoc = brick.getLocation(); //left top corner

		//Clear of brick in cardinal dirs
		if (ball.position.getX() + ball.radius < brickLoc.x 								//west of brick
				|| ball.position.getX() - ball.radius > brickLoc.x + brickSize.width 	//east of brick
				|| ball.position.getY()  + ball.radius < brickLoc.y 						//north of brick
				|| ball.position.getY()  - ball.radius > brickLoc.y + brickSize.height 	//south of brick
				){
			return false;
		}
		return true;
	}


	public void doWallCollision(){

		//make sure it's always changing the velocity for the better
		if (	position.getY() - radius <= 0 && yVelocity < 0 //top wall : change if negative yVel
				||
				position.getY() + radius >= panel.PanelHeight && yVelocity > 0 //bottom wall | change if positive yVel
				){
			yVelocity *= -1;
		}

		if (	position.getX() - radius <= 0 && xVelocity < 0 //left wall | change if negative xVel
				||
				position.getX() + radius >= panel.PanelWidth && xVelocity > 0 //right wall | change if positive xVel
				){
			xVelocity *= -1;
		}
	}

	public void doBrickCollision(Brick brick){

		Dimension brickSize = brick.getSize();
		Point brickLoc = brick.getLocation(); //left top corner

		if (!isBrickCollide(this, brick)){
			return; //not even touchin' it.
		}

		//So we are hitting a brick, ehh?
		//decision decisions...

		//are we sure this isn't one of them ghost bricks?
		if (brick.getToughness() <= 0){
			return;
		}


		//punch that brick we're hitting IN THE FACE;
		brick.decay();


		//		//is it entirely in the brick shadow upwards or downwards?
		//		if (	this.position.x - radius > brickLoc.x
		//				&& 	this.position.x + radius < brickLoc.x + brickSize.width
		//				){
		//			toggleYVelocity();
		//			return; //should be done here
		//		}
		//
		//		if (	this.position.y - radius > brickLoc.y
		//				&& 	this.position.y + radius < brickLoc.y + brickSize.height
		//				){
		//			toggleXVelocity();
		//			return; //nothing more to see
		//		}


		if (	position.getX() > brickLoc.x
				&& 	position.getX() < brickLoc.x + brickSize.width
				){
			toggleYVelocity();
		}

		if (	position.getY() > brickLoc.y
				&& position.getY() < brickLoc.y + brickSize.height
				){
			toggleXVelocity();
		}


		//be super lazy and hard code
		//cardinal directions for corners

		//top left corner of brick | bounce NW
		if (isCollide(new Point( brickLoc.x , brickLoc.y ))){
			if ( xVelocity > 0 ) xVelocity *= -1;
			if ( yVelocity > 0 ) yVelocity *=-1;
		}
		//top right corner | bounce NE
		if ( isCollide(new Point( brickLoc.x + brickSize.width, brickLoc.y )) ){
			if ( xVelocity < 0 ) xVelocity *= -1;
			if ( yVelocity > 0 ) yVelocity *=-1;
		}
		//bottom right corner | bounce SE
		if ( isCollide(new Point( brickLoc.x + brickSize.width, brickLoc.y + brickSize.height )) ){
			if ( xVelocity < 0 ) xVelocity *= -1;
			if ( yVelocity < 0 ) yVelocity *=-1;
		}

		//bottom left corner | bounce SW
		if ( isCollide(new Point( brickLoc.x, brickLoc.y  +  brickSize.height)) ){
			if ( xVelocity > 0 ) xVelocity *= -1;
			if ( yVelocity < 0 ) yVelocity *=-1;
		}






	}

	public boolean isCollide(Point p){ //is colliding given any pixel point
		double deltaX = Math.abs(position.getY() - p.x);
		double deltaY = Math.abs(position.getY() - p.y);

		double hyp = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		return hyp <= radius;
	}

	public void toggleXVelocity(){
		this.xVelocity *= -1;
	}
	public void toggleYVelocity(){
		this.yVelocity *= -1;
	}

	public void updatePosition(){
		position.setLocation(position.getX() + xVelocity , position.getY() + yVelocity);
		panel.repaint();
	}

	public void paint(Graphics2D g){
		g.setColor(Color.black);
		g.fillOval(position.getIX() - radius, position.getIY() - radius, radius*2, radius*2);
	}

	public void update() {

		//check bounce against wall
		doWallCollision();

		for (int i = 0; i < panel.bricks.size(); i++){

			doBrickCollision(panel.bricks.get(i));

		}
		updatePosition();
	}

}
