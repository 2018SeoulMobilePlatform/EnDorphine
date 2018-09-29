package endorphine.icampyou;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageConversion {

    public ImageConversion(){}

    //비트맵 인코딩하기
    public String toBase64(ImageView imageView){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap tempBitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        tempBitmap.compress(Bitmap.CompressFormat.JPEG,70,bos);
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
