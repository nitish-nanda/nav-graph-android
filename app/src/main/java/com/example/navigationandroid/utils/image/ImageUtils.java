package com.example.navigationandroid.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.navigationandroid.utils.Utility;

import java.io.File;
import java.io.FileOutputStream;


public class ImageUtils {

    public static String getAccurateImage(Context context, Uri imageUri) {
        try {

            Bitmap myBitmap = CropImageActivity.decodeSampledBitmap(ImageFilePath.getPath(context, imageUri), 1000, 1000);
//                    BitmapFactory.decodeFile(ImageFilePath.getPath(context, imageUri));
            int rotate = Utility.getCameraPhotoOrientation(ImageFilePath.getPath(context, imageUri));
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
            return saveAccurateImage(myBitmap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String saveCropedImage(Bitmap b) {
        int height = b.getHeight();
        int width = b.getWidth();
        int size = b.getByteCount();

        Log.e("original values ", " height " + height + " width " + width + " size in byte " + size + " " + size / 1024);
        int desiredHeight = 1000;
        int desiredWidth = 1000;
        if (height > desiredHeight) {
            b = Bitmap.createScaledBitmap(b, desiredWidth, desiredHeight, true);
        }
        Log.e("scaled values ", " height " + height + " width " + width + " size in byte " + size + " " + size / 1024);
        return ImageUtils.saveImageToExternalStorage(b);
    }

    public static String saveAccurateImage(Bitmap b) {
        int height = b.getHeight();
        int width = b.getWidth();
        int size = b.getByteCount();

        Log.e("original values ", " height " + height + " width " + width + " size in byte " + size + " " + size / 1024);

        b = Bitmap.createScaledBitmap(b, width, height, true);
        Log.e("scaled values ", " height " + height + " width " + width + " size in byte " + size + " " + size / 1024);
        return ImageUtils.saveImageToExternalStorage(b);
    }

    public static String saveImageToExternalStorage(Bitmap bitmap) {

        String ImageURi = null;
        String imageFileName = "imgUrl" + System.currentTimeMillis() + ".png";
        try {
            File dir = new File(Environment.getExternalStorageDirectory(), "pharmacyConnect");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File imageFile = new File(dir, imageFileName);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 30;

            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);

            outputStream.flush();
            outputStream.close();
            ImageURi = imageFile.getAbsolutePath();
            Log.e("File Path: ", " " + imageFile.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ImageURi;
    }

    public static File saveImageAsTemp(Bitmap bitmap) {
        String state = Environment.getExternalStorageState();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TodayFeelsLikeImages");
        File image = null;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            image = new File(file + File.separator + "temp.png");
        }
        return image;
    }

}
