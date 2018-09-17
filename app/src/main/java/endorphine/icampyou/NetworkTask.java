package endorphine.icampyou;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import cz.msebera.android.httpclient.NameValuePair;

public class NetworkTask extends AsyncTask<Void, Void, String> {

    final int passData = 1;
    final int getData = 2;

    private String url;
    private ContentValues values;
    private ArrayList<NameValuePair> data;

    public NetworkTask(String url,ArrayList<NameValuePair> _data) {

        this.url = url;
        this.values = values;
        this.data = _data;
    }

    @Override
    protected String doInBackground(Void... params) {

        String result; // 요청 결과를 저장할 변수.
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        //result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
        requestHttpURLConnection.select_doProcess(url,data);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
//        Log.e("result", s);
    }
}

