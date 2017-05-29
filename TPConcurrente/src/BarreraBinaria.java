
public class BarreraBinaria extends Barrera {
	
	public BarreraBinaria() {
		cantidad = 2;
		elements = new Lista[2];
	}
	
	public synchronized Lista getListaLeft() { return(elements[0]); }
	public synchronized Lista getListaRight() { return(elements[1]); }
	
}
