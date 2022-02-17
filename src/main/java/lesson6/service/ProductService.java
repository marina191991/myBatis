package lesson6.service;

import lesson6.DTO.Product;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.*;

public interface ProductService {
    @GET ("products")
    Call<ResponseBody> getProducts ();
    @GET ("products/{id}")
    Call<Product> getProductById (@Path("id")  int id);
    @POST ("products")
    Call<Product> createProduct (@Body Product createProductRequest);
    @PUT ("products")
    Call<Product> modifyProduct (@Body Product modifyProductRequest);
    @DELETE ("products/{id}")
    Call<ResponseBody> deleteProduct (@Path("id") int id);

}
