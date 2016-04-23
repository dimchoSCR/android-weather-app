package team.project.weather;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import team.project.weather.databinding.FragmentMainBinding;
import team.project.weather.model.Day;
import team.project.weather.model.Model;

public class WeatherFragment extends Fragment {

    private static final int UPDATE_INTERVAL_HOURS = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        FragmentMainBinding binding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_main,container,false);

        View weatherView = binding.getRoot();

        Day day;
        Model model;
        CacheManager manager = new CacheManager();

        // Retrieve data from cache if possible
        try{
            day = manager.retrieve();
        }catch(Exception err){
            Log.e("Cache","Error retrieving data from cache",err);
            Toast.makeText(weatherView.getContext(),
                    "Unable to load stored weather data. Please wait for an update from the network"
                    ,Toast.LENGTH_LONG).show();
            day = new Day();
        }

        model = new Model();
        model.currentDay.set(day);
        binding.setModel(model);

        // Setting up a scheduled Executor inorder to execute the thread
        // within the UPDATE_INTERVAL_HOURS
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new UpdaterThread(model),0, UPDATE_INTERVAL_HOURS, TimeUnit.HOURS);

        return weatherView;
    }

    private class UpdaterThread implements Runnable{
        private Model model;
        private OpenWeatherService openWeatherService;

        UpdaterThread(Model model){
            this.model = model;
            openWeatherService = new OpenWeatherService();
        }

        @Override
        public void run(){
            //TODO Get GPS coordinates
            try {
                // No need to synchronize with the UI thread because of the data binding
                model.currentDay.set(openWeatherService.getCurrentDay(0,0));
            } catch (Exception err) {
                Log.e("WeatherUpdate","Could not update weather!",err);
                // Post the toast on the UI thread's MessageQueue
                MainActivity.toastHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Filed to update weather! Please check your internet connection.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        }
    }
}
