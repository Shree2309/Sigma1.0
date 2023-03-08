package com.shree.sigma.watchlist;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class MCXViewModel extends AndroidViewModel {
    private MCXRepository mcxRepository;
    private LiveData<List<MCXModel>> mList;

    private MutableLiveData<Integer> mSelectedItemPosition = new MutableLiveData<>();



    public LiveData<Integer> getSelectedItemPosition() {
        return mSelectedItemPosition;
    }

    public void setSelectedItemPosition(int position) {
        mSelectedItemPosition.setValue(position);
    }

    public MCXViewModel(Application application) {
        super(application);
        mcxRepository = new MCXRepository(application);
        mList = mcxRepository.getList();
    }

    public LiveData<List<MCXModel>> getList() {

        if(mList==null){
            mList = new MutableLiveData<>();
            getList();
        }
        return mList;
    }
}
