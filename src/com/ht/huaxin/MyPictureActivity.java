package com.ht.huaxin;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ht.huaxin.PictureActivity.PicturesLoadThread;
import com.ht.huaxin.entity.Picture;
import com.ht.huaxin.http.HttpUtils;
import com.ht.huaxin.utils.Constant;
import com.ht.huaxin.utils.Constants;
import com.ht.huaxin.utils.Constants.Extra;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-26 上午10:51:37 file declare:
 */
public class MyPictureActivity extends BaseActivity implements Callback {
	DisplayImageOptions options;
	private String albumID;
	private String albumTitle;

	Handler pictureHandler;
	ImagePagerAdapter imagePagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_image_pager);
		pictureHandler = new Handler(this);
		Bundle bundle = getIntent().getExtras();
		String[] imageUrls = Constants.IMAGES;
		int pagerPosition = 0;
		albumID = getIntent().getExtras().getString(Constant.EXTRA_Album_id);
		albumTitle = getIntent().getExtras().getString(
				Constant.EXTRA_Album_title);
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.image_for_empty_url)
				.resetViewBeforeLoading().cacheOnDisc()
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		imagePagerAdapter = new ImagePagerAdapter(this, imageUrls, options);
		pager.setAdapter(imagePagerAdapter);
		pager.setCurrentItem(pagerPosition);

		PicturesLoadThread thread = new PicturesLoadThread();
		new Thread(thread).start();
	}

	class PicturesLoadThread implements Runnable {
		public void run() {
			try {
				String url = Constant.PICTURE_URL_PREFIX + albumID;
				JSONObject json = HttpUtils.get(url);
				Log.i("debug", json.toString());
				JSONArray jsonArray = json.getJSONArray("entries");
				Type type = new TypeToken<List<Picture>>() {
				}.getType();
				Gson gson = new Gson();
				List<Picture> pictures = gson.fromJson(jsonArray.toString(),
						type);
				Message msg = Message.obtain();
				msg.obj = pictures;
				MyPictureActivity.this.pictureHandler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("json", e.toString());
			}

		}
	}

	@Override
	public boolean handleMessage(Message msg) {

		List<Picture> pictures = (List<Picture>) msg.obj;
		imagePagerAdapter.setPictures(pictures);
		imagePagerAdapter.notifyDataSetChanged();
		return false;
	}

}
