import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.HttpMethod;


public class OpenUrl {
    public static String UserAgent= "Mozilla Firefox 6.66.666";
    public HttpState State;
    public HttpMethod Request;

    public OpenUrl(String url) throws Exception {
        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.useragent", UserAgent);
        client.getParams().setParameter("http.protocol.single-cookie-header", true);
        client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

        Request = new GetMethod(url);
        client.executeMethod(Request);
        State = client.getState();
    }

    protected void finalize() {
        try {
            Request.releaseConnection();
        } catch (Exception e){
            // pass
        }
    }
}
