package in.ecelladgitm.modelclasses;

// In EventAdapter, we are using more than one type of layouts.
// So all the types will be wrapped to this class
// This class has member variable viewType which can be used in Adapter to populate the correct layout
// This class will be used to provide data to EventAdapter

public class EventAdapterModelClass {
    public static int PAST_EVENT = 0;
    public static int CURRENT_EVENT = 1;
    public static int PAST_EVENT_HEADER = 2;
    public static int CURRENT_EVENT_HEADER = 3;
    public static int NO_CURRENT_EVENT = 4;

    private int viewType;
    private Event event;

    public EventAdapterModelClass(int viewType, Event event) {
        this.viewType = viewType;
        this.event = event;
    }

    public EventAdapterModelClass() {
    }

    public int getViewType() {
        return viewType;
    }

    public Event getEvent() {
        return event;
    }

}
