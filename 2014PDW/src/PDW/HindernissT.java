package PDW;

import java.awt.Image;

import javax.swing.ImageIcon;

public class HindernissT {
	private int x;
	private int y;
	public int typ;
	public Image Hinderniss;
		
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public HindernissT() {
		
	}
	
	public HindernissT(String str,int typ,int x,int y)
	{
		ImageIcon iihinderniss=new ImageIcon(this.getClass().getResource(str));
		Hinderniss=iihinderniss.getImage();
		this.typ=typ;
		this.y = y;
		this.x = x;

	}
	
}
