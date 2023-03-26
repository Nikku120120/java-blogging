package com.example.app;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Service
public class CoronaService {

    @Autowired
    CoronaRepository coronaRepository;

    public void save (Corona corona){
        coronaRepository.save(corona);
    }

    public void populate () {
        var csvFile = "C:\\Users\\nithi\\Downloads\\03-01-2023.csv";
        CSVReader reader = null;

        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            int i = 0;
            while ((line = reader.readNext()) != null){
                if(i == 0) {
                    i++;
                    continue;
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Corona corona = new Corona();
                corona.setConfirmed(Long.valueOf(ConvertIntoNumeric(line[7])));
                corona.setActive(Long.valueOf(ConvertIntoNumeric(line[10])));
                corona.setRecovered(Long.valueOf(ConvertIntoNumeric(line[9])));
                corona.setCombinedKey(line[11]);
                corona.setLastUpdated(LocalDateTime.parse(line[4], formatter));
                coronaRepository.save(corona);
                System.out.println(corona.toString());
            }

        }catch ( IOException e) {
            e.printStackTrace();
        }finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int ConvertIntoNumeric(String xVal)
    {
        try
        {
            return Integer.parseInt(xVal);
        }
        catch(Exception ex)
        {
            return 0;
        }
    }
}
