package me.rileycalhoun.landclaim.towns.invites;

import me.rileycalhoun.landclaim.towns.Town;
import me.rileycalhoun.landclaim.towns.TownsCache;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class InviteManager {

    private final HashMap<Town, ArrayList<OfflinePlayer>> invites = new HashMap<>();

    private final TownsCache townsCache;

    public InviteManager(final TownsCache townsCache) {
        this.townsCache = townsCache;
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

    public boolean joinTown(Town town, OfflinePlayer player) {
        if (!isInvited(town, player)) return false;
        invites.get(town).remove(player);

        TownCitizen citizen = new TownCitizen(player, TownRank.CITIZEN);
        town.addCitizen(citizen);
        return true;
    }

}
