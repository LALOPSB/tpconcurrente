

public class Buffer {
	private int maxSize;
	private int inicio;
	private int fin;
	private boolean listo;
	private UnidadDeTrabajo[] uts;

	public Buffer(int max) {
		this.maxSize = max;
		this.uts = new UnidadDeTrabajo[max];
		this.inicio = 0;
		this.fin = 0;
		this.listo = false;
	}

	public synchronized void write(UnidadDeTrabajo u) {
		while (this.getSize()==maxSize) {
			try { wait(); } catch (InterruptedException e) {}
		}
		uts[fin] = u;
		this.incIndex(fin);
		notifyAll();
	}

	public synchronized UnidadDeTrabajo read() {
		while (this.getSize()==0) {
			try { wait(); } catch (InterruptedException e) {}
		}
		UnidadDeTrabajo u = uts[inicio];
		this.incIndex(inicio);
		notifyAll();
		return u;
	}
	
	public synchronized void hayQueTerminar() {
		listo = true;
	}
	
	public synchronized boolean sigo() {
		return (!listo);
	}

	private int getSize() {
		if (inicio<=fin) { return (fin-inicio); } // Desde inicio hasta fin-1
		else             { return (maxSize-inicio+fin); } // Desde inicio hasta maxSize-1 y desde 0 hasta fin-1
	}
	
	private int incIndex(int i) {
		return ((i+1)/maxSize);
	}
}
