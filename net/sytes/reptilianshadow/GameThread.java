package net.sytes.reptilianshadow;

import java.util.ArrayList;

public class GameThread extends Thread{
	
	public static ArrayList<Updatable> stuffToUpdate = new ArrayList<Updatable>();
	
	public GameThread(){
		
	}
	
	public void run(){
		
		
		System.out.println("1 Sec");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {}
		
		System.out.println("Start");
		
		while(true){
			for (int i = 0; i < stuffToUpdate.size(); i++){
				stuffToUpdate.get(i).update();
			}
			try{
				Thread.sleep(10);
			}catch(Exception e){
				System.out.println(e);
			}
			
		}
		
	}
}
