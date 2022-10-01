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

    @Column(name = "product_weight", nullable = false)
    private short weight;

    @Column(name = "product_kcals", nullable = false)
    private short kcals;

    @Column(name = "product_proteins", nullable = false)
    private short proteins;

    @Column(name = "product_fats", nullable = false)
    private short fats;

    @Column(name = "product_carbs", nullable = false)
    private short carbs;

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

    public short getWeight() {
        return weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

    public short getKcals() {
        return kcals;
    }

    public void setKcals(short kcals) {
        this.kcals = kcals;
    }

    public short getProteins() {
        return proteins;
    }

    public void setProteins(short proteins) {
        this.proteins = proteins;
    }

    public short getFats() {
        return fats;
    }

    public void setFats(short fats) {
        this.fats = fats;
    }

    public short getCarbs() {
        return carbs;
    }

    public void setCarbs(short carbs) {
        this.carbs = carbs;
    }
}
