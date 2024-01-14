package org.if22b179.apps.mtcg.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @JsonProperty("Username")
    private String username; // eindeutiger username PK

    @JsonProperty("Password")
    private String password;

    @JsonProperty("SecurityToken")
    private String securityToken; // Wird nach login generiert 'username'+'-mctgToken'

    @JsonProperty("Name")
    private String name; // full Name

    @JsonProperty("Bio")
    private String bio;

    @JsonProperty("Image")
    private String image;

    @JsonProperty("EloValue")
    private int eloValue = 100;

    @JsonProperty("Coins")
    private int coins = 20; // Para zum kaufen von Packs

}
