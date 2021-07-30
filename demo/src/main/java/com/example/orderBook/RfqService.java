package com.example.orderBook;

import java.util.Optional;

public interface RfqService {
    Optional<Quote> quoteFor(int amount);
}
