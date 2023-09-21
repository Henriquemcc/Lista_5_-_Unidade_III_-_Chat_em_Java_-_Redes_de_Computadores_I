package comum.controlador;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP {

    public static class UdpObjetoRecebido {
        public final Object object;
        public final InetAddress ipOrigem;
        public final int portaOrigem;

        public UdpObjetoRecebido(Object object, InetAddress ipOrigem, int portaOrigem) {
            this.object = object;
            this.ipOrigem = ipOrigem;
            this.portaOrigem = portaOrigem;
        }
    }

    public static UdpObjetoRecebido receberObjeto(DatagramSocket datagramSocket) throws IOException, ClassNotFoundException {
        byte[] bufferRecebimento = new byte[1024];
        DatagramPacket pacoteRecebido = new DatagramPacket(bufferRecebimento, bufferRecebimento.length);
        datagramSocket.receive(pacoteRecebido);
        return receberObjeto(pacoteRecebido);
    }

    public static UdpObjetoRecebido receberObjeto(DatagramPacket datagramPacket) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
        final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        final Object objetoRecebido = objectInputStream.readObject();
        final InetAddress ipOrigem = datagramPacket.getAddress();
        final int portaOrigem = datagramPacket.getPort();
        return new UdpObjetoRecebido(objetoRecebido, ipOrigem, portaOrigem);
    }

    public static void enviarObjeto(Object object, DatagramSocket datagramSocket, InetAddress ipDestino, int portaDestino) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        byte[] bytesObjeto = byteArrayOutputStream.toByteArray();
        DatagramPacket pacoteEnviado = new DatagramPacket(bytesObjeto, bytesObjeto.length, ipDestino, portaDestino);
        datagramSocket.send(pacoteEnviado);
    }
}
