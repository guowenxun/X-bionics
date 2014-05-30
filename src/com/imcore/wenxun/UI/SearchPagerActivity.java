package com.imcore.wenxun.UI;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.os.Build;

public class SearchPagerActivity extends Activity {

	private GridView mGridView;
	private Button productButton;
	private EditText productEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchpager_activity);

		mGridView=(GridView) findViewById(R.id.product_search_gridview);
		productButton=(Button) findViewById(R.id.product_find_button);
		productEditText=(EditText) findViewById(R.id.product_search_edittext);
		
		
		}
	}

	