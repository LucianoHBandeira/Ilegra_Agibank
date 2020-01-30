package br.com.lhb.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.lhb.implementations.GravadorArquivosImp;
import br.com.lhb.implementations.LeitorArquivosImp;
import br.com.lhb.implementations.ProcessadorArquivosImp;
import br.com.lhb.interfaces.GravadorArquivos;
import br.com.lhb.interfaces.LeitorArquivos;
import br.com.lhb.interfaces.ProcessadorArquivos;
import br.com.lhb.registros.Cliente;
import br.com.lhb.registros.Venda;
import br.com.lhb.registros.Vendedor;

public class AnalizadorVendas {

	public static void main(String[] args) throws IOException {

		AnalizadorVendas analizadorVendas = new AnalizadorVendas();
		analizadorVendas.processarArquivos();
	}

	private void processarArquivos() {

		LeitorArquivos leitor = new LeitorArquivosImp();
		ProcessadorArquivos processador = new ProcessadorArquivosImp();
		GravadorArquivos gravador = new GravadorArquivosImp();

		Runnable task = () -> {

			int i = 0;

			while (true) {

				System.out.println("Thread rodando..." + i);
				i++;
				List<Cliente> clientes = new ArrayList<>();
				List<Vendedor> vendedores = new ArrayList<>();
				List<Venda> vendas = new ArrayList<>();

				try {
					List<File> arquivos = leitor.lerArquivos();

					if (arquivos != null && !arquivos.isEmpty()) {
						for (File file : arquivos) {
							Files.lines(file.toPath())
									.forEach(r -> processador.analizarRegistros(r, clientes, vendedores, vendas));
						}
						List<Venda> vendasOrdenadas = vendas.stream()
								.sorted(Comparator.comparing(Venda::getValorTotal).reversed())
								.collect(Collectors.toList());
						Map<String, Long> pioresVendedoresPorQuantidade = processador.encontrarPiorVendedorQtd(vendas,
								vendedores);
						Map<String, Double> pioresVendedoresPorValor = processador.encontrarPiorVendedorPorValor(vendas,
								vendedores);
						gravador.salvarRelatorio(clientes, vendedores, vendasOrdenadas, pioresVendedoresPorQuantidade,
								pioresVendedoresPorValor);

						vendasOrdenadas = null;
						pioresVendedoresPorQuantidade = null;
						pioresVendedoresPorValor = null;
					}

					Thread.sleep(1000);

				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}
		};

		new Thread(task).start();
	}

}
