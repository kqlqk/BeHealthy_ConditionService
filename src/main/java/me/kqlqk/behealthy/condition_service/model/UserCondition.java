package me.kqlqk.behealthy.condition_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.enums.Activity;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;

import javax.persistence.*;

@Entity
@Table(name = "user_condition", schema = "public", catalog = "condition_service_db")
@Data
@NoArgsConstructor
public class UserCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "daily_kcal_id", referencedColumnName = "id", nullable = false)
    private DailyKcal dailyKcal;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 10)
    private Gender gender;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "height", nullable = false)
    private int height;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity", nullable = false, length = 10)
    private Activity activity;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal", nullable = false, length = 10)
    private Goal goal;

    @Column(name = "fat_percent", nullable = false, precision = 3, scale = 1)
    private double fatPercent;


    public UserCondition(long userId,
                         DailyKcal dailyKcal,
                         Gender gender,
                         int age,
                         int height,
                         int weight,
                         Activity activity,
                         Goal goal,
                         double fatPercent) {
        this.userId = userId;
        this.dailyKcal = dailyKcal;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.activity = activity;
        this.goal = goal;
        this.fatPercent = fatPercent;
    }
}
