package endorphine.icampyou;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import endorphine.icampyou.Login.RegisterUserException;

public class NetworkTask extends AsyncTask<Void, Void, String> {

    private String url;
    private JSONObject data;

    //사용자 등록 경우
    public static final int USER_REGISTER = 1111;

    //사용자 로그인 경우
    public static final int USER_LOGIN = 1112;

    //사용자 로그인 경우
    public static final int USER_FIND_INFO = 1113;

    //이메일 아이디 중복되는 경우
    public static final int DUPLICATED_EMAIL = 1114;

    //이메일 아이디 중복되는 경우
    public static final int DUPLICATED_NICKNAME = 1115;

    //이메일 아이디 중복되는 경우
    public static final int DUPLICATED_PHONENUMBER = 1116;

    private int select;
    private Context context;
    ProgressDialog asyncDialog;
    EditText insert;

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
        asyncDialog = new ProgressDialog(_context);
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, EditText _insert){
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.insert = _insert;
    }

    @Override
    protected void onPreExecute() {
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        switch (select){
            case USER_REGISTER:
                asyncDialog.setMessage("사용자 등록 중 입니다..");
                break;
            case USER_LOGIN:
                asyncDialog.setMessage("로그인 중 입니다..");
                break;
            case USER_FIND_INFO:
                asyncDialog.setMessage("사용자 정보를 찾는 중 입니다..");
                break;
            case DUPLICATED_EMAIL:
                asyncDialog.setMessage("이메일 중복 검사 중 입니다..");
                break;
            case DUPLICATED_NICKNAME:
                asyncDialog.setMessage("닉네임 중복 검사 중 입니다..");
                break;
            case DUPLICATED_PHONENUMBER:
                asyncDialog.setMessage("핸드폰 중복 검사 중 입니다..");
                break;
            default:
                    break;

        }

        // show dialog
        asyncDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = null;
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, data);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        //다이얼로그 종료
        asyncDialog.dismiss();

        //경우에 따른 스위치문
        switch (select){
            case USER_REGISTER:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if(real_result.equals("id_fail")){
                        Toast.makeText(context, "중복된 아이디가 존재합니다", Toast.LENGTH_LONG).show();
                    } else if( real_result.equals("nickname_fail")){
                        Toast.makeText(context, "중복된 닉네임이 존재합니다", Toast.LENGTH_LONG).show();
                    } else if( real_result.equals("phonenumber_fail")){
                        Toast.makeText(context, "중복된 핸드폰번호가 존재합니다", Toast.LENGTH_LONG).show();
                    } else if( real_result.equals("success")){
//                        FragmentManager fragmentManager =
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace();
//                        fragmentTransaction.commit();
                        Toast.makeText(context, "사용자 등록을 완료하였습니다", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                        onCancelled();
                        Toast.makeText(context, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case USER_FIND_INFO:
                break;
            case DUPLICATED_EMAIL:
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if(real_result.equals("success")){
                        insert.setBackgroundResource(R.drawable.uncheck_edittext);
                    } else{
                        insert.setBackgroundResource(R.drawable.check_edittext);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
                break;
            case DUPLICATED_NICKNAME:
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if(real_result.equals("success")){
                        insert.setBackgroundResource(R.drawable.uncheck_edittext);
                    } else{
                        insert.setBackgroundResource(R.drawable.check_edittext);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
                break;
            case DUPLICATED_PHONENUMBER:
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if(real_result.equals("success")){
                        insert.setBackgroundResource(R.drawable.uncheck_edittext);
                    } else{
                        insert.setBackgroundResource(R.drawable.check_edittext);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
                break;
            default:
                    break;
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}





