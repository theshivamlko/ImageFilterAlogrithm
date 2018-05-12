package com.navoki.imagefilters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Shivam on 5/11/2018.
 */
public class ImageFilters {

    private final static int HIGHEST_COLOR_VALUE = 255;
    private final static int LOWEST_COLOR_VALUE = 0;


    /**
     * Apply Grey Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    public static Bitmap setGreyFilter(Bitmap oldBitmap) {

        // copying to newBitmap for manipulation
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // height and width of Image
        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        Log.e("Image Size", "Height=" + imageHeight + " Width=" + imageWidth);


        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {

            for (int j = 0; j < imageHeight; j++) {

                // getting each pixel
                int oldPixel = oldBitmap.getPixel(i, j);

                // each pixel is made from RED_BLUE_GREEN_ALPHA
                // so, getting current values of pixel
                int oldRed = Color.red(oldPixel);
                int oldBlue = Color.blue(oldPixel);
                int oldGreen = Color.green(oldPixel);
                int oldAlpha = Color.alpha(oldPixel);


                // Algorithm for getting new values after calculation of filter
                // Algorithm for GREY FILTER, by intensity of each pixel
                int intensity = (oldRed + oldBlue + oldGreen) / 3;
                int newRed = intensity;
                int newBlue = intensity;
                int newGreen = intensity;


                // applying new pixel values to newBitmap
                int newPixel = Color.argb(oldAlpha, newRed, newGreen, newBlue);
                newBitmap.setPixel(i, j, newPixel);

            }
        }

        return newBitmap;
    }


    /**
     * Apply Negative Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    public static Bitmap setNegativeFilter(Bitmap oldBitmap) {

        // copying to newBitmap for manipulation
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // height and width of Image
        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        Log.e("Image Size", "Height=" + imageHeight + " Width=" + imageWidth);

        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {

            for (int j = 0; j < imageHeight; j++) {

                // getting each pixel
                int oldPixel = oldBitmap.getPixel(i, j);


                // each pixel is made from RED_BLUE_GREEN_ALPHA
                // so, getting current values of pixel
                int oldRed = Color.red(oldPixel);
                int oldBlue = Color.blue(oldPixel);
                int oldGreen = Color.green(oldPixel);


                // Algorithm for getting new values after calculation of filter
                // Algorithm for NEGATIVE FILTER
                int newRed = HIGHEST_COLOR_VALUE - oldRed;
                int newBlue = HIGHEST_COLOR_VALUE - oldBlue;
                int newGreen = HIGHEST_COLOR_VALUE - oldGreen;


                // applying new pixel value to newBitmap
                int newPixel = Color.rgb(newRed, newGreen, newBlue);
                newBitmap.setPixel(i, j, newPixel);
            }
        }

        return newBitmap;
    }


    /**
     * Apply Sepia Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    public static Bitmap setSepiaFilter(Bitmap oldBitmap) {

        // copying to newBitmap for manipulation
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // height and width of Image
        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        Log.e("Image Size", "Height=" + imageHeight + " Width=" + imageWidth);

        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {

                // operating on each pixel
                int oldPixel = oldBitmap.getPixel(i, j);

                // each pixel is made from RED_BLUE_GREEN_ALPHA
                // so, getting current values of pixel
                int oldRed = Color.red(oldPixel);
                int oldBlue = Color.blue(oldPixel);
                int oldGreen = Color.green(oldPixel);
                int oldAlpha = Color.alpha(oldPixel);


                // Algorithm for getting new values after calculation of filter
                // Algorithm for SEPIA FILTER
                int newRed = (int) (0.393 * oldRed + 0.769 * oldGreen + 0.189 * oldBlue);
                int newGreen = (int) (0.349 * oldRed + 0.686 * oldGreen + 0.168 * oldBlue);
                int newBlue = (int) (0.272 * oldRed + 0.534 * oldGreen + 0.131 * oldBlue);

                // if value is > HIGHEST_COLOR_VALUE then set value HIGHEST_COLOR_VALUE
                newRed = newRed > HIGHEST_COLOR_VALUE ? HIGHEST_COLOR_VALUE : newRed;
                newGreen = newGreen > HIGHEST_COLOR_VALUE ? HIGHEST_COLOR_VALUE : newGreen;
                newBlue = newBlue > HIGHEST_COLOR_VALUE ? HIGHEST_COLOR_VALUE : newBlue;

                // applying new pixel value to newBitmap
                int newPixel = Color.argb(oldAlpha, newRed, newGreen, newBlue);
                newBitmap.setPixel(i, j, newPixel);
            }
        }

        return newBitmap;
    }


    /**
     * Apply Green Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    public static Bitmap setGreenFilter(Bitmap oldBitmap) {

        // copying to newBitmap for manipulation
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // height and width of Image
        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        Log.e("Image Size", "Height=" + imageHeight + " Width=" + imageWidth);

        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {

                // getting each pixel
                int oldPixel = oldBitmap.getPixel(i, j);

                // each pixel is made from RED_BLUE_GREEN_ALPHA
                // so, getting current values of pixel
                int oldGreen = Color.green(oldPixel);
                int oldAlpha = Color.alpha(oldPixel);

                // Algorithm for getting new values after calculation of filter
                // Algorithm for GREEN FILTER, by intensity of each pixel
                // set only green value others 0
                int newRed = 0;
                int newGreen = oldGreen;
                int newBlue = 0;

                // applying new pixel value to newBitmap
                int newPixel = Color.argb(oldAlpha, newRed, newGreen, newBlue);
                newBitmap.setPixel(i, j, newPixel);
            }
        }

        return newBitmap;
    }


    /**
     * Apply Grey Bars Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    public static Bitmap setGreyOutBarsFilter(Bitmap oldBitmap) {

        // copying to newBitmap for manipulation
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // height and width of Image
        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        Log.e("Image Size", "Height=" + imageHeight + " Width=" + imageWidth);

        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {

                // getting each pixel
                int oldPixel = oldBitmap.getPixel(i, j);

                // each pixel is made from RED_BLUE_GREEN_ALPHA
                // so, getting current values of pixel
                int oldRed = Color.red(oldPixel);
                int oldBlue = Color.blue(oldPixel);
                int oldGreen = Color.green(oldPixel);
                int oldAlpha = Color.alpha(oldPixel);


                // Algorithm for getting new values after calculation of filter
                // Algorithm for GREY FILTER, by intensity of each pixel
                int intensity = (oldRed + oldBlue + oldGreen) / 3;
                int newRed = intensity;
                int newBlue = intensity;
                int newGreen = intensity;
                int newPixel = 0;

                // condition for bars ,setting GREY values to particular pixel comes in this range only
                if (i <= imageWidth / 3 || i >= (imageWidth - imageWidth / 3)) {
                    // apply grey
                    newPixel = Color.argb(oldAlpha, newRed, newGreen, newBlue);

                } else {
                    //  don't apply grey
                    newPixel = oldPixel;
                }

                newBitmap.setPixel(i, j, newPixel);
            }
        }

        return newBitmap;
    }


    /**
     * Apply Sepia Bars Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    public static Bitmap setSepiaOutBarsFilter(Bitmap oldBitmap) {

        // copying to newBitmap for manipulation
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // height and width of Image
        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        Log.e("Image Size", "Height=" + imageHeight + " Width=" + imageWidth);

        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {

                // getting each pixel
                int oldPixel = oldBitmap.getPixel(i, j);

                // each pixel is made from RED_BLUE_GREEN_ALPHA
                // so, getting current values of pixel
                int oldRed = Color.red(oldPixel);
                int oldBlue = Color.blue(oldPixel);
                int oldGreen = Color.green(oldPixel);
                int oldAlpha = Color.alpha(oldPixel);


                // Algorithm for getting new values after calculation of filter
                // Algorithm for SEPIA FILTER
                int newRed = (int) (0.393 * oldRed + 0.769 * oldGreen + 0.189 * oldBlue);
                int newGreen = (int) (0.349 * oldRed + 0.686 * oldGreen + 0.168 * oldBlue);
                int newBlue = (int) (0.272 * oldRed + 0.534 * oldGreen + 0.131 * oldBlue);

                newRed = newRed > 255 ? 255 : newRed;
                newGreen = newGreen > 255 ? 255 : newGreen;
                newBlue = newBlue > 255 ? 255 : newBlue;


                // applying new pixel value to newBitmap
                // condition for bars ,setting SEPIA values to particular pixel comes in this range only
                int newPixel = 0;
                if (i <= imageWidth / 3 || i >= (imageWidth - imageWidth / 3)) {
                    // apply sepia
                    newPixel = Color.argb(oldAlpha, newRed, newGreen, newBlue);

                } else {
                    //  don't apply grey
                    newPixel = oldPixel;
                }

                newBitmap.setPixel(i, j, newPixel);
            }
        }

        return newBitmap;
    }


    /**
     * Apply Sepia Grey Diagonal Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    public static Bitmap setGreyDiagonalFilter(Bitmap oldBitmap) {
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        Log.e("Image Size", "Height=" + imageHeight + " Width=" + imageWidth);


        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {

                // getting each pixel
                int oldPixel = oldBitmap.getPixel(i, j);

                // each pixel is made from RED_BLUE_GREEN_ALPHA
                // so, getting current values of pixel
                int oldRed = Color.red(oldPixel);
                int oldBlue = Color.blue(oldPixel);
                int oldGreen = Color.green(oldPixel);
                int oldAlpha = Color.alpha(oldPixel);


                // Algorithm for getting new values after calculation of filter
                // Algorithm for GREY FILTER
                int intensity = (oldRed + oldBlue + oldGreen) / 3;
                int newRed = intensity;
                int newBlue = intensity;
                int newGreen = intensity;

                // applying new pixel value to newBitmap
                // condition for Diagonals ,setting GREY values to particular pixel comes in this range only
                int newPixel = 0;
                if (i < j - imageHeight / 4) {
                    // apply grey at lower
                    newPixel = Color.argb(oldAlpha, newRed, newGreen, newBlue);

                } else if ((i - (imageHeight / 4)) > j) {
                    // apply grey upper
                    newPixel = Color.argb(oldAlpha, newRed, newGreen, newBlue);

                } else {
                    //  don't apply grey
                    newPixel = oldPixel;
                }

                newBitmap.setPixel(i, j, newPixel);
            }
        }

        return newBitmap;
    }

    /**
     * Apply Sepia Diagonal Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    public static Bitmap setSepiaDiagonalFilter(Bitmap oldBitmap) {

        // copying to newBitmap for manipulation
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // height and width of Image
        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        Log.e("Image Size", "Height=" + imageHeight + " Width=" + imageWidth);

        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {

                // getting each pixel
                int oldPixel = oldBitmap.getPixel(i, j);

                // each pixel is made from RED_BLUE_GREEN
                // so, getting current values of pixel
                int oldRed = Color.red(oldPixel);
                int oldBlue = Color.blue(oldPixel);
                int oldGreen = Color.green(oldPixel);
                int oldAlpha = Color.alpha(oldPixel);


                // Algorithm for getting new values after calculation of filter
                // Algorithm for SEPIA FILTER
                int newRed = (int) (0.393 * oldRed + 0.769 * oldGreen + 0.189 * oldBlue);
                int newGreen = (int) (0.349 * oldRed + 0.686 * oldGreen + 0.168 * oldBlue);
                int newBlue = (int) (0.272 * oldRed + 0.534 * oldGreen + 0.131 * oldBlue);

                newRed = newRed > 255 ? 255 : newRed;
                newGreen = newGreen > 255 ? 255 : newGreen;
                newBlue = newBlue > 255 ? 255 : newBlue;


                // applying new pixel value to newBitmap
                // condition for Diagonals ,setting GREY values to particular pixel comes in this range only

                int newPixel = 0;
                if (i < j - imageHeight / 2) {
                    // apply sepia at lower
                    newPixel = Color.argb(oldAlpha, newRed, newGreen, newBlue);

                } else if ((i - (imageHeight / 2)) > j) {
                    // apply sepia upper
                    newPixel = Color.argb(oldAlpha, newRed, newGreen, newBlue);

                } else {
                    //  don't apply sepia
                    newPixel = oldPixel;
                }

                newBitmap.setPixel(i, j, newPixel);
            }
        }

        return newBitmap;
    }


    /**
     * Apply Sketch Filter on image
     *
     * @param oldBitmap image where filter to be applied
     * @return newBitmap new image after filter
     */
    public static Bitmap setSketchFilter(Bitmap oldBitmap) {
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        Log.e("Image Size", "Height=" + imageHeight + " Width=" + imageWidth);

        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {

                // operating on each pixel
                int oldPixel = oldBitmap.getPixel(i, j);

                // each pixel is made from RED_BLUE_GREEN
                // so, getting current values of pixel
                int oldRed = Color.red(oldPixel);
                int oldBlue = Color.blue(oldPixel);
                int oldGreen = Color.green(oldPixel);
                int oldAlpha = Color.alpha(oldPixel);


                // Algorithm for getting new values after calculation of filter
                // Algorithm for SKETCH FILTER
                int intensity = (oldRed + oldBlue + oldGreen) / 3;

                // applying new pixel value to newBitmap
                // condition for Sketch
                int newPixel = 0;
                int INTENSITY_FACTOR = 120;

                if (intensity > INTENSITY_FACTOR) {
                    // apply white color
                    newPixel = Color.argb(oldAlpha, HIGHEST_COLOR_VALUE, HIGHEST_COLOR_VALUE, HIGHEST_COLOR_VALUE);

                } else if (intensity > 100) {
                    // apply grey color
                    newPixel = Color.argb(oldAlpha, 150, 150, 150);
                } else {
                    // apply black color
                    newPixel = Color.argb(oldAlpha, LOWEST_COLOR_VALUE, LOWEST_COLOR_VALUE, LOWEST_COLOR_VALUE);
                }


                newBitmap.setPixel(i, j, newPixel);
            }

        }

        return newBitmap;
    }


}
