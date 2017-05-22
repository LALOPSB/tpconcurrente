public class Barrera {
	int cantidad = 0;

	public Barrera(int cant) {
		this.cantidad = cant;
	}

	public synchronized void esperar() {
		while (this.cantidad > 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	public synchronized void avisar() {
		this.cantidad--;
		this.notifyAll();
	}

}
