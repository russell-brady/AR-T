package com.arproject.russell.ar_t.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.arproject.russell.ar_t.R;

public class CreateAssignmentDialog extends DialogFragment {

    private EditText assignmentName;
    private EditText assignmentDesc;
    private DatePicker datePicker;
    private CreateAssignmentListener createAssignmentListener;

    public static CreateAssignmentDialog newInstance() {
        Bundle args = new Bundle();
        CreateAssignmentDialog fragment = new CreateAssignmentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.create_assignment_dialog, null, false);
        assignmentName = view.findViewById(R.id.assignmentName);
        assignmentDesc = view.findViewById(R.id.assignmentDesc);
        datePicker = view.findViewById(R.id.datePicker);
        builder.setTitle("Create an assignment");
        builder.setView(view);

        builder.setPositiveButton("Create", (dialog, d) -> {
                    String name = assignmentName.getText().toString();
                    String desc = assignmentDesc.getText().toString();
                    String date = "" + datePicker.getYear() + "-" + pad(datePicker.getMonth() + 1) + "-" + pad(datePicker.getDayOfMonth());
                    getDialog().dismiss();
                    createAssignmentListener.onAssignmentCreated(name, desc, date);
                });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> getDialog().dismiss());


        return builder.create();
    }

    public void setListener(CreateAssignmentListener createAssignmentListener) {
        this.createAssignmentListener = createAssignmentListener;
    }

    private static String pad(int c) {
        if (c >= 10){
            return String.valueOf(c);
        } else{
            return"0"+String.valueOf(c);
        }
    }
}
