# GalleryPager
利用ViewPager实现Gallery效果，中间控件放大效果

Using Gallery to achieve ViewPager, on both sides of the display edge, animation zoom effect

### 版本号：1.1

#### 使用非常简单：
1.创建布局文件
```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <com.sherwin.gallerypager.GalleryViewPagerLayout
        android:id="@+id/gallery_pager_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent" >
    </com.sherwin.gallerypager.GalleryViewPagerLayout>

</RelativeLayout>
```
2.继承GalleryViewPgerAdapter适配器，并实现其中的抽象方法，getView(View contentView, int position)，可以对数据进行绑定，由于contentView已经被设置过Tag,所以这里不建议用ViewHolder
```
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
```
3.创建Activity,并创建适配器
```
galleryViewPagerLayout = (GalleryViewPagerLayout) findViewById(R.id.gallery_pager_layout);
/**
 * 创建适配器
 */
MyGalleryViewPgerAdapter adapter = new MyGalleryViewPgerAdapter(this, imgList, galleryViewPagerLayout);
/**
 * 设置适配器
 */
galleryViewPagerLayout.setAdapter(adapter);
```



至此完成配置，具体在使用中可以根据自定义的Adapter来实现不同的布局和不同的功能需要
