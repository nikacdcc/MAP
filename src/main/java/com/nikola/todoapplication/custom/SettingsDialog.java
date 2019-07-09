package com.nikola.todoapplication.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nikola.todoapplication.R;

public class SettingsDialog {

    public static Dialog create(Activity activity) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        final SharedPreferences.Editor editor = preferences.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.settings)
                .setMultiChoiceItems(R.array.settings, new boolean[]{preferences.getBoolean("notifications", true)},
                        (dialog, which, isChecked) -> {
                            editor.putBoolean("notifications", isChecked);
                            editor.apply();
                        })
                .setPositiveButton(R.string.save, (dialog, id) -> {
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());

        return builder.create();
    }
}
