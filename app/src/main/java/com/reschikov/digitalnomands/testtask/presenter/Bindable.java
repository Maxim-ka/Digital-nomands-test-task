package com.reschikov.digitalnomands.testtask.presenter;

import com.reschikov.digitalnomands.testtask.model.data.Article;
import com.reschikov.digitalnomands.testtask.view.fragments.adapters.Displayed;

public interface Bindable {
	Article bindView(Displayed displayed, int position);
	int getItemCount();
	void selectItem(int position);
	void repeatDownload();
}
