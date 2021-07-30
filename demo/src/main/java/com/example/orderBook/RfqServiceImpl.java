package com.example.orderBook;

import java.math.BigDecimal;
import java.util.*;

public class RfqServiceImpl implements RfqService {

    private BigDecimal makeSomeMoney = BigDecimal.valueOf(0.01);

    private interface CalculatePriceStrategy {
        double calculatePrice(double price);
    }

    private CalculatePriceStrategy bidPriceStrategy = price -> BigDecimal.valueOf(price).subtract(makeSomeMoney).doubleValue();
    private CalculatePriceStrategy askPriceStrategy = price -> BigDecimal.valueOf(price).add(makeSomeMoney).doubleValue();

    private LiveOrderBoard liveOrderBoard;

    public RfqServiceImpl(LiveOrderBoard liveOrderBoard) {
        this.liveOrderBoard = liveOrderBoard;
    }

    @Override
    public Optional<Quote> quoteFor(int amount) {
        List<Order> orders = liveOrderBoard.ordersFor();

        Optional<Order> bid = findHighestBuy(orders, amount);
        if (!bid.isPresent()) {
            return Optional.empty();
        }
        Optional<Order> ask = findLowestSell(orders, amount);
        if (!ask.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(createQuote(bid.get(), ask.get()));
    }

    private Optional<Order> findHighestBuy(List<Order> orders, int amount) {
        return orders.stream().filter(o -> o.getDirection() == Direction.BUY && o.getAmount() == amount)
                .max((o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));
    }

    private Optional<Order> findLowestSell(List<Order> orders, int amount) {
        return orders.stream().filter(o -> o.getDirection() == Direction.SELL && o.getAmount() == amount)
                .min((o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));
    }

    private Quote createQuote(Order bid, Order ask) {
        return new Quote(bidPriceStrategy.calculatePrice(bid.getPrice()), askPriceStrategy.calculatePrice(ask.getPrice()));
    }
}