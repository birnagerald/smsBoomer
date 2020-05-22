package com.example.smsboomer.ui.params;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smsboomer.R;

import static com.example.smsboomer.MainActivity.textAutos;

public class ParamsFragment extends Fragment {

    private ParamsViewModel paramsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paramsViewModel =
                ViewModelProviders.of(this).get(ParamsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_params, container, false);
        final EditText auto1 = root.findViewById(R.id.field_auto1);
        final EditText auto2 = root.findViewById(R.id.field_auto2);
        final EditText auto3 = root.findViewById(R.id.field_auto3);

        final TextView text1 = root.findViewById(R.id.text_auto1);
        final TextView text2 = root.findViewById(R.id.text_auto2);
        final TextView text3 = root.findViewById(R.id.text_auto3);

        text1.setText(textAutos[0]);
        text2.setText(textAutos[1]);
        text3.setText(textAutos[2]);

        final Button button = root.findViewById(R.id.confirm_auto);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText1 = auto1.getText().toString();
                String newText2 = auto2.getText().toString();
                String newText3 = auto3.getText().toString();

                text1.setText(newText1);
                textAutos[0] = newText1;

                text2.setText(newText2);
                textAutos[1] = newText2;

                text3.setText(newText3);
                textAutos[2] = newText3;
            }
        });

        paramsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
}
