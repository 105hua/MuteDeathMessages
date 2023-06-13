package me.joshdev.mutedeathmessages.commands;

import me.joshdev.mutedeathmessages.MuteDeathMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleDeathMessagesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            boolean canSeeDeathMsgs = !MuteDeathMessages.getToggleFromPlayer(player);
            MuteDeathMessages.setToggleForPlayer(player, canSeeDeathMsgs);
            if(canSeeDeathMsgs){
                TextComponent enabledComponent = Component.text(
                        "Death Messages are now enabled!",
                        NamedTextColor.GREEN,
                        TextDecoration.BOLD
                );
                player.sendMessage(enabledComponent);
            }else{
                TextComponent disabledComponent = Component.text(
                        "Death Messages are now disabled.",
                        NamedTextColor.RED,
                        TextDecoration.BOLD
                );
                player.sendMessage(disabledComponent);
            }
        }else{
            TextComponent invalidSenderComponent = Component.text("This command is for players only!");
            sender.sendMessage(invalidSenderComponent);
        }
        return true;
    }
}
