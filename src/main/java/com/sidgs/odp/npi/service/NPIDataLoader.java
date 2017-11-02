package com.sidgs.odp.npi.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by ayeluri on 8/14/2017.
 */

public interface NPIDataLoader {
    Log logger = LogFactory.getLog(NPIDataLoader.class);

    final String Healthcare_Provider_Taxonomy_Code =   "Healthcare Provider Taxonomy Code_" ;
    final String Provider_License_Number = "Provider License Number_" ;
    final String Provider_License_Number_State_Code = "Provider License Number State Code_" ;
    final String Healthcare_Provider_Primary_Taxonomy_Switch = "Healthcare Provider Primary Taxonomy Switch_" ;


    final String OtherProviderIdentifier =   "Other Provider Identifier_" ;
    final String OtherProviderIdentifierTypeCode   = "Other Provider Identifier Type Code_" ;
    final String OtherProviderIdentifierState = "Other Provider Identifier State_" ;
    final String OtherProviderIdentifierIssuer = "Other Provider Identifier Issuer_" ;

    /**
     * Load Taxonomy for the NPI Specified;
     * @param transactionId
     * @param npi
     */
    public void loadEntityProviderTaxonomies(String transactionId, int npi );

    /**
     * Load Identifier for the NPI Specified;
     * @param transactionId
     * @param npi
     */
    public void loadEntityProviderIdentifier(String transactionId, int npi) ;

    /**
     * Load NPI Entity  for the NPI Specified;
     * @param transactionId
     * @param npi
     */

    public void loadProviderNPI(String transactionId, int npi);

    /**
     * Reload Provider Taxonomy
     * @param transactionId
     */
    public void loadEntityProviderTaxonomies(String transactionId);

    /**
     * Reload  Provider Identifiers
     * @param transactionId
     */
    public void loadEntityProviderIdentifier(String transactionId) ;

    /**
     * Rebuild all Provider NPI
     * @param transactionId
     */
    public void loadProviderNPI(String transactionId);


}
