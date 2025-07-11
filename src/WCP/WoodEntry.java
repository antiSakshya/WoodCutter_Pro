package WCP;

public class WoodEntry {
	private double length;
	private double roundness;
	private double rate;
	private double cft;
	
	public WoodEntry(double length, double roundness, double rate) {
		this.length = length;
		this.roundness = roundness;
		this.rate = rate;
		this.cft = calculateCFT();
	}
	
	private double calculateCFT() {
		return length * roundness * roundness / 2304;
	}
	
	public double getLength() {return length;}
	public double getRoundness() {return roundness;}
	public double getRate() {return rate;}
	public double getCFT() {return cft;}
	public double getCost() {return cft * rate;}
}

