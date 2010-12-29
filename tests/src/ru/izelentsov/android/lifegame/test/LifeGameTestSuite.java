package ru.izelentsov.android.lifegame.test;

import junit.framework.TestSuite;


public class LifeGameTestSuite extends TestSuite {

	public LifeGameTestSuite () {
		addTest (new LifeGameActivityTest ());
		System.out.println (toString ());
	}
	
}
