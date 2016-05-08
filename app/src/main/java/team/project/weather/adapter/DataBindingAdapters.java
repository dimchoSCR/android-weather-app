package team.project.weather.adapter;

import android.content.SharedPreferences;
import android.databinding.BindingAdapter;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import team.project.weather.MainActivity;
import team.project.weather.SettingsFragment;

public class DataBindingAdapters {
    static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm");

    @BindingAdapter("weather:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    // Formats the date to display in the TextView via the new :date attribute
    @BindingAdapter("weather:date")
    public static void setDate(TextView textView, Date date){
        String formattedDate = "Last updated: " + sdf.format(date);
        textView.setText(formattedDate);
    }
}
