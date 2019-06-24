package com.reschikov.digitalnomands.testtask.view.fragments;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface Updated extends MvpView {
	@StateStrategyType(SingleStateStrategy.class)
	void update();
	@StateStrategyType(SingleStateStrategy.class)
	void scrollDown(int position);
	@StateStrategyType(SingleStateStrategy.class)
	void scrollUp(int position);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void displayMessage(int position);
	@StateStrategyType(AddToEndSingleStrategy.class)
	void displayMessage();
	@StateStrategyType(SkipStrategy.class)
	void goToNewsPage(String url);
}
