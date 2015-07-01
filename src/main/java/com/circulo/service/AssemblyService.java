package com.circulo.service;

import com.circulo.model.Assembly;

/**
 * Created by tfulton on 7/1/15.
 */
public interface AssemblyService {

    /**
     * Creates an inventory assembly order and persists it to the database.
     * <p>
     *     NOTE:  The inventory on hand is not actually adjusted when
     *     an assembly is created.
     * </p>
     * @param assembly The Assembly object to persist.
     */
    public void createAssembly(Assembly assembly);

    /**
     * Updates an existing assembly order.
     *
     * @param assembly The Assembly object to persist.
     */
    public void updateAssembly(Assembly assembly);

    /**
     * Processes an assembly order and adjusts stock levels.  The following actions
     * are taken:
     * <p>
     *     <ul>
     *         <li>Stock transactions are created for newly assembled items.</li>
     *         <li>Stock transactions are created for existing inventory from which
     *         the assembled items are created (if they existed).</li>
     *         <li>Stock summary is recalculated based on the new stock transactions.</li>
     *         <li>The assembly is noted as processed.</li>
     *     </ul>
     * </p>
     * @param assembly
     */
    public void processAssembly(Assembly assembly);
}
