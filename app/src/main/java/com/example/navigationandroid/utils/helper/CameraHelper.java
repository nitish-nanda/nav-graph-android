package com.example.navigationandroid.utils.helper;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;

import com.example.navigationandroid.utils.Constants;
import com.example.navigationandroid.utils.Utility;
import com.example.navigationandroid.utils.image.CropImageActivity;
import com.example.navigationandroid.utils.image.ImageFilePath;
import com.example.navigationandroid.utils.image.ImageUtils;

import static android.app.Activity.RESULT_OK;


public class CameraHelper {

    private static final int REQUEST_CAMERA = 0, SELECT_FILE = 1, REQUEST_CROP = 2;

    private Dialog dialog = null;
    private Uri temUri;
    private String imagePath;

    private final Context context;
    private final CameraHelperCallback callback;

    private boolean isCropping = true;
    private boolean is32Ratio = false;

    public CameraHelper(Context context, CameraHelperCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public interface CameraHelperCallback {

        void startIntent(Intent intent, int requestCode);

        void onSuccessImage(String imagePath);

        void onErrorImage(String error);
    }

    // for no cropping image
    public void setCropping(boolean isCropping) {
        this.isCropping = isCropping;
    }

    // for image crop 3:2 ratio
    public void set32Ratio(boolean is32Ratio) {
        this.is32Ratio = is32Ratio;
    }

    public void initCameraDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Profile Picture");
        boolean hasPermission = Utility.checkPermission(context);
        builder.setPositiveButton("From Camera", (dialog, which) -> {
            if (hasPermission)
                cameraIntent();
            dialog.dismiss();
        });
        builder.setNegativeButton("From Gallery", (dialog, which) -> {
            if (hasPermission)
                galleryIntent();
            dialog.dismiss();
        });
        builder.setNeutralButton("Cancel", ((dialog1, which) ->
                dialog1.cancel())
        );
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        callback.startIntent(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "temp");
        temUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, temUri);
        callback.startIntent(intentPicture, REQUEST_CAMERA);
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_FILE:
                    if (isCropping)
                        onSelectFromGalleryResult(data);
                    else {
                        final Uri imageUri = data.getData();
                        assert imageUri != null;
                        String accuratePath = ImageUtils.getAccurateImage(context, imageUri);
                        if (accuratePath != null)
                            imagePath = accuratePath;
                        else
                            imagePath = ImageFilePath.getPath(context, imageUri);

                        callback.onSuccessImage(imagePath);
                    }
                    break;
                case REQUEST_CAMERA:
                    if (isCropping)
                        onCaptureImageResult();
                    else {
                        String accuratePath1 = ImageUtils.getAccurateImage(context, temUri);
                        if (accuratePath1 != null)
                            imagePath = accuratePath1;
                        else
                            imagePath = ImageFilePath.getPath(context, temUri);

                        callback.onSuccessImage(imagePath);
                    }
                    break;
                case REQUEST_CROP:
                    imagePath = data.getStringExtra(Constants.IMAGE_PATH);
                    callback.onSuccessImage(imagePath);
                    break;
            }
        } else {
            if (dialog != null) {
                dialog.hide();
                dialog = null;
            }
        }
    }

    private void onCaptureImageResult() {
        imagePath = ImageFilePath.getPath(context, temUri);
        Intent intent = new Intent(context, CropImageActivity.class);
        intent.putExtra(Constants.IMAGE_PATH, imagePath);
        if (is32Ratio) {
            intent.putExtra("UniqId", "fromAddPost");
        }
        callback.startIntent(intent, REQUEST_CROP);
    }

    private void onSelectFromGalleryResult(Intent data) {
        final Uri imageUri = data.getData();
        assert imageUri != null;
        imagePath = ImageFilePath.getPath(context, imageUri);

        Intent intent = new Intent(context, CropImageActivity.class);
        intent.putExtra(Constants.IMAGE_PATH, imagePath);
        if (is32Ratio) {
            intent.putExtra("UniqId", "fromAddPost");
        }
        callback.startIntent(intent, REQUEST_CROP);
    }

}
