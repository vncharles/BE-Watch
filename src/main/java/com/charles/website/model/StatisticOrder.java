package com.charles.website.model;

import com.charles.website.model.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class StatisticOrder {
    private List<OrderDTO> listOrderSuccess;
    private List<OrderDTO> listOrderCancle;
    private double totalStatistic;
}
