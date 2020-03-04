/**
 * 
 */
package edu.westga.cs3270.tests.TestState;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3270.model.State;

/**
 * @author Incendy
 *
 */
class TestConstructor {

	@Test
	void testXLessThan0() {
		assertThrows(IllegalArgumentException.class, () -> {
			new State(-1, 1, 1);
		});
	}
	@Test
	void testXEqualTo0() {
		assertThrows(IllegalArgumentException.class, () -> {
			new State(0, 1, 1);
		});
	}
	@Test
	void testYLessThan0() {
		assertThrows(IllegalArgumentException.class, () -> {
			new State(1, -1, 1);
		});
	}
	@Test
	void testYEqualTo0() {
		assertThrows(IllegalArgumentException.class, () -> {
			new State(1, 0, 1);
		});
	}
	@Test
	void testValidConstructor() {
		assertEquals(1, new State(1, 3, 1).getxCoor(), "Testing xCoor is set correctly");
		assertEquals(3, new State(1, 3, 1).getyCoor(), "Testing yCoor is set correctly");
	}

}
