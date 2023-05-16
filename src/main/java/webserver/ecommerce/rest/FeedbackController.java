package webserver.ecommerce.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webserver.ecommerce.business.dto.CategoryDto;
import webserver.ecommerce.business.dto.FeedbackDto;
import webserver.ecommerce.business.services.FeedbackServices;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "http://localhost:8080")
public class FeedbackController {
    @Autowired
    private FeedbackServices feedbackServices;

    @GetMapping("/getAllFeedbacks")
    public List<FeedbackDto> getAllFeedbacks() {
        return feedbackServices.getAll();
    }

    @PostMapping("/createFeedback")
    public ResponseEntity<FeedbackDto> createFeedback(@RequestBody FeedbackDto dto){
        System.out.println("create feedback");
        return feedbackServices.create(dto);
    }

    @DeleteMapping("/deleteFeedback")
    public ResponseEntity<Map<String, Boolean>> deleteFeedback(@RequestParam Long id){
        return feedbackServices.delete(id);
    }

    @PutMapping("/updateFeedback")
    public ResponseEntity<FeedbackDto> updateFeedback(@RequestParam Long id, @RequestBody FeedbackDto dto){
        return feedbackServices.updateById(id, dto);
    }

    @GetMapping("/getFeedbackById")
    public ResponseEntity<FeedbackDto> getFeedbackById(@RequestParam Long id) {
        return feedbackServices.getById(id);
    }
}
