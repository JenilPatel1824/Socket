import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/// Tcp server for mul clients
public class MultipleClientHandler {


    static ThreadPoolExecutor executor;
    public static void main(String[] args) {
        executor = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
        try{
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Waiting for client on port 1234...");
            while(true){
                Socket socket = serverSocket.accept();
                executor.submit(new ClientHandler(socket));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
class ClientHandler implements Runnable{
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            System.out.println("Client connected..." + socket.getInetAddress());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String input;
            while ((input = in.readLine()) != null) {
                if(input.equals("bye")){
                    break;
                }
                System.out.println(input);
                out.println(input);
            }
            socket.close();
            System.out.println("Client disconnected..." + socket.getInetAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
