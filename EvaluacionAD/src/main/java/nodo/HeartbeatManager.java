package nodo;

import modelo.InfoNodo;

import java.util.List;

public class HeartbeatManager {

    public static void iniciarHeartbeats(String nombreNodoActual, List<InfoNodo> nodos) {

        Thread hilo = new Thread(() -> {

            BullyManager.elegirCoordinador(nombreNodoActual, nodos);

            while (true) {

                for (InfoNodo nodo : nodos) {

                    if (!nodo.getNombre().equals(nombreNodoActual)) {

                        boolean respondio = NodoCliente.enviarMensaje(
                                "localhost",
                                nodo.getPuerto(),
                                "HEARTBEAT desde " + nombreNodoActual,
                                0
                        );

                        if (!respondio && nodo.isActivo()) {

                            nodo.setActivo(false);

                            System.out.println("[" + nombreNodoActual + "] "
                                    + nodo.getNombre()
                                    + " detectado como CAIDO");

                            BullyManager.elegirCoordinador(nombreNodoActual, nodos);
                        }

                        if (respondio && !nodo.isActivo()) {

                            nodo.setActivo(true);

                            System.out.println("[" + nombreNodoActual + "] "
                                    + nodo.getNombre()
                                    + " volvio a estar ACTIVO");

                            BullyManager.elegirCoordinador(nombreNodoActual, nodos);
                        }
                    }
                }

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        hilo.start();
    }
}