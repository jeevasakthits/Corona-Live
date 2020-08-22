package io.coronalive.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.coronalive.models.LocationStats;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL_RECOVERED = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
    private static String VIRUS_DATA_URL_DEATHS = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private static String VIRUS_DATA_URL_CONFIRMED = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
//    private static String VIRUS_DATA_URL_RECOVERED = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL_CONFIRMED))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        
       // List<LocationStats> newStats1 = new ArrayList<>();
        HttpClient client1 = HttpClient.newHttpClient();
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL_DEATHS))
                .build();
        HttpResponse<String> httpResponse1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader1 = new StringReader(httpResponse1.body());
        Iterable<CSVRecord> records1 = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader1);
        
        
       // List<LocationStats> newStats2 = new ArrayList<>();
        HttpClient client2 = HttpClient.newHttpClient();
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL_RECOVERED))
                .build();
        HttpResponse<String> httpResponse2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader2 = new StringReader(httpResponse2.body());
        Iterable<CSVRecord> records2 = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader2);
        
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalConfirmedCases(latestCases);
            locationStat.setDiffFromPrevDayConfirmedCases(latestCases - prevDayCases);
            int chk=0;
            for (CSVRecord record1 : records1) {
            	int chk1=0;
            	if (record.get("Province/State").equals(record1.get("Province/State")) && record.get("Country/Region").equals(record1.get("Country/Region"))) {
               // LocationStats locationStat = new LocationStats();
               // locationStat.setState(record1.get("Province/State"));
                //locationStat.setCountry(record1.get("Country/Region"));
                int latestCases_d = Integer.parseInt(record1.get(record1.size() - 1));
                int prevDayCases_d = Integer.parseInt(record1.get(record1.size() - 2));
                locationStat.setLatestTotalDeathCases(latestCases_d);
                locationStat.setDiffFromPrevDeathCases(latestCases_d - prevDayCases_d);
                chk=1;
                for (CSVRecord record2 : records2) {
                	if (record.get("Province/State").equals(record2.get("Province/State")) && record.get("Country/Region").equals(record2.get("Country/Region"))) {
                    // LocationStats locationStat = new LocationStats();
                    // locationStat.setState(record1.get("Province/State"));
                     //locationStat.setCountry(record1.get("Country/Region"));
                     int latestCases_r = Integer.parseInt(record2.get(record2.size() - 1));
                     int prevDayCases_r = Integer.parseInt(record2.get(record2.size() - 2));
                     locationStat.setLatestTotalRecoveredCases(latestCases_r);
                     locationStat.setDiffFromPrevDayRecoveredCases(latestCases_r - prevDayCases_r);
                     chk1=1;
                     break;
                	}
                 }
                if(chk1==0) {
                    locationStat.setLatestTotalRecoveredCases(0);
                    locationStat.setDiffFromPrevDayRecoveredCases(0);
                }
                break;
            	}
            }
            if(chk==0) {
                locationStat.setLatestTotalDeathCases(0);
                locationStat.setDiffFromPrevDeathCases(0);
            }
            
            newStats.add(locationStat);
        }
        this.allStats = newStats;
    }

}
