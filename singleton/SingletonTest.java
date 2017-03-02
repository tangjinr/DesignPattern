package com.tang.dp.singleton;

public class SingletonTest {

	public static void main(String[] args) {
		Singleton instance = Singleton.getInstance();
		instance.otherMethod();
	}

}
