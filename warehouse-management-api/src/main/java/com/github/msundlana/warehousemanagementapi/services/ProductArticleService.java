package com.github.msundlana.warehousemanagementapi.services;

import com.github.msundlana.warehousemanagementapi.models.ProductArticleDTO;

public interface ProductArticleService {
    ProductArticleDTO linkProductArticleToProduct(Long productId, Long articleId, int quantity);
}
