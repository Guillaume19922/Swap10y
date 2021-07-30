package com.example.exchanger;

import java.util.Optional;
import java.util.concurrent.Exchanger;
import java.util.logging.Level;

import com.example.LogProducer;
import com.example.orderBook.LiveOrderBoard;
import com.example.orderBook.Quote;
import com.example.orderBook.RandomOrderBook;
import com.example.orderBook.RfqServiceImpl;

public class Producer implements Runnable {
	private Exchanger<Optional<Quote>> exchanger;

	public Producer(Exchanger<Optional<Quote>> exchanger) {
		this.exchanger = exchanger;
	}

	@Override
	public void run() {

		while (true) {
			try {
                LiveOrderBoard orders = RandomOrderBook.generateOrders(10);

				RfqServiceImpl rfq = new RfqServiceImpl(orders);

				Optional<Quote> optQuote = rfq.quoteFor(1);
				
				if(optQuote.isPresent()) {
					exchanger.exchange(optQuote);
				} else {
					LogProducer.log(Level.INFO, "No Quotes found.");
				}
				
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
