package org.if22b179.apps.mtcg.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Card {
    @JsonProperty("Id")
    private String id; // Eindeutige Identifikationsnummer der Karte

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Damage")
    private double damage;

    @JsonProperty("Elementtype")
    private ElementType elementType;

    @JsonProperty("Cardtype")
    private CardType cardType;

    @JsonProperty("Ownerusername")
    private String ownerUsername; // Benutzername des Besitzers der Karte

    @JsonProperty("Packageid")
    private String packageId; // ID des Pakets, zu dem die Karte gehört

    @JsonProperty("Indeck")
    private boolean inDeck; // Ob die Karte im Deck des Benutzers ist

    public enum ElementType {
        FIRE, WATER, NORMAL // und andere Elementtypen, falls vorhanden
    }

    public enum CardType {
        SPELL, MONSTER
    }
}