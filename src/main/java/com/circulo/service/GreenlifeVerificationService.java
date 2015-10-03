package com.circulo.service;

import com.circulo.model.Patient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * Created by azim on 8/11/15.
 */
@Component("greenlifeVerificationService")
public class GreenlifeVerificationService extends AbstractVerificationService {

    private final static Logger logger = LoggerFactory.getLogger(GreenlifeVerificationService.class);

    //@TODO : Fetch from properties
    //@Value("${circulo.core.verification.greenlife.host}")
    private String host = "verify.greenlifemedical.com";

    //@TODO : Fetch from properties
    //@Value("${circulo.core.verification.greenlife.path}")
    private String path = "/recommendations";

    @Override
    public boolean verify(Patient patient) throws Exception {
        RequestConfig defaultRequestConfig = getRequestConfig();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();

        String normalizedRecommendationNo = patient.getRecommendation().getRecommendationNo().replaceAll("\\s+", "");

        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost(host)
                .setPath(path)
                .setParameter("utf8", "&#x2713;")
                .setParameter("rec_id", normalizedRecommendationNo)
                .build();

        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpGet);

        final int statusCode = response.getStatusLine().getStatusCode();
        final String statusText = response.getStatusLine().getReasonPhrase();

        if (statusCode != 200) {
            logger.error("Greenline Verification Service Error. HTTP Status: " + statusCode + " " + statusText);
            //@TODO : Define an exception hierarchy for Circulo Core and throw specific exception here.
            //throw new RuntimeException("Greenline Verification Service Error. HTTP Status: " + statusCode + " " + statusText);
        }

        HttpEntity responseEntity = response.getEntity();

        String responseBody = null;
        if (responseEntity != null) {
            logger.debug("Response content length: " + responseEntity.getContentLength());
            responseBody = EntityUtils.toString(responseEntity);
            logger.debug("Response body: " + responseBody);
        }

        EntityUtils.consume(responseEntity);

        return parseAndCompareResult(responseBody, normalizedRecommendationNo);
    }

    private boolean parseAndCompareResult(String htmlResponse, String normalizedRecommendationNo) {
        if (htmlResponse == null || htmlResponse.isEmpty()) return false;

        try {
            Document doc = Jsoup.parse(htmlResponse);
            Element result = doc.select("div.result").first();
            if (result == null) {
                logger.error("Result is null");
                return false;
            }

            String recommendationInfo = result.text();
            if (recommendationInfo == null || recommendationInfo.isEmpty()) {
                logger.error("recommendationInfo is null");
                return false;
            }

            if(recommendationInfo.contains("Recommendation " + normalizedRecommendationNo + " is VALID")) return true;

            return false;
        } catch (Exception ex) {
            logger.error("Exception in parsing html response: " + htmlResponse);
            return false;
        }
    }
}
