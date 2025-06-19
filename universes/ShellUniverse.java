import java.util.ArrayList;

public class ShellUniverse implements Universe {

	private boolean complete = false;	
	private Background background = null;
	private DisplayableSprite player1 = null;
	private DisplayableSprite mine1 = null;
	private DisplayableSprite mine2 = null;
	private DisplayableSprite mine3 = null;
	private DisplayableSprite mine4 = null;
	private DisplayableSprite mine5 = null;
	private DisplayableSprite mine6 = null;
	private DisplayableSprite mine7 = null;
	private DisplayableSprite mine8 = null;
	private DisplayableSprite mine9 = null;
	private DisplayableSprite mine10 = null;
	
	private DisplayableSprite unexplodedOrdnance1 = null;
	private DisplayableSprite unexplodedOrdnance2 = null;
	private DisplayableSprite unexplodedOrdnance3 = null;
	private DisplayableSprite unexplodedOrdnance4 = null;
	private DisplayableSprite unexplodedOrdnance5 = null;
	private DisplayableSprite unexplodedOrdnance6 = null;
	private DisplayableSprite unexplodedOrdnance7 = null;
	private DisplayableSprite unexplodedOrdnance8 = null;
	private DisplayableSprite unexplodedOrdnance9 = null;
	private DisplayableSprite unexplodedOrdnance10 = null;
	
	private DisplayableSprite rum1 = null;
	private DisplayableSprite rum2 = null;
	
	private DisplayableSprite bandage1 = null;
	private DisplayableSprite bandage2 = null;
	private DisplayableSprite bandage3 = null;
	private DisplayableSprite bandage4 = null;
	private DisplayableSprite bandage5 = null;
	
	private DisplayableSprite canteen1 = null;
	private DisplayableSprite canteen2 = null;
	private DisplayableSprite canteen3 = null;
	private DisplayableSprite canteen4 = null;
	private DisplayableSprite canteen5 = null;
	
	private DisplayableSprite officer1 = null;
	
	private DisplayableSprite soldier1 = null;
	private DisplayableSprite soldier2 = null;
	private DisplayableSprite soldier3 = null;
	private DisplayableSprite soldier4 = null;
	private DisplayableSprite soldier5 = null;
	private DisplayableSprite soldier6 = null;
	private DisplayableSprite soldier7 = null;
	private DisplayableSprite soldier8 = null;
	private DisplayableSprite soldier9 = null;
	private DisplayableSprite soldier10 = null;
	
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
		ArrayList<DisplayableSprite> barbedWire = ((PrototypeBackground)background).getBarbedWire();
		backgrounds =new ArrayList<Background>();
		backgrounds.add(background);
		
		
		
		//player1 = new StahlhelmSprite(PrototypeBackground.TILE_HEIGHT * 2, PrototypeBackground.TILE_WIDTH * 2);

		this.setXCenter(0);
		this.setYCenter(0);
		
		sprites.addAll(barbedWire);
		
		player1 = new StahlhelmSprite(0,0);
		
		mine1 = new MineSprite(6500, 4200);
		mine2 = new MineSprite(8250, 6400);
		mine3 = new MineSprite(5950, 7250);
		mine4 = new MineSprite(6350, 7000);
		mine5 = new MineSprite(6300, 5800);
		mine6 = new MineSprite(4300, 7400);
		mine7 = new MineSprite(4700, 4450);
		mine8 = new MineSprite(4650, 2500);
		mine9 = new MineSprite(5700, 2800);
		mine10 = new MineSprite(6150, 1700);
		
		unexplodedOrdnance1 = new UnexplodedOrdnanceSprite(2350, 6600);
		unexplodedOrdnance2 = new UnexplodedOrdnanceSprite(2400, 6650);
		unexplodedOrdnance3 = new UnexplodedOrdnanceSprite(2400, 6750);
		unexplodedOrdnance4 = new UnexplodedOrdnanceSprite(1700, 3750);
		unexplodedOrdnance5 = new UnexplodedOrdnanceSprite(4050, 4250);
		unexplodedOrdnance6 = new UnexplodedOrdnanceSprite(4800, 6350);
		unexplodedOrdnance7 = new UnexplodedOrdnanceSprite(7300, 3200);
		unexplodedOrdnance8 = new UnexplodedOrdnanceSprite(6200, 2700);
		unexplodedOrdnance9 = new UnexplodedOrdnanceSprite(5750, 3600);
		unexplodedOrdnance10 = new UnexplodedOrdnanceSprite(7550, 6000);
		
		rum1 = new RumSprite(1800, 8200);
		rum2 = new RumSprite(2900, 6650);
		
		bandage1 = new BandageSprite(4000, 1250);
		bandage2 = new BandageSprite(7450, 2500);
		bandage3 = new BandageSprite(7450, 4250);
		bandage4 = new BandageSprite(6150, 2450);
		bandage5 = new BandageSprite(6350, 8050);
		
		canteen1 = new CanteenSprite(2000, 6100);
		canteen2 = new CanteenSprite(4350, 6650);
		canteen3 = new CanteenSprite(7700, 1600);
		canteen4 = new CanteenSprite(7750, 3900);
		canteen5 = new CanteenSprite(6900, 3250);
		
		officer1 = new OfficerSprite(8000, 3050);
		
		soldier1 = new SoldierSprite(7750, 1400);
		soldier2 = new SoldierSprite(7750, 1500);
		soldier3 = new SoldierSprite(7750, 2450);
		soldier4 = new SoldierSprite(7750, 2800);
		soldier5 = new SoldierSprite(8100, 3050);
		soldier6 = new SoldierSprite(7750, 3700);
		soldier7 = new SoldierSprite(7750, 4050);
		soldier8 = new SoldierSprite(7750, 4400);
		soldier9 = new SoldierSprite(7750, 5000);
		soldier10 = new SoldierSprite(7750, 5550);
		
		sprites.add(mine1);
		sprites.add(mine2);
		sprites.add(mine3);
		sprites.add(mine4);
		sprites.add(mine5);
		sprites.add(mine6);
		sprites.add(mine7);
		sprites.add(mine8);
		sprites.add(mine9);
		sprites.add(mine10);
		
		sprites.add(unexplodedOrdnance1);
		sprites.add(unexplodedOrdnance2);
		sprites.add(unexplodedOrdnance3);
		sprites.add(unexplodedOrdnance4);
		sprites.add(unexplodedOrdnance5);
		sprites.add(unexplodedOrdnance6);
		sprites.add(unexplodedOrdnance7);
		sprites.add(unexplodedOrdnance8);
		sprites.add(unexplodedOrdnance9);
		sprites.add(unexplodedOrdnance10);
		
		sprites.add(rum1);
		sprites.add(rum2);
		
		sprites.add(bandage1);
		sprites.add(bandage2);
		sprites.add(bandage3);
		sprites.add(bandage4);
		sprites.add(bandage5);
		
		sprites.add(canteen1);
		sprites.add(canteen2);
		sprites.add(canteen3);
		sprites.add(canteen4);
		sprites.add(canteen5);
		
		sprites.add(officer1);
		
		sprites.add(soldier1);
		sprites.add(soldier2);
		sprites.add(soldier3);
		sprites.add(soldier4);
		sprites.add(soldier5);
		sprites.add(soldier6);
		sprites.add(soldier7);
		sprites.add(soldier8);
		sprites.add(soldier9);
		sprites.add(soldier10);
		
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
	        
	        this.centerX += (playerX - this.centerX);
	        this.centerY += (playerY - this.centerY); 
		
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
