package webserver.ecommerce.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webserver.ecommerce.business.dto.CategoryDto;
import webserver.ecommerce.business.dto.OrderDto;
import webserver.ecommerce.business.services.OrderServices;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping("/getAllOrders")
    public List<OrderDto> getAllOrders() {
        return orderServices.getAll();
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto dto){
        return orderServices.create(dto);
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@RequestParam Long id){
        return orderServices.delete(id);
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<OrderDto> updateOrder(@RequestParam Long id,@RequestBody OrderDto dto){
        return orderServices.updateById(id, dto);
    }

    @GetMapping("/getOrderById")
    public ResponseEntity<OrderDto> getOrderById(@RequestParam Long id) { return orderServices.getById(id); }
}
