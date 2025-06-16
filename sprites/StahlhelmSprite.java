import java.awt.Graphics;
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
	private double thirst = 100.00;
	

	
	public StahlhelmSprite(double centerX, double centerY) {

		//this.centerX = centerX;
		//this.centerY = centerY;
		
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

	public void setHealth(double health) {
		this.health = health;
	}
	
	public boolean centerOnPlayer() {
		return true;
	}
	
	public boolean barrierIntersects(BarrierSprite barrier) {
	    return this.getMaxX() > barrier.getMinX() && this.getMinX() < barrier.getMaxX() && this.getMaxY() > barrier.getMinY() && this.getMinY() < barrier.getMaxY();
	}
	
	public boolean mineIsInside(MineSprite mine) {
	    return this.getMinX() <= mine.getMinX() && this.getMaxX() >= mine.getMaxX() && this.getMinY() <= mine.getMinY() && this.getMaxY() >= mine.getMaxY();
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
		
		thirst -= 0.005;

		//double deltaSeconds = actual_delta_time / 1000.0;

		KeyboardInput keyboard = KeyboardInput.getKeyboard();

		double velocityX = 0;
		double velocityY = 0;

		if(health > 0 && thirst > 0) {
			//LEFT	
			if (keyboard.keyDown(37)) {
				velocityX = -VELOCITY;
				velocityX -= speedChange;
			}
			//UP
			if (keyboard.keyDown(38)) {
				velocityY = -VELOCITY;
				velocityY -= speedChange;
			}
			// RIGHT
			if (keyboard.keyDown(39)) {
				velocityX = +VELOCITY;
				velocityX += speedChange;
			}
			// DOWN
			if (keyboard.keyDown(40)) {
				velocityY = +VELOCITY;
				velocityY += speedChange;
			}
		} else {
		JOptionPane.showMessageDialog( null, "You are dead, not big suprise.", "Achtung!", 0);
		System.exit(0);
		}

		double deltaX = actual_delta_time * 0.001 * velocityX;
		double deltaY = actual_delta_time * 0.001 * velocityY;

		//collision detection
		boolean collidingWithBarrier = checkCollisionWithBarrier(universe.getSprites(), deltaX, deltaY);
		if (collidingWithBarrier == false) {
			this.centerX += deltaX;
			this.centerY += deltaY;
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

		for(DisplayableSprite sprite : universe.getSprites()) { 
			if(sprite instanceof UnexplodedOrdnanceSprite) {
				UnexplodedOrdnanceSprite UnexplodedOrdnance = (UnexplodedOrdnanceSprite) sprite;
				if(CollisionDetection.overlaps(this, UnexplodedOrdnance)) {
					UnexplodedOrdnance.setDispose(true); 
					health -= 50;
				} 
			}
		}
		for(DisplayableSprite sprite : universe.getSprites()) { 
			if(sprite instanceof RumSprite) {
				RumSprite Rum = (RumSprite) sprite;
				if(CollisionDetection.covers(this, Rum)) {
					Rum.setDispose(true); 
					health -= 10;
					speedChange += 50;
				} 
			}
		}
		for(DisplayableSprite sprite : universe.getSprites()) { 
			if(sprite instanceof BandageSprite) {
				BandageSprite Bandage = (BandageSprite) sprite;
				if(CollisionDetection.covers(this, Bandage)) {
					Bandage.setDispose(true); 
					health += 20;
				} 
			}
		}
		for(DisplayableSprite sprite : universe.getSprites()) { 
			if(sprite instanceof CanteenSprite) {
				CanteenSprite Canteen = (CanteenSprite) sprite;
				if(CollisionDetection.covers(this, Canteen)) {
					Canteen.setDispose(true); 
					thirst += 50;
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

	

	@Override
	public void setDispose(boolean dispose) {
		this.dispose = true;
	}

}
