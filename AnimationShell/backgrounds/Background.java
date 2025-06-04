import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Background {
	public static final int TILE_HEIGHT = 50;
	public static final int TILE_WIDTH = 50;
	
	private int maxCols = 0;
    private int maxRows = 0;
	
	private Image Barrier;
	private Image PrototypeBackground;
	private Image PrototypeItem;
	
	private int map[][] = null; 

public Background() {	
	 int[][] background = CSVReader.importFromCSV("res/PrototypeMap.csv");
	   map = background;
	   
	   try {
 		this.Barrier = ImageIO.read(new File("res/blue-barrier.png"));
 		this.PrototypeBackground = ImageIO.read(new File("res/BackgroundPrototype.png"));
 		this.PrototypeItem = ImageIO.read(new File("res/ItemPrototype"));
	   }
 		catch (IOException e) {
 	   	
 		   maxRows = map.length - 1;
 	   	maxCols = map[0].length - 1;
	   }
}

	public Tile getTile(int col, int row) {
Image image = null;
		
		if(row < 0 || row > maxRows || col < 0 || col > maxCols) {
			image = null;
		} else if(map[row][col] == 0) {
			image = Barrier;
		} else if(map[row][col] == 1) {
			image = PrototypeBackground;
		} else if(map[row][col] == 2) {
			image = PrototypeItem;
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
	
	public ArrayList<DisplayableSprite> getBarriers() {
		ArrayList<DisplayableSprite> barriers = new ArrayList<DisplayableSprite>();
		for(int col = 0; col < map[0].length; col++) {
			for(int row = 0; row < map.length; row++) {
				if(map[row][col] == 0) {
					barriers.add(new BarrierSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH, (row + 1) * TILE_HEIGHT, false));
				}
			}
		}
		return barriers;
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

