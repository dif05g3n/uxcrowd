import org.testng.annotations.Test;
import org.json.JSONObject;
import static org.apache.commons.lang3.RandomStringUtils.randomAscii;

public class TestTempMail {
    @Test()
    public TestTempMail() throws Exception {
        // ...
        TempMail tempMail = new TempMail();
        System.out.println(tempMail.Email + " " + tempMail.MailApiUrl);
        SMTPMailBox outMail = new SMTPMailBox(
                "smtp.yandex.ru",
                "465",
                "fergerfgergh",
                "1qa@WS3ed",
                true,
                "wbngug@ya.ru"
        );
        String randomString = randomAscii(20) + "\n";
        System.out.println(randomString);
        outMail.Send(tempMail.Email, "TEST", randomString);

        int timeout = 10000;
        boolean received = false;
        wait_receive: for (int step = 1000, i = 0; i < timeout; i += step){
            Thread.sleep(step);
            if (tempMail.Refresh().Messages != null)
                for (i = 0; i < tempMail.Messages.length(); i++){
                    JSONObject message = tempMail.Messages.getJSONObject(i);
                    if(message.getString("mail_text").equals(randomString)){
                        received = true;
                        System.out.println("OK!");
                        break wait_receive;
                    }
                }
        }
        if (!received){
            throw new Exception("Timeout");
        }
    }
}
