package com.vuki.bakingapp.ui.home;

/**
 * Created by mvukosav
 */
public class HomeActivityPresenter implements HomeActivityContract.Presenter {

    private HomeActivityContract.View view;

    public HomeActivityPresenter( HomeActivityContract.View view ) {
        this.view = view;
    }
}
