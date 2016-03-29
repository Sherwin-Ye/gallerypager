package com.example.viewpagergallery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sherwin.gallerypager.GalleryViewPagerLayout;
import android.app.Activity;
import android.os.Bundle;
/**
 * demo
 * @author Sherwin.Ye
 * @data 2016年3月29日 下午5:51:04
 * @desc MainActivity.java
 */
public class MainActivity extends Activity {

	private GalleryViewPagerLayout galleryViewPagerLayout;
	/**
	 * 测试数据 test data
	 */
	private Integer[] imgIds = { R.drawable.bg01, R.drawable.bg02, R.drawable.bg03, R.drawable.bg04, R.drawable.bg05, R.drawable.bg06 };
	/**
	 * 数据集合
	 */
	private List<Integer> imgList = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		galleryViewPagerLayout = (GalleryViewPagerLayout) findViewById(R.id.gallery_pager_layout);
		imgList.addAll(Arrays.asList(imgIds));
		/**
		 * 创建适配器
		 */
		MyGalleryViewPgerAdapter adapter = new MyGalleryViewPgerAdapter(this, imgList, galleryViewPagerLayout);
		/**
		 * 设置适配器
		 */
		galleryViewPagerLayout.setAdapter(adapter);
	}

}
