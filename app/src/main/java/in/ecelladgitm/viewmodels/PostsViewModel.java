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
import in.ecelladgitm.modelclasses.Post;

public class PostsViewModel extends ViewModel {
    private MutableLiveData<List<Post>> postListMutableLiveData;
    private List<Post> postList = new ArrayList<>();

    public MutableLiveData<List<Post>> getPosts() {
        if (postListMutableLiveData == null) {
            postListMutableLiveData = new MutableLiveData<>();
            setPosts();
        }
        return postListMutableLiveData;
    }

    private void setPosts() {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_DATABASE_ARTICLES);
        firebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);
                postList.add(post);
                if (postListMutableLiveData != null)
                    postListMutableLiveData.setValue(postList);
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