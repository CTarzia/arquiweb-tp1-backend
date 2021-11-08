package springboot.controller;

import springboot.exception.ResourceNotFoundException;
import springboot.model.RestaurantTable;
import springboot.repository.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/mesas/")
public class RestaurantTableController {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    // get all tables for a restaurant
    @GetMapping("/{restoid}")
    public List<RestaurantTable> getAllTables(@PathVariable Long restoid){
        return getByRestaurantId(restoid);
                //.orElseThrow(() -> new ResourceNotFoundException("Restaurant not exist with id :" + restoid));
    }

    // create table for a restaurant
    @PostMapping("/{restoid}")
    public RestaurantTable createRestaurantTable(@PathVariable Long restoid, @RequestBody RestaurantTable restaurantTable) {
        restaurantTable.setRestaurantId(restoid);
        List<RestaurantTable> tables = getByRestaurantId(restoid);
        if (tables.isEmpty()) {restaurantTable.setTableNumber(1);}
        else {
            RestaurantTable lastTable = tables.get(tables.size() - 1);
            restaurantTable.setTableNumber(lastTable.getTableNumber() + 1);
        }
        return restaurantTableRepository.save(restaurantTable);
    }

    // get table for a restaurant by id
    @GetMapping("/{restoid}/{tableid}")
    public ResponseEntity<RestaurantTable> getTable(@PathVariable Long restoid, @PathVariable Long tableid){
        RestaurantTable table = restaurantTableRepository.findById(tableid)
                .orElseThrow(() -> new ResourceNotFoundException("Table does not exist with id :" + tableid));
        return ResponseEntity.ok(table);
        // No estoy usando el restoid porque el id de la mesa es único
        // El id de la mesa no es el número de mesa. Tendría que incluír el número de mesa en el struct de mesa?
    }

    // change table state
    @PutMapping("/{restoid}/{tableid}")
    public ResponseEntity<RestaurantTable> changeTableState(@PathVariable Long restoid,@PathVariable Long tableid) {
        RestaurantTable table = restaurantTableRepository.findById(tableid)
                .orElseThrow(() -> new ResourceNotFoundException("Table does not exist with id :" + tableid));
        table.setStatus(!(table.getStatus()));
        restaurantTableRepository.save(table);
        return ResponseEntity.ok(table);
    }

    public List<RestaurantTable> getByRestaurantId(Long restaurantId) {
        List<RestaurantTable> tables = restaurantTableRepository.findAll().stream()
                .filter(table -> Objects.equals(table.getRestaurantId(), restaurantId))
                .collect(Collectors.toList());
        return tables;
    }

}
