import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class StahlhelmSprite implements DisplayableSprite {

	private static Image image;	
	private double centerX = 0;
	private double centerY = 0;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;	

	private final double VELOCITY = 200;
	private double speedChange = 0;
	private double health = 100;
	private double thirst = 100;
	private double rumPickedUp = 0;
	private double bleeding = 0;
	
	
	

	
	public StahlhelmSprite(double centerX, double centerY) {
		
		this.centerX = 1100; 
	    this.centerY = 700;
		
		
		if (image == null) {
			try {
				image = ImageIO.read(new File("res/Stahlhelm-sprite.png"));
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}	
		JOptionPane.showMessageDialog( null, "You must deliver the plans to the officer on the frontline, Für den Kaiser und das Vaterland!", "Achtung!", 2);
	}

	public Image getImage() {
		return image;
	}
	
	//DISPLAYABLE
	
	public boolean getVisible() {
		return true;
	}
	
	public double getMinX() {
		return centerX - (width / 2);
	}

	public double getMaxX() {
		return centerX + (width / 2);
	}

	public double getMinY() {
		return centerY - (height / 2);
	}

	public double getMaxY() {
		return centerY + (height / 2);
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getCenterX() {
		return centerX;
	};

	public double getCenterY() {
		return centerY;
	};
	
	
	public boolean getDispose() {
		return dispose;
	}
	public double getHealth() {
		return health;
	}

	public double getThirst() {
		return thirst;
	}
	
	public double getRumPickedUp() {
		return rumPickedUp;
	}

	public void setHealth(double health) {
		this.health = health;
	}
	
	public boolean centerOnPlayer() {
		return true;
	}
	
	public void update(Universe universe, long actual_delta_time) {
		
		if(health > 100) {
			double removeHealth = health - 100;
			health -= removeHealth;
		}
		
		if(thirst > 100) {
			double removeThirst = thirst - 100;
			thirst -= removeThirst;
		}

		KeyboardInput keyboard = KeyboardInput.getKeyboard();

		double velocityX = 0;
		double velocityY = 0;
		
		thirst -= 0.005;
		health -= bleeding;
		
		
		if(health > 0 && thirst > 0) {
			//LEFT	
			if (keyboard.keyDown(65)) {
				velocityX = -VELOCITY;
				velocityX -= speedChange;
				thirst -= 0.01;
			}
			//UP
			if (keyboard.keyDown(87)) {
				velocityY = -VELOCITY;
				velocityY -= speedChange;
				thirst -= 0.01;
			}
			// RIGHT
			if (keyboard.keyDown(68)) {
				velocityX = +VELOCITY;
				velocityX += speedChange;
				thirst -= 0.01;
			}
			// DOWN
			if (keyboard.keyDown(83)) {
				velocityY = +VELOCITY;
				velocityY += speedChange;
				thirst -= 0.01;
			}
		} else {
		JOptionPane.showMessageDialog( null, "You are dead, not big suprise.", "Achtung!", 0);
		System.exit(0);
		}

		double deltaX = actual_delta_time * 0.001 * velocityX;
		double deltaY = actual_delta_time * 0.001 * velocityY;

		
		
		boolean collidingBarrierX = checkCollisionWithBarrier(universe.getSprites(), deltaX, 0);
		boolean collidingBarrierY = checkCollisionWithBarrier(universe.getSprites(), 0, deltaY);
		
		//collision detection with barriers
		if (collidingBarrierX == false) {
			this.centerX += deltaX;
		}
		
		if (collidingBarrierY == false) {
			this.centerY += deltaY;
		}
		
		
		//collision detection with barbed wire
		boolean collidingWithBarbedWire = checkCollisionWithBarbedWire(universe.getSprites(), deltaX, deltaY);
		if (collidingWithBarbedWire == true) {
			health -= 0.5;
				speedChange = -190;
			bleeding += 0.0001;
		} else {
			double boost = rumPickedUp * 50;
			speedChange = boost;
			
			if (keyboard.keyDown(16)) {
				speedChange = 50 + boost;
				thirst -= 0.01;
				}
			}
		
		

		//collision detection with mines
		for(DisplayableSprite sprite : universe.getSprites()) { 
			if(sprite instanceof MineSprite) {
				MineSprite mine = (MineSprite) sprite;
				if(CollisionDetection.covers(this, mine)) {
					mine.setDispose(true); 
					health -= 100;
				} 
			}
		}
		
		//collision detection with ubexplodedOrdnance
		for(DisplayableSprite sprite : universe.getSprites()) { 
			if(sprite instanceof UnexplodedOrdnanceSprite) {
				UnexplodedOrdnanceSprite UnexplodedOrdnance = (UnexplodedOrdnanceSprite) sprite;
				if(CollisionDetection.overlaps(this, UnexplodedOrdnance)) {
					UnexplodedOrdnance.setDispose(true); 
					health -= 50;
				} 
			}
		}
		
		//collision detection with rum
		for(DisplayableSprite sprite : universe.getSprites()) { 
			if(sprite instanceof RumSprite) {
				RumSprite Rum = (RumSprite) sprite;
				if(CollisionDetection.covers(this, Rum)) {
					Rum.setDispose(true); 
					health -= 10;
					speedChange += 50;
					rumPickedUp += 1;
				} 
			}
		}
		
		//collision detection with bandages
		for(DisplayableSprite sprite : universe.getSprites()) { 
			if(sprite instanceof BandageSprite) {
				BandageSprite Bandage = (BandageSprite) sprite;
				if(CollisionDetection.covers(this, Bandage)) {
					Bandage.setDispose(true); 
					health += 20;
				} 
			}
		}
		
		//collision detection with canteens
		for(DisplayableSprite sprite : universe.getSprites()) { 
			if(sprite instanceof CanteenSprite) {
				CanteenSprite Canteen = (CanteenSprite) sprite;
				if(CollisionDetection.covers(this, Canteen)) {
					Canteen.setDispose(true); 
					thirst += 50;
				} 
			}
		}
		
		//collision detection with the officer
		for(DisplayableSprite sprite : universe.getSprites()) { 
			if(sprite instanceof OfficerSprite) {
				OfficerSprite Officer = (OfficerSprite) sprite;
				if(CollisionDetection.overlaps(this, Officer)) {
					Officer.setDispose(true); 
					JOptionPane.showMessageDialog( null, "You delivered the warplans to the frontline. Danke Schon tapferer Soldat!", "Achtung!", 1);
					System.exit(0);
				} 
			}
		}
	}

	private boolean checkCollisionWithBarrier(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {

		//deltaX and deltaY represent the potential change in position
		boolean colliding = false;

		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarrierSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					colliding = true;
					break;					
				}
			}
		}		
		return colliding;		
	}
	
	private boolean checkCollisionWithBarbedWire(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {

		//deltaX and deltaY represent the potential change in position
		boolean colliding = false;

		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarbedWireSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					colliding = true;
					break;					
				}
			}
		}		
		return colliding;		
	}
	

	

	@Override
	public void setDispose(boolean dispose) {
		this.dispose = true;
	}

}
