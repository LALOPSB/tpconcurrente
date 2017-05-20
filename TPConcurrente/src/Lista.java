
public class Lista {
	private int[] lista;
	int last = 0;
	
	Lista(int numero){
		lista = new int[numero];
		
	}
	
	public synchronized int size(){
		return last;
	}
	
	public synchronized Boolean isEmpty(){
		return last==0;
	}
	
	public synchronized Boolean contains(int numero){
			Boolean existeN = false;
			for (int i=0; i <= last; i++){
				if (i==numero) existeN = true;
			}
			return existeN;
	}
	
	public synchronized void add(int numero){
		lista[last]=numero;
		last++;
		if(last>lista.length){
			this.duplicateSizeAndCopy();
		}
	}

	private synchronized void duplicateSizeAndCopy() {
		int nuevaLista[] = new int[2*lista.length];
        for (int i = 0; i < lista.length; i++) {
            nuevaLista[i] = lista[i];
        }
        lista=nuevaLista;
	}
	
	public synchronized int peek(){
		return lista[0];
	}
	
	public synchronized int pop(){
		if (last==0) wait();
		for
		}
	}
}
