package me.kqlqk.behealthy.kcal_counter_service.dto;

import me.kqlqk.behealthy.kcal_counter_service.model.Intensity;

public class UserConditionDTO {
    private long id;
    private KcalsInfoDTO kcalsInfoDTO;
    private short weight;
    private Intensity intensity;
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public KcalsInfoDTO getKcalsInfoDTO() {
        return kcalsInfoDTO;
    }

    public void setKcalsInfoDTO(KcalsInfoDTO kcalsInfoDTO) {
        this.kcalsInfoDTO = kcalsInfoDTO;
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

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
