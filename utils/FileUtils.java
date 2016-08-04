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
     * sd���ĸ�Ŀ¼ 
     */  
    private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();  
    /** 
     * �ֻ��Ļ����Ŀ¼ 
     */  
    private static String mDataRootPath = null;  
    /** 
     * ����Image��Ŀ¼�� 
     */  
    private final static String FOLDER_NAME = "/ClientDlnu";  
      
      
    public FileUtils(Context context){  
        mDataRootPath = context.getCacheDir().getPath();  
    }  
    
    public FileUtils(){}
      
  
    /** 
     * ��ȡ����Image��Ŀ¼ 
     * @return 
     */  
    private String getStorageDirectory(){  
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?  
                mSdRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;  
    }  
      
    /**
     *  * ����Image�ķ�������sd���洢��sd����û�оʹ洢���ֻ�Ŀ¼ 
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
     * ���ֻ�����sd����ȡBitmap 
     * @param fileUrl 
     * @return 
     */  
    public Bitmap getBitmap(String fileUrl){  
        return BitmapFactory.decodeFile(fileUrl);  
    }  
      
    /** 
     * �ж��ļ��Ƿ���� 
     * @param fileUrl 
     * @return 
     */  
    public boolean isFileExists(String fileUrl){  
        return new File(fileUrl).exists();  
    }  
      
    /** 
     * ��ȡ�ļ��Ĵ�С 
     * @param fileUrl 
     * @return 
     */  
    public long getFileSize(String fileUrl) {  
        return new File(fileUrl).length();  
    }  
      
      
    /** 
     * ɾ��SD�������ֻ��Ļ���ͼƬ��Ŀ¼ 
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