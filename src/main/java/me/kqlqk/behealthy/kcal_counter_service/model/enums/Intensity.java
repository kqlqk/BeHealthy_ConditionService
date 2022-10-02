package me.kqlqk.behealthy.kcal_counter_service.model.enums;

public enum Intensity {
    MIN(1.25),
    AVG(1.5),
    MAX(1.8);

    private final double activity;

    Intensity(double activity) {
        this.activity = activity;
    }

    public double getActivity() {
        return activity;
    }
}
