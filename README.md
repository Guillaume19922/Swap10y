This is an application I had to design for an automated trading position in an American Bank.

Write a program in Java that simulates the process of replying to RFQs from clients for a 10y swap.

The application should, for example, construct a ticking 10y swap mid price and order book (for example from a random process generator), simulate RFQs (request for quotes) arriving at 
random intervals with size and direction and reply back with the corresponding price. If possible, implement the pricing and the rfq processes on 2 separate 
threads of the program. You should also log the working of the application (pricing, RFQ events and prices, etc) to a log file (or simply print them to the 
console). Finally, try to build the program as a jar that can be run from the command line.

In order to launch the program, you have to:
cd target -> ls
java -jar demo-1.0-SNAPSHOT.jar.
