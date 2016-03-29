package com.example.viewpagergallery;

import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TestActivity extends Activity {

	private static final int MAX_HEIGHT = 500;
	private static final int OFFSET_HEIGHT = 100;

	private LinearLayout container;
	private ViewPager mViewPager;
	int[] imgIds = { R.drawable.bg01, R.drawable.bg02, R.drawable.bg03, R.drawable.bg04, R.drawable.bg05, R.drawable.bg06 };

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		container = (LinearLayout) findViewById(R.id.container);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		//		// 1.设置幕后item的缓存数目  
		mViewPager.setOffscreenPageLimit(3);
		//		// 2.设置页与页之间的间距  
		//		mViewPager.setPageMargin(30);
		// 3.将父类的touch事件分发至viewPgaer，否则只能滑动中间的一个view对象  
		container.setOnTouchListener(new OnTouchListener() {
			private float startX = -1;
			private float delatX = -1;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					delatX = event.getX() - startX;
					break;
				default:
					break;
				}
				if (delatX!=-1&&Math.abs(delatX) <= 30) {
					if (startX < mViewPager.getLeft() && mViewPager.getCurrentItem() > 0) {
						mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
					} else if (startX > mViewPager.getRight() && mViewPager.getCurrentItem() < mViewPager.getAdapter().getCount()-1) {
						mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
					}else{
						return mViewPager.dispatchTouchEvent(event);
					}
					startX=-1;
					delatX=-1;
					return true;
				} else {
					return mViewPager.dispatchTouchEvent(event);
				}
			}
		});
		mViewPager.setAdapter(new ImageAdapter());
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrolled(int position, float degree, int arg2) {
				View view;
				view = mViewPager.findViewWithTag(position);
				if (view != null) {
					ImageView imageView = (ImageView) view.findViewById(R.id.image);
					ViewGroup.LayoutParams params = imageView.getLayoutParams();
					params.height = MAX_HEIGHT - (int) (OFFSET_HEIGHT * degree);
					params.width = MAX_HEIGHT - (int) (OFFSET_HEIGHT * degree);
					imageView.setLayoutParams(params);
				}
				if (position < imgIds.length - 1) {
					view = mViewPager.findViewWithTag(position + 1);
					if (view != null) {
						ImageView imageView = (ImageView) view.findViewById(R.id.image);
						ViewGroup.LayoutParams params = imageView.getLayoutParams();
						params.height = MAX_HEIGHT - (int) (OFFSET_HEIGHT * (1 - degree));
						params.width = MAX_HEIGHT - (int) (OFFSET_HEIGHT * (1 - degree));
						imageView.setLayoutParams(params);
					}
				}
				System.out.println("position:" + position + "  degree:" + degree);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}

	class ImageAdapter extends PagerAdapter {
		
		private LinkedList<View> viewCache;
		
		public ImageAdapter() {
			viewCache=new LinkedList<View>();
			for (int i = 0; i < mViewPager.getOffscreenPageLimit(); i++) {
				viewCache.add(getItemView());
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgIds.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0.equals(arg1);
		}

		// PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
		@Override
		public void destroyItem(ViewGroup view, int position, Object object) {
			((ViewPager) view).removeView((View) object);
			viewCache.add((View) object);
		}

		// 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View contentView;
			if (viewCache.isEmpty()) {
				contentView = getItemView();
			}else{
				contentView=viewCache.removeFirst();
			}
			ImageView imageView = (ImageView) contentView.findViewById(R.id.image);
			imageView.setImageResource(imgIds[position]);
			((ViewPager) view).addView(contentView, 0);
			contentView.setTag(position);
			return contentView;
		}
		
		private View getItemView(){
			View contentView = getLayoutInflater().inflate(R.layout.item, null);
			ImageView imageView = (ImageView) contentView.findViewById(R.id.image);
			ViewGroup.LayoutParams params = imageView.getLayoutParams();
			params.height = MAX_HEIGHT - OFFSET_HEIGHT;
			params.width = MAX_HEIGHT - OFFSET_HEIGHT;
			imageView.setLayoutParams(params);
			return contentView;
		}
	}
}
