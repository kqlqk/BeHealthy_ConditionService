package me.kqlqk.behealthy.kcal_counter_service.dto;

import me.kqlqk.behealthy.kcal_counter_service.model.UserCondition;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Gender;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Goal;
import me.kqlqk.behealthy.kcal_counter_service.model.enums.Intensity;

public class UserConditionDTO {
    private long id;
    private long userId;
    private Gender gender;
    private byte age;
    private short height;
    private short weight;
    private Intensity intensity;
    private Goal goal;

    public UserConditionDTO() {

    }

    public UserConditionDTO(long id,
                            long userId,
                            Gender gender,
                            byte age,
                            short height,
                            short weight,
                            Intensity intensity,
                            Goal goal) {
        this.id = id;
        this.userId = userId;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.intensity = intensity;
        this.goal = goal;
    }

    public static UserConditionDTO convertUserConditionToUserConditionDTO(UserCondition userCondition) {
        return new UserConditionDTO(
                userCondition.getId(),
                userCondition.getUserId(),
                userCondition.getGender(),
                userCondition.getAge(),
                userCondition.getHeight(),
                userCondition.getWeight(),
                userCondition.getIntensity(),
                userCondition.getGoal());
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
}
