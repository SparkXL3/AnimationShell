import java.util.ArrayList;

public class ShellUniverse implements Universe {

	private boolean complete = false;	
	private Background background = null;
	private DisplayableSprite player1 = null;
	private DisplayableSprite Mine1 = null;
	private DisplayableSprite UnexplodedOrdnance1 = null;
	private DisplayableSprite Rum1 = null;
	private DisplayableSprite bandage1 = null;
	private DisplayableSprite Canteen1 = null;
	private ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	private ArrayList<Background> backgrounds = new ArrayList<Background>();
	private ArrayList<DisplayableSprite> disposalList = new ArrayList<DisplayableSprite>();
	private double xCenter = 0;
	private double yCenter = 0;
	private double centerX;
	private double centerY;
	private static final double SMOOTHING_FACTOR = 0.03;
	
	public ShellUniverse () {
		
		background = new PrototypeBackground();
		ArrayList<DisplayableSprite> barriers = ((PrototypeBackground)background).getBarriers();
		backgrounds =new ArrayList<Background>();
		backgrounds.add(background);
		
		//player1 = new StahlhelmSprite(PrototypeBackground.TILE_HEIGHT * 2, PrototypeBackground.TILE_WIDTH * 2);

		this.setXCenter(0);
		this.setYCenter(0);
		
		player1 = new StahlhelmSprite(0,0);
		//Mine1 = new MineSprite(100, 400);
		//UnexplodedOrdnance1 = new UnexplodedOrdnanceSprite(100, 400);
		//Rum1 = new RumSprite(100, 200);
		bandage1 = new BandageSprite(100, 200);
		Canteen1 = new CanteenSprite(100, 400);
		
		//sprites.add(Mine1);
		//sprites.add(UnexplodedOrdnance1);
		//sprites.add(Rum1);
		sprites.add(bandage1);
		sprites.add(Canteen1);
		sprites.add(player1);
		sprites.addAll(barriers);
			
	}

	public double getScale() {
		return 1;
	}

	public double getXCenter() {
		//return xCenter;
		return this.centerX;
	}

	public double getYCenter() {
		//return yCenter;
		return this.centerY;
	}

	public void setXCenter(double xCenter) {
		this.xCenter = xCenter;
	}

	public void setYCenter(double yCenter) {
		this.yCenter = yCenter;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public StahlhelmSprite getPlayer1() {
		return (StahlhelmSprite) this.player1;
	}

	public ArrayList<Background> getBackgrounds() {
		return backgrounds;
	}	

	public ArrayList<DisplayableSprite> getSprites() {
		return sprites;
	}

	public boolean centerOnPlayer() {
		return true;
	}		

	public void update(Animation animation, long actual_delta_time) {
		if (KeyboardInput.getKeyboard().keyDownOnce(27)) {
			complete = true;
		}

		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
			sprite.update(this, actual_delta_time);
    	} 
		
		 double playerX = player1.getCenterX();
	        double playerY = player1.getCenterY();
	        
	        this.centerX += (playerX - this.centerX) * SMOOTHING_FACTOR; 
	        this.centerY += (playerY - this.centerY) * SMOOTHING_FACTOR; 
		
		disposeSprites();
		
	}
	
    protected void disposeSprites() {
        
    	//collect a list of sprites to dispose
    	//this is done in a temporary list to avoid a concurrent modification exception
		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
    		if (sprite.getDispose() == true) {
    			disposalList.add(sprite);
    		}
    	}
		
		//go through the list of sprites to dispose
		//note that the sprites are being removed from the original list
		for (int i = 0; i < disposalList.size(); i++) {
			DisplayableSprite sprite = disposalList.get(i);
			sprites.remove(sprite);
    	}
		
		//clear disposal list if necessary
    	if (disposalList.size() > 0) {
    		disposalList.clear();
    	}
    }


	public String toString() {
		return "ShellUniverse";
	}

}
