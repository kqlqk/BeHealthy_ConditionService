package me.kqlqk.behealthy.kcal_counter_service.model;

import javax.persistence.*;

@Entity
@Table(name = "kcals_info")
public class KcalsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "proteins", nullable = false)
    private short protein;

    @Column(name = "fats", nullable = false)
    private short fat;

    @Column(name = "carbs", nullable = false)
    private short carb;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getProtein() {
        return protein;
    }

    public void setProtein(short protein) {
        this.protein = protein;
    }

    public short getFat() {
        return fat;
    }

    public void setFat(short fat) {
        this.fat = fat;
    }

    public short getCarb() {
        return carb;
    }

    public void setCarb(short carb) {
        this.carb = carb;
    }

    @Override
    public String toString() {
        return "KcalsInfo{" +
                "id=" + id +
                ", protein=" + protein +
                ", fat=" + fat +
                ", carb=" + carb +
                '}';
    }
}
