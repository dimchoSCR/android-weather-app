package team.project.weather;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

import team.project.weather.model.Day;
import team.project.weather.service.WeatherStorageService;

public class CacheManager implements WeatherStorageService{

    public static final String STORAGE_DIR = "weather";
    public static final String FILE_NAME = "Cache";


    private File file;
    private Context context;

    public CacheManager(Context context){
        this.context = context;
    }

    @Override
    public void store(Day day) throws Exception {
        String pathName = context.getFilesDir().getAbsolutePath() + File.pathSeparator + STORAGE_DIR;
        File storageDir = new File(pathName);

        if(!storageDir.isDirectory()){
            if(!storageDir.mkdir()){
                throw new Exception("Storage directory not created!");
            }
        }

        file = new File(pathName + File.pathSeparator + FILE_NAME);
        if(!file.exists()){
            if(!file.createNewFile()){
                throw new Exception("could not be created");
            }
        }

        ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(file));
        objectOut.writeObject(day);

        objectOut.close();
    }
    @Override
    public Day retrieve() throws Exception {
        if(file==null) {
            throw new Exception("file doesn't exist");
        }

        ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(file));

        return (Day) objectIn.readObject();
    }
}
