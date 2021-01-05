package com.chariotsolutions.example.springboot.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
//import com.amazonaws.regions.DefaultAwsRegionProviderChain;
//import com.amazonaws.services.rds.auth.GetIamAuthTokenRequest;
//import com.amazonaws.services.rds.auth.RdsIamAuthTokenGenerator;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsUtilities;
import software.amazon.awssdk.services.rds.model.GenerateAuthenticationTokenRequest;


/**
 *  Retrieves IAM-generated credentials for the configured user.
 *  <p>
 *  Before you can use this, you must grant <code>rds_iam</code> to the user.
 */
public class IAMAuthDataSource
extends PGSimpleDataSource
{
    private final static long serialVersionUID = 1L;
    
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public Connection getConnection(String user, String password)
    throws SQLException
    {
        // I'd like to do this in constructor, but it can throw SQLException
        setProperty("ssl", "true");
        setProperty("sslmode", "require");


        WebIdentityTokenFileCredentialsProvider webIdentityTokenFileCredentialsProvider = WebIdentityTokenFileCredentialsProvider.create();
        RdsUtilities rdsUtilities = RdsUtilities.builder()
                .credentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .region(Region.AP_SOUTHEAST_1)
                .build();

        GenerateAuthenticationTokenRequest buiAuthenticationTokenRequestld = GenerateAuthenticationTokenRequest.builder()
                .hostname(getServerName())
                .port(getPortNumber())
                .username(user)
                .build();

        String authToken = rdsUtilities.generateAuthenticationToken(buiAuthenticationTokenRequestld);

//        DefaultAWSCredentialsProviderChain credentialsProviderChain = new DefaultAWSCredentialsProviderChain();
//        String awsAccessKey = credentialsProviderChain.getCredentials().getAWSAccessKeyId();
//        String awsSecretKey = credentialsProviderChain.getCredentials().getAWSSecretKey();
//        logger.debug("requesting IAM token for user {}", user);
//        logger.debug("requesting IAM token for awsAccessKey {}", awsAccessKey);
//        logger.debug("requesting IAM token for awsSecretKey {}", awsSecretKey);
//        logger.debug("requesting IAM token for Region {}", new DefaultAwsRegionProviderChain().getRegion());
//
//        // adapted from https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/UsingWithRDS.IAMDBAuth.Connecting.Java.html
//        RdsIamAuthTokenGenerator generator = RdsIamAuthTokenGenerator.builder()
//            .credentials(credentialsProviderChain)
//            .region((new DefaultAwsRegionProviderChain()).getRegion())
//            .build();
//
//        GetIamAuthTokenRequest request = GetIamAuthTokenRequest.builder()
//            .hostname(getServerName())
//            .port(getPortNumber())
//            .userName(user)
//            .build();

//        String authToken = generator.getAuthToken(request);

        logger.debug("requesting IAM token for authToken {}", authToken);

        return super.getConnection(user, authToken);
    }
}
