package lesson6;


import lesson6.DTO.Product;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.geekbrains.java4.lesson6.db.model.Products;
import ru.geekbrains.java4.lesson6.db.model.ProductsExample;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class CreateProductTest extends AbstractTest{



@Test
@SneakyThrows
    void createsNewProduct () {
   product =new Product()
            .withTitle(faker.food().ingredient())
            .withPrice((int) (Math.random() * 10000))
            .withCategoryTitle("Food");
    Response<Product> response = productService.createProduct(product).execute();

     long id= response.body().getId();
    String title=response.body().getTitle();
       assertThat(response.isSuccessful(), CoreMatchers.is(true));
    ProductsExample example = new ProductsExample();
    example.createCriteria().andTitleEqualTo(title).andIdEqualTo(id).andCategory_idEqualTo((long) 1);
    List<Products> list = productsMapper.selectByExample(example);
    assertThat(list , hasSize(1));


    //System.out.println();
}



}
