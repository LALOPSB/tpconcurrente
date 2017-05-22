
public class Lista {

	private int[] lista;
	int last = 0;

	Lista(int numero) {
		lista = new int[numero];

	}

	public synchronized int size() {
		return last;
	}

	public synchronized Boolean isEmpty() {
		return last == 0;
	}

	public synchronized Boolean contains(int numero) {
		Boolean existeN = false;
		for (int i = 0; i < last; i++) {
			if (lista[i] == numero)
				existeN = true;
		}
		return existeN;
	}

	public synchronized void add(int numero) {
		lista[last] = numero;
		last++;
		if (last > lista.length - 1) {
			this.duplicateSizeAndCopy();
		}
		notifyAll();// para el peek y el pop
	}

	public synchronized void addAll(Lista b) {
		for (int i = 0; i < b.getLast(); i++) {
			this.add(b.getLista()[i]);
		}

	}

	private synchronized void duplicateSizeAndCopy() {
		int nuevaLista[] = new int[2 * lista.length];
		for (int i = 0; i < lista.length; i++) {
			nuevaLista[i] = lista[i];
		}
		lista = nuevaLista;
	}

	public synchronized int peek() { // Dijo que si está vacia espera
		while (last == 0) { // pongo while porque en monitores no va if para la
							// espera
			{
				try {// esto es sintaxis para que java no chille
					this.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		return lista[0];
	}

	public synchronized int pop() { // igual a peek
		while (last == 0) {
			{
				try {
					this.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		int res = lista[0];
		for (int i = 1; i < last; i++) {
			lista[i - 1] = lista[i];
		}
		last--;
		return res;
	}

	public synchronized Lista getSublist(int a, int b) {
		Lista sublista = new Lista(b - a);
		for (int i = a; i < b; i++) {
			sublista.add(lista[i]);
		}
		return sublista;

	}

	private void reemplazarListaCon(Lista l) {
		int[] a = l.getLista();
		for (int i = 0; i < l.size(); i++) {
			lista[i] = a[i];
		}
	}

	public void sortSecuencial() {
		this.mergesortSec();
	}

	private synchronized void mergesortSec() {
		if (this.size() > 1) {
			Lista left = this.getSublist(0, this.size() / 2);
			Lista right = this.getSublist(this.size() / 2, this.size());
			left.mergesortSec();
			right.mergesortSec();
			this.reemplazarListaCon(merge(left, right));
		}
	}

	public void sort(int n) { 
		Barrera b = new Barrera(1);  // Solo porque mergesort precisa una barrera...
		this.mergesort(n, b);
	}

	public synchronized void mergesort(int n, Barrera miBarrera) { 
		if (this.size() > 1) {
			if (n == 1) { // Si no puedo usar más de un thread, va secuencial
				this.mergesortSec();
			} else {
				// Si me pide más de un thread, largo 2 threads para hacer los
				// mergesort, pero reparto los threads activos entre mis hijos 
				// (mi thread se inactiva en seguida si no es n==1)
				Barrera b = new Barrera(2);
				Lista left = this.getSublist(0, this.size() / 2);
				Lista right = this.getSublist(this.size() / 2, this.size());
				User l = new User(left, n / 2, b);       // crea la clase de thread que hace mergesort
				User r = new User(right, n - (n / 2), b);// crea la clase de thread que hace mergesort
				l.start(); // larga el primer thread cuyo efecto es ordenar left in place
				r.start(); // larga el segundo thread cuyo efecto es ordenar right in place 
				b.esperar(); // Espera a que ambos thread le avisen que terminaron
				this.reemplazarListaCon(merge(left, right)); // merge de left y right, y reemplazar in place
			}
		}
		miBarrera.avisar(); // Avisar al dueño de la barrera que yo terminé
	}

	// private synchronized Lista mergesort(Lista list, int n) throws
	// InterruptedException {
	// if (list.size() <= 1)!!!!
	// return list;
	// Lista left = list.getSublist(0, list.size() / 2);
	// Lista right = list.getSublist(list.size() / 2, list.size());
	// n--;
	// System.out.println(n);
	// if (n == 0)
	// notify();
	// User l = new User(left, n);
	// User r = new User(right, n);
	// l.start();
	// r.start();
	// wait();
	// list = merge(left, right);
	// return list;
	// }

	private synchronized Lista merge(Lista left, Lista right) {
		Lista result = new Lista(left.getLast() + right.getLast());

		while (!left.isEmpty() && !right.isEmpty()) {
			if (left.peek() <= right.peek())
				result.add(left.pop());
			else
				result.add(right.pop());
		}
		result.addAll(left);
		result.addAll(right);
		return result;
	}

	// ============== getters & setters ============

	public int[] getLista() {
		return lista;
	}

	public void setLista(int[] lista) {
		this.lista = lista;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

}
