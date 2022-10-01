package me.kqlqk.behealthy.kcal_counter_service.model;

import me.kqlqk.behealthy.kcal_counter_service.model.enums.Gender;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Goal;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Intensity;

import javax.persistence.*;

@Entity
@Table(name = "user_condition")
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


    public UserCondition() {

    }

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

    public KcalsInfo getKcalsInfo() {
        return kcalsInfo;
    }

    public void setKcalsInfo(KcalsInfo kcalsInfo) {
        this.kcalsInfo = kcalsInfo;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public short getWeight() {
        return weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public void setIntensity(Intensity intensity) {
        this.intensity = intensity;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    @Override
    public String toString() {
        return "UserCondition{" +
                "id=" + id +
                ", userId=" + userId +
                ", kcalsInfo=" + kcalsInfo +
                ", gender=" + gender +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", intensity=" + intensity +
                ", goal=" + goal +
                '}';
    }
}
