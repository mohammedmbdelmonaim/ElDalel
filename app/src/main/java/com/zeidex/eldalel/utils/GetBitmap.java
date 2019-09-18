package com.zeidex.eldalel.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.IOException;

public class GetBitmap {
    public Bitmap selectedImage = null;

    public void getBitmap(Context context, ImageView view, Bitmap selectedImage) {
        this.selectedImage = selectedImage;

        // original measurements
        int origWidth = selectedImage.getWidth();
        int origHeight = selectedImage.getHeight();
        final int destWidth = view.getWidth();//or the width you need
        final int destHeight = view.getHeight();//or the width you need
        float scaleWidth = ((float) destWidth) / origWidth;
        float scaleHeight = ((float) destHeight) / origHeight;
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(selectedImage, 0, 0, origWidth, origHeight, matrix, false);
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        newBitmap.compress(Bitmap.CompressFormat.JPEG, 10, bos);
//        bos.toByteArray();
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        options.inSampleSize = 3;
//
//        BitmapFactory.decodeResource(getResources(), R.mipmap.hqimage, options);
        view.setImageBitmap(newBitmap);
    }

    public void getBitmap(Context context, ImageView view, Uri photoUri) {

        try {
            selectedImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        int origWidth = selectedImage.getWidth();
//        int origHeight = selectedImage.getHeight();
//        final int destWidth = view.getWidth();//or the width you need
//        final int destHeight = view.getHeight();//or the width you need
//        float scaleWidth = ((float) destWidth) / origWidth;
//        float scaleHeight = ((float) destHeight) / origHeight;
//        Matrix matrix = new Matrix();
//        // RESIZE THE BIT MAP
//        matrix.postScale(scaleWidth, scaleHeight);
//        Bitmap newBitmap = Bitmap.createBitmap(selectedImage, 0, 0, origWidth, origHeight, matrix, false);
//        view.setImageBitmap(newBitmap);
//         original measurements
        int origWidth = selectedImage.getWidth();
        int origHeight = selectedImage.getHeight();
        final int destWidth = 50;//or the width you need
        final int destHeight = 50;//or the width you need
        if (origWidth > destWidth) {
            // picture is wider than we want it, we calculate its target height
//                int destHeight = origHeight / (origWidth / destWidth);
            // we create an scaled bitmap so it reduces the image, not just trim it
            Bitmap b2 = Bitmap.createScaledBitmap(selectedImage, destWidth, destHeight, false);
//            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            // compress to the format you want, JPEG, PNG...
            // 70 is the 0-100 quality percentage
//            b2.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            // Load the selected image into a preview
            view.setImageBitmap(b2);

        } else {
            // Load the selected image into a preview
            view.setImageBitmap(selectedImage);
        }
    }
}
