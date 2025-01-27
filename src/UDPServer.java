import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class UDPServer {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(1234)) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            Set<InetSocketAddress> clients = new HashSet<>();

            System.out.println("Server is listening on port 1234...");
            while (true) {
                socket.receive(packet);

                InetSocketAddress clientSocketAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());

                clients.add(clientSocketAddress);

                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Client (" + packet.getAddress() + ":" + packet.getPort() + ") says: " + receivedMessage);

                for (InetSocketAddress client : clients) {
                    if (!client.equals(clientSocketAddress)) { // Skip the sender
                        String messageToSend = "Client (" + packet.getAddress() + ":" + packet.getPort() + "): " + receivedMessage;
                        DatagramPacket relayPacket = new DatagramPacket(
                                messageToSend.getBytes(),
                                messageToSend.length(),
                                client.getAddress(),
                                client.getPort()
                        );
                        socket.send(relayPacket);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
