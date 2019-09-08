package bean;

public class Item_venda {

	private int id_item_venda;
	private int quantidade;
	private float total_itens;
	private Produto produto;
	private Venda venda;

	public int getId_item_venda() {
		return id_item_venda;
	}

	public void setId_item_venda(int id_item_venda) {
		this.id_item_venda = id_item_venda;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public float getTotal_itens() {
		return total_itens;
	}

	public void setTotal_itens(float total_itens) {
		this.total_itens = total_itens;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
