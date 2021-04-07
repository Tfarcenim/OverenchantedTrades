package tfar.overenchantedtrades;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(OverenchantedTrades.MODID)
public class OverenchantedTrades
{
    // Directly reference a log4j logger.

    public static final String MODID = "overenchantedtrades";

    private static final Logger LOGGER = LogManager.getLogger();

    public OverenchantedTrades() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        bus.addListener(this::common);
        MinecraftForge.EVENT_BUS.addListener(this::setup);
    }


    private static final List<Item> enchantables = new ArrayList<>();

    private void common(FMLCommonSetupEvent e) {
        for (Item item : Registry.ITEM) {
            if (item instanceof TieredItem || item instanceof ArmorItem || item instanceof ShootableItem || item instanceof FishingRodItem) {
                enchantables.add(item);
            }
        }
    }

    private void setup(final VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();

        for (int i = 1; i < 6;i++) {
            List<VillagerTrades.ITrade> tradeList = trades.get(i);
            tradeList.clear();
            for (Item item : enchantables) {
                tradeList.add(new OverpoweredEnchantedItemForEmeraldTrade(item, 1, 3, 10, 0.2F));
            }
        }
    }
}
