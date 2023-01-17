import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Server
{
    public static void main(String[] args)
    {   
          Scanner sc=new Scanner(System.in);
        try 
        {   
            ServerSocket serve = new ServerSocket(3454);
            ServerSocket server = new ServerSocket(5021);
            Socket client = server.accept();
            Socket clien = serve.accept();
            PrintWriter out = new PrintWriter(client.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            PrintWriter o = new PrintWriter(clien.getOutputStream());
            BufferedReader i = new BufferedReader(new InputStreamReader(clien.getInputStream()));
           
            Thread send= new Thread() 
            {
                String msg;              //variable that will contains the data written by the user
                String owner= "Server:-";                
                public void run() 
                {  
                    while(true)
                    {        
                        msg = sc.nextLine(); //input msg   
                        out.println(owner+msg);    // write msg stored in the client Socket
                        o.println(owner+msg); // write msg stored in the client1 Socket
                        out.flush();
                        o.flush();
                    }
                }
            }; 
            send.start();
            
            Thread receive= new Thread()    //Anonymous class of thread for receive message
            {      
                String msg ;
                public void run() 
                {
                    try 
                    {
                        msg = in.readLine();  
                        while(msg!="end")
                        {
                            System.out.println(msg);
                            msg = in.readLine();
                            o.println(msg);
                            o.flush();
                        }
                        System.out.println("Client disconnect");
                        client.close();
                        server.close();
                    } catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
                }
            };
            receive.start();
            Thread receiver= new Thread()   //Anonymous class of thread for receive message
            {      
                String msg1 ;
                public void run() 
                {
                    try
                    {
                        msg1 = i.readLine();  
                        while(msg1!="end")
                        {
                            System.out.println(msg1);
                            msg1 = i.readLine();
                            out.println(msg1);
                            out.flush();
                        }
                        System.out.println("Client disconnect");
                        o.close();
                        out.close();
                        clien.close();
                        serve.close();
                    } 
                    catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
                }
            };
            receiver.start();
        }
         catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}