package lesson6;

import com.github.javafaker.Faker;
import lesson6.DTO.Product;
import lesson6.service.ProductService;
import lesson6.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import retrofit2.Response;
import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.dao.ProductsMapper;
import ru.geekbrains.java4.lesson6.db.model.Products;
import ru.geekbrains.java4.lesson6.db.model.ProductsExample;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class AbstractTest {
    static ProductService productService;
    Faker faker = new Faker();
    Product product;
    int id;
    String title;
    static ProductsMapper productsMapper;
        @SneakyThrows
        @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            //Открываем сессию
            SqlSession session = sqlSessionFactory.openSession();
            //указываем каким интерфейсом будем пользоваться. Откуда будем брать информацию.В нашем случае CategoriesMapper
            productsMapper = session.getMapper(ProductsMapper.class);
    }
    @AfterEach
    @SneakyThrows
    void tearDown() {

        ProductsExample example=new ProductsExample();
        example.createCriteria().andIdEqualTo((long)id);
        productsMapper.deleteByExample(example);
    }
}
