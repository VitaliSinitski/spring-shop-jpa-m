package com.vitali.repository;

import java.util.List;

public interface NewOrderRepository {
    void createNewOrder(List<String> orderItemsIds, String information, Long cardId);
}
