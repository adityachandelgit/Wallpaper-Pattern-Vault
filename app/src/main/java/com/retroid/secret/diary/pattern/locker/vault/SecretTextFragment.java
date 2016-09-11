package com.retroid.secret.diary.pattern.locker.vault;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecretTextFragment extends Fragment {

    TextView tvSecretText;
    Button btnSaveSecretText;
    String retrievedText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_secret_text, container, false);

		/*ActionBar ab = getActivity().getActionBar();
        ab.setTitle("Secret Vault");
		ab.setSubtitle("Save your text here");*/

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("  Secret Vault");
        ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle("   Save Your Text Here");
        ((ActionBarActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.unlock);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        retrievedText = preferences.getString("SecretText", "");

        tvSecretText = (TextView) rootView.findViewById(R.id.editTextSecretText);
        tvSecretText.setText(retrievedText);

        btnSaveSecretText = (Button) rootView.findViewById(R.id.buttonSaveSecretText);
        btnSaveSecretText.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("SecretText", tvSecretText.getText().toString());
                editor.apply();
                Toast.makeText(getActivity(), "Text Saved!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });


        return rootView;
    }


}
