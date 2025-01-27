import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/// TCP server
public class Server {

    public static void main(String[] args) {
        try{


            //ServerSocket serverSocket = new ServerSocket(1024, 50, InetAddress.getByName("10.20.41.91"));

            ServerSocket serverSocket = new ServerSocket(1024);
            serverSocket.setReuseAddress(true);
            System.out.println("Server started");
            while(true){
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(50000);
                System.out.println("Client connected "+socket.getInetAddress() );

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String message;
                while(true){
                    try {
                        if ((message = in.readLine()) != null) {
                            System.out.println("Client says: " + message);
                            out.println("Server Received: " + message);
                        }
                    } catch (IOException e) {
                        System.out.println("Client disconnected or error occurred: " + e.getMessage()+" "+ socket.getRemoteSocketAddress());
                        break;
                    }
                }
                socket.close();
            }

        } catch (IOException e) {
            System.out.println("Server Error: " + e);
        }
    }
}
