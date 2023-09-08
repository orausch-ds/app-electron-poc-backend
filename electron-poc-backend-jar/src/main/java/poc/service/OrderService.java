package poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poc.dto.Order;
import poc.repo.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void addOrder(final Order order) {
        orderRepository.save(order);
    }

    public void deleteOrderById(final Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
