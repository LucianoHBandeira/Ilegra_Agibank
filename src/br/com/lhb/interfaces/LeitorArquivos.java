package br.com.lhb.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface LeitorArquivos {

	public List<File> lerArquivos() throws IOException;
}
