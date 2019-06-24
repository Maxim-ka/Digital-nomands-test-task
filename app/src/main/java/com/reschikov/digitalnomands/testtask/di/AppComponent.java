package com.reschikov.digitalnomands.testtask.di;

import com.reschikov.digitalnomands.testtask.di.modules.ContextModule;
import com.reschikov.digitalnomands.testtask.di.modules.DataBaseModule;
import com.reschikov.digitalnomands.testtask.di.modules.ImageModule;
import com.reschikov.digitalnomands.testtask.di.modules.RequestModule;
import com.reschikov.digitalnomands.testtask.model.Data;
import com.reschikov.digitalnomands.testtask.model.net.Request;
import com.reschikov.digitalnomands.testtask.presenter.NewsListPresenter;
import com.reschikov.digitalnomands.testtask.view.fragments.NewsListFragment;
import com.reschikov.digitalnomands.testtask.view.fragments.adapters.NewsListAdapter;
import com.reschikov.digitalnomands.testtask.view.fragments.glide.ImagePreLoader;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, RequestModule.class, ImageModule.class, DataBaseModule.class})
public interface AppComponent {
	void inject(Request request);
	void inject(NewsListPresenter presenter);
	void inject(NewsListAdapter adapter);
	void inject(Data data);
	void inject(NewsListFragment fragment);
	void inject(ImagePreLoader preLoader);
}
