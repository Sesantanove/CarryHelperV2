package me.Sesantanove.CarryHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatHandler {
    private static long cooldown=0;
    @SubscribeEvent
    public void ChatHandler(ClientChatReceivedEvent e) {
        //Checks if the message recived contains ☠ Defeated so it can auto lap
        if (e.message.getUnformattedText().contains("☠ Defeated")) {
            //Cooldown since the message runs twice idk why, also prevent other mods to run this code multiple time
            if (System.currentTimeMillis()-cooldown<3000){
                return;
            }
            GUI_Handler gui = new GUI_Handler();
            cooldown=System.currentTimeMillis();
            if (gui.isAutoLapEnabled()) {
                gui.carrylap();
                //Logs for debugging
                System.out.println("Boss sconfitto, lap del party");
                Thread t1 = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("§9§lCarry§a§lHelper §7» You defeated the boss GG! I lapped your party!"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                t1.start();
            }

        }
        //Check if a trade was succesfull to add
        if (e.message.getUnformattedText().contains("Trade completed with")) {
            String nickname = e.message.getUnformattedText().replaceAll("Trade completed with", "").replaceAll("\\[VIP]", "").replaceAll("\\[VIP\\+]", "").replaceAll("\\[MVP]", "").replaceAll("\\[MVP\\+]", "").replaceAll("\\[MVP\\+\\+]", "").replaceAll("!", "").replaceAll(" ", "");
            String nicknamekeeprank = e.message.getFormattedText().replaceAll("§r§6Trade completed with", "").replaceAll("!", "");
            IChatComponent comp = new ChatComponentText("§9§lCarry§a§lHelper §7» §eClick me to add" + nicknamekeeprank + "§e to the list of people to carry");
            ChatStyle style = (new ChatStyle()).setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, "/carryhelper add " + nickname + " 1") {
                public Action func_150669_a() {
                    return Action.RUN_COMMAND;
                }
            });
            style.setChatHoverEvent(new HoverEvent(net.minecraft.event.HoverEvent.Action.SHOW_TEXT, new ChatComponentText("§eClicking me will add " + nickname + " to /carryhelper menu!")));
            comp.setChatStyle(style);
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(comp);
        }
    }
}