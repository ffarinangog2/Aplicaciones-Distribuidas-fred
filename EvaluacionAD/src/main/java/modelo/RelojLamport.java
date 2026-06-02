package modelo;

public class RelojLamport {

    private int tiempo;

    public RelojLamport() {
        this.tiempo = 0;
    }

    public synchronized int eventoLocal() {
        tiempo++;
        return tiempo;
    }

    public synchronized int recibirMensaje(int tiempoRecibido) {
        tiempo = Math.max(tiempo, tiempoRecibido) + 1;
        return tiempo;
    }

    public synchronized int getTiempo() {
        return tiempo;
    }
}