package com.bestdreamstore.cosmetics.LIBS;


import android.app.Activity;
import android.content.IntentSender;
import android.util.Log;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

public class Update_App_Google_Play {

    public static int REQUEST_APP_UPDATE = 302;

    public static void setImmediateUpdate(AppUpdateManager appUpdateManager, Activity activity) {

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            // Checks that the platform will allow the specified type of update.
                            if ((appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                                // Request the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.FLEXIBLE,
                                            activity,
                                            REQUEST_APP_UPDATE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }

    public static void setImmediateUpdateOnResume(AppUpdateManager appUpdateManager, Activity activity) {

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            activity,
                                            REQUEST_APP_UPDATE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }





    public static void setFlexibleUpdate(AppUpdateManager appUpdateManager, Activity activity) {

        InstallStateUpdatedListener installStateUpdatedListener = installState -> {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {


                /*

                Snackbar.make(activity.findViewById(android.R.id.content), "Actualizar App Google Play", Snackbar.LENGTH_LONG)
                        .setAction("Actualizar", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(activity.getResources().getColor(R.color.com_facebook_button_background_color_pressed))
                        .show();

                */

                Log.e("UPDATE", "FLEXIBLE BASE");

            } else {

                Log.e("UPDATE", "FLEXIBLE _ Not downloaded yet");

            }

        };

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            // Checks that the platform will allow the specified type of update.
                            if ((appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                                // Request the update.

                                Log.e("UPDATE", "FLEXIBLE  ABAJO");


                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.FLEXIBLE,
                                            activity,
                                            REQUEST_APP_UPDATE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();

                                    Log.e("UPDATE", "FLEXIBLE  ERROR");
                                }
                            }else if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_NOT_AVAILABLE){

                                    /*NO HAY ACTUALIZACION DISPONIBLE

                                Snackbar snackbar =
                                        Snackbar.make(
                                                activity.findViewById(android.R.id.content),
                                                activity.getString(R.string.update_app_NOT_AVIABLE),
                                                Snackbar.LENGTH_INDEFINITE);
                                //lambda operation used for below action
                                snackbar.setAction(activity.getString(R.string.update_app_NOT_AVIABLE), view ->
                                        appUpdateManager.completeUpdate());
                                snackbar.setActionTextColor(activity.getResources().getColor(R.color.com_facebook_button_background_color_pressed))
                                ;
                                snackbar.show();

                                     */




                            }






                        });

        appUpdateManager.registerListener(installStateUpdatedListener);
    }






    public static void setFlexibleUpdateOnResume(AppUpdateManager appUpdateManager, Activity activity) {

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                Log.e("UPDATE", "FLEXIBLE  SI HAY CAMBIOS");
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.FLEXIBLE,
                                            activity,
                                            REQUEST_APP_UPDATE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Log.e("UPDATE", "FLEXIBLE  NO HAY CAMBIOS");
                            }
                        });
    }
}
