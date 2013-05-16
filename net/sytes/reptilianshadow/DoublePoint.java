package net.sytes.reptilianshadow;


import java.awt.Point;


public class DoublePoint{
	private double x, y;
	
	public DoublePoint(){}
	public DoublePoint(double x, double y){
		this.x = x;
		this.y = y;
	}
	public DoublePoint(Point p){
		x = p.getX();
		y = p.getY();
	}
	public Point getPoint(){ //rounded using "normal" 
		return new Point((int)(this.x + 0.5), (int)(this.y + 0.5));
	}
	public DoublePoint getLocation(){
		return this;
	}
	
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public int getIX(){
		return (int)(x + 0.5);
	}
	public int getIY(){
		return (int)(y + 0.5);
	}
	
	public void setLocation(double x, double y){
		this.x = x;
		this.y = y;
	}
}

