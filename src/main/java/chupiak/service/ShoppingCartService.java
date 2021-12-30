package chupiak.service;

import chupiak.model.MovieSession;
import chupiak.model.ShoppingCart;
import chupiak.model.User;

public interface ShoppingCartService {
    void addSession(MovieSession movieSession, User user);

    ShoppingCart getByUser(User user);

    void registerNewShoppingCart(User user);

    void clearShoppingCart(ShoppingCart cart);
}
