
public class Waiter extends Thread {
	private BarreraBinaria low;
	private Barrera high;
	private Buffer buffer;
	private boolean fromLeft;
	
	public Waiter(Buffer buf, BarreraBinaria low, Barrera high, boolean fromLeft) {
		this.low = low;
		this.high = high;
		this.buffer = buf;
		this.fromLeft = fromLeft;
	}
	
	public void run() {
		low.esperar();  // Cuando la barrera de abajo dice que llegaron las dos listas, tengo que preparar el merge
		UnidadMerge um = new UnidadMerge(low.getListaLeft(), low.getListaRight(), high, fromLeft);
		buffer.write(um);
	}

}
