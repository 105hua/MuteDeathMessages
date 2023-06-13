package me.joshdev.mutedeathmessages.events;

import me.joshdev.mutedeathmessages.MuteDeathMessages;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Component deathMsg = event.deathMessage(); // Get death message.
        event.deathMessage(null); // Null death message that's broadcasted to the server.
        if(deathMsg != null){ // Only broadcast if death msg is not null.
            for(Player player: event.getPlayer().getServer().getOnlinePlayers()){
                boolean canSeeDeathMsgs = MuteDeathMessages.getToggleFromPlayer(player);
                if(canSeeDeathMsgs){
                    player.sendMessage(deathMsg);
                }
            }
        }
    }
}
