package com.sherwin.gallerypager;

import com.nineoldandroids.view.ViewHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 实现画廊式ViewPager
 * @author Sherwin.Ye
 * @data 2016年3月29日 下午2:48:54
 * @desc GalleryViewPager.java
 */
@SuppressLint("NewApi")
public class GalleryViewPagerLayout extends LinearLayout {

	private Context mContext;

	private ViewPager mViewPager;
	/**
	 * 适配器
	 */
	private GalleryViewPgerAdapter adapter;
	
	/**
	 * pageChangeh回调
	 */
	private OnPageChangeListener onPageChangeListener;
	/**
	 * viewpager的参数
	 */
	private LayoutParams params;

	/**
	 * 当前控件宽度
	 */
	private int layoutWidth;

	/**
	 * 设置最大宽度
	 */
	private int maxWidth = 500;
	/**
	 * 设置最大高度
	 */
	private int maxHeight = 500;

	/**
	 * 缩放比例0-1.0
	 */
	private float scaleSize = 0.7f;
	/**
	 * 设置动画垂直偏移量
	 */
	private int offsetHeight = 100;

	/**
	 * 两边距离
	 */
	private int offsetSide = 120;

	/**
	 * 页面之间的间距
	 */
	private int pageMargin = 0;
	/**
	 * 是否允许点击两侧切换
	 */
	private boolean enableClickSide = false;
	/**
	 * 是否允许缩放动画
	 */
	private boolean enableScale = true;
	/**
	 * 判断当前控件是否已经绘制
	 */
	private boolean isOnLayout = false;

	public GalleryViewPgerAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(GalleryViewPgerAdapter adapter) {
		this.adapter = adapter;
		if (isOnLayout) {//已经绘制时才直接设置，否则当绘制时才设置
			this.mViewPager.setAdapter(adapter);
		}
	}
	
	public OnPageChangeListener getOnPageChangeListener() {
		return onPageChangeListener;
	}

	public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
		this.onPageChangeListener = onPageChangeListener;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	/**
	 * 设置item最大宽度
	 * @param maxWidth
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	/**
	 * 设置最大高度
	 * @param maxHeight
	 */
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public float getScaleSize() {
		return scaleSize;
	}

	/**
	 * 设置缩放到的比例
	 * @param scaleSize
	 */
	public void setScaleSize(float scaleSize) {
		this.scaleSize = scaleSize;
	}

	public int getOffsetHeight() {
		return offsetHeight;
	}

	/**
	 * 设置item动画垂直偏移宽度
	 * @param offsetHeight
	 */
	public void setOffsetHeight(int offsetHeight) {
		this.offsetHeight = offsetHeight;
	}

	public int getOffsetSide() {
		return offsetSide;
	}

	public void setOffsetSide(int offsetSide) {
		this.offsetSide = offsetSide;
		maxWidth = layoutWidth - offsetSide * 2;
	}

	public int getPageMargin() {
		return pageMargin;
	}

	public void setPageMargin(int pageMargin) {
		this.pageMargin = pageMargin;
	}

	public boolean isEnableClickSide() {
		return enableClickSide;
	}

	public void setEnableClickSide(boolean enableClickSide) {
		this.enableClickSide = enableClickSide;
	}

	public boolean isEnableScale() {
		return enableScale;
	}

	public void setEnableScale(boolean enableScale) {
		this.enableScale = enableScale;
	}

	public ViewPager getViewPager() {
		return mViewPager;
	}

	public GalleryViewPagerLayout(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	public GalleryViewPagerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	private void init() {
		Resources resources = mContext.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		layoutWidth = dm.widthPixels;
		maxWidth = layoutWidth - offsetSide * 2;
		maxHeight = maxWidth * 4 / 3;
		/************** LinearLayout的相关配置 *****************************/
		setClipChildren(false);
		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER);
		setLayerType(LAYER_TYPE_SOFTWARE, new Paint());

		/************** viewpager的相关配置 *****************************/
		mViewPager = new ViewPager(mContext);
		params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.leftMargin = offsetSide;
		params.rightMargin = offsetSide;
		mViewPager.setOverScrollMode(OVER_SCROLL_NEVER);
		mViewPager.setLayoutParams(params);
		mViewPager.setClipChildren(false);

		// 1.设置幕后item的缓存数目  
		mViewPager.setOffscreenPageLimit(3);
		// 2.设置页与页之间的间距  
		mViewPager.setPageMargin(pageMargin);
		this.addView(mViewPager);
		setTouchEvent();
		setPageChangeListener();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		layoutWidth = r - l;
		maxWidth = layoutWidth - offsetSide * 2;
		maxHeight = maxWidth * 4 / 3;
		if (adapter != null && !isOnLayout) {//只会执行一次
			isOnLayout = true;
			adapter.initViewCache();
			this.mViewPager.setAdapter(adapter);
		}
	}

	/**
	 * 设置PageChangeListener
	 */
	@SuppressWarnings("deprecation")
	private void setPageChangeListener() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				if (onPageChangeListener!=null) {
					onPageChangeListener.onPageSelected(position);
				}
			}

			@Override
			public void onPageScrolled(int position, float degree, int arg2) {
				if (adapter == null || !enableScale) {
					return;
				}
				if (onPageChangeListener!=null) {
					onPageChangeListener.onPageScrolled(position, degree, arg2);
				}
				ViewGroup contentView = (ViewGroup) mViewPager.findViewWithTag(position);
				if (contentView != null) {
					ViewHelper.setScaleX(contentView, 1 - degree * (1 - scaleSize));
					ViewHelper.setScaleY(contentView, 1 - degree * (1 - scaleSize));
				}
				if (position < adapter.getCount() - 1) {
					contentView = (ViewGroup) mViewPager.findViewWithTag(position + 1);
					if (contentView != null) {
//						View animView = contentView.getChildAt(0);
						ViewHelper.setScaleX(contentView, 1 - (1 - degree) * (1 - scaleSize));
						ViewHelper.setScaleY(contentView, 1 - (1 - degree) * (1 - scaleSize));
					}
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (onPageChangeListener!=null) {
					onPageChangeListener.onPageScrollStateChanged(state);
				}
			}
		});

	}

	/**
	 * 初始化LinearLayout的Event
	 */
	private void setTouchEvent() {
		// 3.将父类的touch事件分发至viewPgaer，否则只能滑动中间的一个view对象  
		this.setOnTouchListener(new OnTouchListener() {
			private float startX = -1;
			private float delatX = -1;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (!enableClickSide) {//是否允许点击两侧切换
					return mViewPager.dispatchTouchEvent(event);
				}
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
				if (delatX != -1 && Math.abs(delatX) <= 30) {
					if (startX < mViewPager.getLeft() && mViewPager.getCurrentItem() > 0) {
						mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
					} else if (startX > mViewPager.getRight() && mViewPager.getCurrentItem() < mViewPager.getAdapter().getCount() - 1) {
						mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
					} else {
						return mViewPager.dispatchTouchEvent(event);
					}
					startX = -1;
					delatX = -1;
					return true;
				} else {
					return mViewPager.dispatchTouchEvent(event);
				}
			}
		});
	}
}
