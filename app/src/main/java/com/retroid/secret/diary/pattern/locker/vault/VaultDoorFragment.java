package com.retroid.secret.diary.pattern.locker.vault;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class VaultDoorFragment extends Fragment {

    ArrayList<Coordinates> coordinatesnew = new ArrayList<>();
    ArrayList<Coordinates> coordinates = new ArrayList<>();
    Boolean[] unlocked;
    ImageView iv;
    SharedPreferences preferences;

    int touchCounter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vault_door, container, false);
        /*ActionBar ab = getActivity().getActionBar();
        ab.setTitle("Vault Door");
		ab.setSubtitle("Enter Secret Pattern to Unlock Vault");*/
        setHasOptionsMenu(true);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("  Vault Door");
        ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle("   Repeat Secret Pattern to Unlock");
        ((ActionBarActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.lock);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


//////////////////////////------------- Background Theme ------------///////////////////////////////

        int imageViewTheme = preferences.getInt("Theme", 300);

        iv = (ImageView) rootView.findViewById(R.id.imageView1);

        switch (imageViewTheme) {
            case 1:
                iv.setImageResource(R.drawable.wp1);
                break;

            case 2:
                iv.setImageResource(R.drawable.wp2);
                break;

            case 3:
                iv.setImageResource(R.drawable.wp3);
                break;
        }


//////////////////////////------------- Unlocking Vault ------------/////////////////////////////////
        iv.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (touchCounter < 3) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        coordinatesnew.add(new Coordinates(x, y));
                        touchCounter++;
                        if (touchCounter == 3) {
                            readCoordinates();
                        }
                    }
                }
                return true;
            }
        });


