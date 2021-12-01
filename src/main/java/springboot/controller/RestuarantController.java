package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.exception.ResourceNotFoundException;
import springboot.model.App;
import springboot.model.Order;
import springboot.model.Restaurant;
import springboot.repository.OrderRepository;
import springboot.repository.RestaurantRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/restaurantes/")
public class RestuarantController {

	private RestaurantRepository restaurantRepository;

	private final OrderRepository orderRepository;

	public RestuarantController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Autowired
	public RestuarantController(RestaurantRepository restaurantRepository, OrderRepository orderRepository) {
		this.restaurantRepository = restaurantRepository;
		this.orderRepository = orderRepository;
	}

	@GetMapping("/")
	public List<Restaurant> getAllRestaurants(){
		return restaurantRepository.findAll();
	}

	@PostMapping("/")
	public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
		restaurant.setAppId(App.APP_ID);
		return restaurantRepository.save(restaurant);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist with id :" + id));
		return ResponseEntity.ok(restaurant);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurantDetails){
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist with id :" + id));

		restaurant.setLatitude(restaurantDetails.getLatitude());
		restaurant.setLongitude(restaurantDetails.getLongitude());
		restaurant.setName(restaurantDetails.getName());
		restaurant.setWorkingHours(restaurantDetails.getWorkingHours());

		Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
		return ResponseEntity.ok(updatedRestaurant);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteRestaurant(@PathVariable Long id){
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist with id :" + id));

		restaurantRepository.delete(restaurant);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{restoId}/pedidos")
	public ResponseEntity<List<Order>> getRestaurantOrders(@PathVariable Long restoId) {
		List<Order> orders = orderRepository.findAll().stream()
				.filter(order -> Objects.equals(order.getRestoId(), restoId))
				.collect(Collectors.toList());
		return ResponseEntity.ok(orders);
	}
	
}
