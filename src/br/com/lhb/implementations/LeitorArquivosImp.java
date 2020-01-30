package br.com.lhb.implementations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.lhb.interfaces.LeitorArquivos;

public class LeitorArquivosImp implements LeitorArquivos {

	@Override
	public List<File> lerArquivos() throws IOException {

		Path caminhoLeitura = Paths.get(System.getProperty("user.home"), "data/in/");
		File pastaLeitura = new File(caminhoLeitura.toString());
		ArrayList<File> arquivos = new ArrayList<>(Arrays.asList(pastaLeitura.listFiles()));

		return arquivos;
	}

}
