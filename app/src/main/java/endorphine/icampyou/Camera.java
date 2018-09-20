package endorphine.icampyou;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Circle;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import endorphine.icampyou.ExchangeMenu.Chat_Content;

import static android.app.Activity.RESULT_OK;

public class Camera {

    private Context context;

    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;

    private final int REQUEST_PERMISSION_CODE_CAMERA = 2222;
    private final int REQUEST_PERMISSION_CODE_GALLERY = 2223;

    private Uri photoUri;
    private String currentPhotoPath;
    String mimageCaptureName;
    ImageView m_userPhoto;
    CircleImageView circleImageView;

    private int image_type = -1;


    public Camera(Context _context,ImageView insertImage){
        this.context = _context;
        this.m_userPhoto = insertImage;
        image_type = 0;
    }

    public Camera(Context _context,CircleImageView _circleImageView){
        this.context = _context;
        this.circleImageView = _circleImageView;
        image_type = 1;
    }

    //사진 선택하는 함수
    public void selectPhoto(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(context.getPackageManager()) != null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                } catch (IOException ex){

                }
                if(photoFile != null){
                    photoUri = FileProvider.getUriForFile(context, context.getPackageName() +".provider",photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                    ((Activity)context).startActivityForResult(intent,CAMERA_CODE);
                }
            }
        }
    }

    //이미지 파일 생성하는 함수
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

    //카메라를 찍은 사진 적용하는 함수
    public void getPictureForPhoto(){
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

        if(image_type == 0){
            m_userPhoto.setImageBitmap(rotate(bitmap,exifDegree));
        } else{
            circleImageView.setImageBitmap(rotate(bitmap,exifDegree));
        }

    }

    //갤러리에서 사진 가져오는 함수
    public void selectGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        ((Activity)context).startActivityForResult(intent,GALLERY_CODE);
    }

    //사진 적용하는 함수
    public void sendPicture(Uri imgUri){
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
        if(image_type == 0){
            m_userPhoto.setImageBitmap(rotate(bitmap,exifDegree));
        } else{
            circleImageView.setImageBitmap(rotate(bitmap,exifDegree));
        }
    }

    //사진의 회전 값 가져오는 함수
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

    //사진을 정방향대로 회전하는 함수
    private Bitmap rotate(Bitmap src, float degree){
        //Matrix 객체 생성
        Matrix matrix = new Matrix();
        //회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
    }

    //URI 실제 경로 얻는 함수
    private String getRealPathFromURI(Uri contentUri){
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri,proj,null,null,null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

    //카메라 접근 권한 확인하는 함수
    public void CheckCameraAcess(){
        //권한 보유 여부 확인
        int permissionCheck_Camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (permissionCheck_Camera == PackageManager.PERMISSION_GRANTED) {
            selectPhoto();
        } else {
            ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CODE_CAMERA);
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.CAMERA)) {
                //거부했을 경우
                Toast toast = Toast.makeText(context,
                        "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                //거부했을 경우
                Toast toast = Toast.makeText(context,
                        "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    //앨범 권한 접근 확인하는 함수
    public void CheckAlbumAcess(){
        int permissionCheck_Write = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck_Read = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck_Read == PackageManager.PERMISSION_GRANTED && permissionCheck_Write == PackageManager.PERMISSION_GRANTED) {
            selectGallery();
        } else {
            ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_GALLERY);
            ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_GALLERY);
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //거부했을 경우
                Toast toast = Toast.makeText(context,
                        "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                //거부했을 경우
                Toast toast = Toast.makeText(context,
                        "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    //권한 요청하는 함수
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE_GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //동의 했을 경우
                    selectGallery();
                } else {
                    //거부했을 경우
                    Toast toast = Toast.makeText(context,
                            "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case REQUEST_PERMISSION_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //동의 했을 경우
                    selectPhoto();

                } else {
                    //거부했을 경우
                    Toast toast = Toast.makeText(context,
                            "기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
        }
    }

    //선택한 사진 데이터 처리
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
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
}
