package in.ecelladgitm.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import in.ecelladgitm.R;
import in.ecelladgitm.Utilities.Constants;
import in.ecelladgitm.activities.CurrentEventActivity;
import in.ecelladgitm.activities.EventRegistrationActivity;
import in.ecelladgitm.modelclasses.EventAdapterModelClass;

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<EventAdapterModelClass> eventList;
    private Context context;
    private int viewHolderCount = 0;

    public EventAdapter(Context context, List<EventAdapterModelClass> eventList) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == EventAdapterModelClass.PAST_EVENT) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.past_event_card_view, parent, false);
            return new VHPastEvent(itemView);
        } else if (viewType == EventAdapterModelClass.CURRENT_EVENT) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.current_event_card_view, parent, false);

            return new VHCurrentEvent(itemView);
        } else if (viewType == EventAdapterModelClass.NO_CURRENT_EVENT) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.no_current_event, parent, false);

            return new VHNoCurrentEvent(itemView);
        } else if (viewType == EventAdapterModelClass.PAST_EVENT_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_category_header, parent, false);
            return new VHPastEventHeader(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_category_header, parent, false);
            return new VHCurrentEventHeader(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHCurrentEvent) {
            final VHCurrentEvent vh = (VHCurrentEvent) holder;
            vh.eventName.setText(eventList.get(position).getEvent().getEventName());
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = null;
            if (cm != null)
                activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            vh.errorText.setVisibility(View.VISIBLE);
            vh.bgImg.setVisibility(View.GONE);
            if (isConnected) {
                vh.errorText.setText(R.string.loading_image);
            } else {
                vh.errorText.setText(R.string.network_not_available);
            }
            Picasso.with(context).load(eventList.get(position).getEvent().getFeaturedPhoto()).into(vh.bgImg, new Callback() {
                @Override
                public void onSuccess() {
                    vh.errorText.setVisibility(View.GONE);
                    vh.bgImg.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {

                }
            });
            vh.eventVenue.setText(eventList.get(position).getEvent().getEventVenue());
            vh.eventEndTime.setText(eventList.get(position).getEvent().getEndTimeOfEvent());
            vh.eventStartTime.setText(eventList.get(position).getEvent().getStartTimeOfEvent());
            vh.eventEndDate.setText(eventList.get(position).getEvent().getEndDateOfEvent());
            vh.eventStartDate.setText(eventList.get(position).getEvent().getStartDateOfEvent());
            if (eventList.get(position).getEvent().getEndTimeOfEvent().equals(eventList.get(position).getEvent().getStartTimeOfEvent()))
                vh.eventEndTime.setVisibility(View.GONE);
            if (eventList.get(position).getEvent().getEndDateOfEvent().equals(eventList.get(position).getEvent().getStartDateOfEvent()))
                vh.eventEndDate.setVisibility(View.GONE);
            vh.registration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EventRegistrationActivity.class);
                    intent.putExtra(Constants.INTENT_EXTRA_REGISTRATION_LINK, eventList.get(vh.getAdapterPosition()).getEvent().getRegistrationLink());
                    context.startActivity(intent);
                }
            });
            vh.knowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(eventList.get(finalPosition).getEvent().getFbLink()));
                    context.startActivity(intent);
                    */
                    Intent intent = new Intent(context, CurrentEventActivity.class);
                    intent.putExtra("current_event", eventList.get(vh.getAdapterPosition()).getEvent());
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof VHPastEvent) {
            VHPastEvent vh = (VHPastEvent) holder;
            vh.eventName.setText(eventList.get(position).getEvent().getEventName());
            vh.eventVenue.setText(eventList.get(position).getEvent().getEventVenue());
            vh.eventEndTime.setText(eventList.get(position).getEvent().getEndTimeOfEvent());
            vh.eventStartTime.setText(eventList.get(position).getEvent().getStartTimeOfEvent());
            vh.eventEndDate.setText(eventList.get(position).getEvent().getEndDateOfEvent());
            vh.eventStartDate.setText(eventList.get(position).getEvent().getStartDateOfEvent());

            if (eventList.get(position).getEvent().getEndTimeOfEvent().equals(eventList.get(position).getEvent().getStartTimeOfEvent()))
                vh.eventEndTime.setVisibility(View.GONE);
            if (eventList.get(position).getEvent().getEndDateOfEvent().equals(eventList.get(position).getEvent().getStartDateOfEvent()))
                vh.eventEndDate.setVisibility(View.GONE);
            final int finalPosition = position;
            vh.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(eventList.get(finalPosition).getEvent().getFbLink()));
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof VHPastEventHeader) {
            VHPastEventHeader vh = (VHPastEventHeader) holder;
            vh.headerText.setText(R.string.past_events);
        } else if (holder instanceof VHCurrentEventHeader) {
            VHCurrentEventHeader vh = (VHCurrentEventHeader) holder;
            vh.headerText.setText(R.string.current_events);
        }

    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (eventList.get(position).getViewType() == EventAdapterModelClass.CURRENT_EVENT) {
            return EventAdapterModelClass.CURRENT_EVENT;
        } else if (eventList.get(position).getViewType() == EventAdapterModelClass.PAST_EVENT) {
            return EventAdapterModelClass.PAST_EVENT;
        } else if (eventList.get(position).getViewType() == EventAdapterModelClass.PAST_EVENT_HEADER) {
            return EventAdapterModelClass.PAST_EVENT_HEADER;
        } else if (eventList.get(position).getViewType() == EventAdapterModelClass.CURRENT_EVENT_HEADER) {
            return EventAdapterModelClass.CURRENT_EVENT_HEADER;
        } else return EventAdapterModelClass.NO_CURRENT_EVENT;
    }

    // ViewHolder for past events
    class VHPastEvent extends RecyclerView.ViewHolder {
        public TextView eventName, eventVenue, eventStartDate, eventEndDate, eventStartTime, eventEndTime;
        public CardView cardView;
        public int viewHolderNumber;

        public VHPastEvent(View itemView) {
            super(itemView);

            viewHolderCount = viewHolderCount + 1;
            viewHolderNumber = viewHolderCount;

            cardView = itemView.findViewById(R.id.card_view);

            eventName = itemView.findViewById(R.id.past_event_name);
            eventVenue = itemView.findViewById(R.id.past_event_venue);
            eventStartDate = itemView.findViewById(R.id.past_event_start_date);
            eventEndDate = itemView.findViewById(R.id.past_event_end_date);
            eventStartTime = itemView.findViewById(R.id.past_event_start_time);
            eventEndTime = itemView.findViewById(R.id.past_event_end_time);
        }
    }

    // ViewHolder for current events
    class VHCurrentEvent extends RecyclerView.ViewHolder {

        public TextView eventName;
        public TextView eventVenue;
        public TextView eventStartDate;
        public TextView eventEndDate;
        public TextView eventStartTime;
        public TextView eventEndTime;
        public TextView registration;
        public TextView knowMore;
        public TextView errorText;
        public ImageView bgImg;

        public VHCurrentEvent(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventVenue = itemView.findViewById(R.id.add_event_venue);
            eventStartDate = itemView.findViewById(R.id.add_event_start_date);
            eventEndDate = itemView.findViewById(R.id.add_event_end_date);
            eventStartTime = itemView.findViewById(R.id.add_event_start_time);
            eventEndTime = itemView.findViewById(R.id.add_event_end_time);
            bgImg = itemView.findViewById(R.id.current_event_bg_img);
            registration = itemView.findViewById(R.id.current_event_register_button);
            errorText = itemView.findViewById(R.id.errorText);
            knowMore = itemView.findViewById(R.id.current_event_know_more_button);
        }
    }

    // ViewHolder for past category header
    class VHPastEventHeader extends RecyclerView.ViewHolder {

        TextView headerText;

        public VHPastEventHeader(View itemView) {
            super(itemView);
            headerText = itemView.findViewById(R.id.header_text_view);
        }
    }

    // ViewHolder for current category header
    class VHCurrentEventHeader extends RecyclerView.ViewHolder {

        TextView headerText;

        public VHCurrentEventHeader(View itemView) {
            super(itemView);
            headerText = itemView.findViewById(R.id.header_text_view);
        }
    }

    // ViewHolder if there is no current event
    class VHNoCurrentEvent extends RecyclerView.ViewHolder {
        public VHNoCurrentEvent(View itemView) {
            super(itemView);
        }
    }
}
