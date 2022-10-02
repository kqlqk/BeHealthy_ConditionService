package me.kqlqk.behealthy.kcal_counter_service.model;

import javax.persistence.*;

@Entity
@Table(name = "daily_food")
public class DailyFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private long userId;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_weight", nullable = false, precision = 1, scale = 4)
    private double weight;

    @Column(name = "product_kcals", nullable = false, precision = 1, scale = 4)
    private double kcals;

    @Column(name = "product_proteins", nullable = false, precision = 1, scale = 4)
    private double proteins;

    @Column(name = "product_fats", nullable = false, precision = 1, scale = 4)
    private double fats;

    @Column(name = "product_carbs", nullable = false, precision = 1, scale = 4)
    private double carbs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getKcals() {
        return kcals;
    }

    public void setKcals(double kcals) {
        this.kcals = kcals;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }
}
