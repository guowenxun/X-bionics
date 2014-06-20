package com.imcore.wenxun.UI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imcore.wenxun.HTTP.HttpHelper;
import com.imcore.wenxun.HTTP.HttpMethod;
import com.imcore.wenxun.HTTP.RequestEntity;
import com.imcore.wenxun.HTTP.ResponseJsonEntity;
import com.imcore.wenxun.model.ProductCategory;
import com.imcore.wenxun.util.ConnectivityUtil;
import com.imcore.wenxun.util.JsonUtil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class ProductDetail extends ActionBarActivity{
	
	private ViewPager mViewPager;
	private List<ProductCategory> mProductCategory;
	private int subNavId,navId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productdetail);
		
		mViewPager=(ViewPager) findViewById(R.id.productdetail_supportviewpager);
		mViewPager.setAdapter(new viewPagerAdapter());
		mViewPager.setOnPageChangeListener(pageChange);
		
		Intent intent = getIntent();
		subNavId = intent.getIntExtra("subNavId", 0);
		navId = intent.getIntExtra("navId", 0);
		
		if (ConnectivityUtil.isOnline(this)) {
			new ProductCategoryAynsyTask().execute(navId, subNavId);
		} else {
			Toast.makeText(ProductDetail.this, "Çë¼ì²éÍøÂç£¡", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private class ProductCategoryAynsyTask extends AsyncTask<Integer,Void,Void>{

		private int status;
		private int id;
		
		@Override
		protected Void doInBackground(Integer... params) {
			int navid = params[0];
			int subnavid = params[1];
			
			String url = "category/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("navId", navid);
			map.put("subNavId", subnavid);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jsonResponse = "";
			try {
				jsonResponse = HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity
						.fromJSON(jsonResponse);
				if (responseJsonEntity.getStatus() == 200) {
					mProductCategory = JsonUtil.toObjectList(responseJsonEntity.getData(),
							ProductCategory.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);			
			ActionBar actionbar=getSupportActionBar();
			actionbar.setNavigationMode(actionbar.NAVIGATION_MODE_TABS);			
			for(int i=0;i<mProductCategory.size();i++){
				Tab tab=actionbar.newTab();
				tab.setText(mProductCategory.get(i).categoryName);
				tab.setTabListener(mtablistener);
				actionbar.addTab(tab);
			}
		}		
		
	}

	private TabListener mtablistener=new TabListener(){

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		        int position=tab.getPosition();
		        mViewPager.setCurrentItem(position);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		
			
		}
		
	};
	
	class viewPagerAdapter extends FragmentStatePagerAdapter{

		public viewPagerAdapter() {
			super(getSupportFragmentManager());
			
		}

		@Override
		public Fragment getItem(int position) {
			ProductDetailGridview mProductDetailGridview=new ProductDetailGridview();
			Bundle bd=new Bundle();
			bd.putInt("id", mProductCategory.get(position).id);
			bd.putInt("navId", navId);
			bd.putInt("subNavId", subNavId);
			mProductDetailGridview.setArguments(bd);
			
			return mProductDetailGridview;
		}

		@Override
		public int getCount() {
			return mProductCategory.size();
		}
		
	}
	
	private OnPageChangeListener pageChange=new OnPageChangeListener(){

		@Override
		public void onPageScrollStateChanged(int position) {
	
			
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
		
			
		}

		@Override
		public void onPageSelected(int position) {
			getSupportActionBar().setSelectedNavigationItem(position);
			
		}
		
		
	};
}
