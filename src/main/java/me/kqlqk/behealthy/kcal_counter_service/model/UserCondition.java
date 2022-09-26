package me.kqlqk.behealthy.kcal_counter_service.model;

import javax.persistence.*;

@Entity
@Table(name = "user_condition")
public class UserCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kcals_info_id", referencedColumnName = "id")
    private KcalsInfo kcalsInfo;

    @Column(name = "weight", nullable = false)
    private short weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "intensity", nullable = false, length = 30)
    private Intensity intensity;

    @Column(name = "user_id", unique = true, nullable = false)
    private long userId;

    public UserCondition() {

    }

    public UserCondition(KcalsInfo kcalsInfo, short weight, Intensity intensity, long userId) {
        this.kcalsInfo = kcalsInfo;
        this.weight = weight;
        this.intensity = intensity;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public KcalsInfo getKcalsInfo() {
        return kcalsInfo;
    }

    public void setKcalsInfo(KcalsInfo kcalsInfo) {
        this.kcalsInfo = kcalsInfo;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long user_id) {
        this.userId = user_id;
    }
}
