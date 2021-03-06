package onlineShop.dao;

import onlineShop.model.Cart;
import onlineShop.model.CartItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class CartDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Cart getCartById(int cartId) {
        Cart cart = null;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            cart = session.get(Cart.class, cartId);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }

    public Cart validate(int cartId) throws IOException {
        Cart cart = getCartById(cartId);
        if (cart == null || cart.getCartItem().size() == 0) {
            throw new IOException(cartId + "");
        }
        update(cart);
        return cart;
    }

    private void update(Cart cart) {
        double total = calculateTotalPrice(cart);
        cart.setTotalPrice(total);
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(cart);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private double calculateTotalPrice(Cart cart) {
        double total = 0;
        List<CartItem> cartItemList = cart.getCartItem();
        for (CartItem item : cartItemList) {
            total += item.getPrice();
        }
        return total;
    }
}
