package com.github.bruce_mig.query_service.query.api.controller;


import com.github.bruce_mig.axon_cqrs.commons.model.ProductRestModel;
import com.github.bruce_mig.axon_cqrs.commons.queries.GetProductByIdQuery;
import com.github.bruce_mig.axon_cqrs.commons.queries.GetProductsQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries/products")
@Slf4j
public class ProductQueryController {

    @Autowired
    private transient QueryGateway queryGateway;

    public ProductQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public ResponseEntity<List<ProductRestModel>>  getAllProducts() {
        GetProductsQuery getProductsQuery = new GetProductsQuery();
        List<ProductRestModel> productRestModels =
        queryGateway.query(getProductsQuery,
                ResponseTypes.multipleInstancesOf(ProductRestModel.class))
                .join();
        return ResponseEntity.ok(productRestModels);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductRestModel> getProduct(@PathVariable String productId) {
        GetProductByIdQuery getProductByIdQuery = GetProductByIdQuery.builder()
                .productId(productId)
                .build();

        ProductRestModel productRestModel =
                queryGateway.query(getProductByIdQuery,
                                ResponseTypes.instanceOf(ProductRestModel.class))
                        .join();

        return ResponseEntity.ok(productRestModel);
    }
}
