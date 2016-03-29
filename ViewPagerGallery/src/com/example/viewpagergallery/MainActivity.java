package com.example.viewpagergallery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sherwin.gallerypager.GalleryViewPagerLayout;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {


	private GalleryViewPagerLayout galleryViewPagerLayout;
	private Integer[] imgIds = { R.drawable.bg01, R.drawable.bg02, R.drawable.bg03, R.drawable.bg04, R.drawable.bg05, R.drawable.bg06 };
	private List<Integer> imgList=new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		galleryViewPagerLayout = (GalleryViewPagerLayout) findViewById(R.id.gallery_pager_layout);
		imgList.addAll(Arrays.asList(imgIds));
		MyGalleryViewPgerAdapter adapter=new MyGalleryViewPgerAdapter(this, imgList, galleryViewPagerLayout);
		galleryViewPagerLayout.setAdapter(adapter);
	}

}
