package com.vuki.bakingapp.ui.details;

import com.vuki.bakingapp.ui.home.HomeActivityContract;

/**
 * Created by mvukosav
 */
public class ReceiptDetailsActivityPresenter implements HomeActivityContract.Presenter {

    private ReceiptDetailsActivityContract.View view;

    public ReceiptDetailsActivityPresenter( ReceiptDetailsActivityContract.View view ) {
        this.view = view;
    }
}
