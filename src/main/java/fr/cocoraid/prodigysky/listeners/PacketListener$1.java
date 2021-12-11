package fr.cocoraid.prodigysky.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import fr.cocoraid.prodigysky.ProdigySky;
import fr.cocoraid.prodigysky.feature.BiomeData;
import fr.prodigysky.api.ProdigySkyAPI;
import java.util.Arrays;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

class PacketListener$1 extends PacketAdapter {
    PacketListener$1(PacketListener this$0, Plugin plugin, PacketType[] types, ProdigySky var4) {
        super(plugin, types);
        this.this$0 = this$0;
        this.val$instance = var4;
    }

    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        Player p = event.getPlayer();

        try {
            this.this$0.debug(packet);
        } catch (IllegalAccessException var8) {
            var8.printStackTrace();
        }

        if (ProdigySkyAPI.getBiomePlayers().containsKey(p.getUniqueId())) {
            BiomeData biomeData = (BiomeData)ProdigySkyAPI.getBiomePlayers().get(p.getUniqueId());
            if (biomeData.getWorld() != null && !biomeData.getWorld().equals(biomeData.getWorld())) {
                return;
            }

            int[] biomeIDs = (int[])packet.getIntegerArrays().readSafely(0);
            int biomeId = (Integer)this.val$instance.getConfiguration().getBiomes().get(biomeData.getName());
            ClientboundLevelChunkPacketData data = (ClientboundLevelChunkPacketData)packet.getSpecificModifier(ClientboundLevelChunkPacketData.class).readSafely(0);
            Arrays.fill(biomeIDs, biomeId);
            packet.getIntegerArrays().write(0, biomeIDs);
        }

    }
}
