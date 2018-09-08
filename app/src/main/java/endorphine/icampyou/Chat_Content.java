package endorphine.icampyou;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Chat_Content extends AppCompatActivity {

    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;

    private Uri imgUri, photoURI, albumURI;

    private Uri m_imageCaptureUri;
    private ImageView m_userPhoto;
    private int id_view;
    private String absolutePath;

    //add
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__content);

        m_userPhoto = (ImageView) findViewById(R.id.user_image);

        Button save_chat_content = (Button) findViewById(R.id.save_chat_content);

        final EditText need_thing = (EditText)findViewById(R.id.write_need);
        final EditText lettable_thing = (EditText)findViewById(R.id.write_lettable);

        save_chat_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(need_thing.length() ==0 && lettable_thing.length() ==0){
                   Toast.makeText(Chat_Content.this,"입력이 비어있습니다.",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent returnIntent = new Intent();

                    BitmapDrawable bitmapDrawable = (BitmapDrawable)m_userPhoto.getDrawable();
                    Bitmap tempBitmap = bitmapDrawable.getBitmap();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    tempBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                    byte[] bytes = stream.toByteArray();

                    returnIntent.putExtra("image",bytes);
                    returnIntent.putExtra("user","허진규");
                    returnIntent.putExtra("need",need_thing.getText().toString());
                    returnIntent.putExtra("lettable",lettable_thing.getText().toString());
                    setResult(1,returnIntent);
                    finish();
                    Toast.makeText(Chat_Content.this,"저장 완료",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void selectAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent,FROM_ALBUM);
    }

    public void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //사진인테트 호출
        startActivityForResult(takePictureIntent,FROM_CAMERA);

//        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (photoFile != null) {
//                Uri providerURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile); //프로바이드 생성
//                //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI); //사진저장경로 불러옴
//
//                this.startActivityForResult(takePictureIntent, FROM_CAMERA); //엑티비티 사진저장
//            } else {
//                Toast.makeText(this, "저장공간이 접근 불가능한 기기입니다.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }


//        String state = Environment.getExternalStorageState();
//
//        if(Environment.MEDIA_MOUNTED.equals(state)){
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if(intent.resolveActivity(getPackageManager())!=null){
//                File photoFile = null;
//                try{
//                    photoFile = createImageFile();
//                }
//                catch (IOException e){
//                    e.printStackTrace();
//                }
//                if(photoFile!=null){
//                    Uri providerURI = FileProvider.getUriForFile(this,"endorphine.icampyou.provider",photoFile);
//                    //imgUri = providerURI;
//                    //intent.putExtra(MediaStore.EXTRA_OUTPUT,providerURI);
//                    //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                    startActivityForResult(intent,FROM_CAMERA);
//                }
//            }
//        }else{
//            Log.e("알림", "저장공간에 접근 불가능");
//            return;
//        }
    }

    public File createImageFile() throws IOException{
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile= null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        if(!storageDir.exists()){
            Log.e("알림","storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }
        Log.e("알림","storageDir 존재함 " + storageDir.toString());
        imageFile = new File(storageDir,imgFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    public void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this,"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("resultCode",resultCode+"");
        Log.e("requsetCode",requestCode+"");
        Log.e("data",data+"");

        if(resultCode != RESULT_OK){
            return;
        }

        switch (requestCode){
            case FROM_ALBUM : {
                //앨범에서 가져오기
                if(data.getData()!=null){
                    try{
                        File albumFile = null;
                        albumFile = createImageFile();
                        photoURI = data.getData();
                        albumURI = Uri.fromFile(albumFile);
                        m_userPhoto.setImageURI(photoURI);
                        //cropImage();
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("알림","앨범에서 가져오기 에러");
                    }
                }
                break;
            }
            //카메라 촬영
            case FROM_CAMERA : {
                try{
                    Log.e("알림", "FROM_CAMERA 처리");
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    m_userPhoto.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    //이미지 넣을 자리랑 버튼 눌를 시 실행하는 이벤트 함수
    public void put_image(View view){
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takePhoto();
            }
        };
        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectAlbum();
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

}
