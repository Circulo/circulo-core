package com.circulo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by tfulton on 6/10/15.
 */
@Document(collection = "stock_transaction")
public class StockTransaction {

    @Id
    private String id;

    private String productVariantId;

    private Integer quantity;

    private String unitOfMeasure;

    private String userId;

    private String locationIdFrom;

    private String locationIdTo;

    private String notes;

    private LocalDateTime createDate = LocalDateTime.now(ZoneId.of("UTC"));
}
