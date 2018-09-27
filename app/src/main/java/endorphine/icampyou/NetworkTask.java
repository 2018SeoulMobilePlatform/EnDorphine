package endorphine.icampyou;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
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

import java.util.HashSet;
import java.util.Set;

import endorphine.icampyou.ExchangeMenu.ChatList_Adapter;
import endorphine.icampyou.ExchangeMenu.Chat_Item;
import endorphine.icampyou.GuideMenu.Reservation.ConfirmPopupActivity;
import endorphine.icampyou.GuideMenu.GuideActivity;
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
    ArrayList<ReviewListItem> reviewData;
    String campingPlace;
    ArrayList<Chat_Item> copy;
    RatingBar totalReviewStar;
    TextView totalReviewStarScore;
    ImageView drawerQrCode;

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

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, ChatList_Adapter _adapter,ArrayList<Chat_Item> copy,ChatList_Adapter _adpater2) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        this.chatList_adpater = _adapter;
        this.copy = copy;
        asyncDialog = new ProgressDialog(_context);
        this.chatList_adapter2 = _adpater2;
    }

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, ReviewListViewAdapter _adapter, String _campingPlace) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.reviewList_adapter = _adapter;
        this.campingPlace = _campingPlace;
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

    public NetworkTask(Context _context, String url, JSONObject data, int ACTION, ImageView drawerQrCode) {
        this.context = _context;
        this.url = url;
        this.data = data;
        this.select = ACTION;
        asyncDialog = new ProgressDialog(_context);
        this.drawerQrCode = drawerQrCode;
        this.exception = new RegisterUserException();
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
            default:
                break;

        }

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

                        editor.commit();

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

                    Set<String> reservationNum_Set = new HashSet<String>();
                    Set<String> campingPlace_Set = new HashSet<String>();
                    Set<String> date_Set = new HashSet<String>();
                    Set<String> tentType_Set = new HashSet<String>();
                    Set<String> tentNum_Set = new HashSet<String>();
                    Set<String> price_Set = new HashSet<String>();

                    if (!resultResponse.equals("fail")) {
                        JSONObject resultObject;
                        for(int i=0;i<resultObjectArray.length();i++){
                            resultObject = resultObjectArray.getJSONObject(i);
                            reservationNum_Set.add(resultObject.getString("reservation_number"));
                            campingPlace_Set.add(resultObject.getString("camp_name"));
                            date_Set.add(resultObject.getString("date"));
                            tentType_Set.add(resultObject.getString("tent_type"));
                            tentNum_Set.add(resultObject.getString("count"));
                            price_Set.add(resultObject.getString("price"));
                            if(i ==0 ) {
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

                        editor.putStringSet("reservationNum", reservationNum_Set);
                        editor.putStringSet("campingPlace", campingPlace_Set);
                        editor.putStringSet("date", date_Set);
                        editor.putStringSet("tentType", tentType_Set);
                        editor.putStringSet("tentNum", tentNum_Set);
                        editor.putStringSet("price", price_Set);

                        editor.commit();

                    }
                } catch (JSONException e) {
                    Log.e("exception", e.toString());
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
                        Log.e("success", "성공");
                        Toast.makeText(context, "채팅방 개설", Toast.LENGTH_LONG).show();
                        ((Activity) context).finish();
                    } else {
                        Log.e("실패", "실패");
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
                            String user_id = resultObject.getString("user_id");
                            String camp_name = resultObject.getString("camp_name");
                            String myitem = resultObject.getString("myitem");
                            String needitem = resultObject.getString("needitem");
                            Chat_Item item = new Chat_Item(image, user_id, myitem, needitem, camp_name);
                            chatList_adpater.addItem(item);
                            copy.add(item);

                            if(user_id.equals(preferences1.getString("nickname",""))){
                                chatList_adapter2.addItem(item);
                            }
                        }
                        chatList_adpater.notifyDataSetChanged();
                        chatList_adapter2.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.e("exception", e.toString());
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
                        Log.e("success", "성공");
                        intent = new Intent(context, GuideActivity.class);
                        intent.putExtra("캠핑장 이름", data.getString("camp_name"));
                        ((Activity) context).startActivity(intent);
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
                            Bitmap image = imageConversion.fromBase64(resultObject.getString("image"));
                            String nickname = resultObject.getString("nickname");
                            String camp_name = resultObject.getString("camp_name");
                            String point = resultObject.getString("point");
                            String content = resultObject.getString("content");
                            if (camp_name.equals(campingPlace)) {
                                reviewList_adapter.addItem(new ReviewListItem(imageConversion.fromBase64(preferences2.getString("profileImage","")),
                                        camp_name, nickname, Float.parseFloat(point), image, content));
                            }
                        }
                        reviewList_adapter.notifyDataSetChanged();
                        setTotalStarScore(reviewList_adapter.getReviewList());
                    }
                } catch (JSONException e) {
                    Log.e("exception", e.toString());
                    e.printStackTrace();
                }
                break;
            case Constant.MODIFY_USER_INFO:
                try {
                    Log.e("result",result);
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
            default:
                break;
    }

        //다이얼로그 종료
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

        totalReviewStar = ((Activity)context).findViewById(R.id.review_total_star);
        totalReviewStarScore = ((Activity)context).findViewById(R.id.total_star_score);

        for (ReviewListItem review : reviewData) {
            totalStar += review.getStar();
        }

        totalReviewStar.setRating((float) totalStar / reviewData.size());
        totalReviewStarScore.setText("" + totalReviewStar.getRating());
    }

}





