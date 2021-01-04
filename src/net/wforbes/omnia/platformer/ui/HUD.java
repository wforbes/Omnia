package net.wforbes.omnia.platformer.ui;

import javafx.scene.canvas.GraphicsContext;
import net.wforbes.omnia.gameFX.OmniaFX;
import net.wforbes.omnia.platformer.entity.Player;
import org.jfree.fx.FXGraphics2D;

import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {
	
	private Player player;
	private BufferedImage image;
	private Font font;

	private Image img;

	private int width;
	private int height;
	private int fxScale;
	
	//constructor
	public HUD(Player player){
		this.player = player;
		font = new Font("Century Gothic", Font.PLAIN, 14);
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
			width = image.getWidth();
			height = image.getHeight();
		}catch(Exception e){e.printStackTrace();}//end try catch
	}//end constructor

	//constructor
	public HUD(Player player, String type){
		if(!type.equals("fx"))
			return;
		this.player = player;
		this.fxScale = OmniaFX.getScale();
		this.font = new Font("Century Gothic", Font.PLAIN, 14 * fxScale);
		try{
			img = new Image("/HUD/hud.gif");
			//image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
			width = (int)img.getWidth();
			height = (int)img.getHeight();
		}catch(Exception e){e.printStackTrace();}//end try catch
	}//end constructor

	public void draw(GraphicsContext gc) {
		gc.drawImage(img, 0, 10, width * fxScale, height * fxScale);
		gc.setFont(new javafx.scene.text.Font("Century Gothic", 14*fxScale));
		gc.fillText(player.getHealth() + " / " + player.getMaxHealth(), 18*fxScale, 17*fxScale);
		gc.fillText((player.getFire() / 100) + " / " + (player.getMaxFire() / 100), 18*fxScale, 38*fxScale);
	}

	public void draw(FXGraphics2D fxg){
		fxg.drawImage(image, 0, 10, width * OmniaFX.getScale(), height * OmniaFX.getScale(), null);
		fxg.setFont(new Font("Century Gothic", Font.PLAIN, (14 * fxScale))); //TODO: possible bug with size of this font?
		System.out.println(fxg.getFont().getSize());
		//fxg.setFont(this.font);
		fxg.drawString(player.getHealth() + " / " + player.getMaxHealth(), 25*OmniaFX.getScale(), 25*OmniaFX.getScale());
		//fxg.drawString((player.getFire() / 100) + " / " + (player.getMaxFire() / 100), 25*OmniaFX.getScale(), 45*OmniaFX.getScale());
	}
	
	public void draw(Graphics2D g){
		g.drawImage(image, 0, 10, null);
		g.setFont(font);
		g.drawString(player.getHealth() + " / " + player.getMaxHealth(), 25, 25 );
		g.drawString((player.getFire() / 100) + " / " + (player.getMaxFire() / 100), 25, 45 );
	}

}
