package com.innerman.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innerman.dto.StreetDTO;
import org.apache.olingo.client.api.communication.request.retrieve.ODataServiceDocumentRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.v4.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.domain.ODataServiceDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by petrpopov on 27/06/16.
 */

@Service
public class StreetService {

    private static ObjectMapper om = new ObjectMapper();

    @Value("${api.key}")
    private String apiKey;

    @PostConstruct
    public void init() {

        String serviceRoot = "http://api.data.mos.ru/v1/datasets/2527/rows?api_key=" + apiKey;
        ODataClient client = ODataClientFactory.getV4();
        ODataServiceDocumentRequest req = client.getRetrieveRequestFactory().getServiceDocumentRequest(serviceRoot);
        ODataRetrieveResponse<ODataServiceDocument> res = req.execute();

        InputStream rawResponse = res.getRawResponse();

        try {
            String stringFromInputStream = getStringFromInputStream(rawResponse);
            StreetDTO[] streets = om.readValue(stringFromInputStream, StreetDTO[].class);
            for (StreetDTO street : streets) {
                if(street.getCells() != null && street.getCells().getGeoData() != null) {
                    if(street.getCells().getGeoData().getCoordinates() != null) {
                        street.getCells().getGeoData().createPolyline();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
