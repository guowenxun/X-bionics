package com.imcore.wenxun.UI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imcore.wenxun.HTTP.HttpHelper;
import com.imcore.wenxun.HTTP.HttpMethod;
import com.imcore.wenxun.HTTP.RequestEntity;
import com.imcore.wenxun.HTTP.ResponseJsonEntity;
import com.imcore.wenxun.image.ImageFetcher;
import com.imcore.wenxun.model.ProductDetailModel;
import com.imcore.wenxun.util.ConnectivityUtil;
import com.imcore.wenxun.util.JsonUtil;
import com.imcore.wenxun.util.ToastUtil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailGridview extends Fragment{
	
	private GridView mGridView;
	private List<ProductDetailModel> ProductDetailModelList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.productdetialgridview_activity, null);
		
		mGridView=(GridView) view.findViewById(R.id.productdetail_gridview);
		Bundle bundle=getArguments();
		int id=bundle.getInt("id");
		int navId=bundle.getInt("navId");
		int subNavId=bundle.getInt("subNavId");
		
		new gridviewAsyncTask().execute(id,navId,subNavId);
		 
		return view;
	}
	
	private class gridviewAsyncTask extends AsyncTask<Integer,Void,Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			int id=params[0];
			int navid = params[1];
			int subnavid = params[2];
			
			String url="category/products.do";
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", id);
			map.put("navid", navid);
			map.put("subnavid", subnavid);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jsonResponse = "";
			try {
				jsonResponse=HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity=ResponseJsonEntity.fromJSON(jsonResponse);
				if(responseJsonEntity.getStatus()==200){
					ProductDetailModelList=JsonUtil.toObjectList(responseJsonEntity.getData(), ProductDetailModel.class);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mGridView.setAdapter(new girdviewAdapter());
			super.onPostExecute(result);			
		}
		
		
	}
	
	private class girdviewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			ProductDetailModelList.size();
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return ProductDetailModelList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return ProductDetailModelList.get(position).id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=convertView;
			ViewHolder vh=null;
			if(view==null){
			vh=new ViewHolder();
			view=getActivity().getLayoutInflater().inflate(R.layout.productdetialgridview_item_activity, null);
			vh.mImageView=(ImageView) view.findViewById(R.id.productdetial_gridview_item_imageView);
			vh.textName=(TextView) view.findViewById(R.id.productdetial_gridview_item_name);
			vh.textPrice=(TextView) view.findViewById(R.id.productdetial_gridview_item_price);
			view.setTag(vh);
			}else{
				vh=(ViewHolder) view.getTag();
			}
			new ImageFetcher().fetch("http://www.bulo2bulo.com" + ProductDetailModelList.get(position).imageUrl + "_L.jpg", vh.mImageView);
			vh.textName.setText(ProductDetailModelList.get(position).name);
			vh.textPrice.setText("гд"+String.valueOf(ProductDetailModelList.get(position).price));
			return null;
		}
		
	}
	
	class ViewHolder{
		private ImageView mImageView;
		private TextView textName;
		private TextView textPrice;
	}
}
