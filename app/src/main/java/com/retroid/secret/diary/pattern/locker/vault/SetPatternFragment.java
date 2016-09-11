package com.retroid.secret.diary.pattern.locker.vault;


import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SetPatternFragment extends Fragment {

    ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();
    int touchCounter = 0;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vault_door, container, false);

		/*ActionBar ab = getActivity().getActionBar();
        ab.setTitle("Set Your Pattern Password");
		ab.setSubtitle("Tap at Any 3 Points on Image");*/

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("  Set Pattern Password");
        ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle("   Tap at Any 3 Points on Image");
        ((ActionBarActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.tap);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Boolean patternSet = preferences.getBoolean("PatternSet", false);
        Boolean masterPassSet = preferences.getBoolean("MasterPassSet", false);

        FloatingActionsMenu fab = (FloatingActionsMenu) rootView.findViewById(R.id.fab_main);
        fab.setVisibility(FloatingActionsMenu.GONE);

///////////////////------------- Set BackGround Theme -----------------/////////////////
        int imageViewTheme = preferences.getInt("Theme", 100);

        switch (imageViewTheme) {
            case 1:
                imageView = (ImageView) rootView.findViewById(R.id.imageView1);
                imageView.setImageResource(R.drawable.wp1);
                break;

            case 2:
                imageView = (ImageView) rootView.findViewById(R.id.imageView1);
                imageView.setImageResource(R.drawable.wp2);
                break;

            case 3:
                imageView = (ImageView) rootView.findViewById(R.id.imageView1);
                imageView.setImageResource(R.drawable.wp3);
                break;
        }

///////////////////------------- Check patternSet & masterPassSet -----------------/////////////////
        if (patternSet) {
            getFragmentManager().beginTransaction().replace(R.id.container, new VaultDoorFragment()).commit();
        }

        if (!masterPassSet) {
            getFragmentManager().beginTransaction().replace(R.id.container, new MasterPassFragment()).commit();
        }

        View imageView = (ImageView) rootView.findViewById(R.id.imageView1);

        imageView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (touchCounter < 3) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        coordinates.add(new Coordinates(x, y));
                        touchCounter++;
                        writeCoordinates(getActivity());
                    }
                }
                return true;
            }
        });

        return rootView;

    }


///////////////////------------- Write Coordinates -----------------/////////////////

    void writeCoordinates(Context context) {

        if (touchCounter == 3) {

            ContextWrapper c = new ContextWrapper(context);
            String filePath = c.getFilesDir().getPath() + "/test.txt";

            FileOutputStream out = null;
            ObjectOutputStream oout = null;

            try {
                out = new FileOutputStream(filePath, false);
                oout = new ObjectOutputStream(out);
                oout.writeObject(coordinates);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (oout != null) {
                        oout.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("PatternSet", true);
            editor.apply();
            Toast.makeText(getActivity(), "Pattern Set!", Toast.LENGTH_SHORT).show();

            getFragmentManager().beginTransaction().replace(R.id.container, new VaultDoorFragment()).commit();
        }

    }

}
