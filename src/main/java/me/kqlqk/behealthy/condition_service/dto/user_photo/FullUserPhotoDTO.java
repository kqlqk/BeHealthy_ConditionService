package me.kqlqk.behealthy.condition_service.dto.user_photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullUserPhotoDTO {
    private String photoPath;
    private Date photoDate;
    private String encodedPhoto;
}
