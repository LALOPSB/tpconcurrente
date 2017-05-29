
public class Lista {

	private int maxL;
	private int[] lista;
	private int nextFree = 0;
	
	private int maxW;
	private Worker[] workers;

	Lista(int numero) {
		lista = new int[numero];
        maxL = numero;
	}

	public synchronized int size() { return nextFree; }
	public synchronized Boolean isEmpty() { return nextFree == 0; }

	public synchronized Boolean contains(int numero) {
		Boolean existeN = false;
		for (int i = 0; i < nextFree; i++) {
			if (lista[i] == numero)
				existeN = true;
		}
		return existeN;
	}

	public synchronized void add(int numero) {
		lista[nextFree++] = numero;
		if (nextFree == maxL) {
			this.duplicateSizeAndCopy();
		}
		notifyAll();// para el peek y el pop
	}

	public synchronized void addAll(Lista b) {
		int[] a = b.getLista();
		for (int i = 0; i < b.size(); i++) {
			this.add(a[i]);
		}
	}

	private synchronized void duplicateSizeAndCopy() {
		int nuevaLista[] = new int[2 * maxL];
		for (int i = 0; i < maxL; i++) {
			nuevaLista[i] = lista[i];
		}
		lista = nuevaLista;
	}

	public synchronized int peek() { // Dijo que si está vacia espera
		while (nextFree == 0) { // pongo while porque en monitores no va if para la
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
		while (nextFree == 0) {
			{
				try {
					this.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		int res = lista[0];
		for (int i = 1; i < nextFree; i++) {
			lista[i - 1] = lista[i];
		}
		nextFree--;
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

	public synchronized void sort(int n) {
		if (this.size()>1) {
			BarreraFinal    elFin    = new BarreraFinal();
			UnidadDeTrabajo laUT     = new UnidadDividir(this, elFin, true); // Solo izquierda en elFin
			
			// Crea un buffer y n trabajadores (threads)
			Buffer          elBuffer = new Buffer(maxL);
			maxW = n;
			workers = new Worker[maxW];
			for (int i=0; i<maxW; i++) { workers[i] = new Worker(elBuffer); }		

			// Mete al buffer la Unidad de trabajo inicial y larga los workers
			elBuffer.write(laUT);        		
			for (int i=0; i<maxW; i++) { workers[i].start(); }
			
			// Cuando el trabajo termina, completa el trabajo copiando la lista de vuelta
			elFin.esperar();
			this.reemplazarListaCon(elFin.getLista());
			elBuffer.hayQueTerminar();
		}
	}
	
// CODIGO VIEJO, que a Daniel no le gustó
	public void sortSecuencial() {
		this.mergesortSec();
	}

	private synchronized void mergesortSec() {
		if (this.size() > 1) {
			Lista left = this.getSublist(0, this.size() / 2);
			Lista right = this.getSublist(this.size() / 2, this.size());
			left.mergesortSec();
			right.mergesortSec();
			this.reemplazarListaCon(left.merge(right));
		}
	}
//
//	public void sort(int n) { 
//		Barrera b = new Barrera(1);  // Solo porque mergesort precisa una barrera...
//		this.mergesort(n, b);
//	}
//
//	public synchronized void mergesort(int n, Barrera miBarrera) { 
//		if (this.size() > 1) {
//			if (n == 1) { // Si no puedo usar más de un thread, va secuencial
//				this.mergesortSec();
//			} else {
//				// Si me pide más de un thread, largo 2 threads para hacer los
//				// mergesort, pero reparto los threads activos entre mis hijos 
//				// (mi thread se inactiva en seguida si no es n==1)
//				Barrera b = new Barrera(2);
//				Lista left = this.getSublist(0, this.size() / 2);
//				Lista right = this.getSublist(this.size() / 2, this.size());
//				User l = new User(left, n / 2, b);       // crea la clase de thread que hace mergesort
//				User r = new User(right, n - (n / 2), b);// crea la clase de thread que hace mergesort
//				l.start(); // larga el primer thread cuyo efecto es ordenar left in place
//				r.start(); // larga el segundo thread cuyo efecto es ordenar right in place 
//				b.esperar(); // Espera a que ambos thread le avisen que terminaron
//				this.reemplazarListaCon(merge(left, right)); // merge de left y right, y reemplazar in place
//			}
//		}
//		miBarrera.avisar(); // AvitSublist(miLista.size() / 2, miLista.size());sar al dueño de la barrera que yo terminé
//	}
//
	public synchronized Lista merge(Lista right) {
		Lista left = this;
		Lista result = new Lista(left.size() + right.size());

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
}
