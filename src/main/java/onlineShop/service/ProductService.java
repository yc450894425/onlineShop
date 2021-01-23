package onlineShop.service;

import onlineShop.dao.ProductDao;
import onlineShop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    public void deleteProduct(int productId) {
        productDao.deleteProduct(productId);
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public Product getProductById(int productId) {
        return productDao.getProductById(productId);
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }
}
