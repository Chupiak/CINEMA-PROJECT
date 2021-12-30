package chupiak.service;

import chupiak.dao.ShoppingCartDao;
import chupiak.dao.TicketDao;
import chupiak.model.MovieSession;
import chupiak.model.ShoppingCart;
import chupiak.model.Ticket;
import chupiak.model.User;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartDao shoppingCartDao;
    private final TicketDao ticketDao;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartDao shoppingCartDao, TicketDao ticketDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.ticketDao = ticketDao;
    }

    @Override
    public void addSession(MovieSession movieSession, User user) {
        Ticket newTicket = new Ticket();
        newTicket.setUser(user);
        newTicket.setMovieSession(movieSession);

        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user);
        shoppingCart.getTickets().add(ticketDao.add(newTicket));
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartDao.add(shoppingCart);
    }

    @Override
    public void clearShoppingCart(ShoppingCart cart) {
        cart.setTickets(new ArrayList<>());
        shoppingCartDao.update(cart);
    }
}
