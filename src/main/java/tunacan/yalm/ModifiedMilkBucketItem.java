package tunacan.yalm;

import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class ModifiedMilkBucketItem extends BucketItem {
	public Fluid fluid;
	
	public ModifiedMilkBucketItem(Item.Settings settings) {
		super(YALM.stillMilk, settings);
		this.fluid = YALM.stillMilk;
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
			Criterions.CONSUME_ITEM.handle(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		
		if (user instanceof PlayerEntity && !((PlayerEntity)user).abilities.creativeMode) {
			stack.decrement(1);
		}
		
		if (!world.isClient) {
			user.clearPotionEffects();
	    }

		return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 32;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		HitResult hitResult = rayTrace(world, user, this.fluid == Fluids.EMPTY ? RayTraceContext.FluidHandling.SOURCE_ONLY : RayTraceContext.FluidHandling.NONE);
		if (hitResult.getType() == HitResult.Type.MISS || hitResult.getType() != HitResult.Type.BLOCK) {
			user.setCurrentHand(hand);
			return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, user.getStackInHand(hand));
	    } else {
			TypedActionResult<ItemStack> result = super.use(world, user, hand);
			if (result.getResult() == ActionResult.SUCCESS) {
				if (!user.abilities.creativeMode) {
					itemStack = new ItemStack(Items.BUCKET);
				}
				return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, itemStack);
			}
	    }
		return new TypedActionResult<ItemStack>(ActionResult.FAIL, itemStack);
	}
}
