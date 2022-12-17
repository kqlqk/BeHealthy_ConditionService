package me.kqlqk.behealthy.condition_service.model.enums;

public enum Intensity {
    MIN(1.25),
    AVG(1.45),
    ACTIVE(1.6),
    MAX(1.8);

    private final double activity;

    Intensity(double activity) {
        this.activity = activity;
    }

    public double getCoefficient() {
        return activity;
    }
}
