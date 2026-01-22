package Game;

import java.io.*;
import java.net.*;

public class Servidor {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Servidor aguardando conexão...");

        Socket client = serverSocket.accept();
        System.out.println("Cliente conectado.");

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        boolean keepPlaying = true;

        while (keepPlaying) {

            // =============================
            // NOVO JOGO (RESET TOTAL)
            // =============================
            Game game = new Game();
            boolean gameRunning = false;

            out.println("ADICIONE 3 JOGADORES.");

            for (int i = 1; i <= 3; i++) {
                out.println("DIGITE O NOME DO JOGADOR " + i + ":");
                String nome = in.readLine();
                game.addPlayer(new Player(nome));
            }

            out.println("3 JOGADORES REGISTRADOS COM SUCESSO.");
            out.println("BEM-VINDO AO BUCKSHOT ROULETTE");
            out.println("DIGITE START PARA COMEÇAR");

            String msg;

            // =============================
            // LOOP DA PARTIDA
            // =============================
            while ((msg = in.readLine()) != null) {

                if (msg.equalsIgnoreCase("START") && !gameRunning) {
                    gameRunning = true;
                    out.println("PRONTO PARA INICIAR. ENVIE 'BEGIN' PARA CONFIRMAR.");

                    String confirm = in.readLine();

                    if (confirm != null && confirm.equalsIgnoreCase("BEGIN")) {

                        if (game.getPlayers().isEmpty()) {
                            out.println("ERRO: Nenhum jogador cadastrado.");
                            gameRunning = false;
                            continue;
                        }

                        out.println("JOGO INICIADO");
                        game.start();
                        out.println("PARTIDA ENCERRADA");

                        // =============================
                        // MENU FINAL
                        // =============================
                        out.println("DIGITE 1 PARA JOGAR NOVAMENTE OU 0 PARA SAIR");
                        String escolha = in.readLine();

                        if (escolha == null || escolha.equals("0")) {
                            out.println("ENCERRANDO SERVIDOR...");
                            keepPlaying = false;
                        }

                        // sai do loop da partida
                        break;
                    }
                }
            }
        }

        client.close();
        serverSocket.close();
        System.out.println("Servidor encerrado.");
    }
}
