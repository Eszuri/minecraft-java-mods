package com.lieval.maceeasily.config;

import com.google.gson.annotations.SerializedName;

public enum TriggerDistance {
    @SerializedName("BLOCKS_1_5")
    BLOCKS_1_5("1.5 Blocks (Default)", 1.5F),

    @SerializedName("BLOCKS_1_0")
    BLOCKS_1_0("1.0 Block", 1.0F),

    @SerializedName(value = "BLOCKS_0", alternate = {"FREE"})
    BLOCKS_0("0 Blocks", 0.0F);

    private final String displayName;
    private final float minDistance;

    TriggerDistance(String displayName, float minDistance) {
        this.displayName = displayName;
        this.minDistance = minDistance;
    }

    public String getDisplayName() {
        return displayName;
    }

    public float getMinDistance() {
        return minDistance;
    }

    public TriggerDistance next() {
        TriggerDistance[] vals = values();
        return vals[(ordinal() + 1) % vals.length];
    }
}
