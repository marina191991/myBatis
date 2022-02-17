package lesson6;

import lesson6.DTO.GetCategoryResponse;
import lesson6.service.CategoryService;
import lesson6.utils.RetrofitUtils;
import lombok.SneakyThrows;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.dao.ProductsMapper;
import ru.geekbrains.java4.lesson6.db.model.Categories;
import ru.geekbrains.java4.lesson6.db.model.CategoriesExample;
import ru.geekbrains.java4.lesson6.db.model.Products;
import ru.geekbrains.java4.lesson6.db.model.ProductsExample;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetCategoryTest {
    static CategoryService categoryService;

    static CategoriesMapper categoriesMapper;

    @BeforeAll
    static void beforeAll() throws IOException {
    categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
        //Настройки подключения к БД
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    //Открываем сессию
    SqlSession session = sqlSessionFactory.openSession();
    //указываем каким интерфейсом будем пользоваться. Откуда будем брать информацию.В нашем случае CategoriesMapper
    categoriesMapper = session.getMapper(CategoriesMapper.class);
    }
    @SneakyThrows
    @Test
    void getCategoryByIdTest () {
        Response<GetCategoryResponse> response = categoryService.getCategory(100).execute();
       /* assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(100));
        assertThat(response.body().getTitle(), equalTo("Clothes"));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo("Clothes")));*/

        CategoriesExample example = new CategoriesExample();
        example.createCriteria().andTitleEqualTo("Clothes").andIdEqualTo(100);
        List<Categories> list = categoriesMapper.selectByExample(example);
        assertThat(list , hasSize(1));
        System.out.println(categoriesMapper.countByExample(example));

    }
}
