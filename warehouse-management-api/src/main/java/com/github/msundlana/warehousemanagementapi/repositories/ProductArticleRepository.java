package com.github.msundlana.warehousemanagementapi.repositories;

import com.github.msundlana.warehousemanagementapi.models.ProductArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductArticleRepository extends JpaRepository<ProductArticle, Long> {
    Page<ProductArticle> findAllByArticleStockGreaterThanAndProductNameIgnoreCaseContaining(
            int minStock,String query,Pageable pageable);


    Optional<ProductArticle> findByProductIdAndArticleId(Long projectId, Long employeeSkillId);
}
