package org.ISAAC.iSAAC;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import net.md_5.bungee.api.ChatColor;

public class RankManager {
    private final RANK normal = new RANK("NORMAL", ChatColor.WHITE, "NORMAL", 0);
    private final RANK modo = new RANK("MODO", ChatColor.BLUE, "MODO", 1);
    private final RANK twitch = new RANK("TWITCH", ChatColor.LIGHT_PURPLE, "TWITCH", 2);
    private final RANK admin = new RANK("ADMIN", ChatColor.RED, "ADMIN", 3);

    private final ISAAC plugin;

    public RankManager(ISAAC plugin) {

        this.plugin = plugin;

        this.normal.allowedCommands.add("isaac.command.sethome");

        this.modo.allowedCommands.add("minecraft.command.seed");
        this.modo.allowedCommands.add("minecraft.command.weather");
        this.modo.allowedCommands.add("minecraft.command.kick");
        this.modo.allowedCommands.add("minecraft.command.time");
        this.modo.allowedCommands.add("isaac.command.sethome");

        this.twitch.allowedCommands.add("minecraft.command.seed");
        this.twitch.allowedCommands.add("minecraft.command.weather");
        this.twitch.allowedCommands.add("minecraft.command.whitelist");
        this.twitch.allowedCommands.add("minecraft.command.ban");
        this.twitch.allowedCommands.add("minecraft.command.kick");
        this.twitch.allowedCommands.add("minecraft.command.time");
        this.twitch.allowedCommands.add("bukkit.command.seed");
        this.twitch.allowedCommands.add("bukkit.command.weather");
        this.twitch.allowedCommands.add("bukkit.command.whitelist");
        this.twitch.allowedCommands.add("bukkit.command.ban");
        this.twitch.allowedCommands.add("bukkit.command.kick");
        this.twitch.allowedCommands.add("bukkit.command.time");
        this.twitch.allowedCommands.add("isaac.command.sethome");

        this.admin.allowedCommands.add("minecraft.command.weather");
        this.admin.allowedCommands.add("minecraft.command.time");
        this.admin.allowedCommands.add("minecraft.command.gamemode");
        this.admin.allowedCommands.add("minecraft.command.tp");
        this.admin.allowedCommands.add("minecraft.command.give");
        this.admin.allowedCommands.add("minecraft.command.effect");
        this.admin.allowedCommands.add("minecraft.command.enchant");
        this.admin.allowedCommands.add("minecraft.command.kill");
        this.admin.allowedCommands.add("minecraft.command.kick");
        this.admin.allowedCommands.add("minecraft.command.ban");
        this.admin.allowedCommands.add("minecraft.command.whitelist");
        this.admin.allowedCommands.add("minecraft.command.op");
        this.admin.allowedCommands.add("minecraft.command.deop");
        this.admin.allowedCommands.add("bukkit.command.kick");
        this.admin.allowedCommands.add("bukkit.command.ban");
        this.admin.allowedCommands.add("bukkit.command.unban");
        this.admin.allowedCommands.add("bukkit.command.whitelist");
        this.admin.allowedCommands.add("bukkit.command.op");
        this.admin.allowedCommands.add("bukkit.command.deop");
        this.admin.allowedCommands.add("bukkit.command.reload");
        this.admin.allowedCommands.add("bukkit.command.stop");
        this.admin.allowedCommands.add("bukkit.command.save");
        this.admin.allowedCommands.add("minecraft.command.seed");
        this.admin.allowedCommands.add("isaac.command.vanish");
        this.admin.allowedCommands.add("isaac.command.sethome");
    }

    public void AddToRANKlist(Player player, String rank) {
        PermissionAttachment attachment = player.addAttachment(plugin);
        if (rank != null) {
            switch (rank.toUpperCase()) {
                case "MODO":
                    plugin.ranklist.put(player.getUniqueId(), modo);
                    break;
                case "TWITCH":
                    plugin.ranklist.put(player.getUniqueId(), twitch);
                    break;
                case "ADMIN":
                    plugin.ranklist.put(player.getUniqueId(), admin);
                    break;
                default:
                    plugin.ranklist.put(player.getUniqueId(), normal);
                    break;
            }
        } else {
            plugin.ranklist.put(player.getUniqueId(), normal);
        }
        for (String command : plugin.ranklist.get(player.getUniqueId()).allowedCommands) {
            attachment.setPermission(command, true);
            player.updateCommands();
        }
    }
    

    public void logRankList() {
        StringBuilder rankListString = new StringBuilder("{");

        for (UUID uuid : plugin.ranklist.keySet()) {
            Player player = plugin.getServer().getPlayer(uuid);
            String playerName = (player != null) ? player.getName() : "JoueurInconnu";
            String rankName = plugin.ranklist.get(uuid).getName();
            
            rankListString.append(playerName).append("=").append(rankName).append(", ");
        }

        if (rankListString.length() > 1) {
            rankListString.setLength(rankListString.length() - 2);
        }
        rankListString.append("}");

        plugin.getLogger().info(rankListString.toString());
    }

}
