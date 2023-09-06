package cliente.controlador;

import comum.modelo.Comando;
import comum.modelo.Mensagem;
import comum.modelo.ProtocoloTransporte;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controlador responsável pelo envio e recepção de mensagens.
 */
public class ControladorMensagens {
    private final String enderecoServidor;
    private final int portaServidor;
    private final ProtocoloTransporte protocoloTransporte;
    private final List<Mensagem> listaRecebimento = Collections.synchronizedList(new ArrayList<>());
    private final String nomeUsuario;
    private Socket socket;
    private final Thread threadRecebimento = new Thread() {

        @Override
        public void run() {
            while (true) {
                enviarComando(Comando.Receber);
                enviarNomeUsuario(nomeUsuario);
                List<Mensagem> mensagensRecebidas = receberMensagens();
                for (Mensagem mensagemRecebida : mensagensRecebidas)
                    listaRecebimento.add(mensagemRecebida);
                try {
                    Thread.sleep(Duration.ofMinutes(1).toMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public ControladorMensagens(String enderecoServidor, int portaServidor, ProtocoloTransporte protocoloTransporte, String nomeUsuario) {
        this.enderecoServidor = enderecoServidor;
        this.portaServidor = portaServidor;
        this.protocoloTransporte = protocoloTransporte;
        this.nomeUsuario = nomeUsuario;

        try {
            this.socket = new Socket(InetAddress.getByName(enderecoServidor), portaServidor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        threadRecebimento.start();
    }

    public void enviarComando(Comando comando) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(comando);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarNomeUsuario(String nomeUsuario) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(nomeUsuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Mensagem> receberMensagens() {
        List<Mensagem> mensagens = new ArrayList<>();
        Mensagem mensagem = null;
        do {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                mensagem = (Mensagem) objectInputStream.readObject();
                mensagens.add(mensagem);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (mensagem != null);
        return mensagens;
    }

    public void enviarMensagem(Mensagem mensagem) {
        try {
            Socket socket = new Socket(enderecoServidor, portaServidor);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(Comando.Enviar);
            objectOutputStream.writeObject(mensagem);
            objectOutputStream.writeObject(Comando.Terminar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Mensagem> retirarMensagens() {
        List<Mensagem> mensagensRetiradas = new ArrayList<>();
        while (!listaRecebimento.isEmpty()) {
            mensagensRetiradas.add(listaRecebimento.remove(listaRecebimento.size() - 1));
        }

        return mensagensRetiradas;
    }
}
