package edu.curso;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements Observer{
	ServerSocket server;
	List<TrataCliente> listaCliente;
	private int idCliente = 0;
	
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
		System.out.println("servidor connectado e executando");
		while(true) {
			try {
				Socket s = this.server.accept();
				TrataCliente novoCliente = new TrataCliente(s, idCliente);
				novoCliente.setServidor(this);
				this.idCliente++;
				this.listaCliente.add(novoCliente);
				Thread threadCliente = new Thread(novoCliente);
				threadCliente.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	@Override
	public void update(String massage, int id) {
		for(TrataCliente t : this.listaCliente) {
			if(t.getId() != id) {
				t.escrever(massage);
			}
		}
		
	}
}
