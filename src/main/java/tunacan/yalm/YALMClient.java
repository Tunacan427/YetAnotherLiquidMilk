package tunacan.yalm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.impl.client.texture.FabricSprite;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ExtendedBlockView;

public class YALMClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        // adding the sprites to the block texture atlas
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register((spriteAtlasTexture, registry) -> {
            Identifier stillSpriteLocation = new Identifier("yalm:block/milk_still");
            Identifier dynamicSpriteLocation = new Identifier("yalm:block/milk_flow");
            // here I tell to use only 16x16 area of the water texture
            FabricSprite stillMilkSprite = new FabricSprite(stillSpriteLocation, 16, 16);
            // same, but 32
            FabricSprite dynamicMilkSprite = new FabricSprite(dynamicSpriteLocation, 32, 32);
            
            registry.register(stillMilkSprite);
            registry.register(dynamicMilkSprite);
            
            FluidRenderHandler milkRenderHandler = new FluidRenderHandler() {
                @Override
                public Sprite[] getFluidSprites(ExtendedBlockView extendedBlockView, BlockPos blockPos, FluidState fluidState) {
                    return new Sprite[] {stillMilkSprite, dynamicMilkSprite};
                }
                
                @Override
                public int getFluidColor(ExtendedBlockView view, BlockPos pos, FluidState state) {
                    return 0xeefced;
                }
            };
            
            FluidRenderHandlerRegistry.INSTANCE.register(YALM.stillMilk, milkRenderHandler);
            FluidRenderHandlerRegistry.INSTANCE.register(YALM.flowingMilk, milkRenderHandler);
        });
	}
}
