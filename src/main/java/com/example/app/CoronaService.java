package com.example.app;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CoronaService {

    @Autowired
    CoronaRepository coronaRepository;

    public void save (Corona corona){
        coronaRepository.save(corona);
    }

    @Scheduled(cron = "0 4 * * *")
    public void populate () throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/03-25-2022.csv");

        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        int responseCode = http.getResponseCode();

        if(responseCode == 200) {
            System.out.print("connection is successful");


        }

//        var csvFile = "C:\\Users\\nithi\\Downloads\\03-01-2023.csv";
        CSVReader reader = null;

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()), 8192);
//           reader = new CSVReader(new FileReader(csvFile));
            reader = new CSVReader(input);
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

    public List<Corona> findByLastUpdate(LocalDate localdate) {
        return coronaRepository.findByLastUpdatedBetween(LocalDateTime.of(localdate, LocalTime.MIN), LocalDateTime.of(localdate.now(), LocalTime.MIN));
    }

    public List<Corona> findAll() {
        return coronaRepository.findAll();
    }
}
