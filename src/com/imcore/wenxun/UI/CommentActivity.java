package com.imcore.wenxun.UI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imcore.wenxun.HTTP.HttpHelper;
import com.imcore.wenxun.HTTP.HttpMethod;
import com.imcore.wenxun.HTTP.RequestEntity;
import com.imcore.wenxun.HTTP.ResponseJsonEntity;
import com.imcore.wenxun.model.Comments;
import com.imcore.wenxun.util.JsonUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommentActivity extends Fragment{
	
	private ListView commentListview;
	private List<Comments> mCommentsList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.productinformation_comment_listview, null);
		
		commentListview=(ListView) view.findViewById(R.id.productinformation_comment_listviewid);
		Bundle bundle = getArguments();
		int id = bundle.getInt("id");
		new commentAsyncTask().execute(id);
		
		return view;
		
	}
	
	private class commentAsyncTask extends AsyncTask<Integer,Void,Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			String url = "product/comments/list.do";
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", params[0]);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jsonResponse=null;
			try {
				jsonResponse=HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity.fromJSON(jsonResponse);
				if(responseJsonEntity.getStatus()==200){
					mCommentsList=JsonUtil.toObjectList(responseJsonEntity.getData(), Comments.class);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			commentListview.setAdapter(new comentlistviewadapter());
		}		
		
	}
	
	private class comentlistviewadapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mCommentsList.size();
		}

		@Override
		public Object getItem(int position) {
			return mCommentsList.get(position);
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
				view=getActivity().getLayoutInflater().inflate(R.layout.productinformation_comment_listview_item, null);
				vh.containTextView=(TextView) view.findViewById(R.id.productinformation_comment_contain_textView);
				vh.colorTextView=(TextView) view.findViewById(R.id.productinformation_comment_color_textView);
				vh.sizeTextView=(TextView) view.findViewById(R.id.productinformation_comment_size_textView);
				vh.nameTextView=(TextView) view.findViewById(R.id.productinformation_comment_name_textView);
				vh.dateTextView=(TextView) view.findViewById(R.id.productinformation_comment_date_textView);
				view.setTag(vh);
			}else{
				vh=(ViewHolder) view.getTag();
			}
			    vh.containTextView.setText(mCommentsList.get(position).comment);
			    vh.dateTextView.setText(mCommentsList.get(position).commentDate);
			    vh.nameTextView.setText(mCommentsList.get(position).Name);
			return view;
		}
		
		
	}
	
	class ViewHolder{
		TextView containTextView,nameTextView,sizeTextView,dateTextView,colorTextView;
	}
}
