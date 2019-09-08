package excecao;

public class ModelValidar extends Exception {
 
	public ModelValidar(String erroNome){
		super(erroNome);
	}
}