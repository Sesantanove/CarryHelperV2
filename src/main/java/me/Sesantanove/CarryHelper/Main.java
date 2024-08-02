package me.Sesantanove.CarryHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "carryhelper", name = "CarryHelper", version = "1.1-Stable", useMetadata = true)
public class Main {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new GUI_Handler());
        MinecraftForge.EVENT_BUS.register(new ChatHandler());
        ClientCommandHandler.instance.registerCommand(new CommandHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }
    public static GuiScreen screenToOpenNextTick = null;

    @SubscribeEvent
    public void onTick( TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) return;
        if (screenToOpenNextTick != null) {
            Minecraft.getMinecraft().displayGuiScreen(screenToOpenNextTick);
            screenToOpenNextTick = null;
        }
    }
}
