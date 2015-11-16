package ch.approppo.androidtesting;

/**
 * Created by Martin Neff @approppo GmbH on 15.11.15.
 */
public class Calculator {

	public int sum(int x, int y) {
		return x + y;
	}

	public int subtract(int x, int y) {
		return x - y;
	}

	public int multiply(int x, int y) {
		return x * y;
	}

	public int divide(int x, int y) {
		if (y == 0) {
			throw new ArithmeticException("division by 0");
		}
		return Math.round(x / (float) y);
	}
}
