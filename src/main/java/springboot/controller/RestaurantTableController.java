package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.exception.ResourceNotFoundException;
import springboot.model.RestaurantTable;
import springboot.repository.RestaurantTableRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/mesas/")
public class RestaurantTableController {
    private final RestaurantTableRepository restaurantTableRepository;

    public RestaurantTableController(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    // get all tables for a restaurant
    @GetMapping("/{restoid}")
    public List<RestaurantTable> getAllTables(@PathVariable Long restoid){
        return restaurantTableRepository.findByRestaurantIdOrderByTableNumberAsc(restoid);
    }

    // create table for a restaurant
    @PostMapping("/{restoid}")
    public RestaurantTable createRestaurantTable(@PathVariable Long restoid, @RequestBody RestaurantTable restaurantTable) {
        List<RestaurantTable> tables = restaurantTableRepository.findByRestaurantIdOrderByTableNumberAsc(restoid);
        long tableNumber = tables.isEmpty() ? 1 : tables.get(tables.size()-1).getTableNumber() + 1;

        restaurantTable.setRestaurantId(restoid);
        restaurantTable.setTableNumber(tableNumber);
        restaurantTable.setCallingServer(false);
        return restaurantTableRepository.save(restaurantTable);
    }

    // get table for a restaurant by id
    @GetMapping("/{restoid}/{tableid}")
    public ResponseEntity<RestaurantTable> getTable(@PathVariable Long restoid, @PathVariable Long tableid){
        RestaurantTable table = restaurantTableRepository.findById(tableid)
                .orElseThrow(() -> new ResourceNotFoundException("Table does not exist with id :" + tableid));
        return ResponseEntity.ok(table);
    }

    @PostMapping("/{restoid}/{tableid}/call")
    public ResponseEntity<RestaurantTable> callServer(@PathVariable Long restoid, @PathVariable Long tableid) {
        RestaurantTable table = restaurantTableRepository.findById(tableid)
                .orElseThrow(() -> new ResourceNotFoundException("Table does not exist with id :" + tableid));
        table.setCallingServer(!table.getCallingServer()); // can only update calling server

        RestaurantTable updatedTable = restaurantTableRepository.save(table);

        return ResponseEntity.ok(updatedTable);
    }

    @DeleteMapping("/{restoid}/{tableid}")
    public ResponseEntity<Map<String, Boolean>> deleteTable(@PathVariable Long restoid,@PathVariable Long tableid) {
        RestaurantTable table = restaurantTableRepository.findById(tableid)
                .orElseThrow(() -> new ResourceNotFoundException("Table does not exist with id :" + tableid));
        restaurantTableRepository.delete(table);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
