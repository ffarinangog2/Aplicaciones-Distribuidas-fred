package nodo;

import java.io.PrintWriter;
import java.net.Socket;

public class NodoCliente {

    public static boolean enviarMensaje(String host,
                                        int puerto,
                                        String mensaje,
                                        int lamport) {

        try {

            Socket socket = new Socket(host, puerto);

            PrintWriter salida =
                    new PrintWriter(
                            socket.getOutputStream(),
                            true);

            salida.println("TOKEN123");
            salida.println(lamport);
            salida.println(mensaje);

            socket.close();

            return true;

        } catch (Exception e) {

            return false;

        }
    }
}