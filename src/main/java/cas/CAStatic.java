package cas;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.relauncher.Side;


@Mod(modid = CAStatic.MODID, name = CAStatic.NAME, version = CAStatic.VERSION)
@EventBusSubscriber(Side.SERVER)
public class CAStatic {
    public static final String MODID = "castatic";
    public static final String NAME = "CA-Static";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @Mod.EventHandler
 // Define a method to set the gamerules
    public void setFireTickGamerule(FMLServerStartedEvent event) {
    	MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        WorldServer world = server.getWorld(0);
        GameRules rules = world.getGameRules();
        rules.setOrCreateGameRule("doFireTick", "false");
		rules.setOrCreateGameRule("doWeatherCycle", "false");
		rules.setOrCreateGameRule("commandBlockOutput", "false");
		rules.setOrCreateGameRule("mobGriefing", "false");
    }

    @SubscribeEvent
    public void onBlockNotify(BlockEvent.NeighborNotifyEvent event) {
        BlockPos blockPos = event.getPos();
        IBlockState state = event.getWorld().getBlockState(blockPos);
        Block block = state.getBlock();

        
        // Allow redstone to work
        if (block instanceof BlockPistonBase || block instanceof BlockPistonExtension || block instanceof BlockPistonMoving || block instanceof BlockPressurePlate || block instanceof BlockRedstoneDiode || block instanceof BlockRedstoneTorch || block instanceof BlockRedstoneWire) {
            return;
        }
          
        // Prevent snow layers from melting
        if (block instanceof BlockSnow) {
             event.setCanceled(true);
        }

        // Prevent lava and water from flowing, sand and gravel from falling
        if (block == Blocks.WATER || block == Blocks.FLOWING_WATER || block == Blocks.LAVA || block == Blocks.FLOWING_LAVA || block == Blocks.SAND || block == Blocks.GRAVEL) {
            event.setCanceled(true);
        }

        if (block == Blocks.WHEAT || block == Blocks.CARROTS || block == Blocks.POTATOES) {
            // Allow breaking crops
        } else if (block == Blocks.PORTAL || block == Blocks.END_PORTAL) {
            // Allow breaking portals
        } else if (block instanceof BlockTallGrass || block instanceof BlockVine) {
            // Allow breaking tall grass and vines
        } else {
            event.setCanceled(true);
        }

        // Prevent snow from forming
        if (block == Blocks.AIR) {
            if (block instanceof BlockSnow) {
                event.setCanceled(true);
            }
        }
        
        // Prevent snow and ice from melting
        if (block == Blocks.SNOW_LAYER || block == Blocks.SNOW) {
            if (event.getState().getBlock() == Blocks.AIR) {
                event.setCanceled(true);
            }
        }

        if (block == Blocks.ICE || block == Blocks.PACKED_ICE) {
            if (event.getState().getBlock() == Blocks.WATER || event.getState().getBlock() == Blocks.FLOWING_WATER) {
                event.setCanceled(true);
            }
        }

        // Prevent crops from breaking due to lack of support
        if (block instanceof BlockCrops) {
            if (!block.isSideSolid(state, event.getWorld(), blockPos.down(), EnumFacing.UP)) {
                event.setCanceled(true);
            }
        }
       
        }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (!event.world.isRemote) {
            Blocks.DIRT.setTickRandomly(false);
            Blocks.GRASS.setTickRandomly(false);
            Blocks.WATER.setTickRandomly(false);
            Blocks.SNOW.setTickRandomly(false);
            Blocks.ICE.setTickRandomly(false);
            Blocks.SNOW_LAYER.setTickRandomly(false);
            Blocks.PACKED_ICE.setTickRandomly(false);
            Blocks.WHEAT.setTickRandomly(false);
            Blocks.RED_MUSHROOM.setTickRandomly(false);
            Blocks.BROWN_MUSHROOM.setTickRandomly(false);
            Blocks.YELLOW_FLOWER.setTickRandomly(false);
            Blocks.RED_FLOWER.setTickRandomly(false);
            Blocks.DEADBUSH.setTickRandomly(false);
            Blocks.LEAVES.setTickRandomly(false);
            Blocks.FIRE.setTickRandomly(false);
            Blocks.CACTUS.setTickRandomly(false);
        }
    }
    

    @SubscribeEvent
    public void onBlockGrow(BlockEvent.NeighborNotifyEvent event) {
        Block block = event.getState().getBlock();
        if (block == Blocks.GRASS) {
            BlockPos blockPos = event.getPos();
            World world = event.getWorld();
            world.setBlockState(blockPos, Blocks.DIRT.getDefaultState());
            event.setCanceled(true);
        }
    }
    
}