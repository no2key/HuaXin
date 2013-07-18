package com.ht.huaxin;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ht.huaxin.database.dao.OrmDateBaseHelper;
import com.ht.huaxin.database.dao.interfaze.AlbumDao;
import com.ht.huaxin.entity.Album;
import com.ht.huaxin.http.HttpUtils;
import com.ht.huaxin.image.DisplayUtil;
import com.ht.huaxin.utils.Constants;

public class AlbumsActivity extends CommonActivity implements Callback {
	ListView albumsView;
	AlbumsAdapter albumsAdapter;
	Handler albumHandler;
	private static final int MSG_GET_NET = 0;
	private static final int MSG_GET_LOCAL = 1;
	private static final int MSG_ERROR = 2;
	private OrmDateBaseHelper ormDateBaseHelper;
	private AlbumDao albumDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		albumHandler = new Handler(this);
		DisplayUtil du = new DisplayUtil(this);

		albumsView = (ListView) findViewById(R.id.albums);
		albumsView.setOnItemClickListener(albumClicked);

		AlbumsLoadThread loadThread = new AlbumsLoadThread();
		ormDateBaseHelper = getOrmDateBaseHelper();
		albumDao = ormDateBaseHelper.getAlbumDao();
		albumsAdapter = new AlbumsAdapter(this);
		albumsView.setAdapter(albumsAdapter);
		
//		new Thread(loadThread).start();

		initLocal();
	}

	private void initLocal() {
		new Thread() {
			public void run() {
				try {
					List<Album> albums = albumDao.queryForAll();
					Message msg = new Message();
					msg.what = MSG_GET_LOCAL;
					msg.obj = albums;
					albumHandler.sendMessage(msg);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			};
		}.start();
	}

	ListView.OnItemClickListener albumClicked = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Album album = (Album) parent.getItemAtPosition(position);

			Intent intent = new Intent(AlbumsActivity.this,
					PictureActivity.class);
			intent.putExtra(Constants.EXTRA_Album_id,
					String.valueOf(album.getId()));
			intent.putExtra(Constants.EXTRA_Album_title,
					String.valueOf(album.getTitle()));
			startActivity(intent);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class AlbumsLoadThread implements Runnable {
		public void run() {
			try {
				String url = Constants.ALBUM_URL_PREFIX;
				JSONObject json = HttpUtils.get(url);
				JSONArray jsonArray = json.getJSONArray("entries");
				Type type = new TypeToken<List<Album>>() {
				}.getType();
				Gson gson = new Gson();
				List<Album> albums = gson.fromJson(jsonArray.toString(), type);
				Message msg = Message.obtain();
				msg.obj = albums;
				msg.what = MSG_GET_NET;
				AlbumsActivity.this.albumHandler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("json", e.toString());
				Message msg = Message.obtain();
				msg.what = MSG_ERROR;
				AlbumsActivity.this.albumHandler.sendMessage(msg);
			}

		}
	}

	@Override
	public boolean handleMessage(Message msg) {

		int what = msg.what;
		List<Album> albums;
		switch (what) {
		case MSG_GET_NET:
			albums = (List<Album>) msg.obj;
			// albumsAdapter = new AlbumsAdapter(AlbumsActivity.this, albums);

			albumsAdapter.setAlbums(albums);
			albumDao.batchInsert(albums);
			break;

		case MSG_GET_LOCAL:
			albums = (List<Album>) msg.obj;
			albumsAdapter.setAlbums(albums);
			albumsAdapter.notifyDataSetChanged();
			break;
		case MSG_ERROR:
			Toast.makeText(AlbumsActivity.this, "网络连接异常", Toast.LENGTH_SHORT)
					.show();
			break;
		}

		// if (msg.arg1 == 0) {
		// albumsAdapter = new AlbumsAdapter(AlbumsActivity.this,
		// (List<Album>) msg.obj);
		// albumsView.setAdapter(albumsAdapter);
		// } else {
		// Toast.makeText(AlbumsActivity.this, "网络连接异常", Toast.LENGTH_SHORT)
		// .show();
		// }

		return false;
	}
}
