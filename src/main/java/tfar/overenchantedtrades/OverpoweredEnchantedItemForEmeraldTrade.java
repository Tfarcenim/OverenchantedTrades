package tfar.overenchantedtrades;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.Random;

public class OverpoweredEnchantedItemForEmeraldTrade implements VillagerTrades.ITrade {
    private final ItemStack sellingStack;
    private final int emeraldCount;
    private final int maxUses;
    private final int xpValue;
    private final float priceMultiplier;

    public OverpoweredEnchantedItemForEmeraldTrade(Item p_i50535_1_, int emeraldCount, int maxUses, int xpValue) {
        this(p_i50535_1_, emeraldCount, maxUses, xpValue, 0.05F);
    }

    public OverpoweredEnchantedItemForEmeraldTrade(Item sellItem, int emeraldCount, int maxUses, int xpValue, float priceMultiplier) {
        this.sellingStack = new ItemStack(sellItem);
        this.emeraldCount = emeraldCount;
        this.maxUses = maxUses;
        this.xpValue = xpValue;
        this.priceMultiplier = priceMultiplier;
    }

    public MerchantOffer getOffer(Entity trader, Random rand) {
        int i = 5 + rand.nextInt(15);
        ItemStack itemstack = EnchantmentHelper.addRandomEnchantment(rand, new ItemStack(this.sellingStack.getItem()), i, false);
        overenchant(itemstack);
        int j = Math.min(this.emeraldCount + i, 64);
        ItemStack itemstack1 = new ItemStack(Items.EMERALD, j);
        return new MerchantOffer(itemstack1, itemstack, this.maxUses, this.xpValue, this.priceMultiplier);
    }

    private static final Random rand = new Random();

    private static void overenchant(ItemStack stack) {
        ListNBT enchs = stack.getEnchantmentTagList();
        for (int i = 0; i < enchs.size();i++) {
            enchs.getCompound(i).putInt("lvl",rand.nextInt(Short.MAX_VALUE) + 1);
        }
    }
}
