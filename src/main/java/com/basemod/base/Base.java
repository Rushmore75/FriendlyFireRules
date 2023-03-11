package com.basemod.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

@Mod(modid = Base.MOD_ID, name = Base.NAME, version = Base.VERSION)
public class Base {

    public final static Gson gson = new GsonBuilder().create();
    public static final String MOD_ID = "friendlyfirerules";
    public static final String NAME = "Rushmore Tweaks";
    public static final String VERSION = "1.0.0";
    // public static final String ACEPTED_VERSIONS = "[1.12.2]";

    public static Logger getLogger() { return logger; } 

    @Instance
    public static Base instance;
    private static Logger logger; // used to print messages to our console output

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    /** This is the final initialization event. Register actions from other mods here*/
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CmdGetUniverse());
    }
    
}
