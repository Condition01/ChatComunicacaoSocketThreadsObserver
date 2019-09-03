package edu.curso;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
	ServerSocket server;
	List<TrataCliente> listaCliente;
	
	public Servidor() {
		this.listaCliente = new ArrayList<TrataCliente>();
		try {
			this.server = new ServerSocket(12300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executar() {
		try {
			Socket s = this.server.accept();
			TrataCliente novoCliente = new TrataCliente(s);
			Thread threadCliente = new Thread(novoCliente);
			threadCliente.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
