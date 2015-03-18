package PDW;

import java.util.ArrayList;

public class MapT {
	
	public int block_width = 32;
	 public int map_x = 1;
	public int map_y = 0;
	public int map_height = 26;
    public int map_width = 26;
    
    
    ArrayList<HindernissT> ziegelbloecke = new ArrayList<HindernissT>(); 
  
	
	int[] mapstucture = new int[map_height*map_width];
	
	
   
	
	public MapT(int[] mapstucture)
	{
	        this.mapstucture = mapstucture;
	}
	public MapT()
	{
		
	}
	
}
