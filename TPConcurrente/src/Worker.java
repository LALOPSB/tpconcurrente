
public class Worker extends Thread {
    private Buffer miBuffer;
	
	public Worker(Buffer b) {
		this.miBuffer = b;
	}

	public void run() {
		UnidadDeTrabajo u;
		while (miBuffer.sigo()) {
			u = miBuffer.read();
			u.trabajar(miBuffer);
		}
	}

}
