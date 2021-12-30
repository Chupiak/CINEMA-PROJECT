package chupiak.dao.impl;

import chupiak.dao.OrderDao;
import chupiak.exception.DataProcessingException;
import chupiak.model.Order;
import chupiak.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order add(Order order) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert order to DB: "
                    + order, e);
        }
    }

    @Override
    public List<Order> getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Order o "
                            + "join fetch o.tickets ot "
                            + "join fetch ot.movieSession otm "
                            + "join fetch otm.movie "
                            + "join fetch otm.cinemaHall "
                            + "join fetch o.user "
                            + "where o.user =:user", Order.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find order by user: " + user, e);
        }
    }
}
