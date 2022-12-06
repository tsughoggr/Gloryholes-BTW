package btw.community.tsughoggr.gloryholes;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.StatCollector;

import btw.item.items.ProgressiveCraftingItem;
import btw.item.util.ItemUtils;
import java.util.List;

public class TSGItemBlowpipe extends ProgressiveCraftingItem{

	public TSGItemBlowpipe ( int id){
		super(id);
		setMaxStackSize(1);
	}

	public void
	addInformation(ItemStack is, EntityPlayer player, List taglist, boolean b4){
		if(is.hasTagCompound()){
			NBTTagCompound tag = is.getTagCompound().getCompoundTag("Blowing");
			if(tag != null && tag.hasKey("result")){
				ItemStack res = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("result"));
				taglist.add("Currently Blowing: " + StatCollector.translateToLocal(Item.itemsList[res.itemID].getUnlocalizedName() + ".name"));
			}
		}
	}



	public ItemStack
	onEaten(ItemStack stack, World world,  EntityPlayer player){
		world.playSoundAtEntity(player, "block.glass.break", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);


		if(!world.isRemote && stack.getTagCompound() != null && stack.getTagCompound().getCompoundTag("Blowing") != null){
			NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Blowing");
			ItemUtils.ejectStackWithRandomOffset(world, (int)player.posX, (int)player.posY, (int)player.posZ, ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("Blowing").getCompoundTag("result")));
		}

		return new ItemStack(TSGGloryhole.ghItemBlowpipe);
	}
 
	public int 
	getProgressiveCraftingMaxDamage(){
		return 4000;
	}

	public void
	playCraftingFX(ItemStack is, World world, EntityPlayer player){
		player.playSound( "mob.cow.say4", 
			0.25F + 0.25F * (float)world.rand.nextInt( 2 ), 
			( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );
	}
	public void
	updateUsingItem(ItemStack stack, World world, EntityPlayer player) {
		int scl;
		if(stack.getTagCompound() == null || stack.getTagCompound().getCompoundTag("Blowing") == null){
			return;
		}
		NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Blowing");
		if(tag != null && tag.hasKey("time")){
			scl = 4000 / tag.getInteger("time");
		} else {
			scl = 1;
		}
		/*From super*/
		int iUseCount = player.getItemInUseCount();
		
		if ( getMaxItemUseDuration( stack ) - iUseCount > getItemUseWarmupDuration() )
		{
			if ( iUseCount % 4 == 0 )
			{
				playCraftingFX( stack, world, player );
			}
			
			if ( !world.isRemote && iUseCount % PROGRESS_TIME_INTERVAL == 0 )
			{
				int iDamage = stack.getItemDamage();
				
				iDamage -= scl;
				
				if ( iDamage > 0 )
				{				
					stack.setItemDamage( iDamage );
				}
				else
				{
					// set item usage to immediately complete
					
					player.setItemInUseCount( 1 );
				}
			}
		}
	}
}
