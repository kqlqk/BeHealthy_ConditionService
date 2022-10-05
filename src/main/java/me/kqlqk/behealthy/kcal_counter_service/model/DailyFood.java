package me.kqlqk.behealthy.kcal_counter_service.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "daily_food")
@Data
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
}
