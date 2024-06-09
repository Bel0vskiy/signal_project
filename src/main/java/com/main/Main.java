package com.main;

import com.data_management.DataStorage;
import com.cardio_generator.HealthDataSimulator;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        switch (args[0]) {
            case "DataStorage":
                DataStorage.main(args);
                break;
            case "HealthDataSimulator":
                HealthDataSimulator.main(Arrays.copyOfRange(args, 1, args.length));
                break;
            default:
                HealthDataSimulator.main(new String[]{"console"});
        }
//        if (args.length > 0 && args[0].equals("DataStorage")){
//            DataStorage.main(args);
//        }
//        else{
//            HealthDataSimulator.main(args);
//        }
    }
}
