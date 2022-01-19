package net.minecraft.src;

import java.util.Random;

public class TSGBlockGloryhole extends Block implements FCIBlockMechanical {


	private static final int sandcookdelay = 40;
	private static final int mechcookdelay = 40;
	private static final int firecookdelay = 1200;

	public
	TSGBlockGloryhole(int id){
		super(id, Material.rock);
		setHardness(2F);
		setResistance(10F);
		setTickRandomly(true);
	}

	public boolean
	renderAsNormalBlock() {
		return false;
	}
	public boolean
	isOpaqueCube(){
		return false;
	}
	public boolean
	HasLargeCenterHardPointToFacing(IBlockAccess ba, int i, int j, int k, int face, boolean bt){
		return true;
	}
	public void
	onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player, ItemStack item){
		int cfac = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360F) + 0.5D) &3;
		int face;
		switch(cfac){
			case 0:
				face = 0;
				break;
			case 1:
				face = 3;
				break;
			case 2:
				face = 1;
				break;
			default:
				face = 2;
				break;
		}
		world.setBlockMetadataWithNotify(x,y,z,face);
	}
	public boolean
	CanInputMechanicalPower(){
		return true;
	}
	public boolean
	IsInputtingMechanicalPower(World world, int x, int y, int z){
		return FCUtilsMechPower.IsBlockPoweredByAxle(world, x, y, z, this);
	}
	public boolean
	CanInputAxlePowerToFacing(World world, int x, int y, int z, int face){
		int md = world.getBlockMetadata(x,y,z) & 3;
		switch(face){
			case 0:
			case 1: return false;
			case 2: return md == 1;
			case 3: return md == 0;
			case 4: return md == 3;
			case 5: return md == 2;
			default: return false;
		}
			
	}
	public void
	updateTick(World world, int i, int j, int k, Random rn){
		int md = world.getBlockMetadata(i,j,k);
		int state = md >> 2;
		switch(state){
			case 0:
			case 1:
				if(world.getBlockId(i, j+1, k) == Block.sand.blockID){
					world.setBlockMetadataWithNotify(i,j,k, (md & 3) | ((state<<2) | 8));
					world.setBlockToAir(i,j+1,k);
				} break;
			case 2:
				if(world.getBlockId(i, j-1,k) == Block.fire.blockID || world.getBlockId(i, j-1, k) == FCBetterThanWolves.fcBlockFireStoked.blockID){
					world.setBlockMetadataWithNotify(i,j,k,(md & 3) | 4 );
				} break;
			default:
				if(IsInputtingMechanicalPower(world,i,j,k) && (state & 1) == 1 && (world.getBlockId(i, j-1,k) == Block.fire.blockID || world.getBlockId(i, j-1, k) == FCBetterThanWolves.fcBlockFireStoked.blockID || world.getBlockId(i,j-1,k) == 0)){
					world.setBlockMetadataWithNotify(i,j,k, (md&3) | ((state & 2)<<2));
					world.setBlock(i,j-1,k, Block.glass.blockID);
				} break;				
		}
		
		int retstate = world.getBlockMetadata(i,j,k) >> 2;
		world.scheduleBlockUpdate(i,j,k,blockID, scheduleUpdateBasedOnState(retstate) * ((world.getBlockId(i,j-1,k) == FCBetterThanWolves.fcBlockFireStoked.blockID)?1:2));
	}
	private int
	scheduleUpdateBasedOnState(int state){
		switch (state){
			case 0: return sandcookdelay;
			case 2: return firecookdelay;
			default: return mechcookdelay;
		}
	}
	public void
	RandomUpdateTick(World world, int i, int j,int k, Random rn){
		if(!world.IsUpdateScheduledForBlock(i,j,k,blockID)){
			int md = world.getBlockMetadata(i,j,k);
			int state = md >> 2;
			world.scheduleBlockUpdate(i,j,k, blockID, scheduleUpdateBasedOnState(state));
		}
	}
	public void
	Overpower(World world, int x, int y, int z){
		FCUtilsItem.EjectStackWithRandomOffset(world, x, y, z, new ItemStack(Item.brick, 5));
		FCUtilsItem.EjectStackWithRandomOffset(world, x, y, z, new ItemStack(Block.pressurePlateIron));
		world.setBlockToAir(x,y,z);
	}
	public boolean
	IsOutputtingMechanicalPower(World world, int x, int y, int z){
		return false;
	}
	public boolean
	CanOutputMechanicalPower(){
		return false;
	}
	public boolean
	onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float cx, float cy, float cz){
		int md = world.getBlockMetadata(x,y,z);
		if( (md&4) != 4 || player == null || player.getHeldItem() == null || player.getHeldItem().itemID != TSGGloryhole.ghItemBlowpipe.itemID || cy > .5F){
			return false;
		}
		else if (world.isRemote){ 
			return true;
		}
		player.getHeldItem().itemID = TSGGloryhole.ghItemBlowpipeWorkable.itemID;
		world.setBlockMetadataWithNotify(x,y,z, md & ~4);
		return true;
	}

	/* Render Code */


	public MovingObjectPosition
	collisionRayTrace( World world, int i, int j, int k, Vec3 startRay, Vec3 endRay ){

		FCModelBlock model;
		int md = world.getBlockMetadata(i,j,k); /*1 sand, 1 glass, 11 rotate*/
		int face = (md&3) + 2;

		model = new FCModelBlock();
		model.AddBox(0D, 0D, 0D, 1D, 0.01D, 1D); /*Flat Vessel Texture as bottom*/
		model.AddBox(0D, 0.01D, 0D, 0.2D, 1D, 1D);
		model.AddBox(0.2D, 0.01D, 0.8D, 1D, 1D, 1D);
		model.AddBox(0.8D, 0.01D, 0D, 1D, 1D, 0.8D);
		model.AddBox(0.2D, 0.01D, 0D, 0.8D, 0.2D, 0.2D);
		model.AddBox(0.2D, 0.8D, 0D, 0.8D, 1D, 0.2D); /* Main block cube*/
		model.AddBox(0.2D, 0.8D, 0.2D, 0.8D, 1D, 0.3D);
		model.AddBox(0.2D, 0.8D, 0.3D, 0.3D, 1D, 0.8D);
		model.AddBox(0.7D, 0.8D, 0.3D, 0.8D, 1D, 0.8D);
		model.AddBox(0.3D, 0.8D, 0.7D, 0.7D, 1D, 0.8D);
		model.AddBox(0.3D, 0.7D, 0.3D, 0.7D, 0.8D, 0.4D);
		model.AddBox(0.3D, 0.7D, 0.4D, 0.4D, 0.8D, 0.7D);
		model.AddBox(0.6D, 0.7D, 0.3D, 0.7D, 0.8D, 0.7D);
		model.AddBox(0.3D, 0.7D, 0.6D, 0.6D, 0.8D, 0.7D);
		model.AddBox(0.2D, 0.48D, 0.2D, 0.8D, 0.52D, 0.8D); /*Hopper and shelf*/
		model.AddBox(0.2D, 0.52D, 0.2D, 0.2D + (double)(0.8D * ((md & 0x8) >> 3)), 0.7D, 0.2D + (double)(0.8D * ((md & 0x8) >> 3))); /*Sand*/
		model.AddBox(0.2D, 0.2D, 0.2D, 0.2D + (double)(0.8D * ((md & 0x4) >> 2)), 0.44D, 0.2D + (double)(0.8D * ((md & 0x4) >> 2))); /*Glass*/
		model.AddBox(0.5D - 0.125D, 0.5D - 0.125D, 1D, 0.5D + 0.125D, 0.5D + 0.125D, 1.01D); /*Axle on back*/


		model.RotateAroundJToFacing(face);
		return model.CollisionRayTrace(world,i,j,k, startRay, endRay);
	
	}

}
