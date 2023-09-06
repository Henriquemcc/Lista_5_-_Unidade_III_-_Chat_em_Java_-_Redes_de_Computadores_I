package servidor.controlador;

import comum.modelo.Comando;
import comum.modelo.Mensagem;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TratadorCliente extends Thread {

    private final Socket conexaoCliente;

    public TratadorCliente(Socket conexaoCliente) {
        System.out.println("Conectado com: " + conexaoCliente.getInetAddress() + ":" + conexaoCliente.getPort());
        this.conexaoCliente = conexaoCliente;
    }

    /**
     * Obtém o comando a ser executado.
     *
     * @return Comando a ser executado.
     */
    private Comando obterComando() {
        Comando comando = null;

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(conexaoCliente.getInputStream());
            comando = (Comando) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return comando;
    }

    private String obterNomeUsuario() {
        String nomeUsuario = null;

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(conexaoCliente.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            nomeUsuario = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nomeUsuario;
    }

    private void entregarMensagens(String nomeUsuario) {
        Mensagem mensagem = Servidor.controladorMensagens.retirarMensagem(nomeUsuario);
        while (mensagem != null) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(conexaoCliente.getOutputStream());
                objectOutputStream.writeObject(mensagem);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Obtendo a próxima mensagem
            mensagem = Servidor.controladorMensagens.retirarMensagem(nomeUsuario);
        }
    }

    private void terminar() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(conexaoCliente.getOutputStream());
            objectOutputStream.writeObject(Comando.Terminar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Mensagem> receberMensagens() {
        List<Mensagem> mensagens = new ArrayList<>();
        Mensagem mensagem = null;
        do {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(conexaoCliente.getInputStream());
                mensagem = (Mensagem) objectInputStream.readObject();
                mensagens.add(mensagem);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (mensagem != null);
        return mensagens;
    }


    @Override
    public void run() {
        Comando comando = obterComando();
        if (comando == Comando.Receber) {
            System.out.println("Cliente Recebendo mensagem");
            String nomeUsuario = obterNomeUsuario();
            System.out.println("nomeUsuario = " + nomeUsuario);
            entregarMensagens(nomeUsuario);
            terminar();
        } else if (comando == Comando.Enviar) {
            System.out.println("Cliente enviando mensagem");
            List<Mensagem> mensagens = receberMensagens();
            for (Mensagem mensagem : mensagens)
                Servidor.controladorMensagens.adicionarMensagem(mensagem);
        }
    }

}
