package modelo;

public class InfoNodo {

    private String nombre;
    private int puerto;
    private boolean activo;

    public InfoNodo(String nombre, int puerto) {
        this.nombre = nombre;
        this.puerto = puerto;
        this.activo = true;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuerto() {
        return puerto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}