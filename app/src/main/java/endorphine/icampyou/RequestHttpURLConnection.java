package endorphine.icampyou;

import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHttpURLConnection {

    public String request(String _url, JSONObject data) {
        String result = null;
        BufferedReader reader = null;

        try {
            URL urlObject = new URL(_url);
            HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();

            conn.setReadTimeout(150000); //10초동안 서버로부터 반응없으면 에러
            conn.setConnectTimeout(15000); // 접속하는 커넥션 타임 15초동안 접속안되면 접속 안되는 것으로 간주

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset ����. //character set을 utf-8로 선언
            conn.setRequestProperty("Content-Type", "application/json"); //서버로 보내는 패킷이 어떤타입인지 선언

            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(data.toString().getBytes());

            int resCode = conn.getResponseCode();

            InputStreamReader InputStream = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader Reader = new BufferedReader(InputStream);
            StringBuilder builder = new StringBuilder();

            while ((result = Reader.readLine()) != null) {
                builder.append(result + "\n");
            }

            result = builder.toString();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

