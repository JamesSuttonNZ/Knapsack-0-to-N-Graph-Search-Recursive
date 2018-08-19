package part5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Knapsack_0_to_N_GraphSearch_Recursive {

	private static int[] weights;
	private static int[] values;
	private static int[] groupsize;
	private static ArrayList<Item> items = new ArrayList<Item>(); //to sort items by value/weight for greedy algorithm
	private static int[] choices;
	private static int[] bestChoice;
	private static int weightLimit;
	private static Set<KnapsackNode> visited = new HashSet<KnapsackNode>();
	private static double maxValue = 0;
	private static double maxWeight = 0;
	
	static int count = 0;
	
	
	public static void main(String[] args){

		String[] w = args[0].split(",");
		weights = new int[w.length];
		for(int i = 0; i < w.length; i++){
			weights[i] = Integer.parseInt(w[i]);
		}
		
		String[] v = args[1].split(",");
		values = new int[v.length];
		for(int i = 0; i < v.length; i++){
			values[i] = Integer.parseInt(v[i]);
		}
		
		String[] g = args[2].split(",");
		groupsize = new int[g.length];
		for(int i = 0; i < g.length; i++){
			groupsize[i] = Integer.parseInt(g[i]);
		}
		
		weightLimit = Integer.parseInt(args[3]);
		
		if(values.length != weights.length || values.length != groupsize.length || weights.length != groupsize.length){
			throw new Error("number of values and weights needs to be the same");
		}
		
		for(int i = 0; i < groupsize.length; i++){
			if(groupsize[i] < 1){
				throw new Error("every group must be atleast of size 1");
			}
		}
		
		for(int i = 0; i < values.length; i++){
			items.add(new Item(i, weights[i], values[i], groupsize[i]));
		}
		
		Collections.sort(items, new ItemComparator());
		
		System.out.println("Weights: "+Arrays.toString(weights));
		System.out.println("Values: "+Arrays.toString(values));
		System.out.println("Groupsize: "+Arrays.toString(groupsize));
		System.out.println("Weight Limit: "+weightLimit+"\n");
		
		final long startTime = System.currentTimeMillis();
		knapsack();
		final long endTime = System.currentTimeMillis();

		System.out.println("\nTotal execution time: " + (endTime - startTime) + "ms");
	}
	
	private static void knapsack() {
		
		choices = new int[values.length];
		
		KnapsackNode root = new KnapsackNode(choices, weights, values, 0, 0);
		
		Comparator<KnapsackNode> comparator = new KnapsackComparator();
		
		PriorityQueue<KnapsackNode> pending = new PriorityQueue<KnapsackNode>(10, comparator);
		
		pending.offer(root);
		
		
		
		BFS(pending, comparator); //recursive Best first search
		

		
		
		System.out.println("Choices: "+Arrays.toString(bestChoice));
		System.out.println("Total Value of chosen objects: "+(int)maxValue);
		System.out.println("Total Weight of chosen objects: "+(int)maxWeight);
	
	}

	private static void BFS(PriorityQueue<KnapsackNode> pending, Comparator<KnapsackNode> comparator) {
		while(!pending.isEmpty()){
			KnapsackNode n = pending.poll();
			
			if(!visited.contains(n)){
				
				PriorityQueue<KnapsackNode> pending2 = new PriorityQueue<KnapsackNode>(10, comparator);
				
				visited.add(n);
				
				int[] c = n.getChoices();
				
				int V = 0;
				int W = 0;
				
				if(maxValue > 0){
					int x = 0;
					while(W+n.getWeight() < weightLimit && x < c.length){
	
						Item item = items.get(x);
						
						int index = item.getIndex();
						int noItemLeft = item.getGroupsize()-c[index];
						int weight = item.getWeight();
						int value = item.getValue();
						
						for(int i = 0; i < noItemLeft; i++){
							if((W+n.getWeight()+weight) <= weightLimit){
								W += weight;
								V += value;
							}
							else{
								double weightLeft = (weightLimit - (W+n.getWeight()));
								V += weightLeft*items.get(x).getRatio();
								W += weightLeft;
							}
						}
						
						x++;
						
					}
				}
				
				if((V+n.getValue() > maxValue && maxValue != 0) || maxValue == 0){
	
					boolean complete = true;
					
					for(int i = 0; i < values.length; i++){
		
						if((c[i]+1) <= groupsize[i] && n.getWeight()+weights[i] <= weightLimit){
							
							complete = false;
							
							KnapsackNode n1 = new KnapsackNode(c, i, weights, values, n.getWeight()+weights[i], n.getValue()+values[i]);
							
							if(!visited.contains(n1)){
								
								pending2.offer(n1);
								
							}
						}
					}
					
					if(complete && n.getValue() > maxValue || (complete && n.getValue() == maxValue && n.getWeight() < maxWeight)){ //if we have a solution with same value but less weight we want the one with less weight
						bestChoice = Arrays.copyOf(n.getChoices(), n.getChoices().length);
						maxValue = n.getValue();
						maxWeight = n.getWeight();
					}
				}
				BFS(pending2, comparator);
			}
			
		}
	}
	
}

class KnapsackComparator implements Comparator<KnapsackNode>{
    @Override
    public int compare(KnapsackNode x, KnapsackNode y)
    {
        if (x.valueToWeight() < y.valueToWeight())
        {
            return 1;
        }
        else if (x.valueToWeight() > y.valueToWeight())
        {
            return -1;
        }
        else{
        	if(x.getValue() < y.getValue()){
        		return 1;
        	}
        	else if(x.getValue() > y.getValue()){
        		return -1;
        	}
        	else{
        		return 0;
        	}
        }
        
    }
}

class ItemComparator implements Comparator<Item>{
    @Override
    public int compare(Item x, Item y)
    {
        if (x.getRatio() < y.getRatio())
        {
            return 1;
        }
        else if (x.getRatio() > y.getRatio())
        {
            return -1;
        }
        else{
        	if(x.getValue() < y.getValue()){
        		return 1;
        	}
        	else if(x.getValue() > y.getValue()){
        		return -1;
        	}
        	else{
        		return 0;
        	}
        }
    }
}
