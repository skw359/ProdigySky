package fr.cocoraid.prodigysky.filemanager;

import com.mojang.serialization.Lifecycle;
import fr.cocoraid.prodigysky.ProdigySky;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.biome.BiomeBase;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;

public class Configuration {
    private Map<String, Integer> biomes = new HashMap();
    private static final Pattern HEX_PATTERN = Pattern.compile("^([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    private List<World> enabledWorlds = new ArrayList();
    private ProdigySky instance;
    private static final String ENABLED_WORLD = "enabled-worlds";

    public Configuration(ProdigySky plugin) {
        this.instance = plugin;
    }

    public Configuration init() {
        File file = new File(this.instance.getDataFolder(), "configuration.yml");
        if (!file.exists()) {
            this.instance.saveResource("configuration.yml", false);
        }

        return this;
    }

    public void load() {
        ConsoleCommandSender cc = Bukkit.getConsoleSender();
        File file = new File(this.instance.getDataFolder(), "configuration.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        if (data.isSet("enabled-worlds")) {
            List<String> worlds = data.getStringList("enabled-worlds");
            Iterator var5 = worlds.iterator();

            while(var5.hasNext()) {
                String world = (String)var5.next();
                World w = Bukkit.getWorld(world);
                if (w == null) {
                    cc.sendMessage("Â§6[ProdigySky Warning] Â§4 " + world + " Â§cdoes not exist for enabled-world in configuration.yml");
                } else {
                    this.enabledWorlds.add(w);
                }
            }
        }

        DedicatedServer ds = ((CraftServer)Bukkit.getServer()).getHandle().b();
        ConfigurationSection section = data.getConfigurationSection("custom_colors");
        if (data.isSet("custom_colors")) {
            Iterator var21 = section.getKeys(false).iterator();

            while(true) {
                while(var21.hasNext()) {
                    String colorName = (String)var21.next();
                    if (this.biomes.containsKey(colorName)) {
                        Bukkit.getLogger().log(Level.WARNING, "Color name " + colorName + " already exists !");
                    } else {
                        ResourceKey<BiomeBase> newKey = ResourceKey.a(IRegistry.aR, new MinecraftKey(colorName));
                        String key = colorName + ".";
                        String fog = section.getString(key + "fog");
                        String sky = section.getString(key + "sky");
                        String water = section.getString(key + "water");
                        String waterFog = section.getString(key + "water_fog");
                        String foliage = section.getString(key + "foliage");
                        String grass = section.getString(key + "grass");
                        if (this.isHex(fog) && this.isHex(sky) && this.isHex(water) && this.isHex(waterFog) && (foliage == null || this.isHex(foliage)) && (grass == null || this.isHex(grass))) {
                            BiomeBase biomeBase = this.instance.getNMS().getBiome().build(fog, water, waterFog, sky, grass, foliage);
                            IRegistryWritable<BiomeBase> rw = ds.aV().b(IRegistry.aR);
                            rw.a(newKey, biomeBase, Lifecycle.stable());
                            int id = ds.aV().d(IRegistry.aR).a(biomeBase);
                            this.biomes.put(colorName.toLowerCase(), id);
                        }
                    }
                }

                return;
            }
        }
    }

    private boolean isHex(String hex) {
        Matcher matcher = HEX_PATTERN.matcher(hex);
        boolean match = matcher.matches();
        if (!match) {
            Bukkit.getLogger().log(Level.WARNING, " Color with hex " + hex + " is invalid");
        }

        return match;
    }

    public Map<String, Integer> getBiomes() {
        return this.biomes;
    }

    public List<World> getEnabledWorlds() {
        return this.enabledWorlds;
    }
}
