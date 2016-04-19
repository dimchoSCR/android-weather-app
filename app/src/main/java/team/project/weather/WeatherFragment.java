package team.project.weather;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import team.project.weather.databinding.FragmentMainBinding;
import team.project.weather.model.Day;
import team.project.weather.model.Model;

public class WeatherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        FragmentMainBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false);

        View weatherView = binding.getRoot();

        Day day = new Day();
        day.setLocationCity("Sofia");
        day.setTemperature(40f);
        day.setWeather(Day.Weather.CLOUDY);
        day.setWindSpeed(12);
        day.setLastUpdated(new Date());

        Model model = new Model(day);
        binding.setModel(model);

        return weatherView;
    }
}
