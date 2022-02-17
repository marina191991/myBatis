package lesson6;

import lesson6.DTO.Product;
import lombok.SneakyThrows;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.java4.lesson6.db.model.Products;
import ru.geekbrains.java4.lesson6.db.model.ProductsExample;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ModifyProductTest extends AbstractTest{
    int price;
    int priceModify;
    ProductsExample example;
    @SneakyThrows
    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withPrice((int) (Math.random() * 10000))
                .withCategoryTitle("Food");
        Response<Product> response = productService.createProduct(product).execute();
        id = response.body().getId();
        price = response.body().getPrice();
        title = response.body().getTitle();

        example = new ProductsExample();

    }


    @Test
    @SneakyThrows
    void modifyProducts() {
        example.createCriteria().andIdEqualTo((long)id);
        Products products = new Products();
        priceModify =price + 1;
        products.setId((long)id);
        products.setTitle(title);
        products.setCategory_id((long)1);
        products.setPrice(priceModify);
        productsMapper.updateByExample(products,example);
        example.createCriteria().andIdEqualTo((long)id).andCategory_idEqualTo((long) 1).andPriceEqualTo(priceModify);
        List<Products> list = productsMapper.selectByExample(example);
        assertThat(list , hasSize(1));


    }

    @Test
    @SneakyThrows
    void returnsProductById() {
        Response<Product> response = productService.getProductById(id).execute();
        assertThat(response.code(), CoreMatchers.is(200));
        assertThat(response.body().getId(), CoreMatchers.is(id));

        example.createCriteria().andIdEqualTo((long)id);
        List<Products> list = productsMapper.selectByExample(example);
        assertThat(list , hasSize(1));
        list.forEach(p -> assertThat( p.getTitle(),equalTo(title)));
        list.forEach(p -> assertThat( p.getCategory_id(),equalTo((long)1)));

        System.out.println();
    }


}
