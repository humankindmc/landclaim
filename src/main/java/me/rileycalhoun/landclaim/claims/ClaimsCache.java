package me.rileycalhoun.landclaim.claims;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ClaimsCache {

    @NotNull
    private LinkedHashMap<UUID, ClaimArea> claims;

    public ClaimsCache(int max_size) {
        this.claims = new LinkedHashMap<>(max_size+1, 0.75f, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<UUID, ClaimArea> eldest) {
                return size() > max_size;
            }

        };
    }

    public LinkedHashMap<UUID, ClaimArea> getClaims() {
        return claims;
    }

    @Nullable
    public ClaimArea getClaimByUUID(UUID uuid) {
        return claims.get(uuid);
    }

}
