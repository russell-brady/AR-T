package com.arproject.russell.ar_t.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.arproject.russell.ar_t.R;

public class CreateClassDialog extends DialogFragment {

    private EditText classId;
    private EditText className;
    private DialogFinishedInterface dialogFinishedInterface;

    public static CreateClassDialog newInstance() {
        Bundle args = new Bundle();
        CreateClassDialog fragment = new CreateClassDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.create_class_dialog, null);
        classId = view.findViewById(R.id.classId);
        className = view.findViewById(R.id.className);
        builder.setView(view)

                .setPositiveButton("Create", (dialog, d) -> {
                    String id = classId.getText().toString();
                    int finalId = Integer.parseInt(id);
                    String name = className.getText().toString();
                    dialogFinishedInterface.onFinished(finalId, name);
                    getDialog().dismiss();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> getDialog().dismiss());

        builder.setTitle("Create a class");

        return builder.create();
    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.create_class_dialog, container, false);
//        classId = view.findViewById(R.id.classId);
//        className = view.findViewById(R.id.className);
//        button = view.findViewById(R.id.btn_create_class);
//        return view;
//    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        button.setOnClickListener(view -> {
//            String id = classId.getText().toString();
//            int finalId = Integer.parseInt(id);
//            String name = className.getText().toString();
//            dialogFinishedInterface.onFinished(finalId, name);
//        });
//    }

    public void setListener(DialogFinishedInterface dialogFinishedInterface) {
        this.dialogFinishedInterface = dialogFinishedInterface;
    }
}
