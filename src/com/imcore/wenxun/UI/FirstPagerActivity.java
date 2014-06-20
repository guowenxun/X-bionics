package com.imcore.wenxun.UI;

import java.util.ArrayList;
import java.util.List;

import com.imcore.wenxun.UI.R.drawable;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FirstPagerActivity extends Activity implements OnClickListener {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private List<String> mList;
	private Button drawerListButton, introduceButton, productButton,
			storyButton, actionButton, tribesquareButton, searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstpager_activity);

		mList = new ArrayList<String>();
		mList.add("您的订购");
		mList.add("账户设置");
		mList.add("达人申请");
		mList.add("部落社区");
		mList.add("购物车");
		mList.add("订阅信息");
		mList.add("分享设置");

		ButtonClick();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.firstpager_drawerlayout);
		mDrawerList = (ListView) findViewById(R.id.drawerlayout_list);
		mDrawerList.setAdapter(new drawerlistAdapter());
		mDrawerList.setOnItemClickListener(item);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_launcher, R.string.drawer_open,
				R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	class drawerlistAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			view = getLayoutInflater().inflate(
					R.layout.firstpager_item_activity, null);
			TextView textView = (TextView) view
					.findViewById(R.id.firstpage_item_textview);
			textView.setText(mList.get(position));

			return view;
		}

	}

	private void ButtonClick() {
		drawerListButton = (Button) findViewById(R.id.drawerlist_button);
		drawerListButton.setOnClickListener(this);
		tribesquareButton = (Button) findViewById(R.id.tribesquare_button);
		tribesquareButton.setOnClickListener(this);
		searchButton = (Button) findViewById(R.id.search_button);
		searchButton.setOnClickListener(this);
		productButton = (Button) findViewById(R.id.product_buy_button);
		productButton.setOnClickListener(this);
		storyButton = (Button) findViewById(R.id.man_story_button);
		storyButton.setOnClickListener(this);
		actionButton = (Button) findViewById(R.id.X_action_button);
		actionButton.setOnClickListener(this);
		introduceButton = (Button) findViewById(R.id.X_introduce_button);
		introduceButton.setOnClickListener(this);

	}

	public OnItemClickListener item = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 3:
				Intent intent = new Intent(FirstPagerActivity.this,
						LoginMainActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.drawerlist_button:
			mDrawerLayout.openDrawer(mDrawerList);
			break;
		case R.id.product_buy_button:
			Intent product = new Intent(FirstPagerActivity.this,
					ProductBuyActivity.class);
			startActivity(product);
			break;
		case R.id.X_introduce_button:
			Intent introduce = new Intent(FirstPagerActivity.this,
					IntroductionHomeActivity.class);
			startActivity(introduce);
			break;
		case R.id.tribesquare_button:
			break;
		case R.id.search_button:
			Intent search = new Intent(FirstPagerActivity.this,
					SearchPagerActivity.class);
			startActivity(search);
			break;
		}

	}
}
