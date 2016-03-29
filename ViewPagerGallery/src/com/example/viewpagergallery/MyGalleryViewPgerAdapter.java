package com.example.viewpagergallery;

import java.util.List;

import com.sherwin.gallerypager.GalleryViewPagerLayout;
import com.sherwin.gallerypager.GalleryViewPgerAdapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * 实现了适配器
 * @author Sherwin.Ye
 * @data 2016年3月29日 下午4:07:13
 * @desc GalleryViewPgerAdapter.java
 */
public class MyGalleryViewPgerAdapter extends GalleryViewPgerAdapter{
	/**
	 * 数据集
	 */
	private List<Integer> dataList;
	
	public MyGalleryViewPgerAdapter(Context context,List<Integer> dataList,GalleryViewPagerLayout galleryViewPager) {
		super(context, galleryViewPager);
		this.dataList=dataList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList==null?0:dataList.size();
	}

	@Override
	public void getView(View contentView, int position) {
		ImageView imageView=(ImageView) contentView.findViewById(R.id.image);
		imageView.setImageResource(dataList.get(position));
	}

	@Override
	public int getItemLayoutId() {
		return R.layout.item;
	}
}