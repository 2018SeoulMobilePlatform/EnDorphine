package endorphine.icampyou;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import endorphine.icampyou.ExchangeMenu.ChatList_Adapter;
import endorphine.icampyou.ExchangeMenu.ChatMessage_Adapter;
import endorphine.icampyou.ExchangeMenu.Chat_Item;
import endorphine.icampyou.ExchangeMenu.ChattingMessageActivity;
import endorphine.icampyou.GuideMenu.Reservation.ConfirmPopupActivity;
import endorphine.icampyou.GuideMenu.Review.ReviewListItem;
import endorphine.icampyou.GuideMenu.Review.ReviewListViewAdapter;
import endorphine.icampyou.Login.LoginActivity;
import endorphine.icampyou.Login.PasswordPopupActivity;
import endorphine.icampyou.Login.RegisterUserException;

import static android.content.Context.MODE_PRIVATE;

public class NetworkTask extends AsyncTask<Void, Void, String> {

    private String url;
    private JSONObject data;
    private Intent intent;
    private int select;
    private Context context;
    ProgressDialog asyncDialog;
    EditText insert;
    String insert_value;
    RegisterUserException exception;
    ChatList_Adapter chatList_adpater;
    ChatList_Adapter chatList_adapter2;
    ReviewListViewAdapter reviewList_adapter;
    ChatMessage_Adapter chatMessage_adapter;
    ArrayList<ReviewListItem> reviewData;
    ArrayList<Chat_Item> copy;
    ArrayList<Chat_Item> copy2;
    RatingBar totalReviewStar;
    TextView totalReviewStarScore;
    ImageView drawerQrCode;
    TextView check_textView;

    String number;
    String other;
    boolean check;
    //예약부분
    String contents;
    String price;
    String quantity;
    String tentName;
    String campName;
    String period;
    Bitmap qrcodeBitmap;

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, String contents, String price, String quantity, String tentName, String campName, String period) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        this.contents = contents;
        this.price = price;
        this.quantity = quantity;
        this.tentName = tentName;
        this.campName = campName;
        this.period = period;
        asyncDialog = new ProgressDialog(_context);
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, ChatList_Adapter _adapter, ArrayList<Chat_Item> copy, ChatList_Adapter _adpater2, ArrayList<Chat_Item> copy2) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        this.chatList_adpater = _adapter;
        this.copy = copy;
        asyncDialog = new ProgressDialog(_context);
        this.chatList_adapter2 = _adpater2;
        this.copy2 = copy2;
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, ReviewListViewAdapter _adapter) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.reviewList_adapter = _adapter;
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, ChatMessage_Adapter chatMessage_adapter) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.chatMessage_adapter = chatMessage_adapter;
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, Boolean check, String number) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.check = check;
        this.number = number;
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, Boolean check, String number, String other) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.check = check;
        this.number = number;
        this.other = other;
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, EditText _insert) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.insert = _insert;
        this.exception = new RegisterUserException();
        this.insert_value = insert.getText().toString();
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, EditText _insert, TextView _textView) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.insert = _insert;
        this.exception = new RegisterUserException();
        this.insert_value = insert.getText().toString();
        this.check_textView = _textView;
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, ImageView drawerQrCode) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.drawerQrCode = drawerQrCode;
        this.exception = new RegisterUserException();
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, String number, String other) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.number = number;
        this.other = other;
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
            case Constant.DUPLICATED_NICKNAME2:
                asyncDialog.setMessage("닉네임 중복 검사 중 입니다..");
                break;
            case Constant.DUPLICATED_PHONENUMBER:
                asyncDialog.setMessage("핸드폰 중복 검사 중 입니다..");
                break;
            case Constant.MAKE_CHATTINGLIST:
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
            case Constant.MODIFY_USER_INFO:
                asyncDialog.setMessage("사용자 정보 수정 중 입니다..");
                break;
            case Constant.GET_CHATTINGMESSAGELIST:
                asyncDialog.setMessage("메세지를 불러오는 중 입니다..");
                break;
            case Constant.REMOVE_CHATTINGLIST:
                asyncDialog.setMessage("채팅방 목록 삭제 중 입니다..");
                break;
            case Constant.SET_OPPONENT:
                asyncDialog.setMessage("상대방 설정 중 입니다..");
                break;
            case Constant.GET_OPPONENT:
                asyncDialog.setMessage("로딩 중..");
                break;
            case Constant.SET_FLAG:
                asyncDialog.setMessage("로딩 중..");
                break;
            case Constant.SET_CHATTINGFLAG:
                asyncDialog.setMessage("로딩 중..");
                break;
            default:
                break;

        }

        asyncDialog.show();
        asyncDialog.setCanceledOnTouchOutside(false);
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
                    if (!real_result.equals("fail")) {
                        // 홈 액티비티 실행
                        String user_email = jsonObject.getJSONObject("result").getString("id");
                        String user_image = jsonObject.getJSONObject("result").getString("image");
                        String user_name = jsonObject.getJSONObject("result").getString("name");
                        String user_nickname = jsonObject.getJSONObject("result").getString("nickname");
                        String user_password = jsonObject.getJSONObject("result").getString("password");
                        String user_phonenumber = jsonObject.getJSONObject("result").getString("phonenumber");
                        String user_flag = jsonObject.getJSONObject("result").getString("flag");


                        SharedPreferences preferences = context.getSharedPreferences("preferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        // 유저정보 다 삭제
                        editor.clear();
                        editor.commit();

                        // 유저정보 저장

                        editor.putString("email", user_email);
                        editor.putString("password", user_password);
                        editor.putString("name", user_name);
                        editor.putString("nickname", user_nickname);
                        editor.putString("profileImage", user_image);
                        editor.putString("phoneNumber", user_phonenumber);
                        editor.putString("flag", user_flag);

                        editor.commit();

                        SharedPreferences auto = context.getSharedPreferences("auto", MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = auto.edit();

                        autoLogin.putString("inputId", user_email);
                        autoLogin.putString("inputPwd", user_password);
                        autoLogin.commit();

                        context.startActivity(new Intent(context, HomeActivity.class));
                        ((Activity) context).finish();

                    } else {
                        Toast.makeText(context, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.GET_RESERVATION_INFO:

                SharedPreferences preferences = context.getSharedPreferences("preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultResponse = jsonObject.getString("result");
                    JSONArray resultObjectArray = new JSONArray(resultResponse);

                    StringBuilder reservationNum_Builder = new StringBuilder();
                    StringBuilder campingPlace_Builder = new StringBuilder();
                    StringBuilder date_Builder = new StringBuilder();
                    StringBuilder tentType_Builder = new StringBuilder();
                    StringBuilder tentNum_Builder = new StringBuilder();
                    StringBuilder price_Builder = new StringBuilder();

                    if (!resultResponse.equals("fail")) {
                        JSONObject resultObject;
                        if (resultObjectArray.length() != 0) {
                            for (int i = 0; i < resultObjectArray.length(); i++) {
                                resultObject = resultObjectArray.getJSONObject(i);
                                reservationNum_Builder.append(resultObject.getString("reservation_number")).append(",");
                                campingPlace_Builder.append(resultObject.getString("camp_name")).append(",");
                                date_Builder.append(resultObject.getString("date")).append(",");
                                tentType_Builder.append(resultObject.getString("tent_type")).append(",");
                                tentNum_Builder.append(resultObject.getString("count")).append(",");
                                price_Builder.append(resultObject.getString("price")).append(",");
                                if (i == 0) {
                                    QRCodeWriter qrCodeWriter = new QRCodeWriter();
                                    contents = resultObject.getString("reservation_number");
                                    try {
                                        qrcodeBitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 500, 500));
                                    } catch (WriterException e) {
                                        e.printStackTrace();
                                    }
                                    drawerQrCode.setImageBitmap(qrcodeBitmap);
                                }
                            }
                            editor.putString("reservationNum", reservationNum_Builder.toString());
                            editor.putString("campingPlace", campingPlace_Builder.toString());
                            editor.putString("date", date_Builder.toString());
                            editor.putString("tentType", tentType_Builder.toString());
                            editor.putString("tentNum", tentNum_Builder.toString());
                            editor.putString("price", price_Builder.toString());


                        } else {
                            editor.putString("reservationNum", "");
                            editor.putString("campingPlace", "");
                            editor.putString("date", "");
                            editor.putString("tentType", "");
                            editor.putString("tentNum", "");
                            editor.putString("price", "");
                        }
                        editor.commit();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
            case Constant.USER_FIND_INFO:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (!real_result.equals("fail")) {
                        Intent intent = new Intent((Activity) context, PasswordPopupActivity.class);
                        intent.putExtra("password", real_result);
                        ((Activity) context).startActivity(intent);
                    } else {
                        Toast.makeText(context, "일치하는 사용자 정보가 없습니다", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.DUPLICATED_EMAIL:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("fail") && exception.EmailException(insert_value)) {
                        insert.setBackgroundResource(R.drawable.check_edittext);
                    } else {
                        insert.setBackgroundResource(R.drawable.uncheck_edittext);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.DUPLICATED_NICKNAME:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("fail") && exception.UserNickNameException(insert_value)) {
                        insert.setBackgroundResource(R.drawable.check_edittext);
                    } else {
                        insert.setBackgroundResource(R.drawable.uncheck_edittext);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.DUPLICATED_NICKNAME2:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("fail") && exception.UserNickNameException(insert_value)) {
                        check_textView.setVisibility(View.VISIBLE);
                    } else {
                        check_textView.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.DUPLICATED_PHONENUMBER:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("fail") && exception.UserPhoneException(insert_value)) {
                        insert.setBackgroundResource(R.drawable.check_edittext);
                    } else {
                        insert.setBackgroundResource(R.drawable.uncheck_edittext);
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
                        Toast.makeText(context, "채팅방 개설", Toast.LENGTH_LONG).show();
                        ((Activity) context).finish();
                    } else {
                        Toast.makeText(context, "이미 개설된 채팅방이 있습니다", Toast.LENGTH_LONG).show();
                        ((Activity) context).finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.GET_CHATTINGLIST:
                SharedPreferences preferences1 = context.getSharedPreferences("preferences", MODE_PRIVATE);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultResponse = jsonObject.getString("result");
                    JSONArray resultObjectArray = new JSONArray(resultResponse);
                    if (!resultResponse.equals("fail")) {
                        JSONObject resultObject;
                        for (int i = 0; i < resultObjectArray.length(); i++) {
                            resultObject = resultObjectArray.getJSONObject(i);
                            Bitmap image = imageConversion.fromBase64(resultObject.getString("image"));
                            String number = resultObject.getString("number");
                            String user_id = resultObject.getString("user_id");
                            String nickname = resultObject.getString("nickname");
                            String camp_name = resultObject.getString("camp_name");
                            String myitem = resultObject.getString("myitem");
                            String needitem = resultObject.getString("needitem");
                            String flag = resultObject.getString("flag");
                            Chat_Item item = new Chat_Item(number, image, user_id, nickname, myitem, needitem, camp_name, flag);
                            chatList_adpater.addItem(item);
                            copy.add(item);
                            if (user_id.equals(preferences1.getString("email", ""))) {
                                chatList_adapter2.addItem(item);
                                copy2.add(item);
                            }
                        }
                        chatList_adpater.notifyDataSetChanged();
                        chatList_adapter2.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.RESERVATION_CAMPING:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        intent = new Intent(context, ConfirmPopupActivity.class);
                        generateQRCode();
                        ((Activity) context).startActivity(intent);
                        Toast.makeText(context, "결제 완료", Toast.LENGTH_LONG).show();
                        ((Activity) context).finish();
                    } else if (real_result.equals("countfail")) {
                        Toast.makeText(context, "예악 가능 횟수를 초과하였습니다", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "결제 실패하였습니다", Toast.LENGTH_LONG).show();
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
                        intent = new Intent();
                        intent.putExtra("캠핑장 이름", data.getString("camp_name"));
                        switch (data.getString("camp_name")) {
                            case "난지 캠핑장":
                                ((Activity) context).setResult(Constant.NANJI, intent);
                                break;
                            case "서울대공원 캠핑장":
                                ((Activity) context).setResult(Constant.SEOUL, intent);
                                break;
                            case "노을 캠핑장":
                                ((Activity) context).setResult(Constant.NOEUL, intent);
                                break;
                            case "중랑 캠핑장":
                                ((Activity) context).setResult(Constant.JUNGRANG, intent);
                                break;
                            case "초안산 캠핑장":
                                ((Activity) context).setResult(Constant.CHOANSAN, intent);
                                break;
                            case "강동 캠핑장":
                                ((Activity) context).setResult(Constant.GANGDONG, intent);
                                break;
                        }
                        Toast.makeText(context, "후기 작성 완료", Toast.LENGTH_LONG).show();
                        ((Activity) context).finish();
                    } else {
                        Toast.makeText(context, "후기 작성 실패하였습니다", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.GET_REVIEWLIST:
                SharedPreferences preferences2 = context.getSharedPreferences("preferences", MODE_PRIVATE);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultResponse = jsonObject.getString("result");
                    JSONArray resultObjectArray = new JSONArray(resultResponse);
                    if (!resultResponse.equals("fail")) {
                        JSONObject resultObject;
                        for (int i = 0; i < resultObjectArray.length(); i++) {
                            resultObject = resultObjectArray.getJSONObject(i);
                            Bitmap image;
                            if (resultObject.getString("image").equals("null"))
                                image = null;
                            else
                                image = imageConversion.fromBase64(resultObject.getString("image"));

                            Bitmap profile_Image = imageConversion.fromBase64(resultObject.getString("user_image"));
                            String nickname = resultObject.getString("nickname");
                            String camp_name = resultObject.getString("camp_name");
                            String point = resultObject.getString("point");
                            String content = resultObject.getString("content");

                            reviewList_adapter.addItem(new ReviewListItem(profile_Image,
                                    camp_name, nickname, Float.parseFloat(point), image, content));

                        }
                        reviewList_adapter.notifyDataSetChanged();
                        setTotalStarScore(reviewList_adapter.getReviewList());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.MODIFY_USER_INFO:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        Toast.makeText(context, "사용자 정보 수정 완료", Toast.LENGTH_LONG).show();
                        ((Activity) context).finish();
                    } else {
                        Toast.makeText(context, "사용자 정보 수정 실패", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.GET_CHATTINGMESSAGELIST:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultResponse = jsonObject.getString("result");
                    JSONArray resultObjectArray = new JSONArray(resultResponse);
                    ArrayList<ChattingMessageVO> message = new ArrayList<>();
                    SharedPreferences preferences3 = context.getSharedPreferences("preferences", MODE_PRIVATE);
                    if (!resultResponse.equals("fail")) {
                        JSONObject resultObject;
                        for (int i = 0; i < resultObjectArray.length(); i++) {
                            resultObject = resultObjectArray.getJSONObject(i);
                            String from = resultObject.getString("from_id");
                            String to = resultObject.getString("to_id");
                            String text = resultObject.getString("message");
                            String date = resultObject.getString("datetime");
                            ChattingMessageVO chattingMessageVO = new ChattingMessageVO(from, to, text, date);
                            message.add(chattingMessageVO);
                        }

                        Collections.sort(message, new Comparator() {
                            @Override
                            public int compare(Object o, Object t1) {
                                return ((ChattingMessageVO) o).getDatetime().compareTo(((ChattingMessageVO) t1).getDatetime());
                            }
                        });

                        for (ChattingMessageVO chattingMessageVO : message) {
                            if (chattingMessageVO.getFrom_id().equals(preferences3.getString("nickname", ""))) {
                                chatMessage_adapter.add(chattingMessageVO.getMessage(), 1);
                            } else {
                                chatMessage_adapter.add(chattingMessageVO.getMessage(), 0);
                            }
                        }
                        chatMessage_adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "대화한 내용이 없습니다", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.REMOVE_CHATTINGLIST:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        Toast.makeText(context, "삭제 성공하였습니다", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "삭제 실패하였습니다", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.SET_OPPONENT:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        Intent intent = new Intent(context, ChattingMessageActivity.class);
                        intent.putExtra("number", number);
                        intent.putExtra("opponent", other);
                        context.startActivity(intent);
                        Toast.makeText(context, "상대방 설정 완료", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "상대방 설정 실패", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.GET_OPPONENT:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (!real_result.equals("fail")) {
                        JSONObject data = new JSONObject(real_result);
                        if (check) {
                            SharedPreferences preferences4 = context.getSharedPreferences("preferences", MODE_PRIVATE);
                            if (preferences4.getString("nickname", "").equals(data.getString("opponent"))) {
                                Intent intent = new Intent(context, ChattingMessageActivity.class);
                                intent.putExtra("number", number);
                                intent.putExtra("opponent", other);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "현재 대화중인 채팅방입니다", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (data.getString("opponent").equals("")) {
                                Toast.makeText(context, "현재 상대방이 지정되어 있지 않습니다", Toast.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent(context, ChattingMessageActivity.class);
                                intent.putExtra("number", number);
                                intent.putExtra("opponent", data.getString("opponent"));
                                ((Activity) context).startActivity(intent);
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            case Constant.SET_FLAG:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        ((Activity) context).finish();
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constant.SET_CHATTINGFLAG:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String real_result = jsonObject.getString("result");
                    if (real_result.equals("success")) {
                        ((Activity) context).finish();
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        //다이얼로그 종료
        asyncDialog.setCanceledOnTouchOutside(true);
        asyncDialog.dismiss();

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }


    // QR코드 생성
    public void generateQRCode() {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            qrcodeBitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 500, 500));
            intent.putExtra("qrcode", qrcodeBitmap);
            intent.putExtra("reservation_number", contents);
            intent.putExtra("tent_name", tentName);
            intent.putExtra("camp_name", campName);
            intent.putExtra("period", period);
            intent.putExtra("price", price);
            intent.putExtra("quantity", quantity);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    // QR코드 이미지 비트맵으로 변환
    public Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    //사용자 예약 정보 데이터 가져오는 JSON 함수


    // 총 별점 평균 구해서 ratingBar 설정하는 메소드
    public void setTotalStarScore(ArrayList<ReviewListItem> reviewData) {
        float totalStar = 0;

        totalReviewStar = ((Activity) context).findViewById(R.id.review_total_star);
        totalReviewStarScore = ((Activity) context).findViewById(R.id.total_star_score);

        for (ReviewListItem review : reviewData) {
            totalStar += review.getStar();
        }

        totalReviewStar.setRating((float) totalStar / reviewData.size());
        totalReviewStarScore.setText("" + totalReviewStar.getRating());
    }


}





