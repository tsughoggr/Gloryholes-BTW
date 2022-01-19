package net.minecraft.src;


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
		CraftingManager.getInstance().RemoveRecipe(new ItemStack(Item.glassBottle, 3), new Object[] {"# #", " # ", '#', Block.glass});
		CraftingManager.getInstance().RemoveRecipe(new ItemStack(Block.thinGlass, 16), new Object[] {"###", "###", '#', Block.glass});
	}
	private static void
	addShapedCraftingRecipes(){
		CraftingManager.getInstance().addRecipe(
			new ItemStack(TSGGloryhole.ghBlockGloryhole),
			new Object[] {
				"IGI", "BPB", "BVB",
				'I', new ItemStack(Item.ingotIron),
				'G', new ItemStack(FCBetterThanWolves.fcItemGear),
				'B', new ItemStack(FCBetterThanWolves.fcBlockBrickLoose),
				'P', new ItemStack(Block.blockNetherQuartz),
				'V', new ItemStack(Block.fenceIron)
			}
		);
		CraftingManager.getInstance().addRecipe(
			new ItemStack(TSGGloryhole.ghItemBlowpipe),
			new Object[]{
				" IS", "ISI", "SI ",
				'I', new ItemStack(FCBetterThanWolves.fcItemNuggetIron),
				'S', new ItemStack(Item.stick)
			}
		);
	}
	private static void
	addSawRecipes(){
		FCCraftingManagerSaw.instance.addRecipe(new ItemStack[]{new ItemStack(Block.thinGlass,2)}, Block.glass, new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
	}
	private static void
	addBlowpipeRecipes(){
		NBTTagCompound blw = new NBTTagCompound();
		NBTTagCompound res = new NBTTagCompound();
		ItemStack rsl = new ItemStack(TSGGloryhole.ghItemBlowpipeWorkable);
		rsl.setItemDamage(3999);
		res.setCompoundTag( "result",
			(new ItemStack(Item.glassBottle)).writeToNBT(new NBTTagCompound())
		);
		res.setInteger("time", 200);
		blw.setCompoundTag("Blowing", res);
		rsl.setTagCompound(blw);

		CraftingManager.getInstance().addRecipe(
			rsl ,
			new Object[]{
				" S ", "SRS", "   ",
				'S', new ItemStack(FCBetterThanWolves.fcItemPileSand),
				'R', new ItemStack(TSGGloryhole.ghItemBlowpipeWorkable)
			}
		);

		res = null;
		blw = null;
		rsl = null;
	}
}
