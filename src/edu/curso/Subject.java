package edu.curso;

public interface Subject {
	void setServidor(Observer servidor);
	void notifyServidor(String texto);
}
