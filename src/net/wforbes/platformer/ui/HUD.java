package net.wforbes.platformer.ui;

import net.wforbes.platformer.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {
	
	private Player player;
	private BufferedImage image;
	private Font font;
	
	//constructor
	public HUD(Player player){
		this.player = player;
		font = new Font("Century Gothic", Font.PLAIN, 14);
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
		}catch(Exception e){e.printStackTrace();}//end try catch
	}//end constructor
	
	public void draw(Graphics2D g){
		g.drawImage(image, 0, 10, null);
		g.setFont(font);
		g.drawString(player.getHealth() + " / " + player.getMaxHealth(), 25, 25 );
		g.drawString((player.getFire() / 100) + " / " + (player.getMaxFire() / 100), 25, 45 );
	}

}
