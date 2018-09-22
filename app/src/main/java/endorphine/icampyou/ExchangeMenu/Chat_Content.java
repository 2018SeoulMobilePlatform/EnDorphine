package endorphine.icampyou.ExchangeMenu;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import endorphine.icampyou.Camera;
import endorphine.icampyou.Constant;
import endorphine.icampyou.Login.RegisterUserActivity;
import endorphine.icampyou.NetworkTask;
import endorphine.icampyou.R;

public class Chat_Content extends AppCompatActivity {

    //데이터 변수 선언
    ImageView m_userPhoto;
    Spinner camp_kind;
    EditText need_thing;
    EditText lettable_thing;

    //예시

    String imageName;

    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__content);

        //이미지 넣기
        m_userPhoto = (ImageView) findViewById(R.id.user_image);

        //카메라 클래스 객체 생성
        camera = new Camera(this,m_userPhoto);

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
                BitmapDrawable bitmapDrawable = (BitmapDrawable) m_userPhoto.getDrawable();
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

                    Log.e("2","2");
                    NetworkTask networkTask = new NetworkTask(Chat_Content.this,url,data, Constant.MAKE_CHATTINGLIST);
                    networkTask.execute();

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
                .setNeutralButton("앨번 선택", albumListener)
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
        imageName = camera.getImageName();
    }

    //POST 요청 JSON 데이터 형식 사용
    private JSONObject sendJSonData()  {

        JSONObject jsonObject = new JSONObject();

        String encodedImage = toBase64(m_userPhoto);

        Log.e("이미지",encodedImage);

        try {
            jsonObject.accumulate("image", encodedImage);
            jsonObject.accumulate("user_id", "허진규멍청이");
            jsonObject.accumulate("myitem", need_thing.getText().toString());
            jsonObject.accumulate("needitem", lettable_thing.getText().toString());
            jsonObject.accumulate("camp_name", camp_kind.getSelectedItem().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    //비트맵 인코딩하기
    public String toBase64(ImageView imageView){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap tempBitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        tempBitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] data = bos.toByteArray();
        String encodedImage = Base64.encodeToString(data, Base64.DEFAULT);
        return encodedImage;
    }

    //비트맵 디코딩하기
    public Bitmap fromBase64 (String encodedImage) {
        byte[] decodedByte = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
