package me.Sesantanove.CarryHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;

public class CommandHandler extends CommandBase {
    @Override
    public String getCommandName() {
        return "carryhelper";
    }

    @Override
    public String getCommandUsage( ICommandSender sender) {
        return "Open an UI to remember how many times you need to carry another dude";
    }
    private void incorrectcmd(){
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§7-----------------------\n§9§lCarry§a§lHelper §fCommands\n§7/carryhelper - Opens the GUI\n§7/carryhelper add <nick> <number> - Add <nick> to the carry list\n§7/carryhelper remove <nick> - Removes <nick> from carry list\n§7/carryhelper clear - Clears the carry list\n§7-----------------------"));
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            if (args.length > 0) {
                if (args[0].equals("add")) {
                    if (args.length == 3) {
                        GUI_Handler.carry.put(args[1], Integer.parseInt(args[2]));
                    } else {
                        incorrectcmd();
                    }
                }else if (args[0].equals("remove")) {
                    if (args.length == 2) {
                        GUI_Handler.carry.remove(args[1]);
                    } else {
                        incorrectcmd();
                    }
                }else if (args[0].equals("clear")) {
                    if (args.length == 2) {
                        GUI_Handler.carry=new HashMap<>();
                    } else {
                        incorrectcmd();
                    }
                }else {
                    incorrectcmd();
                }
            }else {
                Main.screenToOpenNextTick = new GUI_Handler();
                LogManager.getLogger("Comando Test GUI").info("Teoricamente dovrei aver aperto la GUI!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

}