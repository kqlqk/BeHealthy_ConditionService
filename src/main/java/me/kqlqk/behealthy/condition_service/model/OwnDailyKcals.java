package me.kqlqk.behealthy.condition_service.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "own_daily_kcals", schema = "public", catalog = "condition_service_db")
@Data
public class OwnDailyKcals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "proteins", nullable = false)
    private int protein;

    @Column(name = "fats", nullable = false)
    private int fat;

    @Column(name = "carbs", nullable = false)
    private int carb;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "in_priority", nullable = false)
    private boolean inPriority;
}
