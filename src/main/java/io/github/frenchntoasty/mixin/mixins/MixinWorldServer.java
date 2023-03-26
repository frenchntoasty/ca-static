package io.github.frenchntoasty.mixin.mixins;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldServer.class)
public class MixinWorldServer {
    @Redirect(method = "updateBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;canBlockFreezeNoWater(Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean redirectCanBlockFreezeNoWater(WorldServer instance, BlockPos blockPos) {
        return false;
    }

    @Redirect(method = "updateBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;canSnowAt(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private boolean redirectCanSnowAt(WorldServer instance, BlockPos blockPos, boolean checkLight) {
        return false;
    }
}