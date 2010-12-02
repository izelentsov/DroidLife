package ru.izelentsov.android.lifegame.model;



class Generation {

	
	private int width = 0;
	private int height = 0;
	private boolean data[][] = null;
	
	
	
	public Generation (int aWidth, int aHeight) {
		width = aWidth;
		height = aHeight;
		
		data = new boolean [height] [width];
	}

	public Generation (int aWidth, int aHeight, GameConf anInitialConf, boolean wrapConf) {
		this (aWidth, aHeight);
		if (wrapConf) {
			anInitialConf.applyTo (this);
		} else {
			anInitialConf.applyWithoutWrapTo (this);
		}
	}

	
	
	
	public int width () {
		return width;
	}
	
	public int height () {
		return height;
	}
	
	
	
	private int wrap (int aCoord, int aSize) {
		int c = aCoord;
		while (c < 0) {
			c += aSize;
		}
		while (c >= aSize) {
			c -= aSize;
		}
		return c;
	}
	
	
	public boolean isAlive (int xPos, int yPos) {
		int x = wrap (xPos, width);
		int y = wrap (yPos, height);
		return data[y][x];
	}
	
	
	public void setAlive (int xPos, int yPos, boolean anAliveFlag) {
		int x = wrap (xPos, width);
		int y = wrap (yPos, height);
		data[y][x] = anAliveFlag;
	}
	
	public void toggleAlive (int xPos, int yPos) {
		int x = wrap (xPos, width);
		int y = wrap (yPos, height);
		data[y][x] = !data[y][x];
	}
	
	
	public int getNeighbours (int xPos, int yPos) {
		int n = 0;
		if (isAlive (xPos - 1, yPos - 1)) {
			++n;
		}
		if (isAlive (xPos, yPos - 1)) {
			++n;
		}
		if (isAlive (xPos + 1, yPos - 1)) {
			++n;
		}

		if (isAlive (xPos - 1, yPos)) {
			++n;
		}
		if (isAlive (xPos + 1, yPos)) {
			++n;
		}

		if (isAlive (xPos - 1, yPos + 1)) {
			++n;
		}
		if (isAlive (xPos, yPos + 1)) {
			++n;
		}
		if (isAlive (xPos + 1, yPos + 1)) {
			++n;
		}
		return n;
	}

	
	
	public GameConf getConfiguration () {
		GameConf conf = new GameConf ();
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				if (data[y][x] == true) {
					conf.addCell (x, y);
				}
			}
		}
		return conf;
	}

	
	
	public void print () {
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				System.out.print (isAlive (x, y) ? "0" : "-");
				System.out.print (" ");
			}
			System.out.println ();
		}
		System.out.println ();
	}
	
	
	
}
