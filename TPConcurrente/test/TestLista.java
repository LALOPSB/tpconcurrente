import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestLista {
	Lista prueba;
	Lista prueba2;
	Lista pruebasort;
	Lista prueba3;
	Lista pruebasort3;
	Lista resultado;
	User user;

	@Before
	public void setUp() {
		prueba = new Lista(10);
		prueba.add(3);
		prueba.add(21);
		prueba.add(8);
		prueba.add(4);
		prueba.add(9);
		prueba.add(0);
		prueba.add(6);
		prueba.add(5);
		prueba.add(2);

		prueba2 = new Lista(4);
		prueba2.add(15);
		prueba2.add(10);
		prueba2.add(12);
		prueba2.add(22);

		pruebasort = new Lista(10);
		pruebasort.add(0);
		pruebasort.add(2);
		pruebasort.add(3);
		pruebasort.add(4);
		pruebasort.add(5);
		pruebasort.add(6);
		pruebasort.add(8);
		pruebasort.add(9);
		pruebasort.add(21);
		
		prueba3 = new Lista(20);
		prueba3.add(3);
		prueba3.add(21);
		prueba3.add(8);
		prueba3.add(4);
		prueba3.add(9);
		prueba3.add(0);
		prueba3.add(6);
		prueba3.add(5);
		prueba3.add(99);
		prueba3.add(32);
		prueba3.add(212);
		prueba3.add(85);
		prueba3.add(45);
		prueba3.add(995);
		prueba3.add(52);
		prueba3.add(65);
		prueba3.add(527);
		prueba3.add(215);
		
		pruebasort3 = new Lista(20);
		pruebasort3.add(0);
		pruebasort3.add(3);
		pruebasort3.add(4);
		pruebasort3.add(5);
		pruebasort3.add(6);
		pruebasort3.add(8);
		pruebasort3.add(9);
		pruebasort3.add(21);
		pruebasort3.add(32);
		pruebasort3.add(45);
		pruebasort3.add(52);
		pruebasort3.add(65);
		pruebasort3.add(85);
		pruebasort3.add(99);
		pruebasort3.add(212);
		pruebasort3.add(215);
		pruebasort3.add(527);
		pruebasort3.add(995);
	}

	@Test
	public void testSize() {
		assertEquals(prueba.size(), 9);

	}

	@Test
	public void testContains() {
		assertTrue(prueba.contains(5));
		assertFalse(prueba.contains(7));

	}

	@Test
	public void testAdd() {
		prueba.add(7);
		assertEquals(prueba.size(), 10);
		assertTrue(prueba.contains(7));

	}

	@Test
	public void testPeek() {
		assertEquals(prueba.peek(), 3);
	}

	@Test
	public void testPop() {
		assertEquals(prueba.peek(), 3);
		assertEquals(prueba.size(), 9);
		assertEquals(prueba.pop(), 3);
		assertEquals(prueba.peek(), 21);
		assertEquals(prueba.size(), 8);

	}

	@Test
	public void testAddOverLimit() {
		prueba.add(10);
		prueba.add(11);
		assertEquals(prueba.size(), 11);
		assertEquals(prueba.getLista().length, 20);
	}

	@Test
	public void testSortSecuencial() {
		prueba.sortSecuencial();
		for (int i = 0; i < pruebasort.size(); i++) {
			assertEquals(prueba.pop(), pruebasort.pop());
		}
	}
	
	@Test
	public void testSort3S() {
		prueba3.sortSecuencial();
		for (int i = 0; i < prueba3.size(); i++) {
			assertEquals(prueba3.pop(), pruebasort3.pop());
		}
	}

	@Test
	public void testSort3P() {
		prueba3.sort(8);
		for (int i = 0; i < prueba3.size(); i++) {
			assertEquals(prueba3.pop(), pruebasort3.pop());
		}
	}

	@Test
	public void testSort() {
		prueba.sort(4);
		for (int i = 0; i < prueba.size(); i++) {
			assertEquals(prueba.pop(), pruebasort.pop());
		}
	}

	public void printLista(Lista l) {
		int[] a = l.getLista();
		int t = l.getLast();
		System.out.print("Tamaño: ");
		System.out.println(t);
		System.out.print(a[0]);
		for (int i = 1; i < t; i++) {
			System.out.print(", ");
			System.out.print(a[i]);
		}
		System.out.println("");
	}

}
