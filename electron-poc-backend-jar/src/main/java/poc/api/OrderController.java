package poc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poc.dto.Order;
import poc.service.OrderService;

import java.util.List;

@RestController
public class OrderController {

    private static final String BASE_URL = "/orders";
    @Autowired
    private OrderService orderService;

    @GetMapping(BASE_URL)
    public ResponseEntity<List<Order>> getOrders() {
        final List<Order> allOrders = orderService.getAllOrders();
        return ResponseEntity.ofNullable(allOrders);
    }

    @PostMapping(BASE_URL + "/order")
    public ResponseEntity<String> addOrder(@RequestBody final Order order) {
        orderService.addOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(BASE_URL + "/order/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable final Long orderId) {
        orderService.deleteOrderById(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
