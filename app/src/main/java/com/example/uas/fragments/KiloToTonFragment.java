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
 * {@link KiloToTonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KiloToTonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KiloToTonFragment extends Fragment {


    public KiloToTonFragment() {
        // Required empty public constructor
    }
    EditText input_kilo;
    TextView hasilTon;
    Button convertTon, clearTon;
    double Ton, Kilo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kilo_to_ton, container, false);
        input_kilo = view.findViewById(R.id.text_kilo);
        convertTon = view.findViewById(R.id.button_convert);
        clearTon = view.findViewById(R.id.button_clear);
        hasilTon = view.findViewById(R.id.ton);

        convertTon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ton = Double.valueOf(input_kilo.getText().toString());
                Kilo = (Ton/1000);
                hasilTon.setText(NumberFormat.getInstance().format(Kilo));

            }
        });
        clearTon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_kilo.setText("");
            }
        });
        return view;
    }

}
