package org.if22b179.apps.mtcg.entity;

import lombok.Data;

@Data
public class User {

    private String username; // eindeutiger username PK
    private String password;
    private String securityToken; // Wird nach login generiert 'username'+'-mctgToken'
    private String name; // full Name
    private String bio;
    private String image;
    private int eloValue;
    private int coins; // Para zum kaufen von Packs

}
