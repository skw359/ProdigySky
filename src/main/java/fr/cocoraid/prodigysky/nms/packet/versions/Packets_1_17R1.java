package fr.cocoraid.prodigysky.nms.packet.versions;

import fr.cocoraid.prodigysky.nms.packet.Packets;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.world.level.lighting.LightEngine;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Packets_1_17R1 implements Packets {
    public Packets_1_17R1() {
    }

    public void sendFakeBiome(Player player) {
        Iterator var2 = this.getChunkAround(player.getLocation().getChunk(), 10).iterator();

        while(var2.hasNext()) {
            Chunk chunk = (Chunk)var2.next();
            net.minecraft.world.level.chunk.Chunk c = ((CraftChunk)chunk).getHandle();
            LightEngine engine = c.q.l_();
            ((CraftPlayer)player).getHandle().b.a(new ClientboundLevelChunkWithLightPacket(c, engine, (BitSet)null, (BitSet)null, false));
        }

    }

    private Collection<Chunk> getChunkAround(Chunk origin, int radius) {
        World world = origin.getWorld();
        int length = radius * 2 + 1;
        Set<Chunk> chunks = new HashSet(length * length);
        int cX = origin.getX();
        int cZ = origin.getZ();

        for(int x = -radius; x <= radius; ++x) {
            for(int z = -radius; z <= radius; ++z) {
                chunks.add(world.getChunkAt(cX + x, cZ + z));
            }
        }

        return chunks;
    }
}
