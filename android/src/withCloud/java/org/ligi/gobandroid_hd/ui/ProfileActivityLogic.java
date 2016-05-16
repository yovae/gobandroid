package org.ligi.gobandroid_hd.ui;

import android.app.AlertDialog;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import org.ligi.gobandroid_hd.R;

public class ProfileActivityLogic implements GoogleApiClient.OnConnectionFailedListener {

    BaseProfileActivity base;
    GoogleApiClient mGoogleApiClient;

    @BindView(R.id.sign_in_button)
    SignInButton signInButton;

    @OnClick(R.id.sign_in_button)
    void onSignInClick() {
        new AlertDialog.Builder(base).setMessage(" " + mGoogleApiClient.isConnected()).show();
    }

    public void onResume(BaseProfileActivity base, GoogleApiClient mGoogleApiClient) {
        this.base = base;
        this.mGoogleApiClient = mGoogleApiClient;

        ButterKnife.bind(this, base);

        mGoogleApiClient.registerConnectionFailedListener(this);
        mGoogleApiClient.disconnect();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(base, 1001);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }


//        private EditText username_et;
//    private EditText rank_et;
//    private View mSignInButton;
//    private View mDisconnectButton;
//    private View mSignOutButton;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.profile);
//
//        username_et = (EditText) findViewById(R.id.username_edit);
//        rank_et = (EditText) findViewById(R.id.rank_edit);
//
//        rank_et.setText(getApp().getSettings().getRank());
//        username_et.setText(getApp().getSettings().getUsername());
//        mSignInButton = findViewById(R.id.sign_in_button);
//        mSignOutButton = findViewById(R.id.sign_out_button);
//        mDisconnectButton = findViewById(R.id.disconnect_button);
//        setButtonVisibilityByConnectedState();
//
//        AQuery profileImageView = getAQ().find(R.id.profileImage);
//
//        if (profileImageView == null) {
//            Log.w("profileImageView is null");
//        } else {
//            profileImageView.gone();
//        }
//
//        mSignInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //getPlayServicesHelper().beginUserInitiatedSignIn();;
//                /*
//                if (!getPlusClient().isConnected()) {
//                    if (mConnectionResult == null) {
//                        //mPlusClient.connect();
//                        mConnectionProgressDialog.show();
//                    } else {
//                        try {
//                            mConnectionResult.startResolutionForResult(BaseProfileActivity.this, REQUEST_CODE_RESOLVE_ERR);
//                        }
//                        catch (IntentSender.SendIntentException e) {
//                            // Try connecting again.
//                            mConnectionResult = null;
//                            getPlusClient().connect();
//                        }
//                    }
//                }
//                */
//            }
//        });
//
//        mSignOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (getPlusClient().isConnected()) {
//                    mConnectionResult = null;
//
//                    getPlusClient().clearDefaultAccount();
//                    getPlusClient().disconnect();
//                    getPlusClient().connect();
//                    setToNoUser();
//                }
//            }
//        });
//
//        mDisconnectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (getPlusClient().isConnected()) {
//                    // Prior to disconnecting, run clearDefaultAccount().
//                    getPlusClient().clearDefaultAccount();
//
//                    getPlusClient().revokeAccessAndDisconnect(new PlusClient.OnAccessRevokedListener() {
//                        @Override
//                        public void onAccessRevoked(ConnectionResult status) {
//                            // mPlusClient is now disconnected and access has been revoked.
//                            // Trigger app logic to comply with the developer policies
//                            setToNoUser();
//                            getPlusClient().connect();
//                        }
//                    });
//                }
//            }
//        });
//    }
//
//    private void setToNoUser() {
//        setButtonVisibilityByConnectedState();
//        getAQ().find(R.id.profileImage).gone();
//    }
//
//    private void setButtonVisibilityByConnectedState() {
//        mSignInButton.setVisibility(!getPlusClient().isConnected() ? View.VISIBLE : View.GONE);
//        mSignOutButton.setVisibility(getPlusClient().isConnected() ? View.VISIBLE : View.GONE);
//        mDisconnectButton.setVisibility(getPlusClient().isConnected() ? View.VISIBLE : View.GONE);
//
//        if (getPlusClient().isConnected()) {
//            getPlusClient().loadPerson(new PlusClient.OnPersonLoadedListener() {
//                @Override
//                public void onPersonLoaded(ConnectionResult connectionResult, Person person) {
//
//                    if (person != null && person.getImage() != null && person.getImage().hasUrl()) {
//                        getAQ().find(R.id.profileImage).visible();
//
//
//                        String profilePicURL = person.getImage().getUrl();
//                        // hack to get a image with better resolution
//
//                        int profileImageInPixels = (int) getResources().getDimension(R.dimen.profile_size);
//                        profilePicURL = profilePicURL.replaceAll("sz=[0-9]*$", "sz=" + profileImageInPixels);
//                        if (getAQ().find(R.id.profileImage) != null) {
//                            getAQ().find(R.id.profileImage).image(profilePicURL, true, true);
//                        }
//                    }
//
//                    if (username_et.getText().toString().equals("")) {
//                        username_et.setText(person.getDisplayName());
//                    }
//
//
//                }
//            }, "me");
//        }
//    }
//
//    @Override
//    protected void onPause() {
//
//        getApp().getSettings().setRank(rank_et.getText().toString());
//        getApp().getSettings().setUsername(username_et.getText().toString());
//        CloudHooks.syncUser(getApp());
//        super.onPause();
//    }
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        super.onConnected(bundle);
//        setButtonVisibilityByConnectedState();
//    }
//
//    /*
//    @Override
//    public void onDisconnected() {
//        super.onDisconnected();
//        setButtonVisibilityByConnectedState();
//    }
//    @Override
//    public void onSignInSucceeded() {
//        super.onSignInSucceeded();
//        setButtonVisibilityByConnectedState();
//    }
//
//*/

}
