package com.imcore.wenxun.UI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imcore.wenxun.HTTP.HttpHelper;
import com.imcore.wenxun.HTTP.HttpMethod;
import com.imcore.wenxun.HTTP.RequestEntity;
import com.imcore.wenxun.HTTP.ResponseJsonEntity;
import com.imcore.wenxun.image.ImageFetcher;
import com.imcore.wenxun.model.Comments;
import com.imcore.wenxun.model.News;
import com.imcore.wenxun.util.JsonUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewsActivity extends Fragment{
	
	private ListView newsListView;
	private List<News> mNewsList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.productinformation_news_listview, null);
		
		newsListView=(ListView) view.findViewById(R.id.productinformation_news_listviewid);
		Bundle bundle = getArguments();
		int id = bundle.getInt("id");
		new newsAsyncTask().execute(id);
		
		return view;
		
	}
	
	private class newsAsyncTask extends AsyncTask<Integer,Void,Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			String url = "news/list.do";
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", params[0]);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jsonResponse=null;
			try {
				jsonResponse=HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity.fromJSON(jsonResponse);
				if(responseJsonEntity.getStatus()==200){
					mNewsList=JsonUtil.toObjectList(responseJsonEntity.getData(), News.class);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			newsListView.setAdapter(new newslistviewadapter());
		}
		
		
		private class newslistviewadapter extends BaseAdapter{

			@Override
			public int getCount() {
				return mNewsList.size();
			}

			@Override
			public Object getItem(int position) {
				return mNewsList.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view=convertView;
				ViewHolder vh=null;
				if(view==null){
					vh=new ViewHolder();
					view=getActivity().getLayoutInflater().inflate(R.layout.productinformation_news_listview_item, null);
					vh.nameTextView=(TextView) view.findViewById(R.id.productinformation_news_item_name_textView);
					vh.timeTextView=(TextView) view.findViewById(R.id.productinformation_news_item_time_textView);
					vh.pictrueImageView=(ImageView) view.findViewById(R.id.productinformation_news_item_imageView);
					view.setTag(vh);
				}else{
					vh=(ViewHolder) view.getTag();
				}
				
				new ImageFetcher().fetch("http://www.bulo2bulo.com"+mNewsList.get(position).imageUrl+"_M.jpg", vh.pictrueImageView);
				vh.nameTextView.setText(mNewsList.get(position).title);
				vh.timeTextView.setText(mNewsList.get(position).updateDate);
				return view;
			}
			
		}
	}
	
	class ViewHolder{
		TextView nameTextView,timeTextView;
		ImageView pictrueImageView;
	}

}
