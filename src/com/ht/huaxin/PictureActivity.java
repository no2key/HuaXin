package com.ht.huaxin;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ht.huaxin.entity.Picture;
import com.ht.huaxin.http.HttpUtils;
import com.ht.huaxin.utils.Constants;
import com.ht.huaxin.view.MyViewPaper;
import com.ht.huaxin.view.ShaderView;

public class PictureActivity extends Activity implements Callback {
	private String albumID;
	private String albumTitle;
	private static final String STATE_POSITION = "STATE_POSITION";

	MyViewPaper  pager;
	PictureAdapter pictureAdapter;

	Handler pictureHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
		pictureHandler = new Handler(this);

		pager = (MyViewPaper ) findViewById(R.id.album_pager);
		albumID = getIntent().getExtras().getString(Constants.EXTRA_Album_id);
		albumTitle = getIntent().getExtras().getString(
				Constants.EXTRA_Album_title);

		PicturesLoadThread thread = new PicturesLoadThread();
		new Thread(thread).start();
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.e("debug", "onTouch");
		View current = pictureAdapter.getCurrentView();
		ShaderView image = (ShaderView) current.findViewById(R.id.picture_img);
		boolean isShow = image.isShowing();
		Log.e("debug", isShow + "");
		return super.onTouchEvent(event);
	}

	class PicturesLoadThread implements Runnable {
		public void run() {
			try {
				String url = Constants.PICTURE_URL_PREFIX + albumID;
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
				PictureActivity.this.pictureHandler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("json", e.toString());
			}

		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		pictureAdapter = new PictureAdapter(PictureActivity.this,
				(List<Picture>) msg.obj);
		pictureAdapter.setMyViewPaper(pager);
		pager.setAdapter(pictureAdapter);
		pager.setCurrentItem(0);
		return false;
	}

}
