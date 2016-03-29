package com.sherwin.gallerypager;

import java.util.LinkedList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 抽象类适配器，使用时需要继承
 * @author Sherwin.Ye
 * @data 2016年3月29日 下午4:07:13
 * @desc GalleryViewPgerAdapter.java
 */
public abstract class GalleryViewPgerAdapter extends PagerAdapter{
	
	protected Context mContext;
	/**
	 * 控件layout
	 */
	protected GalleryViewPagerLayout mGalleryViewPagerLayout;
	/**
	 * GalleryViewPagerLayout内部的ViewPager
	 */
	protected ViewPager mViewPager;
	
	/**
	 * View缓存
	 */
	private LinkedList<View> viewCache;
	
	/**
	 * 构造函数
	 * @param context
	 * @param galleryViewPager
	 */
	public GalleryViewPgerAdapter(Context context,GalleryViewPagerLayout galleryViewPager) {
		this.mContext=context;
		this.mGalleryViewPagerLayout=galleryViewPager;
		this.mViewPager=mGalleryViewPagerLayout.getViewPager();
		viewCache=new LinkedList<View>();
	}
	/**
	 * 初始化缓存
	 */
	public void initViewCache(){
		for (int i = 0; i < mViewPager.getOffscreenPageLimit(); i++) {
			viewCache.add(getNewItemView());
		}
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
			contentView = getNewItemView();
		}else{
			contentView=viewCache.removeFirst();
		}
		//执行数据绑定
		getView(contentView, position);
		((ViewPager) view).addView(contentView, 0);
		contentView.setTag(position);
		return contentView;
	}
	/**
	 * 初始化一个新的View
	 * @return
	 */
	private View getNewItemView(){
		ViewGroup contentView = (ViewGroup) LayoutInflater.from(mContext).inflate(getItemLayoutId(), null);
		View animView = contentView.getChildAt(0);
		ViewGroup.LayoutParams params = animView.getLayoutParams();
//		params.height = mGalleryViewPagerLayout.getMaxHeight() - mGalleryViewPagerLayout.getOffsetHeight();
		params.width = mGalleryViewPagerLayout.getMaxWidth() - mGalleryViewPagerLayout.getOffsetWidth();
		animView.setLayoutParams(params);
		return contentView;
	}
	
	/**
	 * 提供给外部设置数据绑定用
	 * @param contentView
	 * @param position
	 */
	public abstract void getView(View contentView, int position);
	/**
	 * 设置布局
	 * @return
	 */
	public abstract int getItemLayoutId();
	
}