package com.charles.website.service;

import com.charles.website.model.StatisticOrder;
import com.charles.website.model.StatisticProduct;
import org.springframework.stereotype.Service;

@Service
public interface StatisticService {
    StatisticProduct statisticProduct();

    StatisticOrder statisticOrder();
}
