package com.nikola.todoapplication.custom;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.nikola.todoapplication.MainActivity;
import com.nikola.todoapplication.R;
import com.nikola.todoapplication.model.TodoItem;
import com.nikola.todoapplication.util.TodoUtils;

import java.io.FileNotFoundException;

public class ItemAddDialog {

    public static Dialog create(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_item, null);

        builder.setView(dialogView)
                .setTitle(R.string.add_item_question)
                .setPositiveButton(R.string.add, (dialog, id) -> {
                    String text = ((TextInputEditText) dialogView.findViewById(R.id.name_input)).getText().toString();
                    DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear();

                    TodoUtils.saveToFile(new TodoItem(text, day, month, year), activity);
                    if (PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("notifications", true)) {
                        Toast.makeText(activity, "Item has been successfully added", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        ((MainActivity) activity).updateListView();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());
    return builder.create();
    }
}
