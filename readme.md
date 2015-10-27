BellmanFord Arbitrage Bot
===========================

This is a simple bellman-ford bot that uses the negative cycle detection feature of the algorithm to 
find favorable currency trades to make.

Typically the trades are less than 0.5% profit, will take 3 steps, and must be filled quickly to be profitable.



Application Structure
=====================

The application consists of two sets of code: 

	1. The cycle detection system. This creates a set of trades to execute.
	2. The executor. It takes a set of trades and executes them
	
The code currently has a Btc-e implementation but is meant to provide interfaces to easily build for 
other exchanges.




Trading API
===============

The application's trading system is based on an existing library that is wrapped to provide multi-step execution
and controlled deviation from the initial limit order intended to be executed.


https://github.com/alexandersjn/btc_e_assist_api

