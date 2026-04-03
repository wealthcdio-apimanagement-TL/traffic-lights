package com.example.traffic.domain;

public class ConflictingGreenException extends RuntimeException {
	public ConflictingGreenException(String m) {
		super(m);
	}
}
