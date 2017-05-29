
public class User extends Thread {

	private Lista lista;
	int nThreads;
	private Barrera miBarrera;

	User(Lista lista, int n, Barrera b) {
		this.lista = lista;
		this.nThreads = n;
		this.miBarrera = b;
	}

	public void run() {
		lista.mergesort(nThreads, miBarrera);
	}

}
