package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e;
	private Date d;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
	@BeforeEach
	public void runBefore() {
		e = new Event("Sensor open at door");   // (1)
		d = Calendar.getInstance().getTime();   // (2)
	}
	
	@Test
	public void testEvent() {
		assertEquals("Sensor open at door", e.getDescription());
		assertEquals(d, e.getDate());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
	}

	@Test
	public void testEqualsNullCase() {
		Event test = null;
		assertFalse(e.equals(test));
	}

	@Test
	public void testEqualsDifferentObjects() {
		MonthlyExpenseGoal mg = new MonthlyExpenseGoal(10);
		assertFalse(e.equals(mg));
	}

	@Test
	public void testEqualsSameClassDifferentObject() {
		Event e1 = new Event("banana");
		Event e2 = new Event("orange");
		assertFalse(e1.equals(e2));
	}

	@Test
	public void testEqualsSameObject() {
		Event e1 = new Event("orange");
		Event e2 = new Event("orange");
		assertTrue(e1.equals(e2));
	}

	// Hashcode test adapted from 
	// https://stackoverflow.com/questions/4449728/how-can-i-do-unit-test-for-hashcode
	@Test
	public void testEqualsAndHashCodeBidirectional() {
		Event e1 = new Event("Orange");
		Event e2 = new Event("Orange");
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e1));
		assertTrue(e1.hashCode() == e2.hashCode());
	}
}
