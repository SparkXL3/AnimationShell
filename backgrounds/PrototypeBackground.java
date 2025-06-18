import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PrototypeBackground implements Background {
	
	public static final int TILE_HEIGHT = 50;
	public static final int TILE_WIDTH = 50;
	
	private int maxCols = 0;
    private int maxRows = 0;
	
	private Image Ground;
	private Image TrenchWallFront;
	private Image TrenchWallVerticalLeft;
	private Image TrenchWallVerticalRight;
	private Image LeftBarbedWire;
	private Image FrontBarbedWire;
	private Image RightBarbedWire;
	private Image Latter;
	//private Image UnexplodedOrdnance;
	
	private int map[][] = null; 
	
	 public PrototypeBackground() {
		   int[][] background = CSVReader.importFromCSV("res/TrenchRunnerMap.csv");
		   map = background;

		   
		   try {
	   		this.Ground = ImageIO.read(new File("res/MyDirt.png"));
	   		this.TrenchWallFront = ImageIO.read(new File("res/NewTrenchWallFront.png"));
	   		this.TrenchWallVerticalLeft = ImageIO.read(new File("res/TrenchWallVertical2.png"));
	   		this.TrenchWallVerticalRight = ImageIO.read(new File("res/TrenchWallVerticalRight.png"));
	   		this.LeftBarbedWire = ImageIO.read(new File("res/BarbedWire.png"));
	   		this.FrontBarbedWire = ImageIO.read(new File("res/SidewaysBarbedWire.png"));
	   		this.RightBarbedWire = ImageIO.read(new File("res/UpsideDownBarbedWire.png"));
	   		this.Latter = ImageIO.read(new File("res/Latter.png"));
	   		//this.UnexplodedOrdnance = ImageIO.read(new File("res/UnexplodedOrdnance.png"))
	   	}
	   	catch (IOException e) {
	   	}
		maxRows = map.length - 1;
	   	maxCols = map[0].length - 1;
	   	
	   	
	   }
	 
	 public ArrayList<DisplayableSprite> getBarriers() {
			ArrayList<DisplayableSprite> barriers = new ArrayList<DisplayableSprite>();
			for(int col = 0; col < map[0].length; col++) {
				for(int row = 0; row < map.length; row++) {
					if(map[row][col] == 1 || map[row][col] == 2 || map[row][col] == 3) {
						barriers.add(new BarrierSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH, (row + 1) * TILE_HEIGHT, false));
					}
				}
			}
			return barriers;
		}
	 
	 public ArrayList<DisplayableSprite> getBarbedWire() {
			ArrayList<DisplayableSprite> barbedWire = new ArrayList<DisplayableSprite>();
			for(int col = 0; col < map[0].length; col++) {
				for(int row = 0; row < map.length; row++) {
					if(map[row][col] == 4 || map[row][col] == 5 || map[row][col] == 6) {
						barbedWire.add(new BarbedWireSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH, (row + 1) * TILE_HEIGHT, false));
					}
				}
			}
			return barbedWire;
		}

	public Tile getTile(int col, int row) {
Image image = null;
		
		if(row < 0 || row > maxRows || col < 0 || col > maxCols) {
			image = null;
		} else if(map[row][col] == 0) {
			image = Ground;
		} else if(map[row][col] == 1) {
			image = TrenchWallFront;
		} else if(map[row][col] == 2) {
			image = TrenchWallVerticalLeft;
		} else if(map[row][col] == 3) {
			image = TrenchWallVerticalRight;
		} else if(map[row][col] == 6) {
				image = LeftBarbedWire;
		} else if(map[row][col] == 5) {
			image = FrontBarbedWire;
		} else if(map[row][col] == 4) {
			image = RightBarbedWire;
		} else if(map[row][col] == 7) {
			image = Latter;
		} else {
			image = null;
		}
		
		int x = (col * TILE_WIDTH);
		int y = (row * TILE_HEIGHT);
		
		Tile newTile = new Tile(image, x, y, TILE_WIDTH, TILE_HEIGHT, false);
		
		return newTile;
	}
	
	public int getCol(double x) {
		int col = 0;
		col = (int) (x / TILE_WIDTH);
		if(x < 0) {
			return col - 1;
		} else {
			return col;
		}
		
	}
	
	public int getRow(double y) {
		int row = 0;
		row = (int) (y / TILE_HEIGHT);
		if(y < 0) {
			return row - 1;
		} else {
			return row;
		}
	}
	
	public double getShiftX() {
		return 0;
	}
	
	public double getShiftY() {
		return 0;
	}
	
	public void setShiftX(double shiftX) {
	}

	public void setShiftY(double shiftY) {
	}
	
	public void update(Universe universe, long actual_delta_time) {
	}		
	
}
