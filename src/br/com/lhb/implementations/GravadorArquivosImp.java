package br.com.lhb.implementations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import br.com.lhb.interfaces.GravadorArquivos;
import br.com.lhb.registros.Cliente;
import br.com.lhb.registros.Venda;
import br.com.lhb.registros.Vendedor;

public class GravadorArquivosImp implements GravadorArquivos {

	public void salvarRelatorio(List<Cliente> clientes, List<Vendedor> vendedores, List<Venda> vendasOrdenadas,
			Map<String, Long> pioresVendedoresPorQtd, Map<String, Double> pioresVendedoresPorValor) throws IOException {

		StringBuffer relatorioFinal = new StringBuffer();
		NumberFormat nf = NumberFormat.getCurrencyInstance();

		relatorioFinal.append(
				"RELATORIO FINAL===========================================================================================================\n");
		relatorioFinal.append("..Quantidade de clientes:\t \t \t \t" + clientes.size() + "\n");
		relatorioFinal.append("..Quantidade de vendedores:\t \t \t \t" + vendedores.size() + "\n");
		relatorioFinal.append("..Id venda mais cara:\t \t \t \t \t" + vendasOrdenadas.get(0).getId() + " ("
				+ nf.format(vendasOrdenadas.get(0).getValorTotal()) + ") \n");
		relatorioFinal.append("..Piores vendedores (menor quantidade de vendas):\t");

		int qtdPioresVendedores = pioresVendedoresPorQtd.size();

		for (Map.Entry<String, Long> pior : pioresVendedoresPorQtd.entrySet()) {
			relatorioFinal.append(pior.getKey() + "(" + pior.getValue() + ")");
			if (qtdPioresVendedores > 1) {
				relatorioFinal.append(", ");
				qtdPioresVendedores--;
			}
		}
		relatorioFinal.append("\n");
		relatorioFinal.append("..Piores vendedores (menor valor total de vendas):\t");
		relatorioFinal.append(pioresVendedoresPorValor.entrySet().stream().findFirst().get().getKey() + " (");
		relatorioFinal
				.append(nf.format(pioresVendedoresPorValor.entrySet().stream().findFirst().get().getValue()) + ") \n");
		relatorioFinal.append(
				"==========================================================================================================================\n");
		relatorioFinal.append("\n");

		relatorioFinal.append(
				"OBS.1: O documento de definição do problema não menciona a necessidade de que os vendedores presentes em cada registro de \n");
		relatorioFinal.append(
				"venda (código 003) sejam os mesmos vendedores descritos nos registros do tipo \"vendedor\" (código 001), já que em cada \n");
		relatorioFinal.append(
				"registro de venda, o último dado (coluna) contém o nome do vendedor e não um código associando o registro de venda (003) \n");
		relatorioFinal.append("com algum registro de vendedor (001).\n");
		relatorioFinal.append("\n");
		relatorioFinal.append(
				"OBS.2: O documento de definição do problema não menciona qual critério para definir o pior vendedor, portando implementei \n");
		relatorioFinal.append(
				"rotinas que selecionam o vendedor que teve o menor número de vendas e o vendedor cujo o valor total das suas vendas foi o \n");
		relatorioFinal.append("menor. \n");

		Path caminhoLeitura = Paths.get(System.getProperty("user.home"), "data/in/");
		Path caminhoEscrita = Paths.get(System.getProperty("user.home"), "data/out/resultado.txt");
		byte[] strToBytes = relatorioFinal.toString().getBytes();
		Files.write(caminhoEscrita, strToBytes);

		File pastaLeitura = new File(caminhoLeitura.toString());

		if (pastaLeitura.isDirectory()) {
			File[] files = pastaLeitura.listFiles();
			for (File file : files) {
				file.delete();
			}
		}

	}

}
