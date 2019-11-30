package tunacan.yalm;

import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.BaseFluid;

public class BaseFluidBlock extends FluidBlock {
	protected BaseFluidBlock(BaseFluid fluid, Settings settings) {
		super(fluid, settings);
	}
}
