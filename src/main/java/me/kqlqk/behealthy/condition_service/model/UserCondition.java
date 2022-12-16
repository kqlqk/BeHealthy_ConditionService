package me.kqlqk.behealthy.condition_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;

import javax.persistence.*;

@Entity
@Table(name = "user_condition", schema = "public", catalog = "conditionservicedb")
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
    @JoinColumn(name = "kcals_info_id", referencedColumnName = "id")
    private KcalsInfo kcalsInfo;

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
    @Column(name = "intensity", nullable = false, length = 10)
    private Intensity intensity;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal", nullable = false, length = 10)
    private Goal goal;

    @Column(name = "fat_percent", nullable = false, precision = 3, scale = 1)
    private double fatPercent;


    public UserCondition(long userId,
                         KcalsInfo kcalsInfo,
                         Gender gender,
                         int age,
                         int height,
                         int weight,
                         Intensity intensity,
                         Goal goal,
                         double fatPercent) {
        this.userId = userId;
        this.kcalsInfo = kcalsInfo;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.intensity = intensity;
        this.goal = goal;
        this.fatPercent = fatPercent;
    }
}
