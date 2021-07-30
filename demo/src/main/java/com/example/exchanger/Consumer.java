package com.example.exchanger;

import java.util.Optional;
import java.util.concurrent.Exchanger;
import java.util.logging.Level;

import com.example.LogProducer;
import com.example.orderBook.Quote;
import com.example.swapPricer.IR;
import com.example.swapPricer.IRresults;

public class Consumer implements Runnable {

	private Optional<Quote> msg;
	private Exchanger<Optional<Quote>> exchanger;

	public Consumer(Exchanger<Optional<Quote>> exchanger) {
		this.exchanger = exchanger;
	}

	@Override
	public void run() {
		while (true) {
			try {
				msg = exchanger.exchange(msg);
				Quote quote = msg.get();
				double bid = quote.bid;
				double ask = quote.ask;

				double rateBid = (100 - bid) / 10;
				double rateAsk = 100 - ask / 10;
				
				IRresults pricerResults = runPricer();
				double y10rate = pricerResults.getY10rate();

				if(shouldBuy(rateBid, y10rate)) {
					LogProducer.log(Level.INFO, 
					"\n----------------\n" +
					quote.toString() + "\n" +
					"y10rate=" + y10rate + "\n" +
					"price=" + pricerResults.getValue() + "\n" +
					"LET'S BUY ! \n" +
					"----------------"
					);
				} else {
					LogProducer.log(Level.INFO, 
					"\n----------------\n" +
					"DON'T BUY !" +
					"\n----------------\n"
					);
				}

				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean shouldBuy(double rateBid, double y10rate) {
		if (rateBid > y10rate) {
			return  true;
		}
		return false;
	}

	private static IRresults runPricer() throws InterruptedException {
		double notional = 1000000; // notional
		double K = 0.04; // fixed rate IRS
		double alpha = 0.25; // daycount factor
		double sigma = 0.001; // fwd rates volatility
		double dT = 0.25;
		int N = 10; // number forward rates
		int M = 1000; // number of simulations

		IR ir1 = new IR(notional, K, alpha, sigma, dT, N, M);
		IRresults results = ir1.runLIBORsimulations();
		return results;
	}

}
