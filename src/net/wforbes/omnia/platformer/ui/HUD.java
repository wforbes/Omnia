package net.wforbes.omnia.platformer.ui;

import net.wforbes.omnia.platformer.entity.Player;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {
	
	private Player player;
	private BufferedImage image;
	private Font font;

	private int width;
	private int height;
	private int scale;
	
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
		font = new Font("Century Gothic", Font.PLAIN, 14);
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
			width = image.getWidth();
			height = image.getHeight();
		}catch(Exception e){e.printStackTrace();}//end try catch
	}//end constructor

	public void draw(FXGraphics2D fxg){
		fxg.drawImage(image, 0, 10, width, height, null);
		fxg.setFont(font); //TODO: possible bug with size of this font?
		fxg.drawString(player.getHealth() + " / " + player.getMaxHealth(), 25, 25);
		fxg.drawString((player.getFire() / 100) + " / " + (player.getMaxFire() / 100), 25, 45);
	}
	
	public void draw(Graphics2D g){
		g.drawImage(image, 0, 10, null);
		g.setFont(font);
		g.drawString(player.getHealth() + " / " + player.getMaxHealth(), 25, 25 );
		g.drawString((player.getFire() / 100) + " / " + (player.getMaxFire() / 100), 25, 45 );
	}

}
