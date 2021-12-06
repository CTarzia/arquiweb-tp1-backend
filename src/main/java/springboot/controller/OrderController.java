package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.exception.ResourceNotFoundException;
import springboot.model.*;
import springboot.repository.OrderRepository;
import springboot.repository.RestaurantTableRepository;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/orders/")
public class OrderController {

    private final OrderRepository orderRepository;
    private final RestaurantTableRepository restaurantTableRepository;

    public OrderController(OrderRepository orderRepository, RestaurantTableRepository restaurantTableRepository) {
        this.orderRepository = orderRepository;
        this.restaurantTableRepository = restaurantTableRepository;
    }

    // get one order by id
    @GetMapping("/{orderid}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderid){
        Order order = orderRepository.findById(orderid)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + orderid));
        return ResponseEntity.ok(order);
    }

    @PostMapping("/table/{restoid}")
    public ResponseEntity<Order> createTableOrder(@RequestBody TableOrder order, @PathVariable Long restoid) {
        if (restaurantTableRepository.findByRestaurantIdAndTableId(restoid, order.getTableNumber()).isEmpty()) {
            throw new IllegalArgumentException(String.format("No table found with tableId %d and restaurantId %d", restoid, order.getTableNumber()));
        }
        return createOrder(order, restoid, Type.TABLE);
    }

    @PostMapping("/pickup/{restoid}")
    public ResponseEntity<Order> createPickupOrder(@RequestBody PickupOrder order, @PathVariable Long restoid) {
        return createOrder(order, restoid, Type.PICKUP);
    }

    // update order
    @PutMapping("/{orderid}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderid, @RequestBody Order newOrder){
        Order order = orderRepository.findById(orderid)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + orderid));

        order.setStatus(newOrder.getStatus());

        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    // delete order rest api
    @DeleteMapping("/{orderid}")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Long orderid){
        Order order = orderRepository.findById(orderid)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + orderid));

        orderRepository.delete(order);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Order> createOrder(Order order, Long restoid, Type type) {
        order.setType(type);
        order.setRestoId(restoid);
        order.setStatus(Status.PENDING);
        order.setAppId(App.APP_ID);
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }
}
