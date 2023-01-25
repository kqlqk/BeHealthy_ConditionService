package me.kqlqk.behealthy.condition_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_condition_photo", schema = "public", catalog = "conditionservicedb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "photo_path", unique = true, nullable = false)
    private String photoPath;

    @Column(name = "photo_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date photoDate;
}
