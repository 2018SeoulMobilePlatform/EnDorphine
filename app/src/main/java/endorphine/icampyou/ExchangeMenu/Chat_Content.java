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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import endorphine.icampyou.R;

public class Chat_Content extends AppCompatActivity {

    //데이터 변수 선언
    ImageView m_userPhoto;
    Spinner camp_kind;
    EditText need_thing;
    EditText lettable_thing;

    //예시
    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    private final int REQUEST_PERMISSION_CODE_CAMERA = 2222;
    private final int REQUEST_PERMISSION_CODE_GALLERY = 2223;
    private Uri photoUri;
    private String currentPhotoPath;
    String mimageCaptureName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__content);

        //콤보 박스
        camp_kind = (Spinner) findViewById(R.id.camp_combobox);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.camp_kind, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        camp_kind.setAdapter(adapter);

        //이미지 넣기
        m_userPhoto = (ImageView) findViewById(R.id.user_image);

        //저장 버튼
        Button save_chat_content = (Button) findViewById(R.id.save_chat_content);

        //빌리고싶은, 빌려줄 수 있는 편집 텍스트 설정
        need_thing = (EditText)findViewById(R.id.write_need);
        lettable_thing = (EditText)findViewById(R.id.write_lettable);

        save_chat_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BitmapDrawable bitmapDrawable = (BitmapDrawable)m_userPhoto.getDrawable();
                Bitmap tempBitmap = bitmapDrawable.getBitmap();

                String camp_name = null;

                if(camp_kind.getSelectedItemPosition() != 0 ){
                    camp_name = camp_kind.getSelectedItem().toString();
                }

                if(need_thing.length() ==0 && lettable_thing.length() ==0 || bitmapDrawable == null || camp_name == null){
                   Toast.makeText(Chat_Content.this,"입력이 비어있습니다.",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent returnIntent = new Intent();

//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    tempBitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
//                    byte[] bytes = stream.toByteArray();

                    try {
                        //Write file
                        String filename = "bitmap.png";
                        FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                        tempBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                        //Cleanup
                        stream.close();
                        tempBitmap.recycle();

                        //Pop intent
                        returnIntent.putExtra("image", filename);
                        returnIntent.putExtra("user","허진규");
                        returnIntent.putExtra("need",need_thing.getText().toString());
                        returnIntent.putExtra("lettable",lettable_thing.getText().toString());
                        returnIntent.putExtra("camp_name",camp_name);
                        setResult(RESULT_OK,returnIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //returnIntent.putExtra("image",bytes);

                    finish();

                    Toast.makeText(Chat_Content.this,"저장 완료",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //이미지 넣을 자리랑 버튼 눌를 시 실행하는 이벤트 함수
    public void put_image(View view){
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //사진촬영
                //권한 보유 여부 확인
                int permissionCheck_Camera = ContextCompat.checkSelfPermission(Chat_Content.this,Manifest.permission.CAMERA);
                if(permissionCheck_Camera == PackageManager.PERMISSION_GRANTED){
                    selectPhoto();
                } else{
                    ActivityCompat.requestPermissions(Chat_Content.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CODE_CAMERA);
                    if(ActivityCompat.shouldShowRequestPermissionRationale(Chat_Content.this,Manifest.permission.CAMERA)){
                        //거부했을 경우
                        Toast toast=Toast.makeText(Chat_Content.this,
                                "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        //거부했을 경우
                        Toast toast=Toast.makeText(Chat_Content.this,
                                "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        };
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int permissionCheck_Write = ContextCompat.checkSelfPermission(Chat_Content.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int permissionCheck_Read = ContextCompat.checkSelfPermission(Chat_Content.this,Manifest.permission.READ_EXTERNAL_STORAGE);
                if(permissionCheck_Read == PackageManager.PERMISSION_GRANTED && permissionCheck_Write == PackageManager.PERMISSION_GRANTED){
                    selectGallery();
                } else{
                    ActivityCompat.requestPermissions(Chat_Content.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_GALLERY);
                    ActivityCompat.requestPermissions(Chat_Content.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_GALLERY);
                    if(ActivityCompat.shouldShowRequestPermissionRationale(Chat_Content.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            || ActivityCompat.shouldShowRequestPermissionRationale(Chat_Content.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                        //거부했을 경우
                        Toast toast=Toast.makeText(Chat_Content.this,
                                "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        //거부했을 경우
                        Toast toast=Toast.makeText(Chat_Content.this,
                                "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

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
                .setPositiveButton("사진 촬영",cameraListener)
                .setNeutralButton("앨번 선택",albumListener)
                .setNegativeButton("취소",cancelListener).show();
    }

    //이 엑티비티에서 뒤로가기를 눌렀을 때
    @Override
    public void onBackPressed(){
        setResult(0);
    }

    //권한 요청하기
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE_GALLERY:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //동의 했을 경우
                    selectGallery();
                    Toast toast=Toast.makeText(this,
                            "통과", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                //거부했을 경우
                    Toast toast=Toast.makeText(this,
                            "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case REQUEST_PERMISSION_CODE_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //동의 했을 경우
                    selectPhoto();
                    Toast toast=Toast.makeText(this,
                            "통과", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    //거부했을 경우
                    Toast toast=Toast.makeText(this,
                            "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
        }
    }

    private void selectPhoto(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                } catch (IOException ex){

                }
                if(photoFile != null){
                    Log.e("패키지",getPackageName());
                    photoUri = FileProvider.getUriForFile(this,getPackageName()+".provider",photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                    startActivityForResult(intent,CAMERA_CODE);
                }
            }
        }
    }

    //이미지 파일 생성
    private File createImageFile() throws IOException {
        File dir = new File (Environment.getExternalStorageDirectory()+"/path/");
        if(!dir.exists()){
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mimageCaptureName =timeStamp + ".png";

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/path/"+mimageCaptureName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;
    }

    //카메라를 찍은 사진 적용
    private void getPictureForPhoto(){
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try{
            exif = new ExifInterface(currentPhotoPath);
        } catch (IOException e){
            e.printStackTrace();
        }

        int exifOrientation;
        int exifDegree;

        if(exif != null){
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else{
            exifDegree = 0;
        }
        //냥냥
        m_userPhoto.setImageBitmap(rotate(bitmap,exifDegree));
    }

    //갤러리에서 사진 가져오는 경우
    private void selectGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_CODE);
    }

    //선택한 사진 데이터 처리
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GALLERY_CODE:
                    sendPicture(data.getData());
                    break;
                case CAMERA_CODE:
                    getPictureForPhoto();
                    break;

                    default:
                        break;
            }
        }
    }

    private void sendPicture(Uri imgUri){
        String imagePath = getRealPathFromURI(imgUri);
        ExifInterface exif = null;
        try{
            exif = new ExifInterface(imagePath);
        } catch (IOException e){
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        m_userPhoto.setImageBitmap(rotate(bitmap,exifDegree));
    }

    //사진의 회전 값 가져오기
    private int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        } else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        } else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }

    //사진을 정방향대로 회전하기
    private Bitmap rotate(Bitmap src,float degree){
        //Matrix 객체 생성
        Matrix matrix = new Matrix();
        //회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
    }


    private String getRealPathFromURI(Uri contentUri){
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri,proj,null,null,null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

}
