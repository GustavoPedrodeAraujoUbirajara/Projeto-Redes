package Game;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 12345)) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true
            );

            Scanner scanner = new Scanner(System.in);

            Thread listener = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            listener.start();

            while (true) {
                String input = scanner.nextLine();
                out.println(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}