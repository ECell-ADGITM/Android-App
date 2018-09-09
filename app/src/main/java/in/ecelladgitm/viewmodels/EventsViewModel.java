package in.ecelladgitm.viewmodels;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import in.ecelladgitm.Utilities.Constants;
import in.ecelladgitm.modelclasses.Event;

public class EventsViewModel extends ViewModel {

    private MutableLiveData<List<Event>> pastEventListMutableLiveData;
    private List<Event> pastEventList;
    private MutableLiveData<List<Event>> currentEventListMutableLiveData;
    private List<Event> currentEventList;

    public MutableLiveData<List<Event>> getPastEventListMutableLiveData() {
        if (pastEventListMutableLiveData == null) {
            pastEventListMutableLiveData = new MutableLiveData<>();
            currentEventListMutableLiveData = new MutableLiveData<>();
            pastEventList = new ArrayList<>();
            currentEventList = new ArrayList<>();
            getEventsData();
        }
        return pastEventListMutableLiveData;
    }

    public MutableLiveData<List<Event>> getCurrentEventListMutableLiveData() {
        return currentEventListMutableLiveData;
    }

    private void getEventsData() {
        FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DATABASE_PAST_EVENTS)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                            pastEventList.add(eventSnapshot.getValue(Event.class));
                        }
                        Collections.reverse(pastEventList);
                        pastEventListMutableLiveData.setValue(pastEventList);
                        FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DATABASE_CURRENT_EVENTS)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                                            currentEventList.add(eventSnapshot.getValue(Event.class));
                                        }
                                        Collections.reverse(currentEventList);
                                        currentEventListMutableLiveData.setValue(currentEventList);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
