package io.github.robothy.sdwebui.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.robothy.sdwebui.sdk.ExtraImage;
import io.github.robothy.sdwebui.sdk.SdWebuiBeanContainer;
import io.github.robothy.sdwebui.sdk.models.SdWebuiOptions;
import io.github.robothy.sdwebui.sdk.models.options.ExtraImageOptions;
import io.github.robothy.sdwebui.sdk.models.results.ExtraImageResult;
import io.github.robothy.sdwebui.sdk.utils.SdWebuiResponseUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DefaultExtraImageService implements ExtraImage {

    private static final String ExtraImage_PATH = "/sdapi/v1/extra-single-image";

    private final SdWebuiBeanContainer beanContainer;

    public DefaultExtraImageService(SdWebuiBeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    @Override
    public ExtraImageResult extraImage(ExtraImageOptions options) {
        HttpClient httpClient = this.beanContainer.getBean(HttpClient.class);
        ClassicHttpRequest extraImageRequest = buildTxt2ImageRequest(options);
        try {
            return httpClient.execute(extraImageRequest, this::parseExtraImageResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ClassicHttpRequest buildTxt2ImageRequest(ExtraImageOptions options) {
        SdWebuiOptions sdWebuiOptions = this.beanContainer.getBean(SdWebuiOptions.class);
        HttpPost httpPost = new HttpPost(sdWebuiOptions.getEndpoint() + ExtraImage_PATH);
        HttpEntity entity = buildExtraImageRequestEntity(options);
        httpPost.setEntity(entity);
        httpPost.addHeader("Content-Type", "application/json");
        return httpPost;
    }

    HttpEntity buildExtraImageRequestEntity(ExtraImageOptions options) {
        try {
            String payload = this.beanContainer.getBean(ObjectMapper.class)
                    .writeValueAsString(options);
            return new StringEntity(payload,StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    ExtraImageResult parseExtraImageResult(ClassicHttpResponse response) {

        SdWebuiResponseUtils.checkResponseStatus(response);

        try {
            return this.beanContainer.getBean(ObjectMapper.class)
                    .readValue(response.getEntity().getContent(), ExtraImageResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
