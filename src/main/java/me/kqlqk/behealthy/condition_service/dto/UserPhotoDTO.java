package me.kqlqk.behealthy.condition_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.UserPhoto;

import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhotoDTO {
    private long userId;
    private String encodedPhoto;

    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-([2-9][0-9])", message = "You should use the following pattern: 'dd-mm-yy'")
    private String photoDate;

    public static UserPhotoDTO convertUserPhotoToUserPhotoDTO(UserPhoto userPhoto) {
        UserPhotoDTO userPhotoDTO = new UserPhotoDTO();
        userPhotoDTO.setUserId(userPhoto.getUserId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        userPhotoDTO.setPhotoDate(dateFormat.format(userPhoto.getPhotoDate()));

        return userPhotoDTO;
    }
}
