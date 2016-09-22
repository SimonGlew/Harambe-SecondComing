package iohandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import core.Location;

public class BoardCreator {
	
	private Scanner sc;
	
	public BoardCreator(String fileName){
		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Location[] loadBoard(){
		return null;
	}
}
