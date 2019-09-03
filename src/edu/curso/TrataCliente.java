package edu.curso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TrataCliente implements Runnable{
	Socket cliente;
	
	public TrataCliente(Socket c) {
		this.cliente = c;
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
		String leitura = "";
		try {
			InputStream in = cliente.getInputStream();
			while(in.available() > 0) { 
				leitura += (char)in.read(); //pega os caracteres enviados pelo cliente		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return leitura;
	}
	
	public void run() {
		while(true) {
			System.out.println(ler());
		}
	}
}
