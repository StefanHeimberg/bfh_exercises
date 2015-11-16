package ch.approppo.androidtesting;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CalculatorTest {

	private Calculator testee;

	@Test
	public void test_sum() {
		assertEquals(0, testee.sum(0, 0));
		assertEquals(1, testee.sum(0, 1));
		assertEquals(1, testee.sum(1, 0));
		assertEquals(2, testee.sum(1, 1));
	}

	@Test
	public void test_subtract() {
		assertEquals(0, testee.subtract(0, 0));
		assertEquals(-1, testee.subtract(0, 1));
		assertEquals(1, testee.subtract(1, 0));
		assertEquals(1, testee.subtract(2, 1));
		assertEquals(-1, testee.subtract(1, 2));
	}

	@Test
	public void test_multiply() {
		assertEquals(0, testee.multiply(0, 0));
		assertEquals(0, testee.multiply(0, 1));
		assertEquals(0, testee.multiply(1, 0));
		assertEquals(1, testee.multiply(1, 1));
		assertEquals(2, testee.multiply(1, 2));
		assertEquals(2, testee.multiply(2, 1));
		assertEquals(4, testee.multiply(2, 2));
	}

	@Test (expected = ArithmeticException.class)
	public void test_divide_by_0() {
		assertEquals(0, testee.divide(0, 0));
	}

	@Test
	public void test_divide() {
		assertEquals(0, testee.divide(0, 1));
		assertEquals(1, testee.divide(1, 1));
		assertEquals(2, testee.divide(2, 1));
		assertEquals(2, testee.divide(4, 2));
		assertEquals(2, testee.divide(3, 2));
	}

	@Before
	public void setup() {
		testee = new Calculator();
	}

	@After
	public void teardown() {
		testee = null;
	}
}