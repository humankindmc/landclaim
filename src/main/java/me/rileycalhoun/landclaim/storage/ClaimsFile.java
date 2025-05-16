package me.rileycalhoun.landclaim.storage;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.rileycalhoun.landclaim.claims.ClaimedArea;
import me.rileycalhoun.landclaim.claims.ClaimedChunk;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ClaimsFile extends YamlFile {

    public ClaimsFile(File dataFolder) throws IOException, NullPointerException {
        super(dataFolder, "claims.yml");
    }

    public void saveAllClaims(Collection<ClaimedArea> claims) throws IOException {
        for (ClaimedArea claim : claims) {
            saveClaim(claim);
        }
    }

    public void saveClaim(ClaimedArea claimedArea) throws IOException {
        UUID townUniqueId = claimedArea.townUniqueId();
        List<ClaimedChunk> claimedChunks = claimedArea.claimedChunks();

        List<String> chunkCoordinates = new ArrayList<>();
        for (ClaimedChunk chunk : claimedChunks) {
            chunkCoordinates.add(
                    chunk.worldUniqueId()
                            + "," + chunk.x()
                            + "," + chunk.z()
            );
        }

        getYamlDocument().set("claims." + townUniqueId, chunkCoordinates);
        getYamlDocument().save();
    }

    public List<ClaimedArea> getClaimedAreas() {
        List<ClaimedArea> claimedAreas = new ArrayList<>();
        Section claimsSection = getYamlDocument().getSection("claims");
        if (claimsSection == null) {
            return claimedAreas;
        }

        for (String key : claimsSection.getRoutesAsStrings(false)) {
            UUID townUniqueId = UUID.fromString(key);
            claimedAreas.add(getClaimByTownUniqueId(townUniqueId));
        }

        return claimedAreas;
    }

    @Nullable
    public ClaimedArea getClaimByTownUniqueId(UUID townUniqueId) {
        List<String> chunkCoordinates = getYamlDocument().getStringList("claims." + townUniqueId);
        if (chunkCoordinates == null || chunkCoordinates.isEmpty()) {
            return null;
        }

        List<ClaimedChunk> claimedChunks = new ArrayList<>();
        for (String coordinates : chunkCoordinates) {
            String[] split = coordinates.split(",");
            UUID worldUniqueId;
            int x, z;

            try {
                worldUniqueId = UUID.fromString(split[0]);
                x = Integer.parseInt(split[1]);
                z = Integer.parseInt(split[2]);
            } catch (IllegalArgumentException e) {
                continue;
            }

            ClaimedChunk chunk = new ClaimedChunk(x, z, worldUniqueId, townUniqueId);
            claimedChunks.add(chunk);
        }

        return new ClaimedArea(townUniqueId, claimedChunks);
    }

}