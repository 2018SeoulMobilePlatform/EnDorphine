package endorphine.icampyou;

import android.content.ContentValues;
import android.net.Uri;
import android.text.style.BulletSpan;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.Buffer;

public class RequestHttpURLConnection {

    public String request(String _url, JSONObject data) {
        String result = null;
        BufferedReader reader = null;

        try {
            URL urlObject = new URL(_url);
            HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();

            conn.setReadTimeout(100000); //10초동안 서버로부터 반응없으면 에러
            conn.setConnectTimeout(15000); // 접속하는 커넥션 타임 15초동안 접속안되면 접속 안된느 것으로 간주

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

//    public String requestImage(String _url,String filePath,) {
//        String[] data = {};
//        String[] dataName = {};
//
//        String result = null;
//
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "androidupload";
//        File targetFile = null;
//        if (filePath != null) {
//            targetFile = new File(filePath);
//        }
//
//        byte[] buffer;
//        int maxBuffersize = 5 * 1024 * 1024;
//        HttpURLConnection conn = null;
//        String url = _url;
//        try {
//            conn = (HttpURLConnection) new URL(url).openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            conn.setRequestMethod("POST");
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        }
//        conn.setReadTimeout(10000);
//        conn.setConnectTimeout(10000);
//        conn.setDoOutput(true);
//        conn.setDoInput(true);
//        conn.setUseCaches(false);
//        conn.setRequestProperty("ENCTYPE","multipart/form-data");
//        conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
//        String delimeter = twoHyphens + boundary + lineEnd;
//        StringBuffer postDataBuilder = new StringBuffer();
//        for(int i=0;i<data.length;i++){
//            postDataBuilder.append(delimeter);
//            postDataBuilder.append("Content-Disposition: form-data; name =\""+dataName[i]+"\""+lineEnd+lineEnd+data[i]+lineEnd);
//        }
//        if(fileName!=null){
//            postDataBuilder.append(delimeter);
//            postDataBuilder.append("Content-Disposition: form-data; name=\""+key+ "\";filename=\""+fileName+"\""+lineEnd);
//        }
//        try{
//            DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
//            ds.write(postDataBuilder.toString().getBytes());
//
//            if(filePath != null){
//                ds.writeBytes(lineEnd);
//                FileInputStream fstream = new FileInputStream(targetFile);
//                buffer = new byte[maxBuffersize];
//                int length = -1;
//                while((length=fstream.read(buffer)) != -1){
//                    ds.write(buffer,0,length);
//                }
//                ds.writeBytes(lineEnd);
//                ds.writeBytes(lineEnd);
//                ds.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);
//                fstream.close();
//            } else{
//                ds.writeBytes(lineEnd);
//                ds.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);
//            }
//            ds.flush();
//            ds.close();
//            int responseCode = conn.getResponseCode();
//            if(responseCode == HttpURLConnection.HTTP_OK){
//                String line = null;
//                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                while((line = br.readLine()) != null){
//                    result += line;
//                }
//            }
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//
//        return result;
//
//    }
//
//    public void doFileUpload(String _url,String imageName,byte[] data) {
//        try {
//            URL url = new URL(_url);
//            Log.i("냥냥", "http://localhost/image_upload.jsp" );
//            String lineEnd = "\r\n";
//            String twoHyphens = "--";
//            String boundary = "*****";
//
//            // open connection
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setDoInput(true); //input 허용
//            con.setDoOutput(true);  // output 허용
//            con.setUseCaches(false);   // cache copy를 허용하지 않는다.
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Connection", "Keep-Alive");
//            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//
//            // write data
//            DataOutputStream dos = new DataOutputStream(con.getOutputStream());
//            Log.i("냥냥", "Open OutputStream" );
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//
//            // 파일 전송시 파라메터명은 file1 파일명은 camera.jpg로 설정하여 전송
//            dos.writeBytes("Content-Disposition: form-data; name=\"file1\";filename=\""+ imageName + lineEnd);
//
//            dos.writeBytes(lineEnd);
//            dos.write(data,0,data.length);
//            Log.i("냥냥", data.length+"bytes written" );
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//            dos.flush(); // finish upload...
//
//            try { dos.close(); } catch(Exception e){}
//        } catch (Exception e) {
//            Log.i("냥냥", "exception " + e.getMessage());
//            // TODO: handle exception
//        }
//        Log.i("냥냥", data.length+"bytes written successed ... finish!!" );
//
//    }

    //이미지 서버에 올리는
    public String requestImage(String _url,Uri uri){
        String result = null;
        try {
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;

            int maxBufferSize = 1024 * 1024;

            URL url = new URL(_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Allow Inputs &amp; Outputs.
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Set HTTP method to POST.
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            FileInputStream fileInputStream;
            DataOutputStream outputStream;
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            outputStream.writeBytes("Content-Disposition: form-data; name=\"reference\""+ lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes("my_refrence_text");
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadFile\";filename=\"" + uri.getLastPathSegment() +"\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            fileInputStream = new FileInputStream(uri.getPath());
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            int serverResponseCode = connection.getResponseCode();
            if (serverResponseCode == 200) {
                StringBuilder s_buffer = new StringBuilder();
                InputStream is = new BufferedInputStream(connection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    s_buffer.append(inputLine);
                }
                result = s_buffer.toString();
            }
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            if (result != null) {
                Log.d("result_for upload", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

