package com.charles.website.service.impl;

import com.charles.website.domain.Category;
import com.charles.website.domain.EStatusOrder;
import com.charles.website.domain.Order;
import com.charles.website.domain.Product;
import com.charles.website.model.CategoryQuantity;
import com.charles.website.model.StatisticOrder;
import com.charles.website.model.StatisticProduct;
import com.charles.website.model.dto.OrderDTO;
import com.charles.website.repository.CategoryRepository;
import com.charles.website.repository.OrderRepository;
import com.charles.website.repository.ProductRepository;
import com.charles.website.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public StatisticProduct statisticProduct() {
        StatisticProduct res = new StatisticProduct();
        List<Category> listCategoryStock = categoryRepository.findAllProductStock();
        List<CategoryQuantity> listCategoryQuantityStock = new ArrayList<>();
        for(Category category: listCategoryStock) {
            List<Product> listProduct = productRepository.findAllStock(category.getId());
            listCategoryQuantityStock.add(new CategoryQuantity(category, listProduct.size()));
        }
        res.setListProductNotSold(listCategoryQuantityStock);

        List<Category> listCategorySold = categoryRepository.findAllProductStock();
        List<CategoryQuantity> listCategoryQuantitySold = new ArrayList<>();
        for(Category category: listCategorySold) {
            List<Product> listProduct = productRepository.findAllSold(category.getId());
            listCategoryQuantitySold.add(new CategoryQuantity(category, listProduct.size()));
        }
        res.setListProductSold(listCategoryQuantitySold);

        return res;
    }

    @Override
    public StatisticOrder statisticOrder() {
        List<Order> listOrderSuccess = orderRepository.findAllByStatus(EStatusOrder.SUCCESS);
        List<Order> listOrderCancle = orderRepository.findAllByStatus(EStatusOrder.CANCLE);

        double total = 0;
        for(Order order: listOrderSuccess) {
            total += order.getOrderTotal();
        }

        StatisticOrder statisticOrder = new StatisticOrder();
        statisticOrder.setListOrderSuccess(listOrderSuccess.stream().map(order -> new OrderDTO(order)).collect(Collectors.toList()));
        statisticOrder.setListOrderCancle(listOrderCancle.stream().map(order -> new OrderDTO(order)).collect(Collectors.toList()));
        statisticOrder.setTotalStatistic(total);

        return statisticOrder;
    }
}
