package com.circulo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by tfulton on 6/10/15.
 */
@Document(collection = "stock_summary_snapshot")
public class StockSummarySnapshot {

    @Id
    private String id;

    private List<StockItemCount> stockItemCounts;

    private LocalDateTime lastCalculatedSnapshot;
}
