package in.ecelladgitm.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Integer> currentFragmentStringID;

    public MutableLiveData<Integer> getCurrentFragmentStringID() {
        if (currentFragmentStringID == null)
            currentFragmentStringID = new MutableLiveData<>();
        return currentFragmentStringID;
    }

    public void setCurrentFragmentStringID(Integer currentFragmentStringID) {
        if (this.currentFragmentStringID == null)
            this.currentFragmentStringID = new MutableLiveData<>();
        this.currentFragmentStringID.setValue(currentFragmentStringID);
    }
}
