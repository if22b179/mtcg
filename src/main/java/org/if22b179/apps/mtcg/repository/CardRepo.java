package org.if22b179.apps.mtcg.repository;

import org.if22b179.apps.mtcg.entity.Card;

import java.util.Optional;

public class CardRepo implements CrudRepo<Card,String>{
    @Override
    public Card save(Card entity) {
        return null;
    }

    @Override
    public Optional<Card> findById(String s) {
        return Optional.empty();
    }

    @Override
    public Card update(Card entity) {
        return null;
    }

    @Override
    public void deleteById(String s) {

    }
}
