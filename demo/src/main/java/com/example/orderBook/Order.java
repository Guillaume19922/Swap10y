package com.example.orderBook;

public class Order {
    private Direction direction;
    private double price;
    private int amount;
    private double yesterdaySettlement;

    public Order(Direction direction, double price, int amount, double yesterdaySettlement) {
        this.direction = direction;
        this.price = price;
        this.amount = amount;
        this.yesterdaySettlement = yesterdaySettlement;
    }

    public double getPrice() {
        return this.price;
    }

    public double getAmount() {
        return this.amount;
    }

    public double getYesterdaySettlement() {
        return this.yesterdaySettlement;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Order{"+
                "direction=" + direction +
                ", price=" + price + '\'' + 
                ", amount=" + amount + 
                ", yesterdaySettlement=" + yesterdaySettlement + 
                '}';
    }
}
