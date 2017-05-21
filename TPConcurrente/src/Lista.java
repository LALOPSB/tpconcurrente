
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

	public void sortSecuencial() {
		Lista l = this.mergesortSec(this);
		int[] a = l.getLista();
		for (int i = 0; i < l.size(); i++) {
			lista[i] = a[i];
		}
	}

	private synchronized Lista mergesortSec(Lista list) {
		if (list.size() > 1) {
			Lista left = list.getSublist(0, list.size() / 2);
			Lista right = list.getSublist(list.size() / 2, list.size());
			Lista ll = list.mergesortSec(left);
			Lista lr = list.mergesortSec(right);
			list = merge(ll, lr);
		}
		return list;
	}

	public void sort(int n) {   // Ahora hay que agregar threads!!!!
		Lista l = this.mergesort(this, n);
		int[] a = l.getLista();
		for (int i = 0; i < l.size(); i++) {
			lista[i] = a[i];
		}
	}

	private synchronized Lista mergesort(Lista list, int n) { // Ahora hay que agregar threads!!!!
		if (list.size() > 1) {
			Lista left = list.getSublist(0, list.size() / 2);
			Lista right = list.getSublist(list.size() / 2, list.size());
			Lista ll = list.mergesort(left, n);
			Lista lr = list.mergesort(right, n);
			list = merge(ll, lr);
		}
		return list;
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
