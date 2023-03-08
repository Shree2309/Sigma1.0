package com.shree.sigma;

import android.widget.PopupWindow;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PopupViewModel extends ViewModel {
    private MutableLiveData<PopupWindow> popupWindow = new MutableLiveData<>();

    public MutableLiveData<PopupWindow> getPopupWindow() {
        return popupWindow;
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        this.popupWindow.setValue(popupWindow);
    }
}
