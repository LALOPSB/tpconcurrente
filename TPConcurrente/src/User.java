
public class User extends Thread{
	
	private Lista lista;
	int n;
	
	User(Lista lista, int n){
		this.lista= lista;
		this.n=n;
	}
	
	public void run(){
		try {
			sort(n);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void sort(int numeroDeThreads) throws InterruptedException{
		lista.mergesort(lista,n);
	}
	
	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}

}
