import org.apache.commons.codec.binary.Hex;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpMethod;
import org.json.JSONArray;

import java.net.URLDecoder;
import java.security.MessageDigest;


public class TempMail {
    public static String MainPage = "https://temp-mail.ru/";
    public static String MailCookie = "mail";
    public static String MailApiBaseUrl = "http://api.temp-mail.ru/request/mail/id/";
    public static String FormatSuffix = "/format/json";

    public String Email = "";
    public String MailApiUrl = "";
    public JSONArray Messages;

    public TempMail() throws Exception {
        Cookie[] cookies = new OpenUrl(MainPage).State.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (MailCookie.equalsIgnoreCase(cookie.getName())) {
                Email = URLDecoder.decode(cookie.getValue(), "UTF-8");
                break;
            }
        }
        if (Email.length() == 0)
            throw new Exception("New e-mail address could not be received!");

        MailApiUrl = MailApiBaseUrl + new String(Hex.encodeHex(
                MessageDigest
                        .getInstance("MD5")
                        .digest(Email.getBytes("UTF-8"))
        )) + FormatSuffix;
    }

    public TempMail Refresh() throws Exception {
        HttpMethod mailbox = new OpenUrl(MailApiUrl).Request;
        if (mailbox.getStatusCode() == 200) {
            Messages =  new JSONArray(mailbox.getResponseBodyAsString());
        } else {
            Messages = null;
        }
        return this;
    }
}
