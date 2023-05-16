package webserver.ecommerce.business.services;

import webserver.ecommerce.business.dto.FeedbackDto;
import webserver.ecommerce.data.entity.FeedbackEntity;

import java.util.List;

public interface FeedbackServices extends ServicesBase<FeedbackEntity, FeedbackDto> {

        public List<FeedbackDto> getFeedbacksOfProduct(Long productId);
}
