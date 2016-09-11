package com.retroid.secret.diary.pattern.locker.vault;

import java.io.Serializable;

class Coordinates implements Serializable {

	private static final long serialVersionUID = 1L;
	
	int x;
	int y;

	public Coordinates(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
