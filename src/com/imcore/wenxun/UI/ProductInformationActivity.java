package com.imcore.wenxun.UI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imcore.wenxun.HTTP.HttpHelper;
import com.imcore.wenxun.HTTP.HttpMethod;
import com.imcore.wenxun.HTTP.RequestEntity;
import com.imcore.wenxun.HTTP.ResponseJsonEntity;
import com.imcore.wenxun.image.ImageFetcher;
import com.imcore.wenxun.model.Labs;
import com.imcore.wenxun.model.ProductGalleryImage;
import com.imcore.wenxun.model.Products;
import com.imcore.wenxun.model.SizeList;
import com.imcore.wenxun.model.SizeStandard;
import com.imcore.wenxun.model.SizeStandardDetailList;
import com.imcore.wenxun.model.Storage;
import com.imcore.wenxun.model.SysColorList;
import com.imcore.wenxun.util.ConnectivityUtil;
import com.imcore.wenxun.util.JsonUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ProductInformationActivity extends FragmentActivity implements OnCheckedChangeListener,OnClickListener{
	
	private List<ProductGalleryImage> GalleryList;
	private List<SysColorList> mSysColorList;
	private List<SizeList> mSizeList;
	private List<Labs> mLabslist;
	private List<SizeStandardDetailList> mSizeStandardDetailList;
	private Gallery mGallery;
	private TextView nameTextView,priceTextView;
	private Button shoppingcartButton,searchButton,accountButton;
	private RadioGroup colorRadioGroup,sizeRadioGroup;
	private EditText mEditText;
	private Products mProducts;
	private Storage mStorage;
	private SizeStandard mSizeStandard;
	private int id;
	private int[] color;
	private int[] size;
	private int colorIndex = 19;
	private int sizeIndex = 19;
	private ListView sizeListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productinfomation_activity);
		
		Intent intent = getIntent();
		int id  = intent.getIntExtra("id", 0);
		Bundle bundle = new Bundle();
		bundle.putInt("id", id);
		productInformationActivityFragment mproductInformationActivityFragment=new productInformationActivityFragment();
		mproductInformationActivityFragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction().add(R.id.productinformation_right_drawer, mproductInformationActivityFragment).commit();
		
		mGallery=(Gallery) findViewById(R.id.productinformation_gallery);
		nameTextView=(TextView) findViewById(R.id.productinformation_productname);
		priceTextView=(TextView) findViewById(R.id.productinformation_textview_price);
		colorRadioGroup=(RadioGroup) findViewById(R.id.productinformation_radiogroup_color);
		sizeRadioGroup=(RadioGroup) findViewById(R.id.productinformation_radiogroup_size);
		mEditText=(EditText) findViewById(R.id.productinformation_edittext);
		shoppingcartButton=(Button) findViewById(R.id.productinformation_entershoppingcart);
		searchButton=(Button) findViewById(R.id.productinformation_button_search);
		accountButton=(Button) findViewById(R.id.productinformation_button_account);
		
		shoppingcartButton.setOnClickListener(this);
		searchButton.setOnClickListener(this);
		accountButton.setOnClickListener(this);
		
		if (ConnectivityUtil.isOnline(this)) {
			new getImage().execute(id);
			new getProductDetail().execute(id);
			new getScience().execute(id);
			new getSize().execute(id);
		} else{
			Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
		}
	}
	
	private class getImage extends AsyncTask<Integer , Void, Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			String url = "product/images/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", params[0]);
			RequestEntity  request = new RequestEntity(url, HttpMethod.GET, map);
			String jsonResponse =null;
			try {
				jsonResponse = HttpHelper.execute(request);
				ResponseJsonEntity JsonEntity = ResponseJsonEntity
						.fromJSON(jsonResponse);
				if(JsonEntity.getStatus() == 200){
					GalleryList = JsonUtil.toObjectList(JsonEntity.getData(), ProductGalleryImage.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mGallery.setAdapter(new galleryadapter());
			mGallery.setSpacing(100);
		}
	}
	private class galleryadapter extends BaseAdapter{

		@Override
		public int getCount() {
			return GalleryList.size();
		}

		@Override
		public Object getItem(int position) {
			return GalleryList.get(position);
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
			   view=getLayoutInflater().inflate(R.layout.productinformation_gallery_activity, null);
			   vh.galleryImageView=(ImageView) view.findViewById(R.id.productinformation_gallery_imageview);
			   view.setTag(vh);
			}else{
				vh=(ViewHolder) view.getTag();
			}
			new ImageFetcher().fetch("http://www.bulo2bulo.com"+GalleryList.get(position).imageUrl+"_M.jpg", vh.galleryImageView);
			return view;
		}
		
	}
	
	class ViewHolder{
		ImageView galleryImageView;
	}
	
	
	private class getProductDetail extends AsyncTask<Integer , Void, Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			String url = "product/get.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", params[0]);
			RequestEntity  request = new RequestEntity(url, HttpMethod.GET, map);
			String jsonResponse = null;
			try {
				jsonResponse = HttpHelper.execute(request);
				ResponseJsonEntity JsonEntity = ResponseJsonEntity
						.fromJSON(jsonResponse);
				if(JsonEntity.getStatus() == 200){
					mProducts = JsonUtil.toObject(JsonEntity.getData(),Products.class);
					String color=mProducts.sysColorList;
					mSysColorList = JsonUtil.toObjectList(color,SysColorList.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
				nameTextView.setText(mProducts.name);
				priceTextView.setText("￥"+String.valueOf(mProducts.price));
				colorRadioGroup.setOnCheckedChangeListener(ProductInformationActivity.this);
				color = new int[mSysColorList.size()];
				for(int i= 0;i <mSysColorList.size();i++){
					RadioButton radiobut = new RadioButton(ProductInformationActivity.this);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
					layoutParams.height = 47;
					layoutParams.width = 47;
					layoutParams.leftMargin = 100;
					layoutParams.setMargins(80, 80, 80, 80);
					radiobut.setLayoutParams(layoutParams);
					radiobut.setId(colorRadioGroup.hashCode() + i);
					radiobut.requestLayout();
					radiobut.setButtonDrawable(null);
//					radiobut.setBackgroundResource(R.drawable.radiobut_bg);
					int id = radiobut.getId();
					color[i] = id;
//					new ImageFetcher().fetch("http://www.bulo2bulo.com" + mSysColorList.get(i).colorImage
//							+ ".jpg", radiobut);
					colorRadioGroup.addView(radiobut);
				}
				sizeRadioGroup.setOnCheckedChangeListener(ProductInformationActivity.this);
				size = new int[mSizeList.size()];
				for(int i= 0;i <mSizeList.size();i++){
					RadioButton radiobut = new RadioButton(ProductInformationActivity.this);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							ViewGroup.LayoutParams.WRAP_CONTENT,
							ViewGroup.LayoutParams.WRAP_CONTENT);
					layoutParams.height = 40;
					layoutParams.width = 60;
//					layoutParams.setMargins(50, 50, 50, 50);
					radiobut.setLayoutParams(layoutParams);
					((MarginLayoutParams) radiobut.getLayoutParams())
							.setMargins(30, 0, 30, 0);
					radiobut.setId(sizeRadioGroup.hashCode() + i);
					radiobut.setBackgroundResource(R.drawable.radiobutton_selector);
					radiobut.setButtonDrawable(R.drawable.radiobutton_selector);
					radiobut.setText(mSizeList.get(i).size);
					radiobut.setGravity(Gravity.CENTER);
					size[i] = radiobut.getId();
					radiobut.requestLayout();
					sizeRadioGroup.addView(radiobut);
				}
			
			super.onPostExecute(result);
		}
				
	}


	private class getScience extends AsyncTask<Integer , Void, Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			String url = "product/labs/list.do";
			Map<String, Object>map = new HashMap<String, Object>();
			map.put("id", params[0]);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jsonResponse =null;
			try {
				jsonResponse = HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity
						.fromJSON(jsonResponse);
				if (responseJsonEntity.getStatus() == 200) {
					mLabslist = JsonUtil.toObjectList(responseJsonEntity.getData(), Labs.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			for(int i = 0; i < mLabslist.size(); i++){
				View view = getLayoutInflater().inflate(R.layout.productinformation_scient_listview_item, null);
				ImageView scientImageView = (ImageView) view.findViewById(R.id.productinformation_scient_listview_imageview);
				TextView scientTextView = (TextView) view.findViewById(R.id.productinformation_scient_listview_textView);
				scientTextView.setText(mLabslist.get(i).title);
				new ImageFetcher().fetch("http://www.bulo2bulo.com"+mLabslist.get(i).imageUrl+"_S.jpg", scientImageView);
			}
			super.onPostExecute(result);
		}
	}
	
	private class getSize extends AsyncTask<Integer , Void, Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			String url = "product/size/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", params[0]);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jsonResponse = null;
			try {
				jsonResponse = HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity
						.fromJSON(jsonResponse);
				if (responseJsonEntity.getStatus() == 200) {
					mSizeStandard = JsonUtil.toObject(responseJsonEntity.getData(),
							SizeStandard.class);
					String detial = mSizeStandard.sizeStandardDetailList;
					String sizeDetial = mSizeStandard.sysSizeList;
					mSizeList = JsonUtil.toObjectList(sizeDetial,
							SizeList.class);
					mSizeStandardDetailList = JsonUtil.toObjectList(detial,
							SizeStandardDetailList.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			sizeListView = (ListView) findViewById(R.id.productinformation_listview_sizeid);
			sizeListView.setAdapter(new sizeListViewAdapter());
		}

	}
	
	private class sizeListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mSizeStandardDetailList.size();
		}

		@Override
		public Object getItem(int position) {
			return mSizeStandardDetailList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			view = getLayoutInflater().inflate(R.layout.productinformation_size_listview, null);
			TextView tvid = (TextView) view.findViewById(R.id.tv_size_1);
			TextView tvsizeid = (TextView) view.findViewById(R.id.tv_size_2);
			TextView tvsize = (TextView) view.findViewById(R.id.tv_size_3);
			TextView tvp1 = (TextView) view.findViewById(R.id.tv_size_4);
			TextView tvp2 = (TextView) view.findViewById(R.id.tv_size_5);
			TextView tvp3 = (TextView) view.findViewById(R.id.tv_size_6);
			TextView tvp4 = (TextView) view.findViewById(R.id.tv_size_7);
			TextView tvp5 = (TextView) view.findViewById(R.id.tv_size_8);
			TextView tvp6 = (TextView) view.findViewById(R.id.tv_size_9);
			TextView tvp7 = (TextView) view.findViewById(R.id.tv_size_10);
			TextView tvp8 = (TextView) view.findViewById(R.id.tv_size_11);
			TextView tvp9 = (TextView) view.findViewById(R.id.tv_size_12);
			TextView tvp10 = (TextView) view.findViewById(R.id.tv_size_13);
			TextView tvp11 = (TextView) view.findViewById(R.id.tv_size_14);
			
			tvid.setText(String.valueOf(mSizeStandardDetailList.get(position).id));
			tvsizeid.setText(String.valueOf(mSizeStandardDetailList.get(position).sizeStandardId));
			tvsize.setText(String.valueOf(mSizeStandardDetailList.get(position).size));
			tvp1.setText(String.valueOf(mSizeStandardDetailList.get(position).p1));
			tvp2.setText(String.valueOf(mSizeStandardDetailList.get(position).p2));
			tvp3.setText(String.valueOf(mSizeStandardDetailList.get(position).p3));
			tvp4.setText(String.valueOf(mSizeStandardDetailList.get(position).p4));
			tvp5.setText(String.valueOf(mSizeStandardDetailList.get(position).p5));
			tvp6.setText(String.valueOf(mSizeStandardDetailList.get(position).p6));
			tvp7.setText(String.valueOf(mSizeStandardDetailList.get(position).p7));
			tvp8.setText(String.valueOf(mSizeStandardDetailList.get(position).p8));
			tvp9.setText(String.valueOf(mSizeStandardDetailList.get(position).p9));
			tvp10.setText(String.valueOf(mSizeStandardDetailList.get(position).p10));
			tvp11.setText(String.valueOf(mSizeStandardDetailList.get(position).p11));
			return view;
		}
		
	}
	
	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		for (int i = 0; i < color.length; i++) {
			if (checkedId == color[i]) {
				colorIndex = i;
				Log.i("color", mSysColorList.get(i).color);
			}
		}
		for (int i = 0; i < size.length; i++) {
			if (checkedId == size[i]) {
				sizeIndex = i;
				Log.i("size", mSizeList.get(i).size);
			}
		}
		if(colorIndex != 19 && sizeIndex != 19){
			if (ConnectivityUtil.isOnline(this)) {
				new AddToShoppingcart().execute(id, mSysColorList.get(colorIndex).id,
						mSizeList.get(sizeIndex).id);
			} else {
				Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private class AddToShoppingcart extends AsyncTask<Integer , Void, Integer>{

		private int productQuantityId;
		
		@Override
		protected Integer doInBackground(Integer... params) {
			
			String url = "shoppingcart/list.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", params[0]);
			map.put("colorId", params[1]);
			map.put("sizeId", params[2]);
			RequestEntity entity = new RequestEntity(url, HttpMethod.GET, map);
			String jsonResponse = "";
			int status = 0;
			try {
				jsonResponse = HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity
						.fromJSON(jsonResponse);
				if (responseJsonEntity.getStatus() == 200) {
					String data = responseJsonEntity.getData();
					mStorage = JsonUtil.toObject(data,
							Storage.class);
					productQuantityId = mStorage.id;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return status;
		}
		@Override
		protected void onPostExecute(Integer result) {			
				new AddShoppingcart().execute(productQuantityId);			
			super.onPostExecute(result);
		}
	}
	
	private class AddShoppingcart extends AsyncTask<Integer , Void, Integer>{

		@Override
		protected Integer doInBackground(Integer... prams) {
			String url = "shoppingcart/update.do";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("token", TribeLoginActivity.token);
			map.put("userId", TribeLoginActivity.userId);
			map.put("productQuantityId",prams[0]);
			map.put("qty", mEditText.getText().toString());
			RequestEntity entity = new RequestEntity(url, HttpMethod.POST, map);
			String json = "";
			int status = 0;
			try {
				json = HttpHelper.execute(entity);
				ResponseJsonEntity responseJsonEntity = ResponseJsonEntity
						.fromJSON(json);
				status = responseJsonEntity.getStatus();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return status;
		}
		@Override
		protected void onPostExecute(Integer result) {
			AlertDialog.Builder builder = new AlertDialog.Builder(ProductInformationActivity.this);
			if (result == 200) {
				builder.setTitle("购物车").setMessage("添加成功")
						.setPositiveButton("确定", null).create().show();
			}else{
				Toast.makeText(ProductInformationActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
			}
			
			super.onPostExecute(result);
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.productinformation_entershoppingcart:
			if (ConnectivityUtil.isOnline(this)) {
				new AddShoppingcart().execute(id, mSysColorList.get(colorIndex).id,
						mSizeList.get(sizeIndex).id);
			} else {
				Toast.makeText(this,"请检查网络", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
}