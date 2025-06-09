import java.util.ArrayList;

public class ShellUniverse implements Universe {

	private boolean complete = false;	
	private Background background = null;
	private DisplayableSprite player1 = null;
	private DisplayableSprite Mine1 = null;
	private DisplayableSprite Mine2 = null;
	private DisplayableSprite Mine3 = null;
	private DisplayableSprite Mine4 = null;
	private ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	private ArrayList<Background> backgrounds = new ArrayList<Background>();
	private ArrayList<DisplayableSprite> disposalList = new ArrayList<DisplayableSprite>();
	private double xCenter = 0;
	private double yCenter = 0;
	
	public ShellUniverse () {
		
		background = new PrototypeBackground();
		ArrayList<DisplayableSprite> barriers = ((PrototypeBackground)background).getBarriers();
		backgrounds =new ArrayList<Background>();
		backgrounds.add(background);
		
		//player1 = new StahlhelmSprite(PrototypeBackground.TILE_HEIGHT * 2, PrototypeBackground.TILE_WIDTH * 2);

		this.setXCenter(0);
		this.setYCenter(0);
		
		player1 = new StahlhelmSprite(0,0);
		Mine1 = new MineSprite(100, 400);
		Mine2 = new MineSprite(100, 350);
		Mine3 = new MineSprite(100, 300);
		Mine4 = new MineSprite(100, 250);
		
		sprites.add(player1);
		
		sprites.add(Mine1);
	
		
		sprites.addAll(barriers);
			
	}

	public double getScale() {
		return 1;
	}

	public double getXCenter() {
		return xCenter;
	}

	public double getYCenter() {
		return yCenter;
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

		// problem stems from here?
		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
			sprite.update(this, actual_delta_time);
    	} 
		
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
