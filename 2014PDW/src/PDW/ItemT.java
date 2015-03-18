package PDW;

import java.awt.Image;

import javax.swing.ImageIcon;

	public class ItemT {
		public int x;
		public int y;
		public Image Item;
		public int typ;
		
		public ItemT() {
			
		}
		
		public ItemT(String str,int zahl)
		{
			ImageIcon iiitem=new ImageIcon(this.getClass().getResource(str));
			Item=iiitem.getImage();
			typ=zahl;
		}
		
		public void setzeItem(int Rand_x, int Rand_y,int Size)
		{
			int r = (int) (Math.random() * Rand_x);
	        this.x = ((r * Size));
	        r = (int) (Math.random() * Rand_y);
	        this.y = ((r * Size));

		}
		
		
	}

