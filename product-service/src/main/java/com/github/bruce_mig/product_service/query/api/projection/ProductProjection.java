package com.github.bruce_mig.product_service.query.api.projection;

import com.github.bruce_mig.product_service.command.api.data.Product;
import com.github.bruce_mig.product_service.command.api.data.ProductRepository;
import com.github.bruce_mig.product_service.command.api.model.ProductRestModel;
import com.github.bruce_mig.product_service.query.api.queries.GetProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductProjection {

    private final ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRestModel> handle(GetProductsQuery getProductsQuery){
        List<Product> products = productRepository.findAll();

        List<ProductRestModel> productRestModels = products.stream()
                .map(product -> ProductRestModel.builder()
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .name(product.getName())
                        .build())
                .toList();

        return productRestModels;
    }
}
