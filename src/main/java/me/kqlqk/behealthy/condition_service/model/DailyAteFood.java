package me.kqlqk.behealthy.condition_service.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "daily_ate_food", schema = "public", catalog = "condition_service_db")
@Data
public class DailyAteFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_weight", nullable = false, precision = 1, scale = 5)
    private double weight;

    @Column(name = "product_kcals", nullable = false)
    private int kcal;

    @Column(name = "product_proteins", nullable = false)
    private int protein;

    @Column(name = "product_fats", nullable = false)
    private int fat;

    @Column(name = "product_carbs", nullable = false)
    private int carb;

    @Column(name = "today", nullable = false)
    private boolean today;
}
