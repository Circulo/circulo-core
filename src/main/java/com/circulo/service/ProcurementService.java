package com.circulo.service;

import com.circulo.model.Procurement;

/**
 * Created by tfulton on 7/1/15.
 */
public interface ProcurementService {

    /**
     * Creates a stock procurement in the database.
     * <p>
     *     NOTE:  while the procurement details are indeed persisted, in order to
     *     actually affect stock levels and create detail stock transactions, the
     *     procurement must be "received" using {@link #receiveProcurement(Procurement procurement)
     *     receiveProcurement} method.
     * </p>
     *
     * @param procurement The Procurement object to persist.
     */
    public void createProcurement(Procurement procurement);

    /**
     * Updates an existing stock procurement.
     *
     * @param procurement The Procurement object to persist.
     */
    public void updateProcurement(Procurement procurement);

    /**
     * Receives a stock procurement and adjusts stock levels.  The following actions are taken:
     * <p>
     *     <ul>
     *         <li>Stock transactions are created for each variant included in the procurement</li>
     *         <li>Stock summary is recalculated based on the new stock transactions.</li>
     *         <li>The procurement is noted as received.</li>
     *     </ul>
     * </p>
     *
     * @param procurement The Procurement object to receive.
     */
    public void receiveProcurement(Procurement procurement);
}
