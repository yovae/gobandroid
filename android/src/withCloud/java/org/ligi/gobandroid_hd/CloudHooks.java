package org.ligi.gobandroid_hd;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.ligi.gobandroid_hd.events.TsumegoSolved;
import org.ligi.gobandroid_hd.ui.BaseProfileActivity;
import org.ligi.gobandroid_hd.ui.ProfileActivityLogic;
import org.ligi.tracedroid.logging.Log;

public class CloudHooks {

    @TargetApi(14)
    public static void onApplicationCreation(final App app) {

        final ProfileActivityLogic profileActivityLogic=new ProfileActivityLogic();

        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            private GoogleApiClient mGoogleApiClient;

            @Subscribe
            public void onEvent(TsumegoSolved event) {
                Log.i("solvedTsumego");
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                // add other APIs and scopes here as needed
                mGoogleApiClient = new GoogleApiClient.Builder(activity)
                        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(@Nullable Bundle bundle) {
                                Log.i("connected");
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // Attempt to reconnect
                                mGoogleApiClient.connect();
                            }
                        })

                        .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                        // add other APIs and scopes here as needed
                        .build();

            }

            @Override
            public void onActivityStarted(Activity activity) {
                mGoogleApiClient.connect();
            }

            @Override
            public void onActivityResumed(final Activity activity) {
                EventBus.getDefault().register(this);

                if ( activity instanceof BaseProfileActivity) {
                    profileActivityLogic.onResume((BaseProfileActivity) activity,mGoogleApiClient);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                EventBus.getDefault().unregister(this);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                mGoogleApiClient.disconnect();
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }

}
