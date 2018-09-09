package in.ecelladgitm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.ecelladgitm.R;
import in.ecelladgitm.adapters.EventAdapter;
import in.ecelladgitm.modelclasses.Event;
import in.ecelladgitm.modelclasses.EventAdapterModelClass;
import in.ecelladgitm.viewmodels.EventsViewModel;
import in.ecelladgitm.viewmodels.MainViewModel;

public class EventsFragment extends Fragment {

    private List<EventAdapterModelClass> eventList;
    private List<Event> currentEventList;
    private List<Event> pastEventList;
    private EventAdapter eventAdapter;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_fragment, container, false);
        eventList = new ArrayList<>();
        currentEventList = new ArrayList<>();
        pastEventList = new ArrayList<>();

        RecyclerView eventsRecyclerView = view.findViewById(R.id.past_events_recycler_view);
        eventAdapter = new EventAdapter(getContext(), eventList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        eventsRecyclerView.setLayoutManager(mLayoutManager);
        eventsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        eventsRecyclerView.setAdapter(eventAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventsViewModel eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        eventsViewModel.getPastEventListMutableLiveData().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                pastEventList = events;
            }
        });
        eventsViewModel.getCurrentEventListMutableLiveData().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                currentEventList = events;
                setEventsData();
            }
        });
        if (getActivity() == null)
            return;
        MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.setCurrentFragmentStringID(R.string.events);
    }

    private void setEventsData() {
        if (currentEventList.size() < 1) {
            eventList.add(new EventAdapterModelClass(EventAdapterModelClass.NO_CURRENT_EVENT, new Event()));
            eventAdapter.notifyItemInserted(eventList.size() - 1);
            if (pastEventList.size() > 1) {
                for (Event event : pastEventList) {
                    eventList.add(new EventAdapterModelClass(EventAdapterModelClass.PAST_EVENT, event));
                    eventAdapter.notifyItemInserted(eventList.size() - 1);
                }
            }
        } else {
            eventList.add(new EventAdapterModelClass(EventAdapterModelClass.CURRENT_EVENT_HEADER, new Event()));
            eventAdapter.notifyItemInserted(eventList.size() - 1);
            for (Event event : currentEventList) {
                eventList.add(new EventAdapterModelClass(EventAdapterModelClass.CURRENT_EVENT, event));
                eventAdapter.notifyItemInserted(eventList.size() - 1);
            }
            eventList.add(new EventAdapterModelClass(EventAdapterModelClass.PAST_EVENT_HEADER, new Event()));
            eventAdapter.notifyItemInserted(eventList.size() - 1);
            if (pastEventList.size() > 1) {
                for (Event event : pastEventList) {
                    eventList.add(new EventAdapterModelClass(EventAdapterModelClass.PAST_EVENT, event));
                    eventAdapter.notifyItemInserted(eventList.size() - 1);
                }
            }
        }
    }

}
