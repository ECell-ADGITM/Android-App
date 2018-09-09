package in.ecelladgitm.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.github.abdularis.civ.AvatarImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import in.ecelladgitm.MyApplication;
import in.ecelladgitm.R;

public class ProfileActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;
    private static final int RC_PHONE_SIGN_IN = 1;
    private static final int RC_EMAIL_SIGN_IN = 2;
    private FirebaseAuth firebaseAuth;
    private TextView userDisplayName;
    private TextView userEmailID;
    private TextView userPhoneNumber;
    private AvatarImageView userPhoto;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.setAppTheme(this);
        super.onCreate(savedInstanceState);
        // Check if user is signed-in, and if not, start sign-in ui.
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null)
            signInWithAllProviders();
        setContentView(R.layout.profile_activity);
        userDisplayName = findViewById(R.id.user_display_name);
        userEmailID = findViewById(R.id.user_email_id);
        userPhoneNumber = findViewById(R.id.user_phone_number);
        userPhoto = findViewById(R.id.user_photo);
        logoutButton = findViewById(R.id.logout_button);
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() == null)
                    return;
                if (userPhoto.getState() == AvatarImageView.SHOW_IMAGE)
                    userPhoto.setState(AvatarImageView.SHOW_INITIAL);
                else userPhoto.setState(AvatarImageView.SHOW_IMAGE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (firebaseAuth.getCurrentUser() != null) {
                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest
                            .Builder()
                            .setPhotoUri(result.getUri())
                            .build();
                    if (firebaseAuth.getCurrentUser() != null)
                        firebaseAuth.getCurrentUser().updateProfile(userProfileChangeRequest)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            updateUI();
                                            Toast.makeText(ProfileActivity.this, "Photo updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        // when Sign-in ui ends, firebaseAuth have the new currentUser.
        if (requestCode == RC_SIGN_IN || requestCode == RC_EMAIL_SIGN_IN || requestCode == RC_PHONE_SIGN_IN) {
            if (resultCode == RESULT_OK)
                updateUI();
        }

    }

    public void signInWithAllProviders() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    public void signInWithPhone() {
        startActivityForResult(
                AuthUI.getInstance().
                        createSignInIntentBuilder().
                        setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.PhoneBuilder().build())).
                        build(),
                RC_PHONE_SIGN_IN);
    }

    public void signInWithEmail() {
        startActivityForResult(
                AuthUI.getInstance().
                        createSignInIntentBuilder().
                        setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.EmailBuilder().build())).
                        build(),
                RC_EMAIL_SIGN_IN);
    }

    @SuppressLint("SetTextI18n")
    public void updateUI() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        logoutButton.setVisibility(View.VISIBLE);
        if (currentUser != null && currentUser.getDisplayName() != null && currentUser.getDisplayName().length() > 0) {
            userDisplayName.setText(currentUser.getDisplayName());
            userPhoto.setText(currentUser.getDisplayName());
        } else {
            userDisplayName.setText("");
            userDisplayName.setHint("No Name");
        }

        if (currentUser != null && currentUser.getEmail() != null && currentUser.getEmail().length() > 0)
            userEmailID.setText(currentUser.getEmail());
        else {
            userEmailID.setHint("Add Email Account");
            userEmailID.setText("");
        }

        if (currentUser != null && currentUser.getPhoneNumber() != null && currentUser.getPhoneNumber().length() > 0)
            userPhoneNumber.setText(currentUser.getPhoneNumber());
        else {
            userPhoneNumber.setHint("Add Phone Number");
            userPhoneNumber.setText("");
        }

        if (currentUser != null && currentUser.getPhotoUrl() != null) {
            Glide.with(getApplicationContext()).load(currentUser.getPhotoUrl()).error(R.drawable.ic_face_black_24dp).into(userPhoto);
            userPhoto.setState(AvatarImageView.SHOW_IMAGE);
            ((AppCompatImageView) findViewById(R.id.upload_image)).setImageDrawable(getDrawable(R.drawable.ic_edit_transparent_white));
        } else {
            userPhoto.setText(currentUser != null && currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "?");
            userPhoto.setImageDrawable(null);
            userPhoto.setState(AvatarImageView.SHOW_INITIAL);
            ((AppCompatImageView) findViewById(R.id.upload_image)).setImageDrawable(getDrawable(R.drawable.ic_file_upload_white_24dp));
        }
    }

    public void logOut(View view) {
        firebaseAuth.signOut();
        updateUI();
    }

    public void editUserInfo(View view) {
        switch (view.getId()) {
            case R.id.edit_user_display_name:
                if (userDisplayName.isEnabled()) {  // if EditText is enabled, it means user is editing the info
                    if (userDisplayName.getText().toString().length() < 2) {
                        Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.horizontal_shake);
                        userDisplayName.startAnimation(animShake);
                    } else {
                        userDisplayName.setEnabled(false);
                        findViewById(R.id.cancel_edit_user_display_name).setVisibility(View.GONE);
                        ((AppCompatImageView) view).setImageDrawable(getDrawable(R.drawable.ic_edit_transparent_white));
                        Toast.makeText(getApplicationContext(), "Updating your heading...", Toast.LENGTH_SHORT).show();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(userDisplayName.getText().toString())
                                .build();
                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        if (currentUser != null)
                            currentUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Name updated", Toast.LENGTH_SHORT).show();
                                            } else {
                                                userDisplayName.setText(firebaseAuth.getCurrentUser().getDisplayName());
                                                Toast.makeText(getApplicationContext(), "An error occurred, heading not updated", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        else {
                            Toast.makeText(getApplicationContext(), "You are not signed-in, sign-in again...", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    ((AppCompatImageView) view).setImageDrawable(getDrawable(R.drawable.ic_done_transparent_white));
                    userDisplayName.setEnabled(true);
                    userDisplayName.requestFocus();
                    userDisplayName.append("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null)
                        imm.showSoftInput(userDisplayName, InputMethodManager.SHOW_IMPLICIT);
                    findViewById(R.id.cancel_edit_user_display_name).setVisibility(View.VISIBLE);
                }
                break;
            case R.id.edit_email_id:
                signInWithEmail();
                break;
            case R.id.edit_phone_number:
                signInWithPhone();
                break;
            case R.id.cancel_edit_user_display_name:
                findViewById(R.id.cancel_edit_user_display_name).setVisibility(View.GONE);
                ((AppCompatImageView) findViewById(R.id.edit_user_display_name)).setImageDrawable(getDrawable(R.drawable.ic_edit_transparent_white));
                userDisplayName.setEnabled(false);
                if (firebaseAuth.getCurrentUser() != null)
                    userDisplayName.setText(firebaseAuth.getCurrentUser().getDisplayName());
                else Toast.makeText(this, "Opps! You are not logged-in", Toast.LENGTH_SHORT).show();
                break;
            case R.id.upload_image:
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(this, "Sign-in to update your profile photo", Toast.LENGTH_SHORT).show();
                    break;
                }
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
                break;
        }
    }
}