package telran.supermarket.dao;

import telran.supermarket.model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class SupermarketImpl implements Supermarket {
    private Collection<Product> products = new ArrayList<>();

    public SupermarketImpl(Collection<Product> products) {
        this.products = products;
    }
    //O(n)
    @Override
    public boolean addProduct(Product product) {
        if (product == null || findByBarCode(product.getBarCode()) != null) {
            return false;
        }
        return products.add(product);
    }
    //O(n)
    @Override
    public Product removeProduct(long barCode) {
        Product product = findByBarCode(barCode);
        if (product != null) {
            products.remove(product);
        }
        return product;
    }
    //O(n)
    @Override
    public Product findByBarCode(long barCode) {
        for (Product product : products) {
            if (product.getBarCode() == barCode) {
                return product;
            }
        }
        return null;
    }
    //O(n)
    @Override
    public Iterable<Product> findByCategory(String category) {
        return createIterable(filteredByCondition(p->p.getCategory().equals(category)));
    }
    //O(n)
    @Override
    public Iterable<Product> findByBrand(String brand) {
        return createIterable(filteredByCondition(p->p.getBrand().equals(brand)));
    }
    //O(n)
    @Override
    public Iterable<Product> findProductsWithExpireDate() {
        LocalDate today = LocalDate.now();
        return createIterable(filteredByCondition(p->p.getExpDate().isBefore(today)));
    }
    //O(1)
    @Override
    public int skuQuantity() {
        return products.size();
    }
    //O(n)
    private Collection<Product> filteredByCondition(Predicate<Product> condition) {
        Collection<Product> res = new ArrayList<>();
        for (Product product : products) {
            if (condition.test(product)) {
                res.add(product);
            }
        }
        return res;
    }

    private Iterable<Product> createIterable(Collection<Product> collection) {
        return new Iterable<Product>() {
            @Override
            public Iterator<Product> iterator() {
                return collection.iterator();
            }
        };
    };
};