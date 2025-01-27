import java.io.*;
import java.net.Socket;


/// TCP client
public class Client {

    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", 1024);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("connected");
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while(socket.isConnected()){
                System.out.print("> ");
                message = console.readLine();
                if(message.equals("exit")){
                    break;
                }
                out.println(message);
                System.out.println(in.readLine());

            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
