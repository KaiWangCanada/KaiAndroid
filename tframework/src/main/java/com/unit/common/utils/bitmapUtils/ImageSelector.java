package com.unit.common.utils.bitmapUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.unit.common.utils.LogOutputUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 使用指南:
 * 1.在Activity内使用,首先要把ImageSelector初始化,生成一个ImageSelector对象并设置相关参数
 *
 * 2.调用[selectImageLocal],[selectImageCamera] 可以打开选择本地图片和拍照;
 *   调用[cropImageLocal],[cropImageCamera] 可以打开本地图片和拍照后,进行图片裁剪;
 *
 * 3.调用完获取图片的方法后,在当前的 Activity 类的 onActivityResult
 *   方法视情况调用[createBitmapLocal]或者[createBitmapCamera]创建图片,
 *   如果抓取的图片被另存为到当前APP的文件夹,则可通过[getFile]获取文件的File对象
 *
 * Created by Sammy03 on 14-8-14.
 */
public class ImageSelector {
    static String TAG="ImageSelector";

    public ImageSelector(Activity activity, String dirpath, String fileName,
                         int requestCodeLocal, int requestCodeCamera) {
        this.mDirpath = dirpath;
        this.mActivity = activity;
        this.requestCodeLocal = requestCodeLocal;
        this.requestCodeCamera = requestCodeCamera;
        this.mFileName = fileName;
        File fileDir = new File(mDirpath);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        this.mCameraTmpFilePath = mDirpath + File.separator + mFileName + FORMAT_JPEG;
    }

