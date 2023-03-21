package io.github.frenchntoasty.cas;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;


@Mod(modid = CAStatic.MODID, name = CAStatic.NAME, version = CAStatic.VERSION)
@EventBusSubscriber(Side.SERVER)
public class CAStatic {
    public static final String MODID = "castatic";
    public static final String NAME = "CA-Static";
    public static final String VERSION = "1.0.2.1";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	
        MinecraftForge.EVENT_BUS.register(this);
    }
    

    
}