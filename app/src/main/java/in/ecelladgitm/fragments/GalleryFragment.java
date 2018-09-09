package in.ecelladgitm.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mzelzoghbi.zgallery.ZGrid;
import com.mzelzoghbi.zgallery.entities.ZColor;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import in.ecelladgitm.R;

public class GalleryFragment extends Fragment {

    private ArrayList<String> imageArrayList;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        imageArrayList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("photo_gallery").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String imageUrl = (String) dataSnapshot1.child("photoUrl").getValue();
                    imageArrayList.add(imageUrl);
                    //photoGalleryRecyclerViewAdapter.notifyItemInserted(imageArrayList.size() - 1);
                }
                Collections.shuffle(imageArrayList);
                ZGrid.with(getActivity(), imageArrayList)
                        .setToolbarColorResId(R.color.colorPrimary) // toolbar color
                        .setTitle("Gallery") // toolbar title
                        .setToolbarTitleColor(ZColor.WHITE) // toolbar title color
                        .setSpanCount(3) // columns count
                        .setGridImgPlaceHolder(R.color.colorPrimary) // color placeholder for the grid image until it loads
                        .show();
                getActivity().getSupportFragmentManager().popBackStack();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
