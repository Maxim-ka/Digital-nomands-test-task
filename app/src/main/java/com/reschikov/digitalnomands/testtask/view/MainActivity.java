package com.reschikov.digitalnomands.testtask.view;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reschikov.digitalnomands.testtask.R;
import com.reschikov.digitalnomands.testtask.view.fragments.HomeFragment;
import com.reschikov.digitalnomands.testtask.view.fragments.NewsListFragment;
import com.reschikov.digitalnomands.testtask.view.fragments.WebFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
	BottomNavigationView.OnNavigationItemSelectedListener,
	Switchable{

	private static final String TAG_HOME = "Tag home";
	private static final String TAG_NEWS = "Tag news";
	private static final String TAG_WEB = "Tag web";
	@BindView(R.id.nav_view) BottomNavigationView bottomNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		bottomNavigationView.setOnNavigationItemSelectedListener(this);
		if (savedInstanceState == null) loadFragment(new HomeFragment(), TAG_HOME);
	}

	private void loadFragment(Fragment fragment, String tag){
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.frame_master, fragment, tag)
			.commit();
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		
		switch (item.getItemId()) {
			case R.id.navigation_home:
				loadFragment(new HomeFragment(), TAG_HOME);
				return true;
			case R.id.navigation_list:
				if (getSupportFragmentManager().findFragmentByTag(TAG_NEWS) != null) return false;
				loadFragment(new NewsListFragment(), TAG_NEWS);
				return true;
		}
		return false;
	}

	@Override
	public void toggleFragments(String url) {
		loadFragment(WebFragment.newInstance(url), TAG_WEB);
		bottomNavigationView.getMenu().findItem(R.id.navigation_public).setChecked(true);
	}
}
