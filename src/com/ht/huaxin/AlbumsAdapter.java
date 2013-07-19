package com.ht.huaxin;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cuubonandroid.sugaredlistanimations.GPlusListAdapter;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;
import com.ht.huaxin.entity.Album;
import com.ht.huaxin.utils.Constants;
import com.ht.huaxin.utils.UIHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class AlbumsAdapter extends GPlusListAdapter {
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private Context context;
	private static List<Album> albums;

	public AlbumsAdapter(Context context, SpeedScrollListener scrollListener) {
		super(context, scrollListener, albums);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	// public AlbumsAdapter(Context context) {
	// super();
	// this.context = context;
	// }
	//
	// public AlbumsAdapter(Context context, List<Album> albums) {
	// super();
	// this.context = context;
	// this.albums = albums;
	// }

	public static class ItemView {
		public ImageView profile_image;
		public TextView album_title;
		public TextView create_time;
		public TextView picture_count;
		public TextView album_intro;
	}

	// @Override
	// public int getCount() {
	// if (albums == null) {
	// return 0;
	// }
	// return albums.size();
	// }

	// @Override
	// public Object getItem(int index) {
	// return albums.get(index);
	// }

	// @Override
	// public long getItemId(int index) {
	// return index;
	// }

	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// View view = null;
	// if (convertView != null) {
	// view = convertView;
	// } else {
	// view = createListItemView();
	// ItemView buffer = new ItemView();
	// buffer.album_title = (TextView) view.findViewById(R.id.album_title);
	// buffer.create_time = (TextView) view.findViewById(R.id.create_time);
	// buffer.picture_count = (TextView) view
	// .findViewById(R.id.picture_count);
	// buffer.album_intro = (TextView) view.findViewById(R.id.album_intro);
	// view.setTag(buffer);
	// }
	//
	// final Album album = albums.get(position);
	//
	// ItemView buffer = (ItemView) view.getTag();
	// buffer.album_title.setText(album.getTitle());
	// buffer.create_time.setText(UIHelper.getYearFromThen(album
	// .getAuthor_intro()));
	// buffer.picture_count.setText(UIHelper.getPictureCount(album
	// .getPicture_count()));
	// buffer.album_intro.setText(album.getAlbum_intro());
	//
	// return view;
	// }
 
	private View createListItemView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		return inflater.inflate(R.layout.album, null);
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

	public List<Album> getAlbums() {

		return albums;
	}

	public void setAlbums(List<Album> a) {
		items = a;
		albums = a;
	}

	@Override
	protected View getRowView(int position, View convertView, ViewGroup parent) {
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
			buffer.profile_image = (ImageView) view
					.findViewById(R.id.picture_img);
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

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory()
				.cacheOnDisc().build();

		imageLoader.displayImage(
				Constants.Image_URL_PREFIX + album.getProfile_img_url()
						+ Constants.Image_PNG_URL_BACKFIX,
				buffer.profile_image, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						String message = null;
						switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
						}
						Toast.makeText(context, message, Toast.LENGTH_SHORT)
								.show();

					}

					@Override
					public void onLoadingComplete(final String imageUri,
							View view, final Bitmap loadedImage) {
					}
				});

		return view;
	}

}
