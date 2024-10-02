package utb.fai;

public class App {
 
    public static void main(String[] args) {

        try {
            String host = args[0];        
            int port = Integer.parseInt(args[1]); 
            String senderEmail = args[2];       
            String recipientEmail = args[3];     
            String subject = args[4];    
            String message = args[5];   

            EmailSender sender = new EmailSender(host, port);
            sender.send(senderEmail, recipientEmail, subject, message);
            sender.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

