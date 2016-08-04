package com.mess.ordermess.utils;

import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  

import com.mess.ordermess.constant.Constants;
  
import android.content.Context;  
import android.graphics.Bitmap;  
import android.graphics.Bitmap.CompressFormat;  
import android.graphics.BitmapFactory;  
import android.os.Environment;  
  
public class FileUtils {  
    /** 
     * sd卡的根目录 
     */  
    private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();  
    /** 
     * 手机的缓存根目录 
     */  
    private static String mDataRootPath = null;  
    /** 
     * 保存Image的目录名 
     */  
    private final static String FOLDER_NAME = "/ClientDlnu";  
      
      
    public FileUtils(Context context){  
        mDataRootPath = context.getCacheDir().getPath();  
    }  
    
    public FileUtils(){}
      
  
    /** 
     * 获取储存Image的目录 
     * @return 
     */  
    private String getStorageDirectory(){  
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?  
                mSdRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;  
    }  
      
    /**
     *  * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录 
     * @param fileName
     * @param loadType
     * @param bitmap
     * @throws IOException
     */
    public void savaBitmap(String fileName, int loadType,Bitmap bitmap) throws IOException{  
        if(bitmap == null){  
            return;  
        }  
        
        File folderFile = new File( getStorageDirectory());  
        if(!folderFile.exists()){  
            folderFile.mkdir();  
        }  
        String filePath = getStorageDirectory() + File.separator + fileName;
        
        if(loadType == Constants.LOADING_BIG_IMAGE){
        	filePath = getStorageDirectory() + File.separator + Constants.PICTURE_YUAN + fileName;
        }
        
        File file = new File(filePath);  
        file.createNewFile();  
        FileOutputStream fos = new FileOutputStream(file);  
        bitmap.compress(CompressFormat.PNG, 100, fos);  
        fos.flush();  
        fos.close();  
    }  
      
    /** 
     * 从手机或者sd卡获取Bitmap 
     * @param fileUrl 
     * @return 
     */  
    public Bitmap getBitmap(String fileUrl){  
        return BitmapFactory.decodeFile(fileUrl);  
    }  
      
    /** 
     * 判断文件是否存在 
     * @param fileUrl 
     * @return 
     */  
    public boolean isFileExists(String fileUrl){  
        return new File(fileUrl).exists();  
    }  
      
    /** 
     * 获取文件的大小 
     * @param fileUrl 
     * @return 
     */  
    public long getFileSize(String fileUrl) {  
        return new File(fileUrl).length();  
    }  
      
      
    /** 
     * 删除SD卡或者手机的缓存图片和目录 
     */  
    public void deleteFile() {  
        File dirFile = new File(getStorageDirectory());  
        if(! dirFile.exists()){  
            return;  
        }  
        if (dirFile.isDirectory()) {  
            String[] children = dirFile.list();  
            for (int i = 0; i < children.length; i++) {  
                new File(dirFile, children[i]).delete();  
            }  
        }  
          
        dirFile.delete();  
    }  
}