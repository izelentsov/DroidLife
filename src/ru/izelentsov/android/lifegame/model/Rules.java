package ru.izelentsov.android.lifegame.model;




public class Rules {

	
	private int neighsToAlive = 0;
	private int minNeighsToLive = 0;
	private int maxNeighsToLive = 0;
	
	
	
	public Rules (int aNeighsToAliveNum, int aMinNeighsToLiveNum, int aMaxNeighsToLiveNum) {
		neighsToAlive = aNeighsToAliveNum;
		minNeighsToLive = aMinNeighsToLiveNum;
		maxNeighsToLive = aMaxNeighsToLiveNum;
	}
	
	
	public boolean equals (Rules r) {
		return 
			(r.neighsToAlive == neighsToAlive) && 
			(r.minNeighsToLive == minNeighsToLive) &&
			(r.maxNeighsToLive == maxNeighsToLive);
	}
	
	
	
	public int neighsToAlive () {
		return neighsToAlive;
	}
	
	public int minNeighsToLive () {
		return minNeighsToLive;
	}
	
	public int maxNeighsToLive () {
		return maxNeighsToLive;
	}
	
	
	
	public Generation makeNextGeneration (Generation aGen) {
		Generation nextGen = new Generation (aGen.width (), aGen.height ());
		
		int neighs = 0;
		boolean isAlive = false;
		
		for (int y = 0; y < aGen.height (); ++y) {
			for (int x = 0; x < aGen.width (); ++x) {
				neighs = aGen.getNeighbours (x, y);
				isAlive = aGen.isAlive (x, y);

//				System.out.print ("(" + x + "," + y + ") N=" + neighs + " L=" + isAlive);
				
				if ((!isAlive) && (neighs == neighsToAlive)) {
					nextGen.setAlive (x, y, true);
//					System.out.print (" +");
					
				} else if ((isAlive) && 
						((neighs >= minNeighsToLive) && (neighs <= maxNeighsToLive))) {
					nextGen.setAlive (x, y, true);
//					System.out.print (" +");
				}
				
//				System.out.println ();
			}
		}
		
		return nextGen;
	}
	
}
