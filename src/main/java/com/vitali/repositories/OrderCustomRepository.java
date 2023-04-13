package com.vitali.repositories;

import java.util.List;

public interface OrderCustomRepository {
    void createNewOrder(List<String> orderItemsIds, String information, Integer cardId);
}
