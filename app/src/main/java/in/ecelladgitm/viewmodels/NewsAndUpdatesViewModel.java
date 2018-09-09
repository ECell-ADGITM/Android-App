package in.ecelladgitm.viewmodels;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import in.ecelladgitm.Utilities.Constants;

public class NewsAndUpdatesViewModel extends ViewModel {
    private MutableLiveData<List<News>> newsListMutableLiveData;
    private List<News> newsList = new ArrayList<>();

    public MutableLiveData<List<News>> getNewsListMutableLiveData() {
        if (newsListMutableLiveData == null) {
            newsListMutableLiveData = new MutableLiveData<>();
            setNewsList();
        }
        return newsListMutableLiveData;
    }

    private void setNewsList() {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DATABASE_NEWS);
        firebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                News news = dataSnapshot.getValue(News.class);
                newsList.add(news);
                if (newsListMutableLiveData != null)
                    newsListMutableLiveData.setValue(newsList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
