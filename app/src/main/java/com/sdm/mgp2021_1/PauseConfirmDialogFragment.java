package com.sdm.mgp2021_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.app.DialogFragment;

public class PauseConfirmDialogFragment extends DialogFragment {

    public static boolean IsShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        IsShown = true;
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (!GameSystem.Instance.GetIsPaused())
            builder.setMessage("Confirm Pause?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            // User triggered pause
                            GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                            IsShown = false;
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            // User cancelled the pause
                            IsShown = false;
                        }
                    });
        else
            builder.setMessage("Confirm Unpause?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            // User triggered pause
                            GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                            IsShown = false;
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            // User cancelled the pause
                            IsShown = false;
                        }
                    });

        // Create the AlertDialog object and return itr
        return builder.create();
    }
}
