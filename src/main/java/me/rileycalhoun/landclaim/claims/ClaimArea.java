package me.rileycalhoun.landclaim.claims;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class ClaimArea {

    @NotNull
    private Vector3d pointA, pointB;

    public ClaimArea(
            @NotNull Vector3d pointA,
            @NotNull Vector3d pointB
    ) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public ClaimArea(
            @NotNull Location locationA,
            @NotNull Location locationB
    ) {
        this(new Vector3d(locationA), new Vector3d(locationB));
    }

    @NotNull
    public Vector3d getPointA() {
        return pointA;
    }

    public void setPointA(@NotNull Vector3d pointA) {
        this.pointA = pointA;
    }

    @NotNull
    public Vector3d getPointB() {
        return pointB;
    }

    public void setPointB(@NotNull Vector3d pointB) {
        this.pointB = pointB;
    }

    public boolean isInClaim(Location location) {
        return isInClaim(new Vector3d(location));
    }

    public boolean isInClaim(Vector3d vector) {
        Vector3d max = Vector3d.max(pointA, pointB);
        Vector3d min = Vector3d.min(pointA, pointB);

        return (max.getX() < vector.getX() && vector.getX() > min.getX())
            && (max.getZ() < vector.getZ() && vector.getZ() > min.getZ());
    }

}
