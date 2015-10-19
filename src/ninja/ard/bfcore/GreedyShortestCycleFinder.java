package ninja.ard.bfcore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import ninja.ard.bfcore.dto.CurrencyEdge;
import ninja.ard.bfcore.dto.CurrencyGraph;
import ninja.ard.bfcore.dto.CurrencyNode;
import ninja.ard.bfcore.tradebeans.CurrencyCycle;

public class GreedyShortestCycleFinder {
	
	

	public static void findCyclesWithIncreasingOutcome(CurrencyGraph graph, String startNode){
		
		List<CurrencyCycle> cycles = new ArrayList<CurrencyCycle>();
		CurrencyNode startOn = graph.findNode(startNode);
		Stack<CurrencyNode> nextNodes = new Stack<CurrencyNode>();
		nextNodes.addAll(startOn.getNeighbors());
		
		// simple DFS
		while(nextNodes.isEmpty() == false) {
			CurrencyNode currNode = nextNodes.pop();
			// if we found A cycle:
			if(currNode.equals(startOn)) {
				// add the cycle:
				cycles.add(buildCycleTrades(startOn, currNode));
			}
			
			// otherwise:
			if(currNode.visited == false) {
				currNode.visited = true;
				nextNodes.addAll(currNode.getNeighbors());
			}
		}
		
	}
		
	private static CurrencyCycle buildCycleTrades(CurrencyNode startNode, CurrencyNode lastNode) {
		// look at all previous nodes and build the cycle !!!
		//Node prev = 
		return null;
	}
}
