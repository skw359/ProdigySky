package fr.cocoraid.prodigysky;

import fr.cocoraid.acf.PaperCommandManager;
import fr.cocoraid.prodigysky.commands.MainCMD;
import fr.cocoraid.prodigysky.filemanager.Configuration;
import fr.cocoraid.prodigysky.listeners.EventListener;
import fr.cocoraid.prodigysky.listeners.PacketListener;
import fr.cocoraid.prodigysky.nms.NMS;
import fr.prodigysky.api.EffectDuration;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ProdigySky extends JavaPlugin {
    private PaperCommandManager manager;
    private static ProdigySky instance;
    private NMS nms;
    private Configuration configuration;

    public ProdigySky() {
    }

    public void onEnable() {
        this.displayBanner();
        instance = this;
        this.nms = new NMS(this);
        this.configuration = new Configuration(this);
        this.configuration.init();
        this.configuration.load();
        this.loadCommands();
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        new PacketListener(this);
    }

    public void onDisable() {
    }

    private void displayBanner() {
        ConsoleCommandSender cc = Bukkit.getConsoleSender();
        cc.sendMessage("Â§5 ______               _ _                 Â§b______ _");
        cc.sendMessage("Â§5(_____ \\             | (_)               Â§b/ _____) |");
        cc.sendMessage("Â§5 _____) )___ ___   __| |_  ____ _   _   Â§b( (____ | |  _ _   _ ");
        cc.sendMessage("Â§5|  ____/ ___) _ \\ / _  | |/ _  | | | |   Â§b\\____ \\| |_/ ) | | |");
        cc.sendMessage("Â§5| |   | |  | |_| ( (_| | ( (_| | |_| |  Â§b _____) )  _ (| |_| |");
        cc.sendMessage("Â§5|_|   |_|   \\___/ \\____|_|\\___ |\\__  |Â§b  (______/|_| \\_)\\__  |");
        cc.sendMessage("Â§5                          (_____(____/      Â§b           (____/");
        cc.sendMessage("Â§d The prodigy is the man who knows how to shape the sky");
    }

    private void loadCommands() {
        this.manager = new PaperCommandManager(this);
        this.manager.getCommandCompletions().registerAsyncCompletion("biomeName", (c) -> {
            return this.getConfiguration().getBiomes().keySet();
        });
        this.manager.getCommandCompletions().registerAsyncCompletion("effectDuration", (c) -> {
            return (Collection)Arrays.stream(EffectDuration.values()).map((e) -> {
                return e.name().toLowerCase();
            }).collect(Collectors.toList());
        });
        this.manager.registerCommand(new MainCMD(this));
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public static ProdigySky getInstance() {
        return instance;
    }

    public NMS getNMS() {
        return this.nms;
    }
}
