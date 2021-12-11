package fr.cocoraid.prodigysky.nms.biome;

import net.minecraft.world.level.biome.BiomeBase;

public interface Biome {
    BiomeBase build(String var1, String var2, String var3, String var4, String var5, String var6);

    BiomeBase build(String var1, String var2, String var3, String var4);
}