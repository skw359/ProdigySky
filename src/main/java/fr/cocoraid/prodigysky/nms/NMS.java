package fr.cocoraid.prodigysky.nms;

import fr.cocoraid.prodigysky.ProdigySky;
import fr.cocoraid.prodigysky.nms.biome.Biome;
import fr.cocoraid.prodigysky.nms.biome.BiomeBaseWrapper_1_17R1;
import fr.cocoraid.prodigysky.nms.packet.Packets;
import fr.cocoraid.prodigysky.nms.packet.versions.Packets_1_17R1;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class NMS {
    private Packets packets;
    private Biome biome;

    public NMS(ProdigySky instance) {
        ConsoleCommandSender var2 = Bukkit.getConsoleSender();

        try {
            this.packets = new Packets_1_17R1();
            this.biome = new BiomeBaseWrapper_1_17R1();
        } catch (ArrayIndexOutOfBoundsException var4) {
            var4.printStackTrace();
        }

    }

    public Biome getBiome() {
        return this.biome;
    }

    public Packets getPackets() {
        return this.packets;
    }
}