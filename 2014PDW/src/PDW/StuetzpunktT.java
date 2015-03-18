package PDW;

import java.awt.Image;

import javax.swing.ImageIcon;

public class StuetzpunktT {
	private int x;
	private int y;
	public int typ;
	public Image Stuetzpunkt;
		
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public StuetzpunktT() {
		
	}
	
	public StuetzpunktT(String str,int typ,int x,int y)
	{
		ImageIcon iistuetzpunkt=new ImageIcon(this.getClass().getResource(str));
		Stuetzpunkt=iistuetzpunkt.getImage();
		this.typ=typ;
		this.y = y;
		this.x = x;

	}
	
}