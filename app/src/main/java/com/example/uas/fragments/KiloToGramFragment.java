package com.example.uas.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uas.R;

import java.text.NumberFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KiloToGramFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KiloToGramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KiloToGramFragment extends Fragment {

    public KiloToGramFragment() {
        // Required empty public constructor
    }
    EditText input_kilo;
    TextView hasilKilo;
    Button convertGram, clearGram;
    double Gram, Kilo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kilo_to_gram, container, false);
        input_kilo = view.findViewById(R.id.text_kilo);
        convertGram = view.findViewById(R.id.button_convert);
        clearGram = view.findViewById(R.id.button_clear);
        hasilKilo = view.findViewById(R.id.gram);

        convertGram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gram = Double.valueOf(input_kilo.getText().toString());
                Kilo = (Gram*1000);
                hasilKilo.setText(NumberFormat.getInstance().format(Kilo));

            }
        });
        clearGram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_kilo.setText("");
            }
        });
        return view;
    }

}