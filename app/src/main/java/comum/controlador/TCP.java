package comum.controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Classe utilizada para a realização de comunicação com o protocolo TCP.
 */
public class TCP {

    /**
     * Recebe um objeto via TCP.
     * @param socket Socket TCP de onde será recebido o objeto.
     * @return Objeto recebido
     * @throws IOException Exceção lançada, em caso de erro de comunicação.
     * @throws ClassNotFoundException Exceção lançada, caso o objeto recebido não corresponda a nenhuma classe.
     */
    public static Object receberObjeto(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        return objectInputStream.readObject();
    }

    /**
     * Envia um objeto via TCP.
     * @param socket Socket TCP para onde será enviado o objeto.
     * @param object Objeto a ser enviado.
     * @throws IOException Exceção lançada, em caso de erro de comunicação.
     */
    public static void enviarObjeto(Socket socket, Object object) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(object);
    }
}
