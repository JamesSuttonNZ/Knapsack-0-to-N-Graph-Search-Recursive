package part5;

import java.util.Arrays;

public class KnapsackNode {
	
	private int[] choices;
	private int[] weights;
	private int[] values;
	private double weight;
	private double value;
	
	public KnapsackNode(int[] choices, int[] weights, int[] values, double weight, double value){
		this.choices = new int[choices.length];
		for(int j = 0; j < choices.length; j++){
			this.choices[j] = choices[j];
		}
		this.weights = weights;
		this.weight = weight;
		this.values = values;
		this.value = value;
	}


	public KnapsackNode(int[] choices, int i, int[] weights, int[] values, double weight, double value) {
		this.choices = new int[choices.length];
		for(int j = 0; j < choices.length; j++){
			this.choices[j] = choices[j];
		}
		this.choices[i]++;
		this.weights = weights;
		this.weight = weight;
		this.values = values;
		this.value = value;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public double getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int[] getChoices() {
		return choices;
	}

	public void setChoices(int[] choices) {
		this.choices = choices;
	}
	
	public double valueToWeight(){
		return value/weight;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.choices);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(choices);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KnapsackNode other = (KnapsackNode) obj;
		if (!Arrays.equals(choices, other.choices))
			return false;
		return true;
	}
	
	
	
}
