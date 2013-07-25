package com.ht.huaxin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ht.huaxin.entity.Picture;
import com.ht.huaxin.image.DisplayUtil;
import com.ht.huaxin.utils.Constants;
import com.ht.huaxin.view.MyScrollView;
import com.ht.huaxin.view.MyViewPaper;
import com.ht.huaxin.view.ShaderView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class PictureAdapter extends PagerAdapter {
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Context context;
	private List<Picture> pictures;
	private List<View> views;
	Bitmap imageLoaded;
	String fileName;
	private View currentView;
	private MyViewPaper myViewPaper;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public PictureAdapter(Context context, List<Picture> pictures) {
		super();
		this.context = context;
		this.pictures = pictures;
		views = new ArrayList<View>();
	}

	public static class ItemView {
		public ImageView picture_image;
		public TextView review;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {
		View imageLayout = createPageItemView();
		TextView picture_index = (TextView) imageLayout
				.findViewById(R.id.picture_index);
		views.add(imageLayout);
		MyScrollView scrollView = (MyScrollView) imageLayout
				.findViewById(R.id.ScrollView);
		final ImageView image = (ImageView) imageLayout
				.findViewById(R.id.picture_img);
		TextView name = (TextView) imageLayout.findViewById(R.id.picture_name);
		TextView review = (TextView) imageLayout
				.findViewById(R.id.picture_review);
		// final Button download = (Button) imageLayout
		// .findViewById(R.id.);
  
		final ProgressBar spinner = (ProgressBar) imageLayout
				.findViewById(R.id.loading);
		final Picture picture = pictures.get(position);

		picture_index.setText((position + 1) + "/" + getCount());
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.displayer(new FadeInBitmapDisplayer(300))
				.showImageOnFail(R.drawable.ic_error).cacheInMemory()
				.cacheOnDisc().build();
		name.setText(picture.getName());
		review.setText(picture.getReview());
		fileName = picture.getImage_url();

//		scrollView.setShaderView(image);
//		if (position == 1) {
//			myViewPaper.setShaderView(image);
//		}
		Log.i("pic", "pic name " + fileName);
		Log.i("pic", "pic position" + position);
		// image.setOnLongClickListener(new OnLongClickListener() {
		// @Override
		// public boolean onLongClick(View v) {
		// Log.e("debug", "onLongclick");
		// image.setShowing(true);
		// image.invalidate();
		//
		// image.postInvalidate();
		// image.invalidate();
		// return false;
		// }
		// });

		imageLoader.displayImage(
				Constants.Image_URL_PREFIX + picture.getImage_url()
						+ Constants.Image_URL_BACKFIX, image, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						spinner.setVisibility(View.VISIBLE);
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
						spinner.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(final String imageUri,
							View view, final Bitmap loadedImage) {
						spinner.setVisibility(View.GONE);
						Log.i("pic", "imageUri " + imageUri);
//						int width = DisplayUtil.getWidth();
//						float scale = (float) width
//								/ (float) loadedImage.getWidth();
//						int height = (int) (loadedImage.getHeight() * scale);

						// Bitmap mBitmap =
						// Bitmap.createScaledBitmap(loadedImage,
						// width, height, true);
						// image.config(loadedImage);
//						image.setImageBitmap(loadedImage);
//						image.setOnLongClickListener(new OnLongClickListener() {
//
//							@Override
//							public boolean onLongClick(View v) {
//								// TODO Auto-generated method stub
//								Log.e("debug", "onLongClick");
////								image.setShowing(true);
//								image.invalidate();
//								return false;
//							}
//						});

						// download.setOnClickListener(new OnClickListener() {
						// @Override
						// public void onClick(View v) {
						// save_pic_to_galley(loadedImage,
						// pictures.gKJet(position).getImage_url());
						// int p = pictures.get(position).getId();
						// Log.e("debug", "position  " + p);
						// }
						// });
						Log.i("pic", fileName + "button clicked listener set");
					}
				});

		((ViewPager) container).addView(imageLayout, 0);
		return imageLayout;
	}

	private View createPageItemView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		return inflater.inflate(R.layout.picture, null);
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.setPrimaryItem(container, position, object);
	}

	@Override
	public int getCount() {
		return pictures.size();
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

	@Override
	public boolean isViewFromObject(View view, Object object) {
		currentView = (View) object;
		return view.equals(object);
	}

	public View getCurrentView() {
		return currentView;
	}

	View.OnLongClickListener imageLongClicked = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View view) {
			view.setDrawingCacheEnabled(true);

			// to_do

			view.setDrawingCacheEnabled(false);
			return false;
		}
	};

	public void save_pic_to_galley(Bitmap bitmap, String picName) {
		Log.i("pic", picName + "button clicked");
		String path = Environment.getExternalStorageDirectory().toString();
		OutputStream fOut = null;
		File file = new File(path, picName + ".jpg");
		try {
			fOut = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fOut.close();
				fOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), file.getName(), file.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public MyViewPaper getMyViewPaper() {
		return myViewPaper;
	}

	public void setMyViewPaper(MyViewPaper myViewPaper) {
		this.myViewPaper = myViewPaper;
	}

	public List<View> getViews() {
		return views;
	}

	public void setViews(List<View> views) {
		this.views = views;
	}

	class UiHandler extends Handler {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			int what = msg.what;
			switch (what) {
			case 1:

				break;

			default:
				break;
			}
		}
	}

}