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
	 * 加载下载好的图片回调接口
	 * 
	 * @author Admin
	 * 
	 */
	public interface GetImageBitmap {
		public void getImageBitmap(Bitmap bitmap, String cacheUrl);
	}

	/**
	 * 异步加载图片
	 * 
	 * @param context
	 *            当前上下文
	 * @param loadType
	 *            加载的是缩略图还是原图
	 * @param fileUrl
	 *            将要加载的网络图片链接地址
	 * @param getImage
	 *            加载图片的接口
	 */
	public static void LoadImageFile(final Activity context,
			final int loadType, final String fileUrl,
			final GetImageBitmap getImage) {

		imageDown = new ImageDownLoader(context);

		final String fileName = DensityUtils.divisionFileName(fileUrl);

		// 如果加载类型为Constants.LOADING_SAMLL_IMAG，就为是缩略图，否则是原图
		if (Constants.LOADING_SAMLL_IMAGE == loadType) {
			// 让服务器自动生成缩略图
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
	 * 取得网络中的图片或缓存中的图片
	 * 
	 * @param context
	 *            当前上下文
	 * @param fileName
	 *            获取的文件名称
	 * @param thumbnailUrl
	 *            图片的下载地址
	 * @param loadType
	 *            加载图片的类型
	 * @param getImage
	 *            加载图片的接口
	 */
	private static void getImageFile(Activity context, String fileName,
			String thumbnailUrl, final int loadType,
			final GetImageBitmap getImage) {
		// 将图片进行签名后，取得可直接访问的地址
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
