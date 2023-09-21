package servidor.controlador;

import comum.modelo.Mensagem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Controlador responsável por armazenar as mensagens a serem encaminhadas.
 */
public class ControladorMensagens {

    /**
     * Lista de mensagens a serem encaminhadas.
     */
    private final List<Mensagem> mensagens = Collections.synchronizedList(new ArrayList<>());

    /**
     * Adiciona uma mensagem à lista.
     * @param mensagem Mensagem a ser adicionada.
     */
    public void adicionarMensagem (Mensagem mensagem) {
        synchronized (mensagem) {
            System.out.println("comum.modelo.Mensagem adicionada: " + mensagem);
            if (!mensagens.contains(mensagem))
                mensagens.add(mensagem);
        }
    }

    /**
     * Retira mensagens da lista.
     * @param usuario Nome do usuário do destinatário da mensagem.
     * @return Lista de mensagens para o destinatário especificado.
     */
    public List<Mensagem> retirarMensagens (String usuario) {
        List<Mensagem> mensagensParaUsuario = new ArrayList<>();
        synchronized (mensagens) {
            for (Mensagem mensagem : mensagens) {
                if (mensagem.nomeUsuarioDestinatario.equals(usuario)) {
                    System.out.println("comum.modelo.Mensagem retirada: " + mensagem);
                    mensagensParaUsuario.add(mensagem);
                }
            }
            mensagens.removeAll(mensagensParaUsuario);
        }

        return mensagensParaUsuario;
    }
}
