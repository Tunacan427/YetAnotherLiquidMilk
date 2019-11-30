package tunacan.yalm.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import tunacan.yalm.YALM;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {
	protected CowEntityMixin(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}

	public boolean interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() == Items.BUCKET && !player.abilities.creativeMode && !this.isBaby()) {
			player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
			itemStack.decrement(1);
			if (itemStack.isEmpty()) {
				player.setStackInHand(hand, new ItemStack(YALM.modifiedMilkBucket));
			} else if (!player.inventory.insertStack(new ItemStack(YALM.modifiedMilkBucket))) {
				player.dropItem(new ItemStack(YALM.modifiedMilkBucket), false);
			}
			
			return true;
		} else {
			return super.interactMob(player, hand);
		}
	}
}
