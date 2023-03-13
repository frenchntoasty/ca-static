package io.github.frenchntoasty.mixin.mixins;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BlockLiquid.class)
public abstract class MixinBlockLiquid {
 
    @Shadow public abstract void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand);

    @Inject(method = "updateTick", at = @At("HEAD"), cancellable = true)
    private void onUpdateTick(World worldIn, BlockPos pos, IBlockState state, Random rand, CallbackInfo ci) {
        if (state.getBlock() == Blocks.WATER && !worldIn.canBlockSeeSky(pos)) {
            // Check if the block is water and is not exposed to the sky
            ci.cancel(); // Prevent the block from updating
        } else {
            updateTick(worldIn, pos, state, rand); // Call the original method to perform normal updates
        }
    }
}