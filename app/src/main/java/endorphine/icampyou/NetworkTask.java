package endorphine.icampyou;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import endorphine.icampyou.ExchangeMenu.ChatList_Adapter;
import endorphine.icampyou.ExchangeMenu.Chat_Item;
import endorphine.icampyou.GuideMenu.ConfirmPopupActivity;
import endorphine.icampyou.GuideMenu.GuideActivity;
import endorphine.icampyou.GuideMenu.ReviewListItem;
import endorphine.icampyou.GuideMenu.ReviewListViewAdapter;
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
    ChatList_Adapter chatList_adpater;
    ReviewListViewAdapter reviewList_adapter;

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, ChatList_Adapter _adapter) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.chatList_adpater = _adapter;
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, ReviewListViewAdapter _adapter) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.reviewList_adapter = _adapter;
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
                Log.e("4", "4");
                asyncDialog.setMessage("채팅방 개설 중 입니다..");
                break;
            case Constant.MAKE_REVIEWLIST:
                asyncDialog.setMessage("후기 작성 중 입니다..");
                break;
            case Constant.GET_CHATTINGLIST:
                asyncDialog.setMessage("채팅방 목록 가져오는 중 입니다..");
                break;
            case Constant.RESERVATION_CAMPING:
                asyncDialog.setMessage("예약이 진행 중 입니다..");
                break;
            case Constant.GET_REVIEWLIST:
                asyncDialog.setMessage("후기 목록 가져오는 중 입니다..");
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

        ImageConversion imageConversion = new ImageConversion();

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
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        Log.e("success", "성공");
                        Toast.makeText(context, "채팅방 개설", Toast.LENGTH_LONG).show();
                        ((Activity)context).finish();
                    } else {
                        Log.e("실패", "실패");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.GET_CHATTINGLIST:
                    try{
                        JSONObject jsonObject = new JSONObject(result);
                        String resultResponse = jsonObject.getString("result");
                        JSONArray resultObjectArray = new JSONArray(resultResponse);
                        if(!resultResponse.equals("fail")){
                            JSONObject resultObject;
                            for(int i=0;i<resultObjectArray.length();i++){
                                resultObject = resultObjectArray.getJSONObject(i);
                                Bitmap image = imageConversion.fromBase64(resultObject.getString("image"));
                                String user_id = resultObject.getString("user_id");
                                String camp_name = resultObject.getString("camp_name");
                                String myitem = resultObject.getString("myitem");
                                String needitem = resultObject.getString("needitem");
                                chatList_adpater.addItem(new Chat_Item(image,user_id,myitem,needitem,camp_name));
                            }
                            chatList_adpater.notifyDataSetChanged();
                        }
                    }catch (JSONException e) {
                        Log.e("exception",e.toString());
                        e.printStackTrace();
                    }
                break;
            case Constant.RESERVATION_CAMPING:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        intent = new Intent(context, ConfirmPopupActivity.class);
                        ((Activity)context).startActivity(intent);
                        Toast.makeText(context, "결제 완료", Toast.LENGTH_LONG).show();
                        ((Activity)context).finish();
                    } else {
                        Toast.makeText(context, "후기 작성에 실패하였습니다", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.MAKE_REVIEWLIST:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        Log.e("success", "성공");
                        intent = new Intent(context, GuideActivity.class);
                        intent.putExtra("캠핑장 이름",data.getString("camp_name"));
                        ((Activity)context).startActivity(intent);
                        Toast.makeText(context, "후기 작성 완료", Toast.LENGTH_LONG).show();
                        ((Activity)context).finish();
                    } else {
                        Toast.makeText(context, "후기 작성에 실패하였습니다", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.GET_REVIEWLIST:
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String resultResponse = jsonObject.getString("result");
                    JSONArray resultObjectArray = new JSONArray(resultResponse);
                    if(!resultResponse.equals("fail")){
                        JSONObject resultObject;
                        for(int i=0;i<resultObjectArray.length();i++){
                            resultObject = resultObjectArray.getJSONObject(i);
                            Bitmap image = imageConversion.fromBase64(resultObject.getString("image"));
                            String nickname = resultObject.getString("nickname");
                            String camp_name = resultObject.getString("camp_name");
                            String point = resultObject.getString("point");
                            String content = resultObject.getString("content");
                            reviewList_adapter.addItem(new ReviewListItem(camp_name,nickname,Float.parseFloat(point),image,content));
                        }
                        reviewList_adapter.notifyDataSetChanged();
                    }
                }catch (JSONException e) {
                    Log.e("exception",e.toString());
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





