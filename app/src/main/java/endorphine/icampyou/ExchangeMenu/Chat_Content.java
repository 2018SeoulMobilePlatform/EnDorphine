package endorphine.icampyou.ExchangeMenu;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;
import endorphine.icampyou.Camera;
import endorphine.icampyou.Constant;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.ImageConversion;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

public class Chat_Content extends AppCompatActivity {

    //데이터 변수 선언
    ImageView need_Photo;
    Spinner camp_kind;
    EditText need_thing;
    EditText lettable_thing;

    Camera camera;

    ImageConversion imageConversion;

    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__content);

        preferences = getSharedPreferences("preferences",MODE_PRIVATE);

        //이미지 넣기
        need_Photo = (ImageView) findViewById(R.id.user_image);

        GlideApp.with(this).load(R.drawable.image).into(need_Photo);

        //카메라 클래스 객체 생성
        camera = new Camera(this,need_Photo);

        imageConversion = new ImageConversion();

        //콤보 박스
        camp_kind = (Spinner) findViewById(R.id.camp_combobox);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.camp_kind, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        camp_kind.setAdapter(adapter);

        //저장 버튼
        Button save_chat_content = (Button) findViewById(R.id.save_chat_content);

        //빌리고싶은, 빌려줄 수 있는 편집 텍스트 설정
        need_thing = (EditText) findViewById(R.id.write_need);
        lettable_thing = (EditText) findViewById(R.id.write_lettable);

        save_chat_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) need_Photo.getDrawable();
                Bitmap tempBitmap = bitmapDrawable.getBitmap();

                String camp_name = null;

                if (camp_kind.getSelectedItemPosition() != 0) {
                    camp_name = camp_kind.getSelectedItem().toString();
                }

                if (need_thing.length() == 0) {
                    Toast.makeText(Chat_Content.this, "필요한 물품을 적어주세요", Toast.LENGTH_LONG).show();
                } else if (camp_name == null) {
                    Toast.makeText(Chat_Content.this, "캠핑장 종류를 선택해 주세요", Toast.LENGTH_LONG).show();
                } else {
                    String url = "http://ec2-18-188-238-220.us-east-2.compute.amazonaws.com:8000/addchatroom";

                    JSONObject data = sendJSonData();

                    NetworkTask networkTask = new NetworkTask(Chat_Content.this,url,data, Constant.MAKE_CHATTINGLIST);
                    networkTask.execute();

                    setResult(1);

                    finish();
                }
            }
        });
    }

    //이미지 넣을 자리랑 버튼 눌를 시 실행하는 이벤트 함수
    public void put_image(View view) {
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //권한 보유 여부 확인
                camera.CheckCameraAcess();
            }
        };
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                camera.CheckAlbumAcess();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(Chat_Content.this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("사진 촬영", cameraListener)
                .setNeutralButton("앨범 선택", albumListener)
                .setNegativeButton("취소", cancelListener).show();
    }

    //이 엑티비티에서 뒤로가기를 눌렀을 때
    @Override
    public void onBackPressed() {
        setResult(0);
        finish();
    }

    //권한 요청하기
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.REQUEST_PERMISSION_CODE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //동의 했을 경우
                    camera.selectGallery();
                } else {
                    //거부했을 경우
                    Toast toast = Toast.makeText(this,
                            "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case Constant.REQUEST_PERMISSION_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //동의 했을 경우
                    camera.selectPhoto();

                } else {
                    //거부했을 경우
                    Toast toast = Toast.makeText(this,
                            "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
        }
    }

    //선택한 사진 데이터 처리
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.GALLERY_CODE:
                    camera.sendPicture(data.getData());
                    break;
                case Constant.CAMERA_CODE:
                    camera.getPictureForPhoto();
                    break;

                default:
                    break;
            }
        }
    }

    //POST 요청 JSON 데이터 형식 사용
    private JSONObject sendJSonData()  {

        JSONObject jsonObject = new JSONObject();

        String encodedImage = imageConversion.toBase64(need_Photo);

        Random random = new Random();
        int reservationNum = random.nextInt();
        if(reservationNum < 0)
            reservationNum = reservationNum * (-1);

        try {
            jsonObject.accumulate("number",String.valueOf(reservationNum));
            jsonObject.accumulate("image", encodedImage);
            jsonObject.accumulate("user_id", preferences.getString("email",""));
            jsonObject.accumulate("nickname",preferences.getString("nickname",""));
            jsonObject.accumulate("myitem", need_thing.getText().toString());
            jsonObject.accumulate("needitem", lettable_thing.getText().toString());
            jsonObject.accumulate("camp_name", camp_kind.getSelectedItem().toString());
            jsonObject.accumulate("flag","0");
            jsonObject.accumulate("opponent","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
