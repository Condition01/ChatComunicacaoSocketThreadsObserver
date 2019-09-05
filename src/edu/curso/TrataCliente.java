package edu.curso;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TrataCliente implements Runnable, Subject{
	private Socket cliente;
	private Observer servidor;
	private long counter = 0;
	private int id;
	private String clientName = "";
	
	public TrataCliente(Socket c, int id) {
		this.cliente = c;
		this.servidor = null;
		this.id = id;
	}
	
	public void escrever(String texto) {
		try {
			OutputStream out = cliente.getOutputStream();
			out.write(texto.getBytes()); //escreve esses caracteres para clientes e servidores.
			out.flush(); //é o responsavel por enviar o conteudo do buffer no momento que esta sendo invocado (além de deixar organizado o buffer de bytes)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
	}
	
	public String ler() {
		String line = null;

		
		try {
			//InputStream in = cliente.getInputStream();
			InputStream in = cliente.getInputStream();
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader bfr = new BufferedReader(reader);
			if(bfr.ready()) {
				line = "";
				line+=(bfr.readLine());
				line=("\r\n" + this.clientName + " disse: " + line) +"\r\n";		
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	public void boasVindas() {
		if(this.counter == 0) {
			try {
				OutputStream out = cliente.getOutputStream();
				byte[] bytes = 
						"Bem vindo ao servidor\r\nPor favor digite seu nome \r\n".getBytes();
				out.write( bytes );
				out.flush();
				setClientName();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		while(true) {
			boasVindas();
			String texto = ler();
			if(texto != null) {
				notifyServidor(texto);
			}
			this.counter++;
		}
		
	}

	@Override
	public void setServidor(Observer servidor) {
		this.servidor = servidor;
	}


	@Override
	public void notifyServidor(String texto) {
		if(this.servidor != null) {
			System.out.println("enviando mensagem " + texto);
			this.servidor.update(texto, this.id);
		}
		
	}
	
	public void setClientName() {
		InputStream in;
		try {
			in = cliente.getInputStream();
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader bfr = new BufferedReader(reader);
			while(this.clientName.equals("")) {
				if(bfr.ready()) {
					this.clientName += bfr.readLine();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getId() {
		return id;
	}
}
