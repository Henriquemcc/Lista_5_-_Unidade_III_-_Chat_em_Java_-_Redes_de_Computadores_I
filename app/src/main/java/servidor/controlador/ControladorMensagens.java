package servidor.controlador;

import comum.modelo.Mensagem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class ControladorMensagens {
        private final List<Mensagem> mensagens = Collections.synchronizedList(new ArrayList<>());

    public void adicionarMensagem (Mensagem mensagem) {
        synchronized (mensagem) {
            System.out.println("comum.modelo.Mensagem adicionada: " + mensagem);
            if (!mensagens.contains(mensagem))
                mensagens.add(mensagem);
        }
    }

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
