package comum.controlador;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Classe utilizada para a realização de comunicação com o protocolo UDP.
 */
public class UDP {

    /**
     * Classe utilizada no retorno da função receber objeto.
     */
    public static class UdpObjetoRecebido {

        /**
         * Objeto recebido.
         */
        public final Object object;

        /**
         * Endereço IP da máquina de origem.
         */
        public final InetAddress ipOrigem;

        /**
         * Porta da máquina de origem.
         */
        public final int portaOrigem;

        /**
         * Constrói uma nova instância da classe UdpObjetoRecebido.
         * @param object Objeto recebido.
         * @param ipOrigem Endereço IP da máquina de origem.
         * @param portaOrigem Porta da máquina de origem.
         */
        public UdpObjetoRecebido(Object object, InetAddress ipOrigem, int portaOrigem) {
            this.object = object;
            this.ipOrigem = ipOrigem;
            this.portaOrigem = portaOrigem;
        }
    }

    /**
     * Recebe um objeto via UDP.
     * @param datagramSocket Socket UDP de onde será recebido o objeto.
     * @return Instância da classe UdpObjetoRecebido com o objeto recebido, o endereço e a porta da máquina de origem.
     * @throws IOException Exceção lançada, em caso de erro de comunicação.
     * @throws ClassNotFoundException Exceção lançada, em caso do objeto recebido não corresponda a nenhuma classe.
     */
    public static UdpObjetoRecebido receberObjeto(DatagramSocket datagramSocket) throws IOException, ClassNotFoundException {
        byte[] bufferRecebimento = new byte[1024];
        DatagramPacket pacoteRecebido = new DatagramPacket(bufferRecebimento, bufferRecebimento.length);
        datagramSocket.receive(pacoteRecebido);
        return receberObjeto(pacoteRecebido);
    }

    /**
     * Recebe um objeto via UDP.
     * @param datagramPacket Socket UDP de onde será recebido o objeto.
     * @return Instância da classe UdpObjetoRecebido com o objeto recebido, o endereço e a porta da máquina de origem.
     * @throws IOException Exceção lançada, em caso de erro de comunicação.
     * @throws ClassNotFoundException Exceção lançada, em caso do objeto recebido não corresponda a nenhuma classe.
     */
    public static UdpObjetoRecebido receberObjeto(DatagramPacket datagramPacket) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
        final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        final Object objetoRecebido = objectInputStream.readObject();
        final InetAddress ipOrigem = datagramPacket.getAddress();
        final int portaOrigem = datagramPacket.getPort();
        return new UdpObjetoRecebido(objetoRecebido, ipOrigem, portaOrigem);
    }

    /**
     * Envia um objeto via UDP.
     * @param object Objeto a ser enviado.
     * @param datagramSocket Socket UDP para onde o objeto será enviado.
     * @param ipDestino Endereço IP da máquina de destino.
     * @param portaDestino Porta da máquina de destino.
     * @throws IOException Exceção lançada, em caso de erro de comunicação.
     */
    public static void enviarObjeto(Object object, DatagramSocket datagramSocket, InetAddress ipDestino, int portaDestino) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        byte[] bytesObjeto = byteArrayOutputStream.toByteArray();
        DatagramPacket pacoteEnviado = new DatagramPacket(bytesObjeto, bytesObjeto.length, ipDestino, portaDestino);
        datagramSocket.send(pacoteEnviado);
    }
}