    /**
     * 选择本地图片
     * @param srcUri 指定的资源
     */
    public void selectImageLocal(Uri srcUri){

        Intent intent = new Intent();

        if (srcUri != null){
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(srcUri, "image/*");
        }else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        }
        if (null == mFile) {
            String fileName = mFileName;
            if (fileName == null) {
                fileName = Local_Select_Name;
            }
            mFile = this.createSaveFile(fileName);
        }
        selectMode = SELECT_MODE_SELECT;
        mActivity.startActivityForResult(intent, requestCodeLocal);
    }

    public void selectImageByAlbum() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        selectMode = SELECT_MODE_SELECT;
        mActivity.startActivityForResult(intent, requestCodeLocal);
    }

    /**
     * 选择拍照图片
     * @param scale 是否保持分辨率
     */
    public void selectImageCamera(boolean scale){
        selectImageCamera(scale, null);
    }

    /**
     * 选择拍照图片
     * @param scale 是否保持分辨率
     * @param imageName 照片名, 设置null时,该照片储存为临时图片, 下一次调用会被覆盖
     */
    public void selectImageCamera(boolean scale, String imageName){

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("scale", scale);
        intent.putExtra("return-data", false);

        if (imageName != null){
            imageName = ("".equals(imageName)) ?
                    String.valueOf(System.currentTimeMillis()) : imageName;
            mFile = this.createSaveFile(imageName);
        }else {
            String fileName = mFileName;
            if (fileName == null) {
                fileName = Camera_Select_Name;
            }
            mFile = this.createSaveFile(fileName);
        }

        if (mFile.exists()) {
            Uri uri = Uri.fromFile(mFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            LogOutputUtils.e(TAG,"file can not exists");
            return;
        }

        selectMode = SELECT_MODE_SELECT;
        mActivity.startActivityForResult(intent, requestCodeCamera);
    }

    /**
     * 选择并裁剪本地图片
     * @param aspectX X方向的比例
     * @param aspectY Y方向的比例
     * @param outputX X方向的长度
     * @param outputY Y方向的长度
     * @param scale 是否保持长宽比
     * @param circleCrop 是否圆形裁剪
     * @param srcUri 图片源
     */
    public void cropImageLocal(int aspectX, int aspectY, int outputX, int outputY,
                               boolean scale, boolean circleCrop, Uri srcUri){

        Intent intent;
//        LogOutputUtils.i(TAG, "cropImageLocal uri:" + srcUri);
        if (srcUri != null) {
            intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(srcUri, "image/*");
            if (srcUri.getPath() != null && null == mFile) {
                mFile = new File(srcUri.getPath());
            }
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            } else {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
            }
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            mFile = null;
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", scale);
        intent.putExtra("return-data", false);
        intent.putExtra("circleCrop", circleCrop);
        if (mFile == null) {
            String fileName = mFileName;
            if (fileName == null) {
                fileName = Local_Crop_Name;
            }
            mFile = this.createSaveFile(fileName);
        }
        if (mFile.exists()) {
            Uri uri = Uri.fromFile(mFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            LogOutputUtils.e(TAG,"file can not exists");
            return;
        }

        selectMode = SELECT_MODE_CROP;
        mActivity.startActivityForResult(intent, requestCodeLocal);
    }

    /**
     * 拍照后马上裁剪本地图片
     * @param aspectX X方向的比例
     * @param aspectY Y方向的比例
     * @param outputX X方向的长度
     * @param outputY Y方向的长度
     * @param scale 是否保持长宽比
     * @param circleCrop 是否圆形裁剪
     * @param isSaveInCameraPath true: 改变拍照后图片的储存位置,
     *                                 放置在参数'dirpath'生成的目录中的临时图片中;
     *                           false: 不改变拍照后图片的储存位置
     */
    public void cropImageCamera(int aspectX, int aspectY, int outputX, int outputY,
                                boolean scale, boolean circleCrop,
                                boolean isSaveInCameraPath){

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        this.mIsSaveInCameraPath = isSaveInCameraPath;
        if (mIsSaveInCameraPath){

            File file = new File(mCameraTmpFilePath);
            if (file.exists()){
                file.delete();
            }

            Uri uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        cameraCropParam.setAspectX(aspectX);
        cameraCropParam.setAspectY(aspectY);
        cameraCropParam.setOutputX(outputX);
        cameraCropParam.setOutputY(outputY);
        cameraCropParam.setCircleCrop(circleCrop);
        cameraCropParam.setScale(scale);

        selectMode = SELECT_MODE_CROP;
        mActivity.startActivityForResult(intent, requestCodeCamera);
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    /**
     * 从本地图片源生成图片, 在选择(裁剪)操作完成后
     * @param data
     * @param compressRange
     */
    public void createBitmapLocal(Intent data, int compressRange){

        if (SELECT_MODE_SELECT.equals(selectMode)){
            createBitmapFromUri(data, compressRange);
        }

        if (SELECT_MODE_CROP.equals(selectMode)){
            createBitmapFromFile(data, compressRange);
        }
    }

    /**
     * 从拍照中生成图片, 在选择(裁剪)操作完成后
     * 有些手机系统无法获取拍照后的图片位置,返回的参数data会为null,
     * 这时则需要把图片储存在 dirPath 下的一张临时图片中再进行图片创建操作
     * @param data
     * @param compressRange
     */
    public void createBitmapCamera(Intent data, int compressRange){

        if (SELECT_MODE_CROP.equals(selectMode) && data != null){

            Uri uri = data.getData();
            mBitmap = null;
            mFile = null;
            cropImageLocal(cameraCropParam.aspectX, cameraCropParam.aspectY,
                    cameraCropParam.outputX, cameraCropParam.outputY,
                    cameraCropParam.scale, cameraCropParam.circleCrop, uri);
        }else if (SELECT_MODE_SELECT.equals(selectMode)){

            createBitmapFromFile(data, compressRange);
        }else {

            if (mIsSaveInCameraPath){

                File file = new File(mCameraTmpFilePath);
                if (file.exists()){
                    Uri uri = Uri.fromFile(file);
                    cropImageLocal(cameraCropParam.aspectX, cameraCropParam.aspectY,
                            cameraCropParam.outputX, cameraCropParam.outputY,
                            cameraCropParam.scale, cameraCropParam.circleCrop, uri);
                }

            }else {
                LogOutputUtils.i(TAG,"can not create camera bitmap, because data is null," +
                        " try to set the parameter 'isSaveInCameraPath' value is 'true'" +
                        " when invoke method <cropImageCamera> ");
            }

        }
    }

    public File getFile() {
        return mFile;
    }

    public File getOrCreateFile() {
        if (null == mFile) {
            String fileName = mFileName;
            if (fileName == null) {
                fileName = Local_Select_Name;
            }
            mFile = this.createSaveFile(fileName);
        }
        return mFile;
    }

    private void createBitmapFromUri(Intent data, int compressRange){

        if (compressRange == 0){
            LogOutputUtils.e(TAG,"createBitmapFromCrop param compressRange can not be 0");
            return;
        }

        if (data != null) {
            Uri imageUri = data.getData();
            InputStream ins;
            LogOutputUtils.i(TAG, ("create bitmap from crop:" + imageUri));

            ContentResolver cr = mActivity.getContentResolver();
            try {
                ins = cr.openInputStream(imageUri);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(ins, null, options);

                //如果本地图片不能使用,删除本地创建图片
                if (options.outWidth <= 0 || options.outHeight <= 0) {

                    try {
                        mFile.delete();
                    } catch (Exception e) {
                        LogOutputUtils.e(TAG, "delete file error");
                    }
                }

                int size = (options.outWidth > options.outHeight)
                        ? options.outWidth : options.outHeight;

                if (compressRange < size) {
                    options.inSampleSize = size / compressRange;
                } else {
                    options.inSampleSize = 1;
                }

                options.inJustDecodeBounds = false;
                mBitmap = BitmapFactory.decodeStream(ins, null, options);

            } catch (FileNotFoundException e) {
                LogOutputUtils.e(TAG, "createBitmapFromSelect can not found file");
            } catch (Exception e) {
                LogOutputUtils.e(TAG, "createBitmapFromSelect other error");
            }
//            try {
//
//                if (mBitmap != null){
//                    mBitmap.recycle();
//                }
//
//                mBitmap = BitmapFactory.decodeStream(cr.openInputStream(imageUri));
//            } catch (FileNotFoundException e) {
//                Logger.e("can not create bitmap", e);
//            }

        }//end of if
    }

    //创建图片
    private void createBitmapFromFile(Intent data, int compressRange){

        if (compressRange == 0){
            LogOutputUtils.e(TAG,"createBitmapFromCrop param compressRange can not be 0");
            return;
        }

        if (mFile.exists()) {
            LogOutputUtils.i(TAG,"create bitmap from file:" + mFile.getAbsolutePath());

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mFile.getAbsolutePath(), options);

            //如果本地图片不能使用,则尝试从intent对象创建,并删除本地创建图片
            if ((options.outWidth <= 0 || options.outHeight <= 0) && data != null){
                createBitmapFromUri(data, compressRange);

                try {
                    mFile.delete();
                }catch (Exception e){
                    LogOutputUtils.e(TAG,"delete file error");
                }

                return;
            }

            int size = (options.outWidth > options.outHeight)
                    ? options.outWidth : options.outHeight;

            if (compressRange < size){
                options.inSampleSize = size / compressRange;
            }else {
                options.inSampleSize = 1;
            }

            options.inJustDecodeBounds = false;

            try {

                if (mBitmap != null){
                    mBitmap.recycle();
                }

                mBitmap = BitmapFactory.decodeFile(mFile.getAbsolutePath(), options);
            } catch (Exception e) {
                LogOutputUtils.e(TAG,"can not create file");
            }

        }else {
            LogOutputUtils.e(TAG,"file is not exists");
        }
    }

    //创建图片
    private File createSaveFile(String fileName) {

        String path = mDirpath;

        if (!path.endsWith("/")){
            path = path + "/";
        }
        if (null == fileName) {
            fileName = "temp";
        }
        path = path + fileName + FORMAT_JPEG;

        File file = new File(path);
        LogOutputUtils.i(TAG,"create image file, file path:" + path);

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                LogOutputUtils.e(TAG,"create file error, filePath:" + path);
            }
        }

        mFile = file;
        return file;
    }

    public void setFileName(String mFileName) {
        this.mFileName = mFileName;
        this.mCameraTmpFilePath = mDirpath + File.separator + mFileName + FORMAT_JPEG;
    }

    private String mDirpath;
    private String mCameraTmpFilePath;
    private String mFileName;
    private Bitmap mBitmap;
    private Activity mActivity;
    private File mFile;
    private CropParam cameraCropParam = new CropParam();

    private int requestCodeLocal;
    private int requestCodeCamera;
    private boolean mIsSaveInCameraPath = false;
    private String selectMode;
    private static String SELECT_MODE_CROP = "crop";
    private static String SELECT_MODE_SELECT = "select";
    public static final String FORMAT_JPEG = ".jpg";

    private static final String Camera_Crop_Name = "CameraCrop";
    private static final String Local_Crop_Name = "LocalCrop";
    private static final String Camera_Select_Name = "CameraSelect";
    private static final String Local_Select_Name = "LocalSelect";

    public static class CropParam{

        public int getAspectX() {
            return aspectX;
        }

        public void setAspectX(int aspectX) {
            this.aspectX = aspectX;
        }

        public int getAspectY() {
            return aspectY;
        }

        public void setAspectY(int aspectY) {
            this.aspectY = aspectY;
        }

        public int getOutputX() {
            return outputX;
        }

        public void setOutputX(int outputX) {
            this.outputX = outputX;
        }

        public int getOutputY() {
            return outputY;
        }

        public void setOutputY(int outputY) {
            this.outputY = outputY;
        }

        public boolean isScale() {
            return scale;
        }

        public void setScale(boolean scale) {
            this.scale = scale;
        }

        public boolean isCircleCrop() {
            return circleCrop;
        }

        public void setCircleCrop(boolean circleCrop) {
            this.circleCrop = circleCrop;
        }

        private int aspectX;
        private int aspectY;
        private int outputX;
        private int outputY;
        private boolean scale;
        private boolean circleCrop;
    }
}
