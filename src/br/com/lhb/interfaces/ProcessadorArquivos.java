package br.com.lhb.interfaces;

import java.util.List;
import java.util.Map;

import br.com.lhb.registros.Cliente;
import br.com.lhb.registros.Venda;
import br.com.lhb.registros.Vendedor;

public interface ProcessadorArquivos {

	public void analizarRegistros(String record, List<Cliente> clientes, List<Vendedor> vendedores, List<Venda> vendas);

	public Map<String, Long> encontrarPiorVendedorQtd(List<Venda> vendas, List<Vendedor> vendedores);

	public Map<String, Double> encontrarPiorVendedorPorValor(List<Venda> vendas, List<Vendedor> vendedores);
}
