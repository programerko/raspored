package com.emendi.raspored;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.nikola.raspored.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String SHARED_SET = "pre";
    private static final String SHARED_SET2 = "po";

    public static boolean smena = true;

    public static final String[] casovi = {"pon1","pon2","pon3","pon4","pon5","pon6","pon7","pon8",
                                            "uto1","uto2","uto3","uto4","uto5","uto6","uto7","uto8",
                                            "sre1","sre2","sre3","sre4","sre5","sre6","sre7","sre8",
                                            "cet1","cet2","cet3","cet4","cet5","cet6","cet7","cet8",
                                            "pet1","pet2","pet3","pet4","pet5","pet6","pet7","pet8",};


    private List<EditText> editTexts;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        int[] buttonIDs = new int[] {R.id.pon1, R.id.uto1, R.id.sre1, R.id.cet1, R.id.pet1,
                                        R.id.pon2, R.id.uto2, R.id.sre2, R.id.cet2, R.id.pet2,
                                        R.id.pon3, R.id.uto3, R.id.sre3, R.id.cet3, R.id.pet3,
                                        R.id.pon4, R.id.uto4, R.id.sre4, R.id.cet4, R.id.pet4,
                                        R.id.pon5, R.id.uto5, R.id.sre5, R.id.cet5, R.id.pet5,
                                        R.id.pon6, R.id.uto6, R.id.sre6, R.id.cet6, R.id.pet6,
                                        R.id.pon7, R.id.uto7, R.id.sre7, R.id.cet7, R.id.pet7,
                                        R.id.pon8, R.id.uto8, R.id.sre8, R.id.cet8, R.id.pet8, };

        editTexts = new ArrayList<>();

        for (int id : buttonIDs){
            EditText et = (EditText) findViewById(id);
            et.addTextChangedListener(new CustomTextWatcher(et));
            editTexts.add(et);
        }
        for( EditText et : editTexts)
            et.setText(izvuciPodatke(getSet(),et.getTag().toString()));

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().toString().equalsIgnoreCase("Пре подне")){
                    button.setText("После подне");
                }
                else{
                    button.setText("Пре подне");
                }
                changeSet();

                for( EditText et : editTexts)
                    et.setText(izvuciPodatke(getSet(),et.getTag().toString()));
            }
        });

    }

    public static String getSet(){
        return smena ?   SHARED_SET : SHARED_SET2;
    }
    public static void changeSet(){
        smena = !smena;
    }


    //PODACI IZ SHAREDPREFERENCES///
    private String izvuciPodatke( String ime_seta, String ime_kolone){
        SharedPreferences preferences = getSharedPreferences(ime_seta,MODE_PRIVATE);
        String extract = preferences.getString(ime_kolone,"");
        return extract;
    }

    //CUVANJ U SHAREDPREFERENCES///
    private void sacuvajPodatke(EditText et, String ime_kolone, String ime_seta){
        SharedPreferences preferences = getSharedPreferences(ime_seta,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ime_kolone,et.getText().toString());
        editor.commit();
    }

    //ZA MENJANJE SVAKOG EDITTEXTA
    private class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;

        public CustomTextWatcher(EditText e) {
            mEditText = e;
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        public void afterTextChanged(Editable s) {
            sacuvajPodatke(mEditText,mEditText.getTag().toString(),getSet());
        }
    }
}
