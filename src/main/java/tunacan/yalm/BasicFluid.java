package tunacan.yalm;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;

public abstract class BasicFluid extends BaseFluid {
    /**
     * @return does it produce infinite fluid (like water)?
     */
    @Override
    protected boolean isInfinite() {
        return false;
    }
 
    // make it transparent
    @Override
    protected BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
 
    /**
     *
     * @return an associated item that "holds" this fluid
     */
    @Override
    public abstract Item getBucketItem();
 
    /**
     *
     * @return a blockstate of the associated {@linkplain net.minecraft.block.FluidBlock} with {@linkplain net.minecraft.block.FluidBlock#LEVEL}
     */
    @Override
    protected abstract BlockState toBlockState(FluidState fluidState);
 
    /**
     *
     * @return flowing static instance of this fluid
     */
    @Override
    public abstract Fluid getFlowing();
 
    /**
     *
     * @return still static instance of this fluid
     */
    @Override
    public abstract Fluid getStill();
 
    // how much does the height of the fluid block decreases
    @Override
    protected int getLevelDecreasePerBlock(ViewableWorld world)
    {
        return 1;
    }
 
    /**
     * 
     * @return update rate
     */
    @Override
    public int getTickRate(ViewableWorld world)
    {
        return 5;
    }
 
    @Override
    protected float getBlastResistance()
    {
        return 100.0F;
    }
 
    // this seems to determine fluid's spread speed (higher value means faster)
    @Override
    protected int method_15733(ViewableWorld world)
    {
        return 4;
    }
 
    // I don't know what this does, but it's present in the water fluid
    @Override
    protected void beforeBreakingBlock(IWorld world, BlockPos blockPos, BlockState blockState) {
        BlockEntity blockEntity = blockState.getBlock().hasBlockEntity() ? world.getBlockEntity(blockPos) : null;
        Block.dropStacks(blockState, world.getWorld(), blockPos, blockEntity);
    }
 
    // also don't know what it does
    public boolean method_15777(FluidState fluidState, BlockView blockView, BlockPos blockPos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN;
    }
 
    /**
     *
     * @return is given fluid instance of this fluid?
     */
    @Override
    public abstract boolean matchesType(Fluid fluid);
}