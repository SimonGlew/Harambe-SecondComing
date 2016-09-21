package renderer;

import java.awt.Point;

public class Renderer {

	TempWindow window;
	
	public Renderer(){
		window = new TempWindow();	
	}
	
	public Point isoTo2D(int x, int y){
		  Point tempPt = new Point(0, 0);
		  tempPt.x = (2 * y + x) / 2;
		  tempPt.y = (2 * y - x) / 2;
		  return(tempPt);
		}
	
	public Point twoDToIso(int x, int y){
		  Point tempPt = new Point(0,0);
		  tempPt.x = x - y;
		  tempPt.y = (x + y) / 2;
		  return(tempPt);
		}
	
	public static void main(String[] args){
		new Renderer();
	}
}