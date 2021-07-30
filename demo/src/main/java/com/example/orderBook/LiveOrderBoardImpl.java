package com.example.orderBook;

import java.util.Arrays;
import java.util.List;

public class LiveOrderBoardImpl implements LiveOrderBoard {
    private List<Order> orders;

    public LiveOrderBoardImpl(List<Order> orders){
        this.orders = orders;
    }

    public static LiveOrderBoard createOrderBoard(Order[] o){
        return new LiveOrderBoardImpl(Arrays.asList(o));
    }

    @Override
    public List<Order> ordersFor() {
        return orders;
    }


}