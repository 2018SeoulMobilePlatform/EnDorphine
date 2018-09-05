package endorphine.icampyou;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Chat_Content extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri m_imageCaptureUri;
    private ImageView m_userPhoto;
    private int id_view;
    private String absolutePath;

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
                            doTakePhotoAction();
                        }
                    };
                    DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doTakeAlbumAction();
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
        save_chat_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Chat_Content.this,"저장 완료",Toast.LENGTH_LONG).show();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.commit();
            }
        });
    }

    //파일 존재하는지 확인
    private boolean Is_Exists_File(File file){
        if(file.exists()){
            return true;
        }
        else{
            return false;
        }
    }

    //카메라에서 사진 촬영
    public void doTakePhotoAction(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";

        m_imageCaptureUri = FileProvider.getUriForFile(this,
                "endorphine.icampyou.provider",new File(Environment.getExternalStorageDirectory(),url));

//        if(!Is_Exists_File(new File(Environment.getExternalStorageDirectory(),url))){
//            Toast.makeText(Chat_Content.this,"안녕하세용",Toast.LENGTH_LONG).show();
//            return ;
//        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT,m_imageCaptureUri);
        startActivityForResult(intent,PICK_FROM_CAMERA);
    }

    //앨범에서 이미지 가져오기
    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != RESULT_OK){
            return ;
        }

        switch(requestCode){
            case PICK_FROM_ALBUM:
            {
                m_imageCaptureUri = data.getData();
                Log.e("SmartWheel",m_imageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA:
            {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(m_imageCaptureUri,"image/*");

                intent.putExtra("outputX",100);
                intent.putExtra("outputY",100);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent,CROP_FROM_IMAGE);
                break;
            }

            case CROP_FROM_IMAGE:
            {
                if(resultCode != RESULT_OK){
                    return ;
                }

                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SmartWheel/"+System.currentTimeMillis()+".jpg";

                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    m_userPhoto.setImageBitmap(photo);

                    storeCropImage(photo,filePath);
                    absolutePath = filePath;
                    break;
                }

                File file = new File(m_imageCaptureUri.getPath());
                if(file.exists()){
                    file.delete();
                }
            }
        }
    }

    private void storeCropImage(Bitmap bitmap,String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel =  new File(dirPath);
        if(!directory_SmartWheel.exists()){
            directory_SmartWheel.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(copyFile)));

            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
