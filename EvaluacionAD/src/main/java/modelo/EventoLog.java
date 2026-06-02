package modelo;

public class EventoLog implements Comparable<EventoLog> {

    private int lamport;
    private String nodo;
    private String descripcion;

    public EventoLog(int lamport, String nodo, String descripcion) {
        this.lamport = lamport;
        this.nodo = nodo;
        this.descripcion = descripcion;
    }

    @Override
    public int compareTo(EventoLog otro) {
        if (this.lamport != otro.lamport) {
            return Integer.compare(this.lamport, otro.lamport);
        }
        return this.nodo.compareTo(otro.nodo);
    }

    @Override
    public String toString() {
        return "[L=" + lamport + "][" + nodo + "] " + descripcion;
    }
}