package endorphine.icampyou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkTask extends AsyncTask<Void, Void, String> {

    private String url;
    private JSONObject data;

    public static final int USER_REGISTER = 1111;
    public static final int USER_LOGIN = 1112;

    public static boolean CHECK_LOGIN = false;

    private int select;
    private Context context;

    public NetworkTask(String url, JSONObject data,int ACTION){
        this.url = url;
        this.data = data;
        this.select = ACTION;
    }

    public NetworkTask(Context _context,String url, JSONObject data,int ACTION){
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
    }


    @Override
    protected String doInBackground(Void... voids) {
        String result = null;
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, data);

        Log.e("1","1");



        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        switch (select){
            case USER_REGISTER:
                break;
            case USER_LOGIN:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        // 홈 액티비티 실행
                        context.startActivity(new Intent(context, HomeActivity.class));
                        ((Activity)context).finish();
                    } else {
                        Toast.makeText(context, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}





