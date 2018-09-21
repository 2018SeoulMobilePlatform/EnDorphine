package endorphine.icampyou;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import endorphine.icampyou.ExchangeMenu.Chat_Content;
import endorphine.icampyou.Login.LoginActivity;
import endorphine.icampyou.Login.PasswordPopupActivity;

public class NetworkTask extends AsyncTask<Void, Void, String> {

    private String url;
    private JSONObject data;
    private Intent intent;
    private int select;
    private Context context;
    ProgressDialog asyncDialog;
    EditText insert;

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, EditText _insert) {
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
        switch (select) {
            case Constant.USER_REGISTER:
                asyncDialog.setMessage("사용자 등록 중 입니다..");
                break;
            case Constant.USER_LOGIN:
                asyncDialog.setMessage("로그인 중 입니다..");
                break;
            case Constant.USER_FIND_INFO:
                asyncDialog.setMessage("비밀번호를 찾는 중 입니다..");
                break;
            case Constant.DUPLICATED_EMAIL:
                asyncDialog.setMessage("이메일 중복 검사 중 입니다..");
                break;
            case Constant.DUPLICATED_NICKNAME:
                asyncDialog.setMessage("닉네임 중복 검사 중 입니다..");
                break;
            case Constant.DUPLICATED_PHONENUMBER:
                asyncDialog.setMessage("핸드폰 중복 검사 중 입니다..");
                break;
            case Constant.MAKE_CHATTINGLIST:
                Log.e("4","4");
                asyncDialog.setMessage("채팅방 개설 중 입니다..");
                break;
            case Constant.MAKE_REVIEWLIST:
                asyncDialog.setMessage("후기 작성 중 입니다..");
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
        if(result.equals(null)){
            Log.e("null","입니당");
        }
        else{
            Log.e("냥냥",result);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        //다이얼로그 종료
        asyncDialog.dismiss();

        //경우에 따른 스위치문
        switch (select) {
            case Constant.USER_REGISTER:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        intent = new Intent((Activity) context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ((Activity) context).startActivity(intent);

                        Toast.makeText(context, "사용자 등록을 완료하였습니다", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "사용자 등록 실패하였습니다.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.USER_LOGIN:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        // 홈 액티비티 실행
                        context.startActivity(new Intent(context, HomeActivity.class));
                        ((Activity) context).finish();
                    } else {
                        onCancelled();
                        Toast.makeText(context, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.USER_FIND_INFO:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    Intent intent = new Intent((Activity) context, PasswordPopupActivity.class);
                    intent.putExtra("password", real_result);
                    ((Activity) context).startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.DUPLICATED_EMAIL:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        insert.setBackgroundResource(R.drawable.uncheck_edittext);
                    } else {
                        insert.setBackgroundResource(R.drawable.check_edittext);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.DUPLICATED_NICKNAME:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        insert.setBackgroundResource(R.drawable.uncheck_edittext);
                    } else {
                        insert.setBackgroundResource(R.drawable.check_edittext);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.DUPLICATED_PHONENUMBER:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        insert.setBackgroundResource(R.drawable.uncheck_edittext);
                    } else {
                        insert.setBackgroundResource(R.drawable.check_edittext);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.MAKE_CHATTINGLIST:
                try {
            Log.e("5","5");
            JSONObject jsonObject = new JSONObject(result);
            String real_result = jsonObject.getString("result");
            if (real_result.equals("success")) {
                Log.e("success","성공");
                Toast.makeText(context, "채팅방 개설", Toast.LENGTH_LONG).show();
                //((Activity)context).finish();
            } else {
                Log.e("실패","실패");
            }
        } catch (JSONException e) {
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





