package com.example.navigationandroid.utils.image;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.navigationandroid.R;
import com.example.navigationandroid.utils.Constants;
import com.example.navigationandroid.utils.Utility;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropImageActivity extends AppCompatActivity {

    public CropImageView cropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        initFields();
    }

    private void initFields() {
        cropImageView = findViewById(R.id.imageViewCrop);
        String imagePath = getIntent().getStringExtra(Constants.IMAGE_PATH);
        if (imagePath == null) {
            return;
        }
        int rotate = Utility.getCameraPhotoOrientation(imagePath);
        Bitmap bitmap = decodeSampledBitmap(imagePath, 1000, 1000);
        cropImageView.setImageBitmap(bitmap);
        cropImageView.rotateImage(rotate);
        String fromAddPost = getIntent().getStringExtra(Constants.FROM);
        if (fromAddPost != null && fromAddPost.equals("fromAddPost"))
            cropImageView.setAspectRatio(3, 2);
        else
            cropImageView.setAspectRatio(1, 1);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    public void onClickCancel(View v) {
        onBackPressed();
    }

    public void onClickDone(View v) {
        Bitmap croppedImage = cropImageView.getCroppedImage();
        if (croppedImage != null) {
            String imagePath = saveCropedImage(croppedImage);

            Intent intent = new Intent();
            intent.putExtra(Constants.IMAGE_PATH, imagePath);
            setResult(RESULT_OK, intent);
            finish();
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

    public static Bitmap decodeSampledBitmap(String imagePath, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds = true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        Log.d("Bitmap size", imagePath + " -- height" + String.valueOf(options.outHeight) + " -- width" + String.valueOf(options.outWidth));
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
