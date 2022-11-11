package me.kqlqk.behealthy.condition_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.enums.Gender;
import me.kqlqk.behealthy.condition_service.model.enums.Goal;
import me.kqlqk.behealthy.condition_service.model.enums.Intensity;

import javax.persistence.*;

@Entity
@Table(name = "user_condition")
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
    private byte age;

    @Column(name = "height", nullable = false)
    private short height;

    @Column(name = "weight", nullable = false)
    private short weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "intensity", nullable = false, length = 10)
    private Intensity intensity;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal", nullable = false, length = 10)
    private Goal goal;


    public UserCondition(long userId,
                         KcalsInfo kcalsInfo,
                         Gender gender,
                         byte age,
                         short height,
                         short weight,
                         Intensity intensity, Goal goal) {
        this.userId = userId;
        this.kcalsInfo = kcalsInfo;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.intensity = intensity;
        this.goal = goal;
    }
}
