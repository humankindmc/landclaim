package me.rileycalhoun.landclaim.listener;

import me.rileycalhoun.landclaim.LandClaim;
import me.rileycalhoun.landclaim.citizens.Citizen;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListener implements Listener {

    private final LandClaim landClaim;

    public ConnectionListener(LandClaim landClaim) {
        this.landClaim = landClaim;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean isNotCitizen = landClaim.getCitizensCache()
                .getCitizenByUUID(player.getUniqueId()) == null;

        if (isNotCitizen) {
            Citizen citizen = new Citizen(player, null, null);
            landClaim.getCitizensCache().citizens.put(player.getUniqueId(), citizen);
        }
    }

}
