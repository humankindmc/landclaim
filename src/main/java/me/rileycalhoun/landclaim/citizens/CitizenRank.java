package me.rileycalhoun.landclaim.citizens;

public enum CitizenRank {

    CITIZEN(1), OFFICER(2), MAYOR(3);

    private final int value;

    CitizenRank(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
