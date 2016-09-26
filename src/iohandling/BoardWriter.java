package iohandling;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import core.Board;
import core.Location;

public class BoardWriter {

	public static void writeBoard(Board b, String fname){
		try{
			PrintWriter write = new PrintWriter(new File(fname));
			for(Location loc: b.getLocations().values()){
				
			}
		}catch(IOException e){
			
		}
	}
}
