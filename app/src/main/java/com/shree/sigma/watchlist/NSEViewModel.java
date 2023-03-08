package com.shree.sigma.watchlist;

import android.app.Application;
import android.widget.PopupWindow;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class NSEViewModel extends AndroidViewModel {
    private NSERepository nseRepository;
    private LiveData<List<NSEModel>> mList;


    private MutableLiveData<Integer> mSelectedItemPosition = new MutableLiveData<>();

    public LiveData<Integer> getSelectedItemPosition() {
        return mSelectedItemPosition;
    }

    public void setSelectedItemPosition(int position) {
        mSelectedItemPosition.setValue(position);
    }

    public NSEViewModel(Application application) {
        super(application);
        nseRepository = new NSERepository(application);
        mList = nseRepository.getList();
    }

    public LiveData<List<NSEModel>> getList() {

        if(mList==null){
            mList = new MutableLiveData<>();
            getList();
        }
        return mList;
    }
}
