package com.ht.huaxin;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ht.huaxin.entity.Picture;
import com.ht.huaxin.image.DisplayUtil;
import com.ht.huaxin.utils.Constant;
import com.ht.huaxin.view.MyScrollView;
import com.ht.huaxin.view.ShaderView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-26 上午10:49:52 file declare:
 */
public class ImagePagerAdapter extends PagerAdapter {

	private String[] images;
	private LayoutInflater inflater;
	BaseActivity bActivity;
	private DisplayImageOptions options;
	private List<Picture> pictures;

	ImagePagerAdapter(BaseActivity activity, String[] images,
			DisplayImageOptions opt) {
		DisplayUtil displayUtil = new DisplayUtil(activity);
		options = opt;
		this.images = images;
		bActivity = activity;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

	@Override
	public void finishUpdate(View container) {
	}

	@Override
	public int getCount() {
		if (pictures == null) {
			return 0;
		} else {
			return pictures.size();
		}
	}

	@Override
	public Object instantiateItem(View view, int position) {
		Log.e("debug", "instantiateItem");
		final Picture picture = pictures.get(position);
		final View imageLayout = inflater.inflate(R.layout.picture, null);
		final ShaderView imageView = (ShaderView) imageLayout
				.findViewById(R.id.picture_img);
		final ProgressBar spinner = (ProgressBar) imageLayout
				.findViewById(R.id.loading);
		final TextView picture_index = (TextView) imageLayout
				.findViewById(R.id.picture_index);
		final TextView name = (TextView) imageLayout
				.findViewById(R.id.picture_name);
		final TextView review = (TextView) imageLayout
				.findViewById(R.id.picture_review);

		final MyScrollView scrollView = (MyScrollView) imageLayout
				.findViewById(R.id.ScrollView);
		scrollView.setShaderView(imageView);
		String imgUriString = Constant.Image_URL_PREFIX
				+ picture.getImage_url();
		picture_index.setText((position + 1) + "/" + getCount());
		name.setText(picture.getName());
		review.setText(picture.getReview());
		imageView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Log.e("debug", "onLongclick");
				imageView.setShowing(true);
				imageView.invalidate();

				imageView.postInvalidate();
				imageView.invalidate();
				return false;
			}
		});
		bActivity.imageLoader.displayImage(
				Constant.Image_URL_PREFIX + picture.getImage_url() + ".jpg",
				imageView, options, new SimpleImageLoadingListener() {
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
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
						}
						Toast.makeText(bActivity, message, Toast.LENGTH_SHORT)
								.show();

						spinner.setVisibility(View.GONE);
						imageView
								.setImageResource(android.R.drawable.ic_delete);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
						spinner.setVisibility(View.GONE);

						int width = DisplayUtil.getWidth();
						float scale = (float) width
								/ (float) loadedImage.getWidth();
						int height = (int) (loadedImage.getHeight() * scale);
						Bitmap mBitmap = Bitmap.createScaledBitmap(loadedImage,
								width, height, true);
						imageView.config(mBitmap);
						imageView.setImageBitmap(mBitmap);
						
					}

				});

		((ViewPager) view).addView(imageLayout, 0);
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View container) {
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

}