import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client 
{
    public static void main(String[] args)
    {
      Scanner sc = new Scanner(System.in); 
        try 
        { 
            Socket client = new Socket("127.0.0.1",5021);
            System.out.println("Serverconnected");
            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
           
            Thread send = new Thread() //Anonymous class for send messages
            { 
                String msg;
                String msg1="Client is online";
                String owner="Client:-";
                public void run() {
                    out.println(msg1);
                    out.flush();
                    while(true)
                    {
                        msg = sc.nextLine();
                        out.println(owner+msg);
                        out.flush();
                    }
                }
            };    
            send.start();
            Thread receive = new Thread()   //Anonymous class for send messages
            {    
                String msg;       
                public void run() 
                {
                    try 
                    {
                        msg = in.readLine();
                        while(msg!="end")
                        {
                            System.out.println(msg);
                            msg = in.readLine();         
                        }
                        System.out.println("Server Disconnected");
                        out.close();
                        client.close();     
                    }
                    catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
                }
            };    // close of anonymous class
            receive .start();
        }
        catch (IOException e)
        {
        e.printStackTrace();
        }
    }
}