package it.totten98.onlypremiumaccess;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WarListener implements Listener {

    public OnlyPremiumAccessMain plugin;

    public WarListener(OnlyPremiumAccessMain instance){
        plugin = instance;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) throws IOException {
        Player tryingToLogin = event.getPlayer();

        if (!isPremium(tryingToLogin))
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER , "Failed to login: User not premium");
        else
            event.allow();

    }

    private boolean isPremium(Player player) throws IOException {
        String uuid = player.getUniqueId().toString().replace("-", "");
        String username = player.getName();

        StringBuilder jsonS = new StringBuilder();
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();

        if (conn.getResponseCode() == 200) {

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                jsonS.append(inputLine);
            }
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonS.toString(), JsonObject.class);
            String name = jsonObject.get("name").toString().replace("\"", "");
            String id = jsonObject.get("id").toString().replace("\"", "");

            in.close();

            return username.equals(name) && !id.isEmpty() && id.equals(uuid);
        }

        return false;
    }

}
