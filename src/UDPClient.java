import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


/// Udp chat group client
public class UDPClient {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            Thread sendThread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Type your message (type 'exit' to quit):");

                while (true) {
                    System.out.print("You: ");
                    String message = scanner.nextLine();

                    if (message.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting...");
                        break;
                    }
                    try {
                        DatagramPacket packet = new DatagramPacket(
                                message.getBytes(),
                                message.length(),
                                address,
                                1234
                        );
                        socket.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                scanner.close();
            });

            Thread receiveThread = new Thread(() -> {
                byte[] buffer = new byte[1024];

                while (true) {
                    try {
                        DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                        socket.receive(receivedPacket);
                        String receivedMessage = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
                        System.out.println("\n" + receivedMessage);
                        System.out.print("You: ");
                    } catch (IOException e) {
                        break;
                    }
                }
            });
            sendThread.start();
            receiveThread.start();
            sendThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
