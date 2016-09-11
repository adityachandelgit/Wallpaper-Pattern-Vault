package com.retroid.secret.diary.pattern.locker.vault;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MasterPassFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_master_pass, container, false);

            alertHowToUseApp(getActivity());

		return rootView;
	}

    private void alertHowToUseApp(Context context) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("How to Use this App?");
        alert.setMessage("1. Set the master password. This will be used to reset the \"Pattern Password\", in case you forget it.\n\n" +
                         "2. Now to set your secret \"Pattern Password\", tap on the image at three different locations, which you can remember. These three points is your secret \"Pattern Password\" (in same sequence). Now your vault is locked.\n\n" +
                         "3. To open the vault, tap on the image at the same three locations. Inside the vault there's a secret diary, where you can save notes.\n\n" +
                         "4. You can change the background and reset the \"Pattern password\" at the Toolbar Menu. Easy huh!?");
        alert.setCancelable(false);

        alert.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertMasterPassword(getActivity());
            }
        });

        alert.show();
    }

    private void alertMasterPassword(Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("Set the master password");
        alert.setMessage("\"In case you forget your 'pattern' password\"");
        alert.setCancelable(false);

        // Set an EditText view to get user input
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        final AlertDialog alertDialog = alert.create();

        alertDialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialog) {
                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        String masterPass = input.getText().toString();
                        if(masterPass.length() < 4) {
                            Toast.makeText(getActivity(), "Password length must be at least 4", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("MasterPassInt", Integer.parseInt(masterPass));
                            editor.putBoolean("MasterPassSet", true);
                            editor.apply();
                            Toast.makeText(getActivity(), "            Master Password Set!\nNow, Set Your New Pattern Password", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                            getFragmentManager().beginTransaction().replace(R.id.container, new SetPatternFragment()).commit();

                        }
                    }
                });
            }
        });

        alertDialog.show();
    }
	
	

}
