package me.rileycalhoun.landclaim.storage;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.rileycalhoun.landclaim.towns.Town;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class TownsFile extends YamlFile {

    public TownsFile(File dataFolder) throws IOException, NullPointerException {
        super(dataFolder, "towns.yml");
    }

    public void saveAllTowns(Collection<Town> towns) throws IOException {
        for (Town town : towns) {
            saveTown(town);
        }
    }

    public void saveTown(Town town) throws IOException {
        UUID uniqueId = town.getUniqueId();
        String name = town.getName();
        String motd = town.getMotd();

        getYamlDocument().set("towns." + uniqueId + ".name", name);
        getYamlDocument().set("towns." + uniqueId + ".motd", motd);
        getYamlDocument().save();
    }

    public List<Town> getTowns() {
        List<Town> towns = new ArrayList<>();
        Section townsSection = getYamlDocument().getSection("towns");
        if (townsSection == null) {
            return towns;
        }

        for (String key : townsSection.getRoutesAsStrings(false)) {
            UUID uuid = UUID.fromString(key);
            towns.add(getTownByUniqueId(uuid));
        }

        return towns;
    }

    @Nullable
    public Town getTownByUniqueId(UUID uuid) {
        Section townSection = getYamlDocument().getSection("towns." + uuid);
        if (townSection == null) {
            return null;
        }

        String name = townSection.getString("name");
        String motd = townSection.getString("motd");
        return new Town(uuid, name, motd);
    }

    @Nullable
    public Town getTownByName(String expected) {
        Section townsSection = getYamlDocument().getSection("towns");
        if (townsSection == null) {
            return null;
        }

        for (String key : townsSection.getRoutesAsStrings(false)) {
            String name = townsSection.getString(key + ".name");
            if (name == null || !name.equalsIgnoreCase(expected)) {
                continue;
            }

            UUID uuid = UUID.fromString(key);
            String motd = townsSection.getString(key + ".motd");
            return new Town(uuid, name, motd);
        }

        return null;
    }

}
