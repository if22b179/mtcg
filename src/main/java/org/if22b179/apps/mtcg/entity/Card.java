package org.if22b179.apps.mtcg.entity;

import lombok.Data;

@Data
public class Card {
    private String id; // Eindeutige Identifikationsnummer der Karte
    private String name;
    private double damage;
    private ElementType elementType;
    private CardType cardType;
    private String ownerUsername; // Benutzername des Besitzers der Karte
    private String packageId; // ID des Pakets, zu dem die Karte gehört
    private boolean inDeck; // Ob die Karte im Deck des Benutzers ist

    public enum ElementType {
        FIRE, WATER, NORMAL // und andere Elementtypen, falls vorhanden
    }

    public enum CardType {
        SPELL, MONSTER
    }
}