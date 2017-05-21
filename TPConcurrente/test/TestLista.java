import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestLista {
	Lista prueba;
	Lista prueba2;
	Lista resultado;
	User user;
	
	
	@Before
	public void setUp(){
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
		
		user = new User(prueba,4);
	}

	@Test
	public void testSize() {
		assertEquals(prueba.size(),9);
		
	}
	
	@Test
	public void testContains() {
		assertTrue(prueba.contains(5));
		assertFalse(prueba.contains(7));
	}
	
	@Test
	public void testAdd(){
		prueba.add(7);
		assertEquals(prueba.size(),10);
		assertTrue(prueba.contains(7));
		
	}
	
	@Test
	public void testPeek(){
		assertEquals(prueba.peek(),3);
	}
	
	@Test
	public void testPop() throws InterruptedException{
		assertEquals(prueba.peek(),3);
		assertEquals(prueba.size(),9);
		assertEquals(prueba.pop(),3);
		assertEquals(prueba.peek(),21);
		assertEquals(prueba.size(),8);
	}
	
	@Test
	public void testAddOverLimit(){
		prueba.add(10);
		prueba.add(11);
		assertEquals(prueba.size(),11);
		assertEquals(prueba.getLista().length,20);
	}
	
	@Test
	public void testMerge(){
		user.start();
		Lista listaFinal = user.getLista();
		for(int i=0;i<listaFinal.size();i++){
			System.out.println(user.getLista().getLista()[i]);
	}
}}
