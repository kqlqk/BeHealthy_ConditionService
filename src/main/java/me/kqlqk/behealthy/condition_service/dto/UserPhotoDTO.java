package me.kqlqk.behealthy.condition_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhotoDTO {
    private long userId;
    private String encodedPhoto;

    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-([2-9][0-9])", message = "You should use the following pattern: 'dd-mm-yy'")
    private String photoDate;
}
