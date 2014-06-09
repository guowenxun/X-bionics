package com.imcore.wenxun.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.imcore.wenxun.HTTP.HttpHelper;
import com.imcore.wenxun.HTTP.HttpMethod;
import com.imcore.wenxun.util.JsonUtil;
import com.imcore.wenxun.HTTP.RequestEntity;
import com.imcore.wenxun.HTTP.ResponseJsonEntity;
import com.imcore.wenxun.image.ImageFetcher;
import com.imcore.wenxun.model.Category;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductBuyActivity extends Activity {
	
	private ExpandableListView mExpandableListView;
	private List<Integer> list;
	private List<Category> mFirst;
	private List<Category> mSecond;
	private List<List<Category>> childList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productbuy_activity);
		mExpandableListView = (ExpandableListView) findViewById(R.id.productbuy_expandablelistview);
		mExpandableListView.setGroupIndicator(null);
		startExpandableListview();
	}

	private void startExpandableListview() {
		childList = new ArrayList<List<Category>>();
		
		list = new ArrayList<Integer>();
		list.add(R.drawable.upbackground);
		list.add(R.drawable.downbackground);
		
		new getCategory().execute(100001);
		new getCategory().execute(100002);
	}
	
	class getCategory extends AsyncTask<Integer, Void, Void>{
		
		private int state;
		private int requestCode;
		private String url;
		
		@Override
		protected Void doInBackground(Integer... params){
			requestCode = params[0];
		    url = "/category/list.do";
	
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("navId", params[0]);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, args);
			String jsonResponse = null;
			try {
				jsonResponse = HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity
						.fromJSON(jsonResponse);
				state = responseJsonEntity.getStatus();
				if (state == 200) {
					String data = responseJsonEntity.getData();
					
					if(params[0] == 100001) {
						mFirst = JsonUtil.toObjectList(data, Category.class);
					}else if (params[0]==100002) {
						mSecond = JsonUtil.toObjectList(data, Category.class);
					}
					for (Category firstCategory : mFirst) {
						Log.i("name", firstCategory.name);
						Log.i("image", firstCategory.imageUrl);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			if(requestCode == 100001) {
				childList.add(mFirst);
			}else if(requestCode == 100002){
				childList.add(mSecond);
			}
			mExpandableListView.setAdapter(new expandablelistviewAdapter());
			super.onPostExecute(result);
		}
	}
	
	private class expandablelistviewAdapter extends BaseExpandableListAdapter{

		@Override
		public int getGroupCount() {
			return list.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return 1;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return list.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			convertView = getLayoutInflater().inflate(R.layout.productbuy_expandablelistview_group_activity, null);
			ImageView imageView = (ImageView)convertView.findViewById(R.id.productbuy_expandablelistview_group_button);
			imageView.setImageResource(list.get(groupPosition));
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			convertView = getLayoutInflater().inflate(R.layout.productbuy_expandablelistview_gridview, null);
			GridView mGridView = (GridView) convertView.findViewById(R.id.expandablelistview_gridview);
			mGridView.setAdapter(new gridviewAdapter(groupPosition));
			return convertView;
		}
		
		class gridviewAdapter extends BaseAdapter{
			
			private int index;
			
			gridviewAdapter(int index){
				this.index = index;
			}

			@Override
			public int getCount() {
				return childList.get(index).size();
			}

			@Override
			public Object getItem(int position) {
				return childList.get(index).get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView = getLayoutInflater().inflate(R.layout.poductbuy_expandablelistview_activity, null);
				ImageView imageView = (ImageView) 
						convertView.findViewById(R.id.expandablelistview_imageview);
				new ImageFetcher().fetch(HttpHelper.IMAGE_URL
						+ childList.get(index).get(position).imageUrl
						+ "_L.png", imageView);
				TextView textView = (TextView)
						convertView.findViewById(R.id.expandablelistview_textview);
				textView.setText(childList.get(index).get(position).name);
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(ProductBuyActivity.this,ProductDetail.class);
						startActivity(intent);
					}
				});
				return convertView;
			}
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
		
	};


}
