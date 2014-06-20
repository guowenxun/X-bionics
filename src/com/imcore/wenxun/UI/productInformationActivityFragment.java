package com.imcore.wenxun.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class productInformationActivityFragment extends Fragment implements OnCheckedChangeListener{
	
	private RadioGroup mRadioGroupContainer,mRadioGroupShare;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.productinformation_fragment_activity, null);
		
		mRadioGroupContainer = (RadioGroup) view.findViewById(R.id.productinformation_radiogroup_detail);
		mRadioGroupContainer.setOnCheckedChangeListener(this);
		Bundle bundle = getArguments();
		int id = bundle.getInt("id");
		ProductInformationActivityFragmentFragment mProductInformationActivityFragmentFragment=new ProductInformationActivityFragmentFragment();
		bundle.putInt("id", id);
		mProductInformationActivityFragmentFragment.setArguments(bundle);
		getFragmentManager().beginTransaction().add(R.id.productinformation_fragment_container, mProductInformationActivityFragmentFragment).commit();
		
		return view;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		Intent intent=null;
		switch(checkedId){
		case R.id.productinformation_radiobutton_share:		
			break;
		case R.id.productinformation_radiobutton_shoppingcart:
			intent=new Intent(getActivity(),MyShoppingCart.class);
			startActivity(intent);
			break;
		case R.id.productinformation_radiobutton_buy:
			break;
		case R.id.productinformation_radiobutton_collection:
			break;
		}
	}
}
