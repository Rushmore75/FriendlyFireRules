package com.friendlyfirerules.base;

import com.friendlyfirerules.base.init.ModTab;
import com.friendlyfirerules.base.proxy.CommonProxy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Base.MOD_ID, name = Base.NAME, version = Base.VERSION)
public class Base {

    //==============================================
    //          Variables
    //==============================================

    public final static Gson gson = new GsonBuilder().create();
    public static final String MOD_ID = "friendlyfirerules";
    public static final String NAME = "Rushmore Tweaks";
    public static final String VERSION = "1.0.0";
    public static final String CLIENT_PROXY_CLASS = "com.friendlyfirerules.base.proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "com.friendlyfirerules.base.proxy.CommonProxy";
    public static final CreativeTabs MOD_TAB = new ModTab("creativeTab");
    
    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @Instance
    public static Base instance;
    private static Logger logger; // used to print messages to our console output

    //==============================================
    //          Methods
    //==============================================

    public static Logger getLogger() { return logger; }

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }
}
