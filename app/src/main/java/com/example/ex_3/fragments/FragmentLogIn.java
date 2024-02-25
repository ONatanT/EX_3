package com.example.ex_3.fragments;

import static android.widget.Toast.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.ex_3.R;
import com.example.ex_3.activitys.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogIn extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLogIn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogIn.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogIn newInstance(String param1, String param2) {
        FragmentLogIn fragment = new FragmentLogIn();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                loginFunc(v);
//                Navigation.findNavController(v).navigate(R.id.action_fragmentLogIn_to_fragmentRecycleView);
            }
        });

        Button buttonSignup = view.findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Navigation.findNavController(v).navigate(R.id.action_fragmentLogIn_to_fragmentSignUp);
            }
        });

        EditText textEmail = view.findViewById(R.id.editTextUsername);
        textEmail.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ((EditText)v).setText("");
                v.performClick();
            }
            return false;
        });

        EditText textPassword = view.findViewById(R.id.editTextPassword);
        textPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ((EditText)v).setText("");
                v.performClick();
            }
            return false;

        });


        return view;
    }

    public void loginFunc(View view) {
        String email = ((EditText) view.findViewById(R.id.editTextUsername) ).getText().toString().trim();
        String password = ((EditText) view.findViewById(R.id.editTextPassword) ).getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("In LogIn Func Success");
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText( getActivity(),"login success",
                                    LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_fragmentLogIn_to_fragmentRecycleView);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(),"login failed",
                                    LENGTH_SHORT).show();
                            Log.e("LoginError", "Error: " + Objects.requireNonNull(task.getException()).getMessage());
                        }
                    }

                });
    }

}