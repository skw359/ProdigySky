package fr.cocoraid.prodigysky.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import fr.cocoraid.prodigysky.ProdigySky;
import fr.cocoraid.prodigysky.feature.BiomeData;
import fr.prodigysky.api.ProdigySkyAPI;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import org.bukkit.entity.Player;

public class PacketListener {
    ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public PacketListener(final ProdigySky instance) {
        this.manager.addPacketListener(new PacketAdapter(instance, new PacketType[]{Server.MAP_CHUNK}) {
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player p = event.getPlayer();

                try {
                    PacketListener.this.debug(packet);
                } catch (IllegalAccessException var8) {
                    var8.printStackTrace();
                }

                if (ProdigySkyAPI.getBiomePlayers().containsKey(p.getUniqueId())) {
                    BiomeData biomeData = (BiomeData)ProdigySkyAPI.getBiomePlayers().get(p.getUniqueId());
                    if (biomeData.getWorld() != null && !biomeData.getWorld().equals(biomeData.getWorld())) {
                        return;
                    }

                    int[] biomeIDs = (int[])packet.getIntegerArrays().readSafely(0);
                    int biomeId = (Integer)instance.getConfiguration().getBiomes().get(biomeData.getName());
                    ClientboundLevelChunkPacketData data = (ClientboundLevelChunkPacketData)packet.getSpecificModifier(ClientboundLevelChunkPacketData.class).readSafely(0);
                    Arrays.fill(biomeIDs, biomeId);
                    packet.getIntegerArrays().write(0, biomeIDs);
                }

            }
        });
    }

    private void debug(Object object) throws IllegalAccessException {
        System.out.println(object.getClass().getSimpleName());
        Class<?> clazz = object.getClass();
        System.out.println("Fields:");
        Field[] var3 = clazz.getDeclaredFields();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            PrintStream var10000 = System.out;
            String var10001 = field.getClass().getSimpleName();
            var10000.println(var10001 + "\t" + field.getName());
            if (!field.getDeclaringClass().getSimpleName().equalsIgnoreCase("Object")) {
                this.debug(field.get(object));
            }
        }

        System.out.println("End of Fields");
    }
}

