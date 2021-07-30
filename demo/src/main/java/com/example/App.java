package com.example;

import java.util.Optional;
import java.util.concurrent.Exchanger;

import com.example.exchanger.Consumer;
import com.example.exchanger.Producer;
import com.example.orderBook.Quote;

public class App 
{
    public static void main( String[] args )
    {
        LogProducer.setConfig("file.txt");
        Exchanger<Optional<Quote>> exchanger = new Exchanger<>();

		Producer p = new Producer(exchanger);
		Consumer c = new Consumer(exchanger);

		(new Thread(p)).start();
		(new Thread(c)).start();
    }
}
