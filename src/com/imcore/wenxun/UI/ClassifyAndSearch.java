package com.imcore.wenxun.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassifyAndSearch extends Activity implements OnClickListener{
	
	private Button sortButton,filteringButton,drawerlistButton,searchButton;
	private GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classifyandsearch_activity);
		
		mGridView=(GridView) findViewById(R.id.classifyandsearch_gridview);
		mGridView.setAdapter(new gridviewAdapter());
		sortButton=(Button) findViewById(R.id.chassifyandseatch_sort_button);
		filteringButton=(Button) findViewById(R.id.chassifyandseatch_filtering_button);
		
		sortButton.setOnClickListener(this);
		filteringButton.setOnClickListener(this);
  }
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.chassifyandseatch_sort_button:
			sort();
			break;
		case R.id.chassifyandseatch_filtering_button:
			filtering();
			break;
		}
	}
	
	private void sort(){
		
	}
	
	private void filtering(){
		
	}
	
	class gridviewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=convertView;
			viewHolder vh=null;
			if(view==null){
				view=getLayoutInflater().inflate(R.layout.classifyandsearch_children_activity, null);
				vh=new viewHolder();
				vh.mImageView=(ImageView) findViewById(R.id.classifyandsearch_children_imageView);
				vh.nameTextView=(TextView) findViewById(R.id.classifyandsearch_children_name_textView);
				vh.priceTextView=(TextView) findViewById(R.id.classifyandsearch_children_price_textView);
				view.setTag(vh);
			}else{
				vh=(viewHolder) view.getTag();
			}
			vh.mImageView.setImageResource(R.drawable.test1);
			vh.nameTextView.setText("仿生绿色自行车骑行服");
			vh.priceTextView.setText(2199);
			
			return view;
		}
		
	}
	
	class viewHolder {
		ImageView mImageView;
		TextView nameTextView,priceTextView;
	}

	
}
