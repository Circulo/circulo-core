package com.circulo.service;

import com.circulo.model.Organization;
import com.circulo.model.StockLocation;
import com.circulo.model.StockSummary;

import java.util.Map;

/**
 * Created by tfulton on 7/1/15.
 */
public interface StockSummaryService {

    /**
     * Retrieves last summary and calculates current based on latest stock transaction history.
     * The calculated summary is saved to the db.
     *
     * @param organization The organization for which to calculate the summary.
     * @return A StockSummary object.
     */
    public StockSummary getCurrentSummary(Organization organization);

    /**
     * Retrieves last summary and calculates current based on latest stock transaction history.
     * The summary is further broken down by location.
     * The calculated summary is saved to the db.
     *
     * @param organization The organization for which to calculate the summary.
     * @return A map of StockSummary objects keyed by location.
     */
    public Map<StockLocation, StockSummary> getCurrentSummaryByLocation(Organization organization);

    /**
     * Retrieves (if it exists) or calculates a summary for a specific date in time.
     * The calculated summary is saved to the db.
     *
     * @param organization The organization for which to calculate the summary.
     * @return A StockSummary object.
     */
    public StockSummary getSummaryByDate(Organization organization);

}
