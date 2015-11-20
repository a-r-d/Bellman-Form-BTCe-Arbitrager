BellmanFord Bitcoin/Fiat Arbitrage Bot
=============================================

This is a simple Bellman-Ford bot that uses the negative cycle detection feature of the algorithm to 
find favorable currency trades to make in forex markets (in this case we are targeting bitcoin/fiat/scryptcoin 
markets on btc-e and other exchanges).

Typically the trades the bot finds are less than 0.5% profit, will take 3 steps, and must be filled quickly to be profitable.

The main entry point into the application is the class *BtceBFBot.java* which is a reference implementation and runs the BFAlgorithm
against Btc-e.com exchange public API in a loop. 


How does this work?
=======================

The following image demonstrates the principal of jumping through a set of currencies in order to make a profit 
in pricing discrepancies. In this project the various currency markets on Btc-e are represented as a graph 
and profitable cycles are detected via the eponymous algorithm. Each currency is represented as a Node and 
each exchange rate is represented as an Edge. The graph is a directed graph but each node will always have 
reciprocal edges so it may thought of as cyclic and undirected.

[Imgur](http://i.imgur.com/mOfvbwE.jpg)


Application Structure
=====================

The application consists of two sets of code: 

	1. The cycle detection system. This creates a set of trades to execute.
	2. The trade executor. It takes a set of trades and executes them (Incomplete)
	
The code currently has the begining of a Btc-e implementation but is meant to provide interfaces to easily build for 
other exchanges.


The Profitable Trade Discovery System
=====================================

Using the Bellman-Ford algorithm to detect so-called "negative" cycles in a graph is a very procedure in forex markets. 
Applying this to thinly traded and unregulated bitcoin markets seemed like a pretty fun idea to me so I implemented a 
cycle detector that will take into account corresponding fees that will be taken to make *n* number of trades in a cycle. 
Negative cycles are found by taking the log of the bid or ask price, using that as the wieght of the edge between nodes 
and as you may well be aware the log of a number less than 1 and greater than 0 yields a negative. 

The underlying mechanics of the application are as follows:
	
	1. The Bellman-Form algorithm is used to find a negative cycle in the currency graph
	2. Build a Cycle of trades from this
	3. Calculate profitability
	4. Send profitable trade steps to the trade executor as a multi-step trade.
	5. Wait for the multi-step trade to fill or fail to fill before we send another.


Trading API, Btc-e.com
=======================

The application's trading system is based on an existing library that is wrapped to provide multi-step execution
and controlled deviation from the initial limit order intended to be executed. I have packaged the libarary I intend
to use as a Jar file in the /libs directory.

You can find that library here:
https://github.com/alexandersjn/btc_e_assist_api


The Multi-Step Trade Executor
====================

The trade executor should implemented with something like a Fill-Or-Kill order type with a given number of 
retries and a trade tolerance which will allow slightly less profitable trades to be placed if the initial 
optimal order price cannot be filled. There should be a time limit and retry limit for each step so 
that the executor can decide when to abort and trade back into the base currency. In my case this would be USD.


Why Publish this?
===================

While initially I was optimistic about the potential profitability of running an arbitrager bot on a bitcoin exchange
I have come to believe that due to the percentage based trading fee, high probability of my own oversight in programming
a trade executor and general low reward / work ratio I decided to share the cycle detector system and hope that some 
avaricious sole may want to help me finish the executor system.

Additionally I have become more interested in market-making algorithms after reading [Irene Aldrige's High Frequency Trading 
book](http://www.amazon.com/High-Frequency-Trading-Practical-Algorithmic-Strategies/dp/1118343506), 
but I think it is almost impossible to implement a decent market maker on percentage based fees.

Anyway, I don't think this little bot will help you end up like this guy:

[Imgur](http://i.imgur.com/l2yHhVN.jpg)



