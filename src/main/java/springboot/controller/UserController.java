package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.exception.ResourceNotFoundException;
import springboot.model.User;
import springboot.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/usuarios/")
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
		return ResponseEntity.ok(user);
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
