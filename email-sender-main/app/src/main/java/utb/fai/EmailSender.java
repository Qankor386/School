package utb.fai;

import java.net.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class EmailSender {

    private Socket socket;
    private InputStream input;
    private OutputStream output;

    private byte[] buffer;
    private final byte[] response = new byte[1024];
    private int len;

    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        input = socket.getInputStream();
        output = socket.getOutputStream();
        System.out.println("Connected to server");
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {  

        try{
            String message = String.format("EHLO %s\r\n", from);
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            
            if(input.available() > 0) {
            len = input.read(response);
            System.out.write(response, len, len);
            }

            message = String.format("MAIL FROM:<%s>\r\n", from);
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            
            if(input.available() > 0) {
            len = input.read(response);
            System.out.write(response, len, len);
            }

            message = String.format("RCPT TO:<%s>\r\n", to);
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            
            if(input.available() > 0) {
            len = input.read(response);
            System.out.write(response, len, len);
            }

            message = String.format("DATA\r\n");
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            
            if(input.available() > 0) {
            len = input.read(response);
            System.out.write(response, len, len);
            }

            message = String.format("From: %s\r\nTo: %s\r\nSubject: %s\r\n\r\n%s\r\n.\r\n", from, to, subject, text);
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            
            if(input.available() > 0) {
            len = input.read(response);
            System.out.write(response, len, len);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
                
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close() {
        
        try {
            String message = "QUIT\r\n";
            buffer = message.getBytes();
            output.write(buffer, 0, buffer.length);
            output.flush();

            Thread.sleep(500);
            
            if(input.available() > 0) {
            len = input.read(response);
            System.out.write(response, len, len);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } 
        
        if(socket != null && !socket.isClosed()){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
    }
}
