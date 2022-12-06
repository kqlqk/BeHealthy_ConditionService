package me.kqlqk.behealthy.condition_service.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "kcals_info", schema = "public", catalog = "conditionservicedb")
@Data
public class KcalsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "proteins", nullable = false)
    private short protein;

    @Column(name = "fats", nullable = false)
    private short fat;

    @Column(name = "carbs", nullable = false)
    private short carb;
}
