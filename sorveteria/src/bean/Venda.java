package bean;

import java.time.LocalDate;
import java.util.List;

public class Venda {

	private int id_venda;
	private LocalDate data;
	private float total_venda;
	private boolean pagamento;
	private Vendedor vendedor; // No lugar do id_vendedor é instanciado um tipo vendedor 
	private List<Item_venda> itensDeVenda;
	

	public int getId_venda() {
		return id_venda;
	}

	public void setId_venda(int id_venda) {
		this.id_venda = id_venda;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public float getTotal_venda() {
		return total_venda;
	}

	public void setTotal_venda(float total_venda) {
		this.total_venda = total_venda;
	}

	public boolean isPagamento() {
		return pagamento;
	}

	public void setPagamento(boolean pagamento) {
		this.pagamento = pagamento;
	}


	public List<Item_venda> getItensDeVenda() {
		return itensDeVenda;
	}

	public void setItensDeVenda(List<Item_venda> itensDeVenda) {
		this.itensDeVenda = itensDeVenda;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

}
