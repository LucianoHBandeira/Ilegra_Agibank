package br.com.lhb.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import br.com.lhb.registros.Cliente;
import br.com.lhb.registros.Venda;
import br.com.lhb.registros.Vendedor;

public interface GravadorArquivos {

	public void salvarRelatorio(List<Cliente> clientes, List<Vendedor> vendedores, List<Venda> vendasOrdenadas,
			Map<String, Long> pioresVendedoresPorQtd, Map<String, Double> pioresVendedoresPorValor) throws IOException;
}
