package me.kqlqk.behealthy.condition_service.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_kcal", schema = "public", catalog = "condition_service_db")
@Data
public class UserKcal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "kcals", nullable = false)
    private int kcal;

    @Column(name = "proteins")
    private int protein;

    @Column(name = "fats")
    private int fat;

    @Column(name = "carbs")
    private int carb;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "in_priority", nullable = false)
    private boolean inPriority;

    @Column(name = "only_kcals", nullable = false)
    private boolean onlyKcal;
}
