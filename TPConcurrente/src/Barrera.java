public abstract class Barrera {
	int cantidad;
    Lista[] elements;

	public synchronized void avisar(Lista l, boolean fromLeft) {
		if (this.cantidad > 0) {
			this.cantidad--;
			this.elements[this.indiceDe(fromLeft)] = l;
			this.notifyAll();
		}
	}
	
	public synchronized void esperar() {
		while (this.cantidad > 0) {
			try { this.wait(); } catch (InterruptedException e) { }
		}
	} 
	
	private int indiceDe(boolean fromLeft) {
		if (fromLeft) { return 0; } else { return 1; }		
	}
}
