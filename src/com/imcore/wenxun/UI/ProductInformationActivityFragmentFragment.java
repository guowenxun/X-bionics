package com.imcore.wenxun.UI;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ProductInformationActivityFragmentFragment extends Fragment implements OnCheckedChangeListener{
	
	private List<Fragment> fragmentList;
	private RadioGroup mRadioGroup;
	private Bundle bundle;
	private int id;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.productinformation_fragment_fragment_activity, null);
		
		fragmentList=new ArrayList<Fragment>();
		fragmentList.add(new CommentActivity());
		fragmentList.add(new NewsActivity());
		fragmentList.add(new AwardWinningActivity());
		
		bundle = getArguments();
		id = bundle.getInt("id");
		mRadioGroup=(RadioGroup) view.findViewById(R.id.productinformation_fragment_share);
		mRadioGroup.setOnCheckedChangeListener(this);
		
		
	    return view;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.productinformation_radiobutton_comment:
			gerFragment(0);
			break;
		case R.id.productinformation_radiobutton_news:
			gerFragment(1);
			break;
		case R.id.productinformation_radiobutton_award:
			gerFragment(2);
			break;
		}
		
	}
	
	private void gerFragment(int x){
		Fragment fragment=fragmentList.get(x);
		bundle.putInt("id", id);
		fragment.setArguments(bundle);
		getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.productinformation_share_container, fragment).commit();
	}
}
