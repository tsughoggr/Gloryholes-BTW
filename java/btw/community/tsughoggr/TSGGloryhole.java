package btw.community.tsughoggr.gloryholes;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import btw.AddonHandler;
import btw.BTWAddon;


import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.CreativeTabs;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public class TSGGloryhole extends BTWAddon{
	public static TSGGloryhole instance;
	public static final String gloryholeVersion = "0.1";

	public static Block ghBlockGloryhole;
	public static Block ghBlockAutoblowpipe;

	public static Item ghItemBlowpipe;
	public static Item ghItemBlowpipeWorkable;

	private Map<String, String> confprop;


	
	public
	TSGGloryhole(){
		super("Gloryholes", gloryholeVersion, "GH");
		instance = this;
	}
	
	public static TSGGloryhole
	getInstance(){
		if(instance == null)
			instance = new TSGGloryhole();
		return instance;
	}
	private void
	registerProperty(String st, int in){
		registerProperty(st, Integer.toString(in));
	}
	private void
	normalizeProperty(String prop, int def){
		if(confprop.get(prop) == null){
			confprop.put(prop, Integer.toString(def));
		}
		try { 
			Integer.parseInt(confprop.get(prop));
		} catch (NumberFormatException e){
			confprop.put(prop, Integer.toString(def));
		}
		

	}
	private int
	getIdFromProp(String prop){
		if(confprop == null)
			throw new InvalidConfigException("Config map is null after sanitization");
		if(confprop.get(prop) == null)
			throw new InvalidConfigException("Config property: " + prop +" is called but not defined");
		return Integer.parseInt(confprop.get(prop)); /*We would actually want this to crash if it throws NFE*/
	}



	public void
	preInitialize(){
		registerProperty("ghBlockGloryhole.blockID", 1750);
		registerProperty("ghBlockAutoblowpipe.blockID",1751);
	
		registerProperty("ghItemBlowpipe.itemID", 24020);
		registerProperty("ghItemBlowpipeWorkable.itemID", 24021);

		registerProperty("EnableBlowpipeBlock", "true");
	}
	private void
	SanitizeConfigMap(){
		if(confprop == null){
			confprop = new HashMap<String, String>();
		}
		normalizeProperty("ghBlockGloryhole.blockID", 1750);
		normalizeProperty("ghBlockAutoblowpipe.blockID",1751);

		normalizeProperty("ghItemBlowpipe.itemID", 24020);
		normalizeProperty("ghItemBlowpipeWorkable.itemID", 24021);

	}
	public void
	initialize(){
		AddonHandler.logMessage("Gloryholes " + gloryholeVersion + " initializing");
		confprop = loadConfigProperties();
		this.SanitizeConfigMap();
		this.InitializeBlocks();
		this.InitializeItems();
		this.ReplaceBlocks();
		TSGGloryHoleRecipes.addAllRecipes();
		AddonHandler.logMessage("Gloryhole initializaiton complete");
	}
	public void
	ReplaceBlocks(){
	}
	public void
	InitializeBlocks(){
		ghBlockGloryhole = new TSGBlockGloryhole(getIdFromProp("ghBlockGloryhole.blockID")).setUnlocalizedName("ghBlockGlorythole").setCreativeTab(CreativeTabs.tabDecorations);



		new ItemBlock(getIdFromProp("ghBlockGloryhole.blockID") - 256 );
	}
	public void
	InitializeItems(){
		ghItemBlowpipe = new Item(getIdFromProp("ghItemBlowpipe.itemID")).setMaxStackSize(1).setUnlocalizedName("ghItemBlowpipe").setCreativeTab(CreativeTabs.tabMaterials);
		ghItemBlowpipeWorkable = new TSGItemBlowpipe(getIdFromProp("ghItemBlowpipeWorkable.itemID")).setUnlocalizedName("ghItemBlowpipeWorkable").setCreativeTab(CreativeTabs.tabMaterials);
	}

	public void
	onLanguageLoaded(StringTranslate st){
		Properties t = st.getTranslateTable();

		t.put(ghBlockGloryhole.getUnlocalizedName() + ".name", "Gloryhole");
		t.put(ghItemBlowpipe.getUnlocalizedName() + ".name", "Blowing Rod");
		t.put(ghItemBlowpipeWorkable.getUnlocalizedName() + ".name", "Blowing Rod");

	}
}
