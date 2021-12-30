package chupiak.dao;

import chupiak.model.Order;
import chupiak.model.User;
import java.util.List;

public interface OrderDao {
    Order add(Order order);

    List<Order> getByUser(User user);
}
