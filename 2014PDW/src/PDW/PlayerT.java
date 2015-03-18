package PDW;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PlayerT {
	private int map_x;
	private int map_y;
	private int x;
	private int y;
	public int typ;
	public Image Tankup;
	public Image Tankright;
	public Image Tankdown;
	public Image Tankleft;
	public Image Schuss;
	public Image Picleben;
	
	
	public boolean up;
	public boolean left;
	public boolean right;
	public boolean down;
	public boolean uphilf;
	public boolean lefthilf;
	public boolean righthilf;
	public boolean downhilf;
	
	public int anzahlwin;
	public boolean hilfinit;
	
	
	public int sek;
	public int speed;
	public int schussx;
	public int schussy;
	
	public int leben;
	public int kills;
	
	public boolean ifschuss;
	public boolean helpifschussup;
	public boolean helpifschussdown;
	public boolean helpifschussleft;
	public boolean helpifschussright;

	public boolean imbusch;
	
	public boolean schiff;
	public boolean hartemun;
	public boolean schnell;
	public boolean item_leben;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getMap_x() {
		return map_x;
	}
	public void setMap_x(int map_x) {
		this.map_x = map_x;
	}
	public int getMap_y() {
		return map_y;
	}
	public void setMap_y(int map_y) {
		this.map_y = map_y;
	}
	public PlayerT() {
		
	}
	
	public PlayerT(int typ,int x,int y)
	{
		if(typ == 1){
			ImageIcon iitankup=new ImageIcon(this.getClass().getResource("Tankup.png"));
			Tankup=iitankup.getImage();
			ImageIcon iitankright=new ImageIcon(this.getClass().getResource("Tankright.png"));
			Tankright=iitankright.getImage();
			ImageIcon iitankdown=new ImageIcon(this.getClass().getResource("Tankdown.png"));
			Tankdown=iitankdown.getImage();
			ImageIcon iitankleft=new ImageIcon(this.getClass().getResource("Tankleft.png"));
			Tankleft=iitankleft.getImage();
			uphilf = true;
			
			
		}
		if(typ == 2){
			ImageIcon iitankup=new ImageIcon(this.getClass().getResource("Tankup2.png"));
			Tankup=iitankup.getImage();
			ImageIcon iitankright=new ImageIcon(this.getClass().getResource("Tankright2.png"));
			Tankright=iitankright.getImage();
			ImageIcon iitankdown=new ImageIcon(this.getClass().getResource("Tankdown2.png"));
			Tankdown=iitankdown.getImage();
			ImageIcon iitankleft=new ImageIcon(this.getClass().getResource("Tankleft2.png"));
			Tankleft=iitankleft.getImage();
			downhilf = true;
		}
			ImageIcon iischuss = new ImageIcon(this.getClass()
				.getResource("Schuss.png"));
			Schuss = iischuss.getImage();
			
			ImageIcon iipicleben = new ImageIcon(this.getClass().getResource(
					"Leben.png"));
			Picleben = iipicleben.getImage();
			
		this.typ=typ;
		this.y = y;
		this.x = x;

	}

	
}