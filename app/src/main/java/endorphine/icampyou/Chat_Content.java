package endorphine.icampyou;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
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

        Button select_Image = (Button)findViewById(R.id.select_img_btn);
        select_Image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                id_view = v.getId();
                if(v.getId() == R.id.select_img_btn){
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

        });

        Button save_chat_content = (Button) findViewById(R.id.save_chat_content);

        final EditText need_thing = (EditText)findViewById(R.id.write_need);
        final EditText lettable_thing = (EditText)findViewById(R.id.write_lettable);

        save_chat_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Chat_Content.this,"저장 완료",Toast.LENGTH_LONG).show();
                if(need_thing.length() ==0 && lettable_thing.length() ==0){
                   Toast.makeText(Chat_Content.this,"입력이 비어있습니다.",Toast.LENGTH_LONG).show();
                }
                else{

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

    public void takePhoto(){
        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                if(photoFile!=null){
                    Uri providerURI = FileProvider.getUriForFile(Chat_Content.this,"endorphine.icampyou.provider",photoFile);
                    imgUri = providerURI;
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent,FROM_CAMERA);
                }
            }

        }else{
            Log.v("알림", "저장공간에 접근 불가능");
            return;
        }
    }

    public File createImageFile() throws IOException{
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile= null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        if(!storageDir.exists()){
            Log.v("알림","storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }
        Log.v("알림","storageDir 존재함 " + storageDir.toString());
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
        Log.e("냥냥냥","냥냥냥");
        Toast.makeText(this,"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("resutl 값:",String.valueOf(resultCode));

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
                        galleryAddPic();
                        m_userPhoto.setImageURI(photoURI);
                        //cropImage();
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.v("알림","앨범에서 가져오기 에러");
                    }
                }
                break;
            }
            //카메라 촬영
            case FROM_CAMERA : {
                try{
                    Log.v("알림", "FROM_CAMERA 처리");
                    galleryAddPic();
                    m_userPhoto.setImageURI(imgUri);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }

        }

    }

}
