package com.ht.huaxin;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.nostra13.example.universalimageloader.downloader.ExtendedImageDownloader;
import com.ht.huaxin.database.dao.OrmDateBaseHelper;
import com.ht.huaxin.utils.Constants.Config;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class UILApplication extends Application {
	private OrmDateBaseHelper ormDateBaseHelper;

	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		if (Config.DEVELOPER_MODE
				&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectAll().penaltyDeath().build());
		}
		ormDateBaseHelper = new OrmDateBaseHelper(this, "huaxiang", null, 1);

		super.onCreate();
		Log.i("debug", "imageloader initing on application create");
		initImageLoader(getApplicationContext());
		Log.i("debug", "imageloader inited on application create");
	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.memoryCacheSize(2 * 1024 * 1024) // 2 Mb
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.imageDownloader(new ExtendedImageDownloader(getApplicationContext()))
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.enableLogging() // Not necessary in common
		.build();
	// Initialize ImageLoader with configuration.
	ImageLoader.getInstance().init(config);
	}

	public OrmDateBaseHelper getOrmDateBaseHelper() {
		return ormDateBaseHelper;
	}

	public void setOrmDateBaseHelper(OrmDateBaseHelper ormDateBaseHelper) {
		this.ormDateBaseHelper = ormDateBaseHelper;
	}

}