package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.exception.ResourceNotFoundException;
import springboot.model.User;
import springboot.repository.RestaurantRepository;
import springboot.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/usuarios/")
public class UserController {

	private final UserRepository userRepository;
	private final RestaurantRepository restaurantRepository;

	public UserController(UserRepository userRepository, RestaurantRepository restaurantRepository) {
		this.userRepository = userRepository;
		this.restaurantRepository = restaurantRepository;
	}

	@PostMapping("/")
	public User createUser(@RequestBody User user) {
		if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
			throw new IllegalArgumentException("Missing username or password");
		}
		if (!restaurantRepository.findById(user.getRestaurantId()).isPresent()) {
			throw new IllegalArgumentException(String.format("Restaurant with id %d does not exist.", user.getRestaurantId()));
		}

		return userRepository.save(user);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
		return ResponseEntity.ok(user);
	}

	@GetMapping("/{username}/{password}")
	public ResponseEntity<User> checkUser(@PathVariable String username, @PathVariable String password) {
		List<User> userList =  userRepository.findByUsernameAndPassword(username, password);

		if (userList.isEmpty()) {
			throw new ResourceNotFoundException("User or password is incorrect");
		}
		return ResponseEntity.ok(userList.get(0));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id){
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
}
