package me.kqlqk.behealthy.kcal_counter_service.dto;

import me.kqlqk.behealthy.kcal_counter_service.model.KcalsInfo;

import javax.persistence.Column;

public class KcalsInfoDTO {
    private long id;
    private short protein;
    private short fat;
    private short carb;

    public KcalsInfo convertToKcalsInfo() {
        KcalsInfo kcalsInfo = new KcalsInfo();
        kcalsInfo.setId(id);
        kcalsInfo.setProtein(protein);
        kcalsInfo.setFat(fat);
        kcalsInfo.setCarb(carb);

        return kcalsInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getProtein() {
        return protein;
    }

    public void setProtein(short protein) {
        this.protein = protein;
    }

    public short getFat() {
        return fat;
    }

    public void setFat(short fat) {
        this.fat = fat;
    }

    public short getCarb() {
        return carb;
    }

    public void setCarb(short carb) {
        this.carb = carb;
    }
}
