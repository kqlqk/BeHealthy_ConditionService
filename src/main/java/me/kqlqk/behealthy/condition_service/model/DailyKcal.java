package me.kqlqk.behealthy.condition_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "daily_kcal", schema = "public", catalog = "condition_service_db")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyKcal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "protein", nullable = false)
    private int protein;

    @Column(name = "fat", nullable = false)
    private int fat;

    @Column(name = "carb", nullable = false)
    private int carb;

    public DailyKcal(int protein, int fat, int carb) {
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
    }
}
