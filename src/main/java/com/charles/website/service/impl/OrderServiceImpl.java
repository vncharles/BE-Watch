package com.charles.website.service.impl;

import com.charles.website.domain.*;
import com.charles.website.exception.BadRequestException;
import com.charles.website.exception.NotFoundException;
import com.charles.website.model.OrderRequest;
import com.charles.website.repository.OrderRepository;
import com.charles.website.repository.ProductRepository;
import com.charles.website.repository.UserRepository;
import com.charles.website.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void create(OrderRequest req) {
        if(req.getProductId()==null) throw new BadRequestException(400, "Product is required");
        if(req.getUserId()==null) throw new BadRequestException(400, "User is required");
        if(req.getQuantity()==null) throw new BadRequestException(400, "Quantity is required");
        if(req.getAddress()==null) throw new BadRequestException(400, "Address is required");

        Product product = null;
        try {
            product = productRepository.findById(req.getProductId()).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if(product==null) throw new NotFoundException(400, "Product is not found");

        User user = null;
        try {
            user = userRepository.findById(req.getUserId()).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if(user==null) throw new NotFoundException(400, "User is not found");

        if(!product.hasStock(req.getQuantity())) throw new BadRequestException(400, "The number of products is not enough");
        double totalPrice = product.getPrice()*req.getQuantity();
        product.decreaseStock(req.getQuantity());
        productRepository.save(product);

        Order order = new Order(EStatusOrder.WAIT, req.getQuantity(), totalPrice, req.getAddress(), product, user);

        orderRepository.save(order);
    }

    @Override
    public void update(Long id, OrderRequest req) {
        Order order = null;
        try {
            order = orderRepository.findById(id).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if (order==null) throw new NotFoundException(404, "Order is not found");

        if(req.getQuantity()!=null) {
            if(req.getQuantity() > order.getQuantity()) {
                if(!order.getProduct().hasStock(req.getQuantity()-order.getQuantity())) {
                    throw new BadRequestException(400, "The number of products is not enough");
                }
                order.setOrderTotal(req.getQuantity()*order.getProduct().getPrice());
                order.getProduct().decreaseStock(req.getQuantity()-order.getQuantity());
                productRepository.save(order.getProduct());
                order.setQuantity(req.getQuantity());
            } else {
                order.setOrderTotal(req.getQuantity()*order.getProduct().getPrice());
                order.getProduct().increaseStock(order.getQuantity()-req.getQuantity());
                productRepository.save(order.getProduct());
                order.setQuantity(req.getQuantity());
            }
        }
        if(req.getAddress()!=null) {
            order.setAddress(req.getAddress());
        }
        if(req.getStatus()!=null) {
            if(req.getStatus().equals("confirm")) {
                order.setStatus(EStatusOrder.CONFIRM);
            } else if(req.getStatus().equals("delivery")) {
                order.setStatus(EStatusOrder.DELIVERY);
            } else if(req.getStatus().equals("success")) {
                order.setStatus(EStatusOrder.SUCCESS);
            } else if(req.getStatus().equals("cancle")) {
                order.setStatus(EStatusOrder.CANCLE);
                order.getProduct().increaseStock(order.getQuantity());
                productRepository.save(order.getProduct());
            }
        }

        orderRepository.save(order);
    }

    @Override
    public void cancleOrder(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Order order = null;
        try {
            order = orderRepository.findById(id).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if (order==null) throw new NotFoundException(404, "Order is not found");

        if(!order.getUser().getUsername().equals(username)) {
            throw new BadRequestException(400, "User forbiden");
        }


        order.setStatus(EStatusOrder.CANCLE);
        order.getProduct().increaseStock(order.getQuantity());
        productRepository.save(order.getProduct());
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        for(Role role: user.getRoles()) {
            if(role.getName().equals(ERole.ROLE_USER)){
                return orderRepository.findAllByUser(user);
            }
        }
        return orderRepository.findAll();
    }

    @Override
    public Order getDetail(Long id) {
        Order order = null;
        try {
            order = orderRepository.findById(id).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if (order==null) throw new NotFoundException(404, "Order is not found");

        return order;
    }

    @Override
    public void delete(Long id) {
        Order order = null;
        try {
            order = orderRepository.findById(id).get();
        } catch (Exception ex) {ex.printStackTrace();}
        if (order==null) throw new NotFoundException(404, "Order is not found");

        orderRepository.delete(order);
    }
}
