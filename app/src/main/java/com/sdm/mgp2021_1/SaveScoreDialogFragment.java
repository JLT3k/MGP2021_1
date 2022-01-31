package com.sdm.mgp2021_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SaveScoreDialogFragment extends DialogFragment {

    public static boolean IsShown = false;
    private String m_Text = "";

    //Set the input
    View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.text_input_name, (ViewGroup) getView(), false);
    final EditText input = (EditText) viewInflated.findViewById(R.id.input);
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        IsShown = true;
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Save Score")
                .setView(viewInflated)
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
