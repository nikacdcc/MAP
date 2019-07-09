package com.nikola.todoapplication.util;

import android.app.Activity;

import com.nikola.todoapplication.model.TodoItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoUtils {

    public static List<TodoItem> fromFileList(String[] files, Activity activity) throws FileNotFoundException {
        List<TodoItem> items = new ArrayList<>();
        for (String file : files) {
            if (!file.substring(file.length() - 5).equals(".todo"))
                continue;
            Scanner s = new Scanner(activity.openFileInput(file)).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            TodoItem item = fromString(result);
            item.setId(Long.parseLong(file.substring(0, file.indexOf("."))));
            items.add(item);
        }
        return items;
    }

    public static void saveToFile(TodoItem item, Activity activity) {
        File file = new File(activity.getFilesDir(), System.currentTimeMillis() / 1000L + ".todo");
        try {
            FileWriter fw = new FileWriter(file);
            fw.append(item.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static TodoItem fromString(String fileContent) {
        String[] parts = fileContent.split("␞");
        return new TodoItem(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
    }
}
