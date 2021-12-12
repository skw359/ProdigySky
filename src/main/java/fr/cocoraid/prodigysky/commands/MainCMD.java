package fr.cocoraid.prodigysky.commands;

import fr.cocoraid.acf.BaseCommand;
import fr.cocoraid.acf.annotation.CommandAlias;
import fr.cocoraid.acf.annotation.CommandCompletion;
import fr.cocoraid.acf.annotation.CommandPermission;
import fr.cocoraid.acf.annotation.Subcommand;
import fr.cocoraid.acf.bukkit.contexts.OnlinePlayer;
import fr.cocoraid.prodigysky.ProdigySky;
import fr.prodigysky.api.EffectDuration;
import fr.prodigysky.api.ProdigySkyAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("prodigysky|pgs")
public class MainCMD extends BaseCommand {
    private ProdigySky instance;

    public MainCMD(ProdigySky instance) {
        this.instance = instance;
    }

    @CommandPermission("pgs.remove.other")
    @CommandCompletion("@players")
    @Subcommand("removeplayer")
    public void remove(Player sender, OnlinePlayer target) {
        ProdigySkyAPI.removeBiome((Player)target);
    }

    @CommandPermission("pgs.remove")
    @Subcommand("remove")
    public void remove(Player p) {
        ProdigySkyAPI.removeBiome(p);
    }

    @CommandPermission("pgs.set")
    @CommandCompletion("@biomeName")
    @Subcommand("set")
    public void set(Player p, String biome, EffectDuration duration) {
        if (!this.instance.getConfiguration().getBiomes().containsKey(biome)) {
            p.sendMessage("Â§cThis biome does not exist !");
        } else {
            ProdigySkyAPI.setBiome(p, biome, duration, (World)null);
        }
    }

    @CommandPermission("pgs.set.other")
    @CommandCompletion("@biomeName")
    @Subcommand("setplayer")
    public void set(CommandSender p, OnlinePlayer target, String biome, EffectDuration duration) {
        if (!this.instance.getConfiguration().getBiomes().containsKey(biome)) {
            p.sendMessage("Â§cThis biome does not exist !");
        } else {
            ProdigySkyAPI.setBiome((Player)target, biome, duration, (World)null);
        }
    }

    @CommandPermission("pgs.set.all")
    @CommandCompletion("@biomeName @effectDuration")
    @Subcommand("setall")
    public void setAll(CommandSender p, String biome, EffectDuration duration) {
        if (!this.instance.getConfiguration().getBiomes().containsKey(biome)) {
            p.sendMessage("Â§cThis biome does not exist !");
        } else {
            Bukkit.getOnlinePlayers().forEach((cur) -> {
                ProdigySkyAPI.setBiome(cur, biome, duration, (World)null);
            });
        }
    }

    @CommandPermission("pgs.remove.all")
    @CommandCompletion("@players")
    @Subcommand("removeall")
    public void setAll(CommandSender p) {
        Bukkit.getOnlinePlayers().forEach((cur) -> {
            ProdigySkyAPI.removeBiome(cur.getPlayer());
        });
    }
}