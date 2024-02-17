package com.example.userinteraction.entity.pojos;

import lombok.Data;

@Data
public class VehicleOwner {
    private long vehicleOwnerId;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String identityCard;
}
