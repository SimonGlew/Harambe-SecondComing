package renderer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Renderer {

	TempWindow window;

	char[][] board = {{'w', 'w', 'w', 'w', 1, 1, 1, 1, 1, 1},
			{'w', 'w', 'w', 'w', 0, 0, 0, 0, 0, 1},
			{'w', 'w', 'w', 0, 0, 0, 0, 0, 0, 0},
			{'w', 'w', 0, 0, 0, 0, 0, 0, 0, 0},
			{'w', 'w', 0, 0, 0, 0, 0, 0, 0, 0},
			{'w', 0, 0, 0, 0, 0, 0, 0, 1, 1},
			{'w', 0, 0, 0, 0, 0, 0, 0, 1, 1},
			{0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
			{0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
			{0, 0, 1, 1, 1, 1, 1, 1, 1, 1}};

	final int TILE_WIDTH = 45;
	int xOffset;
	int yOffset;

	public Renderer() {
		window = new TempWindow();
		
		
		paintImage();
	}
	
	public void paintImage(){
		int w = window.panel.getWidth();
		int h = window.panel.getHeight();
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		drawBoard(g);
		window.setImage(image);
	}
	
	
	public void drawBoard(Graphics2D g){
		calculateOffsets();
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				int x = j*TILE_WIDTH;
				int y = i*TILE_WIDTH;
				Point iso = twoDToIso(x, y);
				try {
					BufferedImage floor = ImageIO.read(new File("src/grassTile.png"));
					if(board[i][j] == 'w'){
						floor = ImageIO.read(new File("src/waterTile.png"));
					}
					g.drawImage(floor, iso.x, iso.y - floor.getHeight(), null);
					if(board[i][j] == 1){	
						BufferedImage object = ImageIO.read(new File("src/treeTile.png"));
						g.drawImage(object, iso.x, iso.y - object.getHeight(), null);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
		
	
	public void calculateOffsets(){
		int boardHeight = (int)((board.length + board[0].length - 1)*TILE_WIDTH*Math.sin(Math.PI/6));
		xOffset = (int) (window.panel.getWidth()/2 - 2*TILE_WIDTH*Math.sin(Math.PI/6));
		yOffset = (int) ((window.panel.getHeight() - boardHeight)/2 + TILE_WIDTH*Math.cos(Math.PI/6));
	}
	

	public Point isoTo2D(int x, int y) {
		Point tempPt = new Point(0, 0);
		tempPt.x = (2 * y + x) / 2;
		tempPt.y = (2 * y - x) / 2;
		return (tempPt);
	}

	public Point twoDToIso(int x, int y) {
		Point tempPt = new Point(0, 0);
		tempPt.x = xOffset + x - y;
		tempPt.y = yOffset + (x + y) / 2;
		return (tempPt);
	}

	public static void main(String[] args) {
		new Renderer();
	}
}