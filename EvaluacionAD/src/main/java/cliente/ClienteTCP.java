package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteTCP {

    public static void main(String[] args) {

        try {

            for (int i = 1; i <= 3; i++) {

                Socket socket = new Socket("localhost", 5000);

                PrintWriter salida = new PrintWriter(
                        socket.getOutputStream(), true);

                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

                salida.println("TOKEN123");
                salida.println("REGISTRO-00" + i);

                String respuesta = entrada.readLine();

                System.out.println(respuesta);

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}