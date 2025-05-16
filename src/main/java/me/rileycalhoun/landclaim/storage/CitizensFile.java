package me.rileycalhoun.landclaim.storage;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.rileycalhoun.landclaim.citizens.Citizen;
import me.rileycalhoun.landclaim.citizens.CitizenRank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CitizensFile extends YamlFile {

    public CitizensFile(File dataFolder) throws IOException, NullPointerException {
        super(dataFolder, "citizens.yml");
    }

    public void saveAllCitizens(Collection<Citizen> citizens) throws IOException {
        for (Citizen citizen : citizens) {
            saveCitizen(citizen);
        }
    }

    public void saveCitizen(Citizen citizen) throws IOException {
        UUID uuid = citizen.getPlayer().getUniqueId();
        UUID townUniqueId = citizen.getTownUniqueId();
        CitizenRank citizenRank = citizen.getCitizenRank();

        getYamlDocument().set("citizens." + uuid + ".town", townUniqueId == null ? null : townUniqueId.toString());
        getYamlDocument().set("citizens." + uuid + ".rank", citizenRank == null ? null : citizenRank.toString());
        getYamlDocument().save();
    }

    public List<Citizen> getCitizens() {
        List<Citizen> citizens = new ArrayList<>();
        Section citizensSection = getYamlDocument().getSection("citizens");
        if (citizensSection == null) {
            return citizens;
        }

        for (String key : citizensSection.getRoutesAsStrings(false)) {
            UUID uuid = UUID.fromString(key);
            citizens.add(getCitizenByUniqueId(uuid));
        }

        return citizens;
    }

    @Nullable
    public Citizen getCitizenByUniqueId(UUID uuid) {
        Section citizenSection = getYamlDocument().getSection("citizens." + uuid);
        if (citizenSection == null) {
            return null;
        }

        String rank = citizenSection.getString("rank");
        CitizenRank citizenRank = CitizenRank.valueOf(rank);

        String town = citizenSection.getString("town");
        UUID townUniqueId = UUID.fromString(town);

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return new Citizen(player, townUniqueId, citizenRank);
    }

}
