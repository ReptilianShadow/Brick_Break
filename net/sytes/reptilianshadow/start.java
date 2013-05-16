package net.sytes.reptilianshadow;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class start {
	
	public static GameThread gt;
	
	public static void main(String[] args){
		
		
		
		System.out.println("Test");
		
		gt = new GameThread();
		
		JFrame frame = new JFrame("Brick Break v2");
		
		JFileChooser jf = new JFileChooser();
		
		jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jf.showOpenDialog(frame);
		
		File levelFile = jf.getSelectedFile();
		
		GamePanel gamePanel = new GamePanel(levelFile);
		
		frame.add(gamePanel);
		frame.pack();
//		frame.setResizable(false);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		gt.start();
	}
}
