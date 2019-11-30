package tunacan.yalm;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateFactory;
import net.minecraft.world.ViewableWorld;

public abstract class MilkFluid extends BasicFluid {
	@Override
	public Item getBucketItem() {
		return YALM.modifiedMilkBucket;
	}

	@Override
	protected BlockState toBlockState(FluidState fluidState) {
		return YALM.milkFluid.getDefaultState().with(FluidBlock.LEVEL, method_15741(fluidState));
	}

	@Override
	public Fluid getFlowing() {
		return YALM.flowingMilk;
	}

	@Override
	public Fluid getStill() {
		return YALM.stillMilk;
	}

	@Override
	public boolean matchesType(Fluid fluid) {
        return fluid == YALM.flowingMilk || fluid == YALM.stillMilk;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.SOLID;
	}
	
	@Override
	protected int method_15733(ViewableWorld world) {
		return 3;
	}
	
    public static class Still extends MilkFluid {
        public boolean isStill(FluidState fluidState) {
            return true;
        }
        
        @Override
        public int getLevel(FluidState fluidState) {
            return 3;
        }
    }
    
    public static class Flowing extends MilkFluid {
        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
        
        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }
 
        @Override
        protected void appendProperties(StateFactory.Builder<Fluid, FluidState> stateFactoryBuilder) {
            super.appendProperties(stateFactoryBuilder);
            stateFactoryBuilder.add(LEVEL);
        }
    }
}
