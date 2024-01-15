package org.if22b179.apps.mtcg.repository;

import org.if22b179.apps.mtcg.data.Database;
import org.if22b179.apps.mtcg.entity.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardRepo implements CrudRepo<Card,String>{

    private final Database database = new Database();

    private final String SAVE_SQL = "INSERT INTO CardTable (id, name, damage, element_type, card_type, package_id) VALUES (?, ?, ?, ?, ?, ?)";

    private final String FIND_BY_ID_SQL = "SELECT * FROM CardTable WHERE id = ?";

    private final String UPDATE_SQL = "UPDATE CardTable SET owner_username = ? WHERE id = ?";

    private final String DELETE_SQL = "DELETE FROM CardTable WHERE id = ?";

    private final String FIND_ALL_CARDS_FROM_PACKAGE = "SELECT * FROM CardTable WHERE package_id = ?";



    @Override
    public Card save(Card card) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE_SQL)
        ) {
            pstmt.setString(1, card.getId());
            pstmt.setString(2, card.getName());
            pstmt.setDouble(3, card.getDamage());
            pstmt.setString(4, card.getElementType().toString());
            pstmt.setString(5, card.getCardType().toString());
            pstmt.setString(6, card.getPackageId());

            pstmt.execute();
            return card;
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Speichern der Karte in der Datenbank: " + card.getId(), e);
        }
    }

    @Override
    public Optional<Card> findById(String id) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID_SQL)
        ) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Card card = new Card();
                card.setId(rs.getString("id"));
                card.setName(rs.getString("name"));
                card.setDamage(rs.getInt("damage"));
                card.setElementType(Card.ElementType.valueOf(rs.getString("element_type")));
                card.setCardType(Card.CardType.valueOf(rs.getString("card_type")));
                card.setPackageId(rs.getString("package_id"));
                card.setInDeck(rs.getBoolean("in_deck"));
                // Setzen Sie weitere Felder, falls erforderlich

                return Optional.of(card);
            }
            return null; // Keine Karte mit der gegebenen ID gefunden
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Suchen der Karte in der Datenbank: " + id, e);
        }
    }

    @Override
    public Card update(Card card) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(UPDATE_SQL)
        ) {
            pstmt.setString(1, card.getOwnerUsername()); // Annahme, dass 'ownerUsername' ein Feld von 'Card' ist
            pstmt.setString(2, card.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating card owner failed, no rows affected.");
            }

            return findById(card.getId()).get(); // Gibt die aktualisierte Karte zurück
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Aktualisieren des Kartenbesitzers in der Datenbank: " + card.getId(), e);
        }
    }


    @Override
    public void deleteById(String id) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(DELETE_SQL)
        ) {
            pstmt.setString(1, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting card failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen der Karte aus der Datenbank: " + id, e);
        }
    }

    public int getMaxPackageId() {
        // SQL-Query, um die höchste packageId zu erhalten
        String sql = "SELECT MAX(package_id) FROM CardTable";
        try (Connection con = database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Gibt die höchste packageId zurück
            } else {
                return 0; // Falls noch keine packageId vorhanden ist
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen der maximalen packageId", e);
        }
    }

    public List<Card> findCardsByPackageId(String packageId) {
        List<Card> cards = new ArrayList<>();

        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(FIND_ALL_CARDS_FROM_PACKAGE)) {

            pstmt.setString(1, packageId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getString("id"));
                card.setName(rs.getString("name"));
                card.setDamage(rs.getDouble("damage"));
                card.setElementType(Card.ElementType.valueOf(rs.getString("element_type")));
                card.setCardType(Card.CardType.valueOf(rs.getString("card_type")));
                card.setOwnerUsername(rs.getString("owner_username"));
                card.setPackageId(rs.getString("package_id"));
                card.setInDeck(rs.getBoolean("in_deck"));
                cards.add(card);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen der Karten aus der Datenbank", e);
        }

        return cards;
    }


}
