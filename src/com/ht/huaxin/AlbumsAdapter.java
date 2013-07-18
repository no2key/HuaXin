package com.ht.huaxin;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ht.huaxin.entity.Album;
import com.ht.huaxin.utils.UIHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class AlbumsAdapter extends BaseAdapter {
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Context context;
	private List<Album> albums;

	public AlbumsAdapter(Context context, List<Album> albums) {
		super();
		this.context = context;
		this.albums = albums;
	}

	public static class ItemView {
		public ImageView profile_image;
		public TextView album_title;
		public TextView create_time;
		public TextView picture_count;
		public TextView album_intro;
	}

	@Override
	public int getCount() {
		return albums.size();
	}

	@Override
	public Object getItem(int index) {
		return albums.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView != null) {
			view = convertView;
		} else {
			view = createListItemView();
			ItemView buffer = new ItemView();
			buffer.album_title = (TextView) view.findViewById(R.id.album_title);
			buffer.create_time = (TextView) view.findViewById(R.id.create_time);
			buffer.picture_count = (TextView) view
					.findViewById(R.id.picture_count);
			buffer.album_intro = (TextView) view.findViewById(R.id.album_intro);
			view.setTag(buffer);
		}

		final Album album = albums.get(position);

		ItemView buffer = (ItemView) view.getTag();
		buffer.album_title.setText(album.getTitle());
		buffer.create_time.setText(UIHelper.getYearFromThen(album
				.getAuthor_intro()));
		buffer.picture_count.setText(UIHelper.getPictureCount(album
				.getPicture_count()));
		buffer.album_intro.setText(album.getAlbum_intro());

		return view;
	}

	private View createListItemView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		return inflater.inflate(R.layout.album, null);
	}

	public void test() {
		ImageLoader iamgeLoader = ImageLoader.getInstance();
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
