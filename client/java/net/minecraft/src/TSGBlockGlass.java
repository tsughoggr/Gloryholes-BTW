package net.minecraft.src;

public class TSGBlockGlass extends FCBlockGlass {
	public
	TSGBlockGlass(int id){
		super(id, Material.glass, false);
	}

	public boolean
	DoesBlockBreakSaw(World world, int x, int y, int z){	
		return false;
	}
}
