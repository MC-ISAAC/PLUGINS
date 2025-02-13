package org.ISAAC.iSAAC.commands;

import org.ISAAC.iSAAC.EnvLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SethomeCommand implements CommandExecutor {
    
    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Cette commande est réservée aux joueurs.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /sethome <nom>");
            return true;
        }


        String homeName = args[0];
        String world = player.getWorld().getName();
        org.bukkit.Location location = player.getLocation();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        // Récupération du Token API
        String tokenApi = EnvLoader.get("TokenApi");
        if (tokenApi == null) {
            player.sendMessage(ChatColor.RED + "Erreur: Impossible de récupérer le Token API.");
            return true;
        }

        // Création du JSON (corrigé avec format correct des nombres)
        String json = String.format(
            "{\"username\":\"%s\",\"name\":\"%s\",\"world\":\"%s\",\"x\":%.2f,\"y\":%.2f,\"z\":%.2f,\"yaw\":%.2f,\"pitch\":%.2f}",
            player.getName(), homeName, world, x, y, z, yaw, pitch
        );
        // Exécution de la requête HTTP en arrière-plan (async)
        Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("ISAAC"), () -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.isaac.erwansinck.com/homes/create")) // Correction de l'URL
                        .header("Accept", "application/json") // Optimisation du header
                        .header("token", tokenApi)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Exécuter l'affichage des messages sur le thread principal
                if (response.statusCode() == 201) {
                    player.sendMessage(ChatColor.GREEN + "Votre home '" + homeName + "' a été enregistré !");
                } else {
                    player.sendMessage(ChatColor.RED + recupMessageError(response.body()));
                    
                };
            } catch (IOException | InterruptedException | java.net.URISyntaxException e) {
                e.printStackTrace();
                Bukkit.getScheduler().runTask(Bukkit.getPluginManager().getPlugin("ISAAC"), () -> 
                    player.sendMessage(ChatColor.RED + "Une erreur est survenue lors de la création du home."));
            }
        });

        return true;
    }

    public String recupMessageError(String json) {
        Pattern pattern = Pattern.compile("\"message\"\\s*:\\s*\"(.*?)\"");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}