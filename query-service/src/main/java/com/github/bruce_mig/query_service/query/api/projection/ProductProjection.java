package com.github.bruce_mig.query_service.query.api.projection;


import com.github.bruce_mig.axon_cqrs.commons.model.ProductRestModel;
import com.github.bruce_mig.query_service.query.api.data.Product;
import com.github.bruce_mig.query_service.query.api.data.ProductRepository;
import com.github.bruce_mig.axon_cqrs.commons.queries.GetProductByIdQuery;
import com.github.bruce_mig.axon_cqrs.commons.queries.GetProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
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
                        .productId(product.getProductId())
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .name(product.getName())
                        .build())
                .toList();

        return productRestModels;
    }

    @QueryHandler
    public ProductRestModel handle(GetProductByIdQuery getProductByIdQuery){
        String productId = getProductByIdQuery.getProductId();
        ProductRestModel productRestModel =  new ProductRestModel();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        BeanUtils.copyProperties(product,productRestModel);

        return productRestModel;
    }
}
