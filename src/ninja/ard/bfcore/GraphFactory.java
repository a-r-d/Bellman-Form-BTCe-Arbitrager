package ninja.ard.bfcore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import ninja.ard.bfcore.dto.CurrencyEdge;
import ninja.ard.bfcore.dto.CurrencyGraph;
import ninja.ard.bfcore.dto.CurrencyNode;
import ninja.ard.bfdata.IQuoteDataContainer;

public class GraphFactory {

	final static Logger logger = Logger.getLogger(GraphFactory.class);

	// flag is ONLY for testing! The correct method is to use Bid/Ask always!
	private static boolean useLastPriceForWeight = false;
	
	public static CurrencyGraph buildUndirectedCurrencyGraph(List<IQuoteDataContainer> quotes){
		
		CurrencyGraph graph = new CurrencyGraph();
		HashMap<String, CurrencyNode> nodeMap = new HashMap<>(); 	// a node for each currency
		Set<CurrencyEdge> edges = new HashSet<>();					// an edge for each pair.
		
		for(IQuoteDataContainer quote: quotes) {
			// each quote will be an edge and maybe two nodes.
			// the nodes will go into a hashmap so that we can quickly grab them
			CurrencyNode start = null;
			CurrencyNode end = null;
			
			CurrencyEdge forwardEdge = new CurrencyEdge();
			CurrencyEdge backwardEdge = new CurrencyEdge();
			
			// init START NODE (forward)
			if(nodeMap.containsKey(quote.getStartCurrency()))  {
				start = nodeMap.get(quote.getStartCurrency());
			} else {
				start = new CurrencyNode();
				start.setCurrency(quote.getStartCurrency());
				nodeMap.put(quote.getStartCurrency(), start);
			}
			// init END NODE (backward)
			if(nodeMap.containsKey(quote.getEndCurrency()))  {
				end = nodeMap.get(quote.getEndCurrency());
			} else {
				end = new CurrencyNode();
				end.setCurrency(quote.getEndCurrency());
				nodeMap.put(quote.getEndCurrency(), end);
			}
			
			// init edges, this is a directed graph so we will only add edges to the start nodes.
			forwardEdge.fromNode = start;
			forwardEdge.toNode = end;
			forwardEdge.quote = quote;
			forwardEdge.weight = getWeight(quote, false);
			
			backwardEdge.fromNode = end;
			backwardEdge.toNode = start;
			backwardEdge.quote = quote;
			backwardEdge.weight = getWeight(quote, true); // 
			backwardEdge.isInverted = true;
			
			// add the adjacencies
			start.adjacencies.add(forwardEdge);
			end.adjacencies.add(backwardEdge);
			
			edges.add(forwardEdge);
			edges.add(backwardEdge);
		}
		graph.setEdges(edges);
		graph.setNodeMap(nodeMap);
		graph.setQuotes(quotes);
		
		return graph;
	}
	
	/**
	 * This is a logarithmically normalized weight.
	 * 
	 * Using the ASK price is correct for forward. While the BID price is correct for an inverted.
	 * 
	 * Remember, invert ... log of less than 1 yields a negative num, so that we will be able to build a negative cycle.
	 * 
	 * The result must be multiplied by negative 1
	 * 
	 * @param quote
	 * @return
	 */
	public static double getWeight(IQuoteDataContainer quote, boolean invert) {
		if(invert) {
			if(useLastPriceForWeight) {
				return Math.log10(1 / quote.getLast());
			}
			return Math.log10(1 / quote.getBid());
		} else {
			if(useLastPriceForWeight) {
				return Math.log10(quote.getLast());
			}
			return  Math.log10(quote.getAsk());
		}
	}

	public static boolean isUseLastPriceForWeight() {
		return useLastPriceForWeight;
	}

	public static void setUseLastPriceForWeight(boolean useLastPriceForWeight) {
		GraphFactory.useLastPriceForWeight = useLastPriceForWeight;
	}
	
	
	
}
