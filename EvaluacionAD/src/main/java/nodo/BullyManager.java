package nodo;

import modelo.InfoNodo;

import java.util.List;

public class BullyManager {

    public static void elegirCoordinador(String nombreNodoActual,
                                         List<InfoNodo> nodos) {

        InfoNodo coordinador = null;

        for (InfoNodo nodo : nodos) {
            if (nodo.isActivo()) {
                if (coordinador == null || nodo.getNombre().compareTo(coordinador.getNombre()) > 0) {
                    coordinador = nodo;
                }
            }
        }

        if (coordinador != null) {
            System.out.println("[" + nombreNodoActual + "] Coordinador actual: " + coordinador.getNombre());
        }
    }
}