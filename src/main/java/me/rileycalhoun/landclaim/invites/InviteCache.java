package me.rileycalhoun.landclaim.invites;

import me.rileycalhoun.landclaim.citizens.CitizensCache;
import me.rileycalhoun.landclaim.towns.Town;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class InviteCache {

    private final LinkedHashMap<Town, ArrayList<OfflinePlayer>> invites;

    public InviteCache(int max_size) {
        this.invites = new LinkedHashMap<>(max_size+1, 0.75f, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<Town, ArrayList<OfflinePlayer>> eldest) {
                return size() > max_size;
            }

        };
    }

    public void invite(Town town, OfflinePlayer player) {
        ArrayList<OfflinePlayer> players = invites.getOrDefault(town, new ArrayList<>());
        if (!players.contains(player)) {
            players.add(player);
        }

        invites.put(town, players);
    }

    public boolean isInvited(Town town, OfflinePlayer player) {
        if (!invites.containsKey(town)) {
            return false;
        }

        ArrayList<OfflinePlayer> players = invites.get(town);
        return players.contains(player);
    }

}
