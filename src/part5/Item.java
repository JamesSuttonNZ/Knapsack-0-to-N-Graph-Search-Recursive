package part5;

public class Item {

	private int index;
	private int weight;
	private int value;
	private int groupsize;
	private double ratio;
	
	public Item(int index, int weight, int value, int groupsize) {
		this.index = index;
		this.weight = weight;
		this.value = value;
		this.groupsize = groupsize;
		this.ratio = (double)value/(double)weight; 
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getGroupsize() {
		return groupsize;
	}

	public void setGroupsize(int groupsize) {
		this.groupsize = groupsize;
	}
	
	
}
