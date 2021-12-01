package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.exception.ResourceNotFoundException;
import springboot.model.RestaurantTable;
import springboot.repository.RestaurantTableRepository;

import java.util.*;
import java.util.stream.Collectors;

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
        return getByRestaurantId(restoid).stream().sorted(Comparator.comparing(RestaurantTable::getTableNumber)).collect(Collectors.toList());
                //.orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist with id :" + restoid));
    }

    // create table for a restaurant
    @PostMapping("/{restoid}")
    public RestaurantTable createRestaurantTable(@PathVariable Long restoid, @RequestBody RestaurantTable restaurantTable) {
        restaurantTable.setRestaurantId(restoid);
        List<RestaurantTable> tables = getByRestaurantId(restoid).stream().sorted(Comparator.comparing(RestaurantTable::getTableNumber)).collect(Collectors.toList());
        if (tables.isEmpty()) {restaurantTable.setTableNumber(1);}
        else {
            boolean foundNumber = false;
            for (int i = 0; !foundNumber; i++) {
                if (tables.size() == i ) {
                    foundNumber = true;
                    restaurantTable.setTableNumber(i+1);
                }else{
                    RestaurantTable currentTable = tables.get(i);
                    if (currentTable.getTableNumber() != (i+1)){
                        foundNumber = true;
                        restaurantTable.setTableNumber(i+1);
                    }
                }
            }
        }
        restaurantTable.setStatus(false);
        restaurantTable.setCalling_server(false);
        return restaurantTableRepository.save(restaurantTable);
    }

    // get table for a restaurant by id
    @GetMapping("/{restoid}/{tableid}")
    public ResponseEntity<RestaurantTable> getTable(@PathVariable Long restoid, @PathVariable Long tableid){
        RestaurantTable table = restaurantTableRepository.findById(tableid)
                .orElseThrow(() -> new ResourceNotFoundException("Table does not exist with id :" + tableid));
        return ResponseEntity.ok(table);
    }

    // change table state
    @PutMapping("/{restoid}/{tableid}/status")
    public ResponseEntity<RestaurantTable> changeTableState(@PathVariable Long restoid,@PathVariable Long tableid) {
        RestaurantTable table = restaurantTableRepository.findById(tableid)
                .orElseThrow(() -> new ResourceNotFoundException("Table does not exist with id :" + tableid));
        table.setStatus(!(table.getStatus()));
        restaurantTableRepository.save(table);
        return ResponseEntity.ok(table);
    }

    @PutMapping("/{restoid}/{tableid}/server")
    public ResponseEntity<RestaurantTable> changeTableServerState(@PathVariable Long restoid,@PathVariable Long tableid) {
        RestaurantTable table = restaurantTableRepository.findById(tableid)
                .orElseThrow(() -> new ResourceNotFoundException("Table does not exist with id :" + tableid));
        table.setCalling_server(!(table.getCalling_server()));
        restaurantTableRepository.save(table);
        return ResponseEntity.ok(table);
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

    public List<RestaurantTable> getByRestaurantId(Long restaurantId) {
        return restaurantTableRepository.findAll().stream()
                .filter(table -> Objects.equals(table.getRestaurantId(), restaurantId))
                .collect(Collectors.toList());
    }

}
