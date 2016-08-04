package com.hotel.temp;

import android.app.Activity;
import android.graphics.Bitmap;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.ThumbnailListener;
import com.hotel.constnats.Constants;
import com.hotel.net.ImageDownLoader;
import com.hotel.net.NetUntils;
import com.hotel.net.ImageDownLoader.onImageLoaderListener;
import com.hotel.utils.DensityUtils;

public class LoadImage {

	public static int modelId = 1;
	public static ImageDownLoader imageDown;

	/**
	 * �������غõ�ͼƬ�ص��ӿ�
	 * 
	 * @author Admin
	 * 
	 */
	public interface GetImageBitmap {
		public void getImageBitmap(Bitmap bitmap, String cacheUrl);
	}

	/**
	 * �첽����ͼƬ
	 * 
	 * @param context
	 *            ��ǰ������
	 * @param loadType
	 *            ���ص�������ͼ����ԭͼ
	 * @param fileUrl
	 *            ��Ҫ���ص�����ͼƬ���ӵ�ַ
	 * @param getImage
	 *            ����ͼƬ�Ľӿ�
	 */
	public static void LoadImageFile(final Activity context,
			final int loadType, final String fileUrl,
			final GetImageBitmap getImage) {

		imageDown = new ImageDownLoader(context);

		final String fileName = DensityUtils.divisionFileName(fileUrl);

		// �����������ΪConstants.LOADING_SAMLL_IMAG����Ϊ������ͼ��������ԭͼ
		if (Constants.LOADING_SAMLL_IMAGE == loadType) {
			// �÷������Զ���������ͼ
			if (NetUntils.isNetworkAvailable(context)) {
				BmobProFile.getInstance(context).submitThumnailTask(fileName,
						modelId, new ThumbnailListener() {

							@Override
							public void onSuccess(String thumbnailName,
									String thumbnailUrl) {
								getImageFile(context, fileName, thumbnailUrl,
										loadType, getImage);
							}

							@Override
							public void onError(int statuscode, String errormsg) {
							}
						});
			}else{
				getImageFile(context, fileName, fileUrl,
						loadType, getImage);
			}

		} else {
			getImageFile(context, fileName, fileUrl, loadType, getImage);
		}

	}

	/**
	 * ȡ�������е�ͼƬ�򻺴��е�ͼƬ
	 * 
	 * @param context
	 *            ��ǰ������
	 * @param fileName
	 *            ��ȡ���ļ�����
	 * @param thumbnailUrl
	 *            ͼƬ�����ص�ַ
	 * @param loadType
	 *            ����ͼƬ������
	 * @param getImage
	 *            ����ͼƬ�Ľӿ�
	 */
	private static void getImageFile(Activity context, String fileName,
			String thumbnailUrl, final int loadType,
			final GetImageBitmap getImage) {
		// ��ͼƬ����ǩ����ȡ�ÿ�ֱ�ӷ��ʵĵ�ַ
		String url = BmobProFile.getInstance(context).signURL(fileName,
				thumbnailUrl, Constants.BMOB_API_ACCESSKEY, 0, null);

		imageDown.downloadImage(url, fileName, loadType,
				new onImageLoaderListener() {

					@Override
					public void onImageLoader(Bitmap bitmap, String cacheUrl) {
						if (bitmap != null) {
							getImage.getImageBitmap(bitmap, cacheUrl);
						}
					}

				});
	}
}
