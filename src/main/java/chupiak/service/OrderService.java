package chupiak.service;

import chupiak.model.Order;
import chupiak.model.ShoppingCart;
import chupiak.model.User;
import java.util.List;

public interface OrderService {
    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getOrdersHistory(User user);
}
