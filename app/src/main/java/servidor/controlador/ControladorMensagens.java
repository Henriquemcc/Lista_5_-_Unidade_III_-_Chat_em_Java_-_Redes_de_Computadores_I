package servidor.controlador;

import comum.modelo.Mensagem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControladorMensagens {
        private final List<Mensagem> mensagens = Collections.synchronizedList(new ArrayList<Mensagem>());

        public void adicionarMensagem (Mensagem mensagem){
            System.out.println("comum.modelo.Mensagem adicionada: " + mensagem);
            if (!mensagens.contains(mensagem))
                mensagens.add(mensagem);
        }

        public Mensagem retirarMensagem (String usuario) {
            Mensagem mensagemParaUsuario = null;
            for (Mensagem mensagem: mensagens) {
                if (mensagem.nomeUsuarioDestinatario.equals(usuario)){
                    mensagemParaUsuario = mensagem;
                    mensagens.remove(mensagem);
                }
            }
            System.out.println("comum.modelo.Mensagem retirada: " + mensagemParaUsuario);
            return mensagemParaUsuario;
        }
}
