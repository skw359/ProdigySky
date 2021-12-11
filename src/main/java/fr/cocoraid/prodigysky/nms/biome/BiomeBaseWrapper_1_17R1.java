package fr.cocoraid.prodigysky.nms.biome;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.biome.BiomeBase.Geography;
import net.minecraft.world.level.biome.BiomeBase.Precipitation;
import net.minecraft.world.level.biome.BiomeBase.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeSettingsGeneration.a;
import net.minecraft.world.level.levelgen.WorldGenStage.Decoration;

public class BiomeBaseWrapper_1_17R1 implements Biome {
    private String fogColor;
    private String waterColor;
    private String waterFogColor;
    private String skyColor;
    private String grassColor;
    private String foliageColor;

    public BiomeBaseWrapper_1_17R1() {
    }

    public BiomeBase build(String fogColor, String waterColor, String waterFogColor, String skyColor) {
        return this.build(fogColor, waterColor, waterFogColor, skyColor, (String)null, (String)null);
    }

    public BiomeBase build(String fogColor, String waterColor, String waterFogColor, String skyColor, String grassColor, String foliageColor) {
        this.fogColor = fogColor;
        this.waterColor = waterColor;
        this.waterFogColor = waterFogColor;
        this.skyColor = skyColor;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
        return this.build();
    }

    public BiomeBase build() {
        a gen = new a();

        try {
            Method method = gen.getClass().getDeclaredMethod("a", Integer.TYPE);
            method.setAccessible(true);
            method.invoke(gen, Decoration.j.ordinal());
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException var3) {
            var3.printStackTrace();
        }

        net.minecraft.world.level.biome.BiomeFog.a biomeFogCodec = (new net.minecraft.world.level.biome.BiomeFog.a()).a(Integer.parseInt(this.fogColor, 16)).b(Integer.parseInt(this.waterColor, 16)).c(Integer.parseInt(this.waterFogColor, 16)).d(Integer.parseInt(this.skyColor, 16));
        if (this.foliageColor != null) {
            biomeFogCodec.e(Integer.parseInt(this.foliageColor, 16));
        }

        if (this.grassColor != null) {
            biomeFogCodec.f(Integer.parseInt(this.grassColor, 16));
        }

        return (new net.minecraft.world.level.biome.BiomeBase.a()).a(Precipitation.a).a(Geography.a).a(0.0F).b(0.0F).a(TemperatureModifier.a).a(biomeFogCodec.a()).a(BiomeSettingsMobs.c).a(gen.a()).a();
    }
}
