
public class UnidadMerge extends UnidadDeTrabajo {
	private Lista miLeft;
	private Lista miRight;
	private Barrera miBarrera;
	private boolean fromLeft;
	
	public UnidadMerge(Lista left, Lista right, Barrera b, boolean fromLeft) {
		this.miLeft    = left;
		this.miRight   = right;
		this.miBarrera = b;
		this.fromLeft  = fromLeft;
	}

	public void trabajar(Buffer elBuffer) {
		Lista result = miLeft.merge(miRight);
		miBarrera.avisar(result, fromLeft);
	}
	
	public Lista getMiLeft()      { return miLeft; }
	public Lista getMiRight()     { return miRight; }
	public Barrera getMiBarrera() { return miBarrera; }

}
