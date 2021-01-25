package onlineShop.dao;

import onlineShop.model.Cart;
import onlineShop.model.CartItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addCartItem(CartItem cartItem) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(cartItem);
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

    public void removeCartItem(int cartItemId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            CartItem cartItem = (CartItem) session.get(CartItem.class, cartItemId);
            List<CartItem> cartItemList = cartItem.getCart().getCartItem();
            cartItemList.remove(cartItem);
            session.beginTransaction();
            session.delete(cartItem);
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

    public void removeAllCartItems(Cart cart) {
        List<CartItem> cartItemList = cart.getCartItem();
        for (CartItem item : cartItemList) {
            removeCartItem(item.getId());
        }
    }
}
