import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTPMailBox {
    public String Server;
    public String Port;
    public String Login;
    public String Password;
    public boolean EnableSSL;
    public String MailAddr;

   public SMTPMailBox(String server, String port, String login, String password, boolean enableSSL, String mailAddr) {
        Server = server;
        Port = port;
        Login = login;
        Password = password;
        EnableSSL = enableSSL;
        MailAddr = mailAddr;
    }

   public void Send(String to, String subject, String body) throws Exception {
        boolean enableAuth = !Login.equals("");

        Properties properties = new Properties();
        properties.put("mail.smtp.host"               , Server);
        properties.put("mail.smtp.port"               , Port);
        properties.put("mail.smtp.auth"               , enableAuth ? "true" : "false");
        properties.put("mail.smtp.ssl.enable"         , EnableSSL ? "true"  : "false");
        if (EnableSSL)
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session;
        if (enableAuth) {
            Authenticator auth = new EmailAuthenticator(Login, Password);
            session = Session.getDefaultInstance(properties, auth);
        } else {
            session = Session.getDefaultInstance(properties);
        }
        session.setDebug(false);
        MimeMessage message = new MimeMessage(session);
        message.setSender(new InternetAddress(MailAddr));
        message.setFrom(new InternetAddress(MailAddr));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(body);
        Transport.send(message);
       }

}
