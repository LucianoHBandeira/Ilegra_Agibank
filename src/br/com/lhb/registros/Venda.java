package br.com.lhb.registros;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Venda {

	private Integer id;
	private String nomeVendedor;
	private List<ItemVenda> itens;
	private BigDecimal valorTotal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public List getItens() {
		return itens;
	}

	public void setItens(List itens) {
		this.itens = itens;
	}

	public void addItem(ItemVenda item) {

		BigDecimal valorTotalItem;

		if (this.valorTotal == null) {
			this.valorTotal = new BigDecimal(0);
		}

		if (this.itens == null) {
			this.itens = new ArrayList<ItemVenda>();
			this.itens.add(item);
		} else {
			this.itens.add(item);
		}
		// atualizar valor total da venda
		valorTotalItem = item.getPreco().multiply(new BigDecimal(item.getQuantidade()));
		this.valorTotal = valorTotal.add(valorTotalItem);

	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public Double getValorTotalDouble() {
		return valorTotal.doubleValue();
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

}