////////////////////////////////////////// Fab Button Clicks Handle //////////////////////////////////

        final FloatingActionsMenu fam = (FloatingActionsMenu) rootView.findViewById(R.id.fab_main);

        FloatingActionButton fabThemeChristmas = (FloatingActionButton) rootView.findViewById(R.id.fab_themeChristmas);
        fabThemeChristmas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fam.collapse();
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if (preferences.getInt("Theme", 100) == 1) {
                    Toast.makeText(getActivity(), "Christmas theme is already set!", Toast.LENGTH_SHORT).show();
                } else {
                    final int pass1 = preferences.getInt("MasterPassInt", 1);
                    AlertDialog.Builder alert1 = new AlertDialog.Builder(getActivity());
                    alert1.setTitle("Enter Master Password");
                    final EditText input1 = new EditText(getActivity());
                    input1.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert1.setView(input1);
                    alert1.setPositiveButton("OK", new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (!input1.getText().toString().equals("")) {
                                if (pass1 == Long.parseLong(input1.getText().toString().trim())) {
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("Theme", 1);
                                    editor.putBoolean("PatternSet", false);
                                    editor.commit();
                                    Toast.makeText(getActivity(), "Set New Password Sequence!\n               Tap 3 points!", Toast.LENGTH_LONG).show();
                                    getFragmentManager().beginTransaction().replace(R.id.container, new SetPatternFragment()).commit();
                                } else {
                                    Toast.makeText(getActivity(), "Sorry, wrong password! Try again!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "You didn't enter anything!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alert1.show();
                }
            }
        });

        FloatingActionButton fabThemeAngelina = (FloatingActionButton) rootView.findViewById(R.id.fab_themeAngelina);
        fabThemeAngelina.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fam.collapse();
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if (preferences.getInt("Theme", 100) == 2) {
                    Toast.makeText(getActivity(), "Girl theme is already set!", Toast.LENGTH_SHORT).show();
                } else {
                    final int pass2 = preferences.getInt("MasterPassInt", 1);
                    AlertDialog.Builder alert2 = new AlertDialog.Builder(getActivity());
                    alert2.setTitle("Enter Master Password");
                    final EditText input2 = new EditText(getActivity());
                    input2.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert2.setView(input2);
                    alert2.setPositiveButton("OK", new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (!input2.getText().toString().equals("")) {
                                if (pass2 == Long.parseLong(input2.getText().toString().trim())) {
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("Theme", 2);
                                    editor.putBoolean("PatternSet", false);
                                    editor.commit();
                                    Toast.makeText(getActivity(), "Set New Password Sequence!\n               Tap 3 points!", Toast.LENGTH_LONG).show();
                                    getFragmentManager().beginTransaction().replace(R.id.container, new SetPatternFragment()).commit();
                                } else {
                                    Toast.makeText(getActivity(), "Sorry, wrong password! Try again!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "You didn't enter anything!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    alert2.show();
                }
            }
        });

        FloatingActionButton fabThemePlants = (FloatingActionButton) rootView.findViewById(R.id.fab_themePlants);
        fabThemePlants.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fam.collapse();
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if (preferences.getInt("Theme", 100) == 3) {
                    Toast.makeText(getActivity(), "Abstract theme is already set!", Toast.LENGTH_SHORT).show();
                } else {
                    final int pass1 = preferences.getInt("MasterPassInt", 3);
                    AlertDialog.Builder alert1 = new AlertDialog.Builder(getActivity());
                    alert1.setTitle("Enter Master Password");
                    final EditText input1 = new EditText(getActivity());
                    input1.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert1.setView(input1);
                    alert1.setPositiveButton("OK", new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (!input1.getText().toString().equals("")) {
                                if (pass1 == Long.parseLong(input1.getText().toString().trim())) {
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("Theme", 3);
                                    editor.putBoolean("PatternSet", false);
                                    editor.commit();
                                    Toast.makeText(getActivity(), "Set New Password Sequence!\n               Tap 3 points!", Toast.LENGTH_LONG).show();
                                    getFragmentManager().beginTransaction().replace(R.id.container, new SetPatternFragment()).commit();
                                } else {
                                    Toast.makeText(getActivity(), "Sorry, wrong password! Try again!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "You didn't enter anything!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    alert1.show();
                }
            }
        });

        FloatingActionButton fabResetPattern = (FloatingActionButton) rootView.findViewById(R.id.fab_reset_pattern);
        fabResetPattern.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fam.collapse();
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                final int pass = preferences.getInt("MasterPassInt", 1);
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Enter Master Password");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setPositiveButton("OK", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!input.getText().toString().equals("")) {
                            if (pass == Long.parseLong(input.getText().toString().trim())) {
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("PatternSet", false);
                                editor.apply();
                                Toast.makeText(getActivity(), "Set new pattern!", Toast.LENGTH_SHORT).show();
                                getFragmentManager().beginTransaction().replace(R.id.container, new SetPatternFragment()).commit();
                            } else {
                                Toast.makeText(getActivity(), "Sorry, wrong password! Try again!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "You didn't enter anything!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.show();
            }
        });

        return rootView;
    }

//////////////////------------- Method: Read Coordinates from file ------------///////////////////////
    void readCoordinates() {
        ContextWrapper c = new ContextWrapper(getActivity());
        String filePath = c.getFilesDir().getPath() + "/test.txt";
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(filePath);
            ois = new ObjectInputStream(fis);
            coordinates = (ArrayList<Coordinates>) ois.readObject();
            int i = 0;
            unlocked = new Boolean[coordinates.size()];
            Coordinates coo;
            for (Coordinates co : coordinates) {
                int x = co.getX();
                int y = co.getY();
                coo = coordinatesnew.get(i);
                int xNew = coo.getX();
                int yNew = coo.getY();

                if (Math.abs(x - xNew) > 50 || (Math.abs(y - yNew) > 50)) {
                    unlocked[i] = false;
                }
                i++;
            }
            if (Arrays.asList(unlocked).contains(false)) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {0, 100, 120, 100, 120, 100};
                vibrator.vibrate(pattern, -1);
                Toast.makeText(getActivity(), "Wrong Pattern! Try Again!", Toast.LENGTH_SHORT).show();
                touchCounter = 0;
                coordinatesnew.clear();
            } else {
                touchCounter = 0;
                getFragmentManager().beginTransaction().replace(R.id.container, new SecretTextFragment()).commit();
                Toast.makeText(getActivity(), "Pattern Matched! Vault Unlocked!", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
