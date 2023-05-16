package webserver.ecommerce.business.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webserver.ecommerce.business.dto.FeedbackDto;
import webserver.ecommerce.business.dto.OrderDto;
import webserver.ecommerce.business.services.FeedbackServices;
import webserver.ecommerce.business.services.OrderServices;
import webserver.ecommerce.business.services.ProductServices;
import webserver.ecommerce.business.services.UserServices;
import webserver.ecommerce.data.entity.FeedbackEntity;
import webserver.ecommerce.data.entity.repository.FeedbackRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackServiceImpl extends ServicesBaseImpl<FeedbackEntity, FeedbackDto> implements FeedbackServices {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private ProductServices productServices;

    @Autowired
    private OrderServices orderServices;

    @Autowired
    public FeedbackServiceImpl(ModelMapper modelMapper) {
        super(FeedbackEntity.class, FeedbackDto.class, modelMapper);
    }

    @Override
    public ResponseEntity<FeedbackDto> create(FeedbackDto feedbackDto) {
        boolean contains = orderServices.getOrdersOfUser(feedbackDto.getUserId()).stream().map(OrderDto::getProductId).toList().contains(feedbackDto.getProductId());
        if (!contains){
            return ResponseEntity.badRequest().header("Feedback","Daha önce satın alınmamış ürün!!").body(feedbackDto);
//            return new ResponseEntity<FeedbackDto>(feedbackDto,HttpStatus.BAD_REQUEST);
        }

        feedbackDto.setFeedbackDate(LocalDateTime.now());
        return super.create(feedbackDto);
    }

    @Override
    public ResponseEntity<FeedbackDto> updateById(Long id, FeedbackDto feedbackDto) {
        FeedbackEntity newEntity = dtoToEntity(feedbackDto);
        FeedbackEntity oldEntity = dtoToEntity(getById(id).getBody());

        oldEntity.setProductEntity(newEntity.getProductEntity());
        oldEntity.setFeedbackDate(LocalDateTime.now());
        oldEntity.setUser(newEntity.getUser());
        oldEntity.setContent(newEntity.getContent());

        newEntity = feedbackRepository.save(oldEntity);
        return ResponseEntity.ok(entityToDto(newEntity));
    }

    @Override
    public List<FeedbackDto> getFeedbacksOfProduct(Long productId) {
        return feedbackRepository
                .getFeedbackEntitiesByProductEntity(productServices.dtoToEntity(productServices.getById(productId).getBody()))
                .stream().map(this::entityToDto).toList();
    }

    @Override
    public FeedbackDto entityToDto(FeedbackEntity feedbackEntity) {
        FeedbackDto dto = super.entityToDto(feedbackEntity);
        dto.setUserId(feedbackEntity.getUser().getId());
        dto.setProductId(feedbackEntity.getProductEntity().getId());
        return dto;
    }

    @Override
    public FeedbackEntity dtoToEntity(FeedbackDto feedbackDto) {
        FeedbackEntity entity = super.dtoToEntity(feedbackDto);
        entity.setUser(userServices.dtoToEntity(userServices.getById(feedbackDto.getUserId()).getBody()));
        entity.setProductEntity(productServices.dtoToEntity(productServices.getById(feedbackDto.getProductId()).getBody()));
        return entity;
    }
}
