
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
			for (int i=0; i < last; i++){
				if (lista[i]==numero) existeN = true;
			}
			return existeN;
	}
	
	public synchronized void add(int numero){
		lista[last]=numero;
		last++;
		if(last>lista.length-1){
			this.duplicateSizeAndCopy();
		}
		notify();
	}
	
	public synchronized void addAll(Lista b){
		for (int i=0;i<b.getLast();i++){
			this.add(b.getLista()[i]);
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
	
	public synchronized int pop() throws InterruptedException{
		int res = lista[0];
		if (last==0)wait();
		for (int i=1;i<last;i++){
			lista[i-1]=lista[i];
		}
		last--;
		return res;
	}
	
	public synchronized Lista getSublist(int a, int b){
		Lista sublista = new Lista(b-a);
		for (int i=a;i<b;i++){
			sublista.add(lista[i]);
		}
		return sublista;
		
	}
	
	public Lista mergesort(Lista list, int n) throws InterruptedException{
		if(list.size()<=1)return list;
		Lista left = list.getSublist(0,list.size()/2);
		Lista  right = list.getSublist(list.size()/2, list.size());
		n--;
		System.out.println(n);
		if (n==0) notify();
		User l = new User(left,n);
		User r = new User(right,n);
		l.start();
		r.start();
		wait();
		list = merge(left,right);
		return list;
	}
	
	public synchronized Lista merge(Lista left, Lista right) throws InterruptedException{
		Lista result = new Lista(left.getLast() + right.getLast());
		
		while (!left.isEmpty() && !right.isEmpty()){
			if (left.peek() <= right.peek())result.add(left.pop());
			else 							result.add(right.pop());
		}
		result.addAll(left);
		result.addAll(right);
		return result;
	}
	
	
	
//============== getters & setters ============
	
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
