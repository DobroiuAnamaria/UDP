import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UdpServer {
    private final DatagramSocket datagramSocket;
    private byte[]buffer=new byte[300];

    public UdpServer(DatagramSocket datagramSocket)
    {
        this.datagramSocket=datagramSocket;
    }

    public void run() throws IOException
    {
        while (true)
        {
            receive_then_send();
        }
    }

    private void receive_then_send()throws IOException {
        DatagramPacket datagramPacket=new DatagramPacket(buffer,0,buffer.length);
        datagramSocket.receive(datagramPacket);
        InetAddress inetAddress=datagramPacket.getAddress();
        int port= datagramPacket.getPort();
        String messaje_from_client= new String(datagramPacket.getData(),0,datagramPacket.getLength());
        System.out.println("Messaje from client "+inetAddress+ ": "+messaje_from_client);
        Scanner scanner =new Scanner(System.in);
        String messaje= scanner.nextLine();
        buffer=messaje.getBytes();
        datagramPacket= new DatagramPacket(buffer, buffer.length,inetAddress,port);
        datagramSocket.send(datagramPacket);
    }

    public static void main(String[] args)throws SocketException {
        DatagramSocket socket= new DatagramSocket(2345);
        UdpServer server=new UdpServer(socket);
        try {
            server.run();
        }catch (IOException exception)
        {
            System.out.println(exception.getMessage());
        }

    }
}
