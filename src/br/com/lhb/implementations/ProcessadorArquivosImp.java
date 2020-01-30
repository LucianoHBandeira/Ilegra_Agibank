package br.com.lhb.implementations;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.lhb.interfaces.ProcessadorArquivos;
import br.com.lhb.registros.Cliente;
import br.com.lhb.registros.ItemVenda;
import br.com.lhb.registros.Venda;
import br.com.lhb.registros.Vendedor;

public class ProcessadorArquivosImp implements ProcessadorArquivos {

	public void analizarRegistros(String record, List<Cliente> clientes, List<Vendedor> vendedores,
			List<Venda> vendas) {

		String[] recordSplitted = record.split("ç");
		String tipoRegistro = recordSplitted[0];

		switch (tipoRegistro) {
		case "001": // vendedor
			criarVendedor(recordSplitted, vendedores);
			break;
		case "002": // cliente
			criarCliente(recordSplitted, clientes);
			break;
		case "003": // venda
			criarVenda(recordSplitted, vendas);
			break;
		}
	}

	private void criarVenda(String[] recordSplitted, List<Venda> vendas) {

		Venda venda = new Venda();
		venda.setId(Integer.parseInt(recordSplitted[1]));
		venda.setNomeVendedor(recordSplitted[3]);
		String[] itens = recordSplitted[2].substring(1, recordSplitted[2].length() - 1).split(",");
		ItemVenda itemVenda;

		for (String item : itens) {

			itemVenda = new ItemVenda();
			String itemInfo[] = item.split("-");
			itemVenda.setId(Integer.parseInt(itemInfo[0]));
			itemVenda.setQuantidade(Integer.parseInt(itemInfo[1]));
			itemVenda.setPreco(new BigDecimal(itemInfo[2]));
			venda.addItem(itemVenda);
		}

		vendas.add(venda);
	}

	private void criarCliente(String[] recordSplitted, List<Cliente> clientes) {

		Cliente cliente = new Cliente();
		cliente.setCnpj(recordSplitted[1]);
		cliente.setNome(recordSplitted[2]);
		cliente.setArea(recordSplitted[3]);
		clientes.add(cliente);
	}

	private void criarVendedor(String[] recordSplitted, List<Vendedor> vendedores) {

		Vendedor vendedor = new Vendedor();
		String x = recordSplitted[1];
		vendedor.setCpf(recordSplitted[1]);
		vendedor.setNome(recordSplitted[2]);
		vendedor.setSalario(new BigDecimal(recordSplitted[3]));
		vendedores.add(vendedor);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Long> encontrarPiorVendedorQtd(List<Venda> vendas, List<Vendedor> vendedores) {

		Map<String, Long> qtdVendasPorVendedor = vendas.stream()
				.collect(Collectors.groupingBy(Venda::getNomeVendedor, Collectors.counting()));
		Optional<Long> menorQtdDeVendas = qtdVendasPorVendedor.values().stream().max(Comparator.reverseOrder());
		Map<String, Long> pioresVendedoresPorQtd = new HashMap<String, Long>();

		for (Map.Entry<String, Long> pair : qtdVendasPorVendedor.entrySet()) {

			if (pair.getValue() == menorQtdDeVendas.get()) {
				pioresVendedoresPorQtd.put(pair.getKey(), pair.getValue());
			}

		}
		return pioresVendedoresPorQtd;
	}

	public Map<String, Double> encontrarPiorVendedorPorValor(List<Venda> vendas, List<Vendedor> vendedores) {

		Map<String, Double> valorTotalVendarPorVendedor = vendas.stream().collect(
				Collectors.groupingBy(Venda::getNomeVendedor, Collectors.summingDouble(Venda::getValorTotalDouble)));

		Map<String, Double> result = valorTotalVendarPorVendedor.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.naturalOrder())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		return result;
	}

}
