package fr.cocoraid.prodigysky.listeners;

import fr.cocoraid.prodigysky.feature.BiomeData;
import fr.prodigysky.api.EffectDuration;
import fr.prodigysky.api.ProdigySkyAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
    public EventListener() {
    }

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (ProdigySkyAPI.getBiomePlayers().containsKey(p.getUniqueId()) && ((BiomeData)ProdigySkyAPI.getBiomePlayers().get(p.getUniqueId())).getDuration() == EffectDuration.VOLATILE) {
            ProdigySkyAPI.getBiomePlayers().remove(p.getUniqueId());
        }

    }
}
