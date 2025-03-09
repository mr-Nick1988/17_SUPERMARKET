package telran.supermarket.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.supermarket.dao.SupermarketImpl;
import telran.supermarket.model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SuperTest {

    private SupermarketImpl supermarket;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        supermarket = new SupermarketImpl(new ArrayList<>());
        product1 = new Product(1L, "Milk", "Dairy", "BrandA", 2.5, LocalDate.of(2025, 3, 10));
        product2 = new Product(2L, "Bread", "Bakery", "BrandB", 1.5, LocalDate.of(2024, 3, 10));
    }

    @Test
    void TestAddProduct() {
        assertTrue(supermarket.addProduct(product1));
        assertEquals(1, supermarket.skuQuantity());
        assertFalse(supermarket.addProduct(product1));
        assertEquals(1, supermarket.skuQuantity());
    }

    @Test
    void TestRemoveProduct() {
        supermarket.addProduct(product1);
        Product removed = supermarket.removeProduct(1L);
        assertEquals(product1, removed);
        assertNull(supermarket.findByBarCode(1L));
        assertEquals(0, supermarket.skuQuantity());
    }

    @Test
    void TestFindByBarCode() {
        supermarket.addProduct(product1);
        assertEquals(product1, supermarket.findByBarCode(1L));
    }

    @Test
    void TestFindByCategory() {
        supermarket.addProduct(product1);
        Product product3 = new Product(3L, "Cheese", "Dairy", "BrandA", 4.0, LocalDate.of(2025, 3, 10));
        supermarket.addProduct(product3);
        int count = 0;
        for (Product product : supermarket.findByCategory("Dairy")) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void TestFindByBrand() {
        supermarket.addProduct(product1);
        supermarket.addProduct(product2);
        Product product3 = new Product(3L, "Cheese", "Dairy", "BrandA", 4.0, LocalDate.of(2025, 3, 10));
        supermarket.addProduct(product3);

        List<Product> brandAproducts = new ArrayList<>();
        for (Product product : supermarket.findByBrand("BrandA")) {
            brandAproducts.add(product);
        }
        assertEquals(2, brandAproducts.size());
        assertTrue(brandAproducts.contains(product1));
        assertTrue(brandAproducts.contains(product3));
        assertFalse(brandAproducts.contains(product2));
    }

    @Test
    void TestFindProductWithExpireDate() {
        supermarket.addProduct(product1);
        supermarket.addProduct(product2);
        List<Product> expiredProducts = new ArrayList<>();
        for(Product product:supermarket.findProductsWithExpireDate()){
            expiredProducts.add(product);
        }
        LocalDate today = LocalDate.of(2025,3,9);
        assertEquals(1,expiredProducts.size());
        for (Product product:expiredProducts){
            assertTrue(product.getExpDate().isBefore(today));
        }
        assertTrue(expiredProducts.contains(product2));
        assertFalse(expiredProducts.contains(product1));
    }
}







