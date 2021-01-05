/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.services.rds;

import java.util.function.Consumer;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.model.GenerateAuthenticationTokenRequest;

@SdkPublicApi
public interface RdsUtilities {
    /**
     * Create a builder that can be used to configure and create a {@link RdsUtilities}.
     */
    static Builder builder() {
        return new DefaultRdsUtilities.DefaultBuilder();
    }

    default String generateAuthenticationToken(Consumer<GenerateAuthenticationTokenRequest.Builder> getUrlRequest) {
        return generateAuthenticationToken(GenerateAuthenticationTokenRequest.builder().applyMutation(getUrlRequest).build());
    }

    default String generateAuthenticationToken(GenerateAuthenticationTokenRequest request) {
        throw new UnsupportedOperationException();
    }

    /**
     * Builder for creating an instance of {@link RdsUtilities}. It can be configured using {@link RdsUtilities#builder()}.
     * Once configured, the {@link RdsUtilities} can created using {@link #build()}.
     */
    @SdkPublicApi
    interface Builder {
        /**
         * The default region to use when working with the methods in {@link RdsUtilities} class.
         *
         * @return This object for method chaining
         */
        Builder region(Region region);

        /**
         * The default credentials provider to use when working with the methods in {@link RdsUtilities} class.
         *
         * @return This object for method chaining
         */
        Builder credentialsProvider(AwsCredentialsProvider credentialsProvider);

        /**
         * Create a {@link RdsUtilities}
         */
        RdsUtilities build();
    }
}