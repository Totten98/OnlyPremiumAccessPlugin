package it.totten98.onlypremiumaccess;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class WarListener implements Listener {

    public OnlyPremiumAccessMain plugin;

    public WarListener(OnlyPremiumAccessMain instance){
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();

        String uuid = player.getUniqueId().toString().replace("-", "");
        String username = player.getName();

        StringBuilder jsonS = new StringBuilder();
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
        URLConnection conn = url.openConnection();
        conn.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;

        while((inputLine = in.readLine()) != null) {
            jsonS.append(inputLine);
        }
        Gson gson = new Gson();
        JsonObject jsonObject= gson.fromJson(jsonS.toString(), JsonObject.class);
        String id = jsonObject.get("id").toString().replace("\"", "");
        System.out.println("\nid:\t" + id + "\nuuid:\t" + uuid);

        in.close();

        if (!id.equals(uuid)) {
            player.kickPlayer("User not premium");
        }

    }

}