package tunacan.yalm;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class YALM implements ModInitializer {
	public static final String MODID = "yalm";
	
    public static MilkFluid stillMilk;
    public static MilkFluid flowingMilk;
    public static FluidBlock milkFluid;
    public static ModifiedMilkBucketItem modifiedMilkBucket;
    
	@Override
	public void onInitialize() {
		stillMilk = Registry.register(Registry.FLUID, new Identifier(MODID, "milk_still"), new MilkFluid.Still());
		flowingMilk = Registry.register(Registry.FLUID, new Identifier(MODID, "milk_flowing"), new MilkFluid.Flowing());
		
		modifiedMilkBucket = new ModifiedMilkBucketItem(new Item.Settings().maxCount(1).group(ItemGroup.MISC));
		Registry.register(Registry.ITEM, 604, "minecraft:milk_bucket", modifiedMilkBucket);

        milkFluid = new BaseFluidBlock(stillMilk, FabricBlockSettings.of(Material.WATER).dropsNothing().build());
        Registry.register(Registry.BLOCK, new Identifier(MODID, "milk_block"), milkFluid);
	}
}
