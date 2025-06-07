import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StahlhelmSprite implements DisplayableSprite {

	private static Image image;	
	private double centerX = 0;
	private double centerY = 0;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;	

	private final double VELOCITY = 200;

	

	
	public StahlhelmSprite(double centerX, double centerY) {

		//this.centerX = centerX;
		//this.centerY = centerY;
		
		this.centerX = 100; 
	    this.centerY = 100;
		
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
	
	public boolean barrierIntersects(BarrierSprite barrier) {
	    return this.getMaxX() > barrier.getMinX() && this.getMinX() < barrier.getMaxX() && this.getMaxY() > barrier.getMinY() && this.getMinY() < barrier.getMaxY();
	}
	
	public boolean mineIsInside(MineSprite coin) {
	    return this.getMinX() <= coin.getMinX() && this.getMaxX() >= coin.getMaxX() && this.getMinY() <= coin.getMinY() && this.getMaxY() >= coin.getMaxY();
	}
	
	public void update(Universe universe, long actual_delta_time) {
		
		double deltaSeconds = actual_delta_time / 1000.0;
		double velocityX = 0;
		double velocityY = 0;
		
		KeyboardInput keyboard = KeyboardInput.getKeyboard();
		
		for(DisplayableSprite sprite : universe.getSprites()) {
	        if(sprite instanceof MineSprite) {
	            MineSprite coin = (MineSprite) sprite;
	            if(this.mineIsInside(coin)) {
	               
	              
					coin.setDispose(true);
	            }
	        }
	    }
		
		for(DisplayableSprite sprite : universe.getSprites()) {
			if(sprite instanceof BarrierSprite) {
	            BarrierSprite barrier = (BarrierSprite) sprite;
	            if(this.barrierIntersects(barrier)) {
	                centerX -= velocityX * deltaSeconds; 
	                centerY -= velocityY * deltaSeconds;
	            }
	        } else {

		//LEFT	
		if (keyboard.keyDown(65)) {
			velocityX = -VELOCITY;
		}
		//UP
		if (keyboard.keyDown(87)) {
			velocityY = -VELOCITY;			
		}
		// RIGHT
		if (keyboard.keyDown(68)) {
			velocityX += VELOCITY;
		}
		// DOWN
		if (keyboard.keyDown(83)) {
			velocityY += VELOCITY;			
		}

		double deltaX = actual_delta_time * 0.001 * velocityX;
        this.centerX += deltaX;
		
		double deltaY = actual_delta_time * 0.001 * velocityY;
    	this.centerY += deltaY;
	        }
		}
	}


	@Override
	public void setDispose(boolean dispose) {
		this.dispose = true;
	}

}
