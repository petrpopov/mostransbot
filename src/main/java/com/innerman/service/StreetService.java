package com.innerman.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.innerman.dto.StreetDTO;
import com.innerman.geo.LocationEntity;
import com.innerman.geo.Polyline;
import org.apache.olingo.client.api.communication.request.retrieve.ODataServiceDocumentRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.v4.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.domain.ODataServiceDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by petrpopov on 27/06/16.
 */

@Service
public class StreetService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private static final String STREET_URL = "http://api.data.mos.ru/v1/datasets/2527/rows?api_key=";
    private static ObjectMapper om = new ObjectMapper();

    @Autowired
    private GeoMeter geoMeter;

    @Value("${api.key}")
    private String apiKey;

    private volatile Boolean updating = false;
    private Set<StreetDTO> streets = Collections.synchronizedSet(new HashSet<>());


    @PostConstruct
    public void init() {

        try {
            updateStreetsInfo();
        }
        catch (Throwable e) {
            logger.error("Failed to update streets info");
        }
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void streetsUpdateTask() {
        updateStreetsInfo();
    }

    public String getStreetInfo(String id) {

        Optional<StreetDTO> st = streets.stream().filter(s -> s.getId().equals(id)).findFirst();
        if(!st.isPresent()) {
            return null;
        }

        StreetDTO street = st.get();
        String res = "*Что ремонтируется:* " + street.getCells().getAddress() + "\n";

        if(!Strings.isNullOrEmpty(street.getCells().getDescr())) {
            res += "\n*" + street.getCells().getDescr() + "*";
        }

        if(!Strings.isNullOrEmpty(street.getCells().getLanesClosed())) {
            res += "\n*Количество перекрытых полос:* " + street.getCells().getLanesClosed();
        }

        if(!Strings.isNullOrEmpty(street.getCells().getName())) {
            res += "\n*Зачем:* " + street.getCells().getName();
        }

        if(!street.getCells().getAdmArea().isEmpty() && !street.getCells().getDistrict().isEmpty()) {
            res += "\n*Где:* " + street.getCells().getAdmArea().get(0) + ", " + street.getCells().getDistrict().get(0);
        }

        if(!Strings.isNullOrEmpty(street.getCells().getRequester())) {
            res += "\n*Кто:* " + street.getCells().getRequester();
        }

        if(street.getCells().getTsFrom() != null && street.getCells().getTsTo() != null) {
            res += "\n*Когда:* с " + sdf.format(street.getCells().getTsFrom()) + " по " + sdf.format(street.getCells().getTsTo());

            Date d = new Date();
            Boolean working = false;
            if(d.before(street.getCells().getTsTo())) {
                working = true;
            }

            res += "\n*Текущий статус:* " + ((working) ? "ремонтируется, выбирайте пути объезда" : "готово!");
        }

        return res;
    }

    public List<StreetDTO> findNearestStreets(LocationEntity loc) {

        logger.info("Finding nearest streets for location {}", loc);

        List<StreetDTO> res = new ArrayList<>();

        synchronized (this) {
            for (StreetDTO s : streets) {
                if(res.size() == 3) {
                    break;
                }

                for (Polyline l : s.getPolylines()) {
                    if(geoMeter.intersects(loc, l)) {
                        if(!res.contains(s)) {
                            res.add(s);
                            break;
                        }
                    }
                }

            }
        }

        return res;
    }

    public void updateStreetsInfo() {

        synchronized (this) {
            if(updating) {
                logger.info("Streets is already in update process");
                return;
            }

            updating = true;
        }

        logger.info("Updating streets info from mos.ru server");

        ODataClient client = ODataClientFactory.getV4();
        ODataServiceDocumentRequest req = client.getRetrieveRequestFactory().getServiceDocumentRequest(STREET_URL + apiKey);
        ODataRetrieveResponse<ODataServiceDocument> res = req.execute();

        InputStream rawResponse = res.getRawResponse();

        try {
            String stringFromInputStream = getStringFromInputStream(rawResponse);
            StreetDTO[] loadedStreets = om.readValue(stringFromInputStream, StreetDTO[].class);
            for (StreetDTO street : loadedStreets) {
                if(street.getCells() != null && street.getCells().getGeoData() != null) {
                    if(street.getCells().getGeoData().getCoordinates() != null) {
                        street.getCells().getGeoData().createPolyline();
                    }
                }
            }

            synchronized (this) {
                streets.clear();
                Collections.addAll(streets, loadedStreets);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        synchronized (this) {
            updating = false;
        }

        logger.info("Done updating streets info. Loaded {} streets", this.streets.size());
    }


    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
