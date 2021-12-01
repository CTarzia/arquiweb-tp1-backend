package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.exception.ResourceNotFoundException;
import springboot.model.*;
import springboot.repository.OrderRepository;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/orders/")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // get one order by id
    @GetMapping("/{orderid}")
    public ResponseEntity<Order> getTableOrder(@PathVariable Long orderid){
        Order order = orderRepository.findById(orderid)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + orderid));
        return ResponseEntity.ok(order);
    }

    // create order
    @PostMapping("/table/{restoid}")
    public long createTableOrder(@RequestBody TableOrder order, @PathVariable Long restoid) {
        Status initialStatus = Status.PENDING;
        order.setRestoId(restoid);
        order.setStatus(initialStatus);
        order.setAppId(App.APP_ID);
        orderRepository.save(order);
        return order.getOrderId();
    }

    @PostMapping("/client/{restoid}")
    public long createPickupOrder(@RequestBody PickupOrder order, @PathVariable Long restoid) {
        Status initialStatus = Status.PENDING;
        order.setRestoId(restoid);
        order.setStatus(initialStatus);
        order.setAppId(App.APP_ID);
        orderRepository.save(order);
        return order.getOrderId();
    }

    // update order rest api

    @PutMapping("/{orderid}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderid, @RequestBody Order newOrder){
        Order order = orderRepository.findById(orderid)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + orderid));

        order.setStatus(newOrder.getStatus());
        order.setContent(newOrder.getContent());

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
}
