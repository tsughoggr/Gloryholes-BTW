package btw.community.tsughoggr.gloryholes;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.StatCollector;


import btw.item.BTWItems;
import btw.crafting.manager.SawCraftingManager;
import btw.block.BTWBlocks;

public abstract class TSGGloryHoleRecipes{
	public static void
	addAllRecipes(){
		removeVanillaGlassRecipes();
		addShapedCraftingRecipes();
		addSawRecipes();
		addBlowpipeRecipes();
	}
	private static void
	removeVanillaGlassRecipes(){
		CraftingManager.getInstance().removeRecipe(new ItemStack(Item.glassBottle, 3), new Object[] {"# #", " # ", '#', Block.glass});
		CraftingManager.getInstance().removeRecipe(new ItemStack(Block.thinGlass, 16), new Object[] {"###", "###", '#', Block.glass});
	}
	private static void
	addShapedCraftingRecipes(){
		CraftingManager.getInstance().addRecipe(
			new ItemStack(TSGGloryhole.ghBlockGloryhole),
			new Object[] {
				"IGI", "BPB", "BVB",
				'I', new ItemStack(Item.ingotIron),
				'G', new ItemStack(BTWItems.gear),
				'B', new ItemStack(BTWBlocks.looseNetherBrick),
				'P', new ItemStack(Block.blockNetherQuartz),
				'V', new ItemStack(Block.fenceIron)
			}
		);
		CraftingManager.getInstance().addRecipe(
			new ItemStack(TSGGloryhole.ghItemBlowpipe),
			new Object[]{
				" IS", "ISI", "SI ",
				'I', new ItemStack(BTWItems.ironNugget),
				'S', new ItemStack(Item.stick)
			}
		);
	}
	private static void
	addSawRecipes(){
		SawCraftingManager.instance.addRecipe(new ItemStack[]{new ItemStack(Block.thinGlass,2)}, Block.glass, new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
	}
	public static void
	addBlowpipeRecipe(ItemStack result, int time, Object[] recipe){

		NBTTagCompound blw = new NBTTagCompound();
		NBTTagCompound res = new NBTTagCompound();
		ItemStack rsl = new ItemStack(TSGGloryhole.ghItemBlowpipeWorkable);
		rsl.setItemDamage(3999);
		res.setCompoundTag( "result",
			result.writeToNBT(new NBTTagCompound())
		);
		res.setInteger("time", time);
		blw.setCompoundTag("Blowing", res);
		rsl.setTagCompound(blw);

		CraftingManager.getInstance().addRecipe(
			rsl ,
			recipe
		);

		res = null;
		blw = null;
		rsl = null;

	}
	private static void
	addBlowpipeRecipes(){
		addBlowpipeRecipe(new ItemStack(Item.glassBottle), 200, new Object[]{
				" S ", "SRS", "   ",
				'S', new ItemStack(BTWItems.sandPile),
				'R', new ItemStack(TSGGloryhole.ghItemBlowpipeWorkable)
			}
		);
	}
}
