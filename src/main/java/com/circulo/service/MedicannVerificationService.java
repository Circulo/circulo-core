package com.circulo.service;

import com.circulo.model.Patient;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by azim on 7/27/15.
 */
@Component("medicannVerificationService")
public class MedicannVerificationService implements VerificationService {

    private final static Logger logger = LoggerFactory.getLogger(MedicannVerificationService.class);

    public static final int MAX_REDIRECTS = 50;

    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String INVALID_ID = "Invalid ID, ID not found in our database.";
    public static final String PATIENT_ID = "patientID";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String YEAR = "year";

    //@TODO : Fetch from properties
    //@Value("${circulo.core.verification.request-timeout}")
    private int requestTimeout = 5000; // request timeout in ms

    //@TODO : Fetch from properties
    //@Value("${circulo.core.verification.medicann.host}")
    private String host = "www.medicann.com";

    //@TODO : Fetch from properties
    //@Value("${circulo.core.verification.medicann.path}")
    private String path = "/verification/verify.php";

    @Override
    public boolean verify(Patient patient) throws Exception {
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setStaleConnectionCheckEnabled(true)
                .setRedirectsEnabled(true)
                .setMaxRedirects(MAX_REDIRECTS)
                .setRelativeRedirectsAllowed(true)
                .setAuthenticationEnabled(true)
                .setConnectionRequestTimeout(requestTimeout)
                .setConnectTimeout(requestTimeout)
                .setSocketTimeout(requestTimeout)
                .build();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(patient.getMember().getDateOfBirth());
        List<NameValuePair> requestData = new ArrayList<>();
        requestData.add(new BasicNameValuePair(PATIENT_ID, patient.getRecommendation().getRecommendationNo()));
        requestData.add(new BasicNameValuePair(MONTH, "" + calendar.get(Calendar.MONTH) + 1));    // Calendar.MONTH is zero-based
        requestData.add(new BasicNameValuePair(DAY, "" + calendar.get(Calendar.DAY_OF_MONTH)));
        requestData.add(new BasicNameValuePair(YEAR, "" + calendar.get(Calendar.YEAR)));


        final UrlEncodedFormEntity entity = new UrlEncodedFormEntity(requestData);
        final Header contentTypeHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE,
                CONTENT_TYPE);
        final Header[] headers = {contentTypeHeader};

        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();

        try {
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(host)
                    .setPath(path)
                    .build();

            HttpPost httpPost = new HttpPost(uri);

            for (Header header : headers) {
                httpPost.addHeader(header);
            }
            httpPost.setEntity(entity);
            logger.debug("Request Entity: " + entity.getContent().toString());

            logger.debug("Executing request " + httpPost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                logger.debug("----------------------------------------");
                logger.debug("Status:      " + response.getStatusLine());
                logger.debug("Status Code: " + response.getStatusLine().getStatusCode());

                HttpEntity responseEntity = response.getEntity();

                String responseBody = null;
                if (responseEntity != null) {
                    logger.debug("Response content length: " + responseEntity.getContentLength());
                    responseBody = EntityUtils.toString(responseEntity);
                    logger.debug("Response body: " + responseBody);
                }

                EntityUtils.consume(responseEntity);

                //@TODO : Match against a set of expected response texts.
                return (!getResult(responseBody).equals(INVALID_ID));
            } finally {
                response.close();
            }

        } catch (ConnectTimeoutException | SocketTimeoutException tex) {
            throw tex;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.warn("Unable to close http connection", e);
            }
            return false;
        }

    }

    private String getResult(String htmlResponse) {
        if (htmlResponse == null || htmlResponse.isEmpty()) return null;

        try {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode rootNode = cleaner.clean(htmlResponse);
            Object[] foundList = rootNode.evaluateXPath("//body/div/div/table");
            if(foundList == null || foundList.length < 1) {
                logger.debug("Response doesn't have table: " + htmlResponse);
                return null;
            }

            TagNode tableNode = (TagNode)foundList[0];
            TagNode[] resultNodes = tableNode.getElementsByAttValue("id", "result", true, true);
            if(resultNodes == null || resultNodes.length < 1) {
                logger.debug("Response doesn't have result: " + htmlResponse);
                return null;
            }
            return resultNodes[0].getText().toString();
        } catch (Exception ex) {
            logger.error("Exception in parsing html response: " + htmlResponse);
            return null;
        }
    }
}
