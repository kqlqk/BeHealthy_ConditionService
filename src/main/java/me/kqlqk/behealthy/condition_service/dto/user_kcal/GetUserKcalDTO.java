package me.kqlqk.behealthy.condition_service.dto.user_kcal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.condition_service.model.UserKcal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserKcalDTO {
    private int kcal;
    private int protein;
    private int fat;
    private int carb;
    private long userId;
    private boolean inPriority;
    private boolean onlyKcal;

    public static GetUserKcalDTO convert(UserKcal userKcal) {
        return new GetUserKcalDTO(userKcal.getKcal(),
                                  userKcal.getProtein(),
                                  userKcal.getFat(),
                                  userKcal.getCarb(),
                                  userKcal.getUserId(),
                                  userKcal.isInPriority(),
                                  userKcal.isOnlyKcal());
    }
}
