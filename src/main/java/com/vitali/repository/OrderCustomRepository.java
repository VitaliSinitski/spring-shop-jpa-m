package com.vitali.repository;

import java.util.List;

public interface OrderCustomRepository {
    void createNewOrder(List<String> orderItemsIds, String information, Long cardId);
}
