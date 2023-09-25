package com.example.nearby.repository;

import com.example.nearby.datamodel.domain.PriceHistory;
import com.example.nearby.datamodel.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    @Query("SELECT ph FROM PriceHistory ph WHERE ph.product.productId = :productId")
    List<PriceHistory> getPriceHistoryForProduct(@Param("productId") Long productId);
}
