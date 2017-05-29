
public class UnidadDividir extends UnidadDeTrabajo {
	private Lista miLista;
	private Barrera miBarrera;
	private boolean fromLeft;
	
	public UnidadDividir(Lista l, Barrera b, boolean fromLeft) {
		this.miLista       = l;
		this.miBarrera     = b;
		this.fromLeft      = fromLeft;
	}

	public void trabajar(Buffer elBuffer) {
		if (miLista.size() > 3) {  
			Lista left  = miLista.getSublist(0, miLista.size() / 2);
			Lista right = miLista.getSublist(miLista.size() / 2, miLista.size());
			BarreraBinaria b   = new BarreraBinaria();
			UnidadDeTrabajo ul = new UnidadDividir(left, b, true);
			UnidadDeTrabajo ur = new UnidadDividir(right, b, false);
			elBuffer.write(ul);
			elBuffer.write(ur);
			
			// crea un thread que espere a la barrera b, y luego apile el merge que le avise a miBarrera			
			Waiter w = new Waiter(elBuffer, b, miBarrera, fromLeft);
			w.start();
		} else { // Si hay menos de 3 elementos, los ordeno secuencial y aviso a miBarrera que terminé
			miLista.sortSecuencial();
			miBarrera.avisar(miLista, fromLeft);
		}
	}
	
}
