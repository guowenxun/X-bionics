package com.imcore.wenxun.UI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imcore.wenxun.HTTP.HttpHelper;
import com.imcore.wenxun.HTTP.HttpMethod;
import com.imcore.wenxun.HTTP.RequestEntity;
import com.imcore.wenxun.HTTP.ResponseJsonEntity;
import com.imcore.wenxun.image.ImageFetcher;
import com.imcore.wenxun.model.Awards;
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

public class AwardWinningActivity extends Fragment{
	
	private List<Awards> AwardsList;
	private ListView awardListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.productinformation_award_listview, null);
		
		awardListView=(ListView) view.findViewById(R.id.productinformation_award_listviewid);
		Bundle bundle = getArguments();
		int id = bundle.getInt("id");
		new awardAsyncTask().execute(id);
		
		return view;
		
	}
	
	private class awardAsyncTask extends AsyncTask<Integer,Void,Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			String url = "honor/list.do";
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", params[0]);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jsonResponse=null;
			try {
				jsonResponse=HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity.fromJSON(jsonResponse);
				if(responseJsonEntity.getStatus()==200){
					AwardsList=JsonUtil.toObjectList(responseJsonEntity.getData(), Awards.class);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			awardListView.setAdapter(new awardlistviewadapter());
		}
		
	}
	
	private class awardlistviewadapter extends BaseAdapter{

		@Override
		public int getCount() {
			return AwardsList.size();
		}

		@Override
		public Object getItem(int position) {
			return AwardsList.get(position);
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
				vh.nameTextView=(TextView) view.findViewById(R.id.productinformation_award_name_textView);
				vh.pictrueImageView=(ImageView) view.findViewById(R.id.productinformation_award_imageView);
				view.setTag(vh);
			}else{
				vh=(ViewHolder) view.getTag();
			}
			new ImageFetcher().fetch("http://www.bulo2bulo.com"+AwardsList.get(position).imageUrl+"_M.jpg", vh.pictrueImageView);
			vh.nameTextView.setText(AwardsList.get(position).title);
				return view;
		}
		
	}
     class ViewHolder{
    	 ImageView pictrueImageView;
    	 TextView nameTextView;
     }
}
