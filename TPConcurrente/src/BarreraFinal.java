
public class BarreraFinal extends Barrera {
	public BarreraFinal() {
		cantidad = 1;
		elements = new Lista[1];
	}
	
	public synchronized Lista getLista() {
		return (elements[0]);
	} 
	
}
