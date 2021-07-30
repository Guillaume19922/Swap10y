package com.example.orderBook;

public class RandomOrderBook {

    public static LiveOrderBoard generateOrders(int nbOrders) {
        Order[] orders = new Order[nbOrders];
        for (int i = 0; i < nbOrders; i++) {
            if (i < (nbOrders / 2) - 1) {
                orders[i] = generateOneOrder(Direction.BUY);
            } else {
                orders[i] = generateOneOrder(Direction.SELL);
            }
        }
        return LiveOrderBoardImpl.createOrderBoard(orders);
    }

    public static Order generateOneOrder(Direction direction) {
        double price;
        if (direction == Direction.BUY) {
            price = getRandomNumber(94.0, 94.595);
        } else {
            price = getRandomNumber(94.795, 95.395);
        }
        int amount = (int) getRandomNumber(0, 2);
        double yesterdaySettlement = getRandomNumber(95.325, 95.825);

        return new Order(direction, price, amount, yesterdaySettlement);
    }

    public static double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

}
