package com.example.nearby.service;

import com.example.nearby.datamodel.domain.PriceHistory;
import com.example.nearby.repository.PriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceHistoryService extends AbstractBaseService<PriceHistory, PriceHistoryRepository> {

    public PriceHistoryService(PriceHistoryRepository repository) {
        super(repository);
    }

    public List<PriceHistory> getPriceHistoryForProduct(Long id) {
       return getRepository().getPriceHistoryForProduct(id);
    }
}
