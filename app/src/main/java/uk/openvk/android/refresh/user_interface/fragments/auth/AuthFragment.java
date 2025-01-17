package uk.openvk.android.refresh.user_interface.fragments.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import uk.openvk.android.refresh.R;
import uk.openvk.android.refresh.user_interface.activities.AuthActivity;

public class AuthFragment extends Fragment {
    private View view;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.auth_fragment, container, false);
        Button sign_in_btn = view.findViewById(R.id.sign_in_btn);
        AutoCompleteTextView instance_edit = view.findViewById(R.id.instance_edit);
        TextInputLayout password_layout = view.findViewById(R.id.password_layout);
        TextInputEditText username_edit = view.findViewById(R.id.username_edit);
        TextInputEditText password_edit = view.findViewById(R.id.password_edit);
        instance_edit.setText("openvk.uk");
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() != null) {
                    if (getActivity().getClass().getSimpleName().equals("AuthActivity")) {
                        ((AuthActivity) getActivity()).signIn(instance_edit.getText().toString(), Objects.requireNonNull(username_edit.getText()).toString(),
                                Objects.requireNonNull(password_edit.getText()).toString());
                    }
                }
            }
        });

        ((LinearLayoutCompat) view.findViewById(R.id.auth_layout)).setGravity(Gravity.CENTER);
        instance_edit.setAdapter(
                new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.avaliable_instances)));
        TextInputLayout instance_layout = (view.findViewById(R.id.instance_input_layout));
        instance_edit.setThreshold(25000000);
        password_layout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebAddress(String.format("http://%s/restore", instance_edit.getText().toString()));
            }
        });
        return view;
    }

    public void setAuthorizationData(String instance, String username, String password) {
        AutoCompleteTextView instance_edit = view.findViewById(R.id.instance_edit);
        TextInputEditText username_edit = view.findViewById(R.id.username_edit);
        TextInputEditText password_edit = view.findViewById(R.id.password_edit);
        instance_edit.setText(instance);
        username_edit.setText(username);
        password_edit.setText(password);
    }

    private void openWebAddress(String address) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(address));
        startActivity(i);
    }
}
