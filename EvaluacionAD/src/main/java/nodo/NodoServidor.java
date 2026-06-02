package nodo;

import modelo.EventoLog;
import modelo.InfoNodo;
import modelo.RelojLamport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NodoServidor {

    private static final String TOKEN_VALIDO = "TOKEN123";

    public static void iniciar(String nombreNodo, int puerto) {

        RelojLamport reloj = new RelojLamport();
        List<EventoLog> logEventos = new ArrayList<>();

        List<InfoNodo> nodos = Arrays.asList(
                new InfoNodo("N1", 5000),
                new InfoNodo("N2", 5001),
                new InfoNodo("N3", 5002)
        );

        try {

            ServerSocket servidor = new ServerSocket(puerto);

            System.out.println(nombreNodo + " escuchando en puerto " + puerto + "...");

            HeartbeatManager.iniciarHeartbeats(nombreNodo, nodos);

            while (true) {

                Socket cliente = servidor.accept();

                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(cliente.getInputStream()));

                PrintWriter salida = new PrintWriter(
                        cliente.getOutputStream(), true);

                String token = entrada.readLine();
                String segundaLinea = entrada.readLine();

                int lamportRecibido = 0;
                String mensaje;

                if (segundaLinea.matches("\\d+")) {
                    lamportRecibido = Integer.parseInt(segundaLinea);
                    mensaje = entrada.readLine();
                    reloj.recibirMensaje(lamportRecibido);
                } else {
                    mensaje = segundaLinea;
                }

                if (!TOKEN_VALIDO.equals(token)) {
                    int tiempo = reloj.eventoLocal();

                    EventoLog evento = new EventoLog(
                            tiempo,
                            nombreNodo,
                            "Token invalido");

                    logEventos.add(evento);
                    System.out.println(evento);

                    salida.println("ERROR - Token invalido");
                    cliente.close();
                    continue;
                }

                int tiempo = reloj.eventoLocal();

                if (mensaje.startsWith("HEARTBEAT")) {

                    EventoLog eventoHeartbeat = new EventoLog(
                            tiempo,
                            nombreNodo,
                            mensaje);

                    logEventos.add(eventoHeartbeat);
                    System.out.println(eventoHeartbeat);

                    salida.println("OK - Heartbeat recibido por " + nombreNodo);
                    cliente.close();
                    continue;
                }

                EventoLog evento = new EventoLog(
                        tiempo,
                        nombreNodo,
                        "Operacion recibida: " + mensaje);

                logEventos.add(evento);
                System.out.println(evento);

                if (nombreNodo.equals("N1")
                        && !mensaje.startsWith("REPLICA")) {

                    NodoCliente.enviarMensaje(
                            "localhost",
                            5001,
                            "REPLICA desde N1: " + mensaje,
                            tiempo);

                    NodoCliente.enviarMensaje(
                            "localhost",
                            5002,
                            "REPLICA desde N1: " + mensaje,
                            tiempo);

                    int tiempoReplica = reloj.eventoLocal();

                    EventoLog eventoReplica = new EventoLog(
                            tiempoReplica,
                            nombreNodo,
                            "Operacion replicada a N2 y N3");

                    logEventos.add(eventoReplica);
                    System.out.println(eventoReplica);
                }

                Collections.sort(logEventos);

                System.out.println("----- LOG ORDENADO " + nombreNodo + " -----");
                for (EventoLog ev : logEventos) {
                    System.out.println(ev);
                }
                System.out.println("--------------------------------");

                salida.println("OK - " + nombreNodo + " registro la operacion con Lamport=" + tiempo);

                cliente.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}