package io.github.robothy.sdwebui.sdk.models.options;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtraImageOptionsTest {

    @Test
    void testSerialization() throws JsonProcessingException{
        ExtraImageOptions options = ExtraImageOptions.builder()
                .image("")
                .codeformerWeight(0.1)
                .upscalingResizeW(1L)
                .upscalingResizeH(2L)
                .codeformerVisibility(0.2)
                .extrasUpscaler2Visibility(0.3)
                .gfpganVisibility(0.4)
                .showExtrasResults(true)
                .upscaleFirst(true)
                .upscaler2("upscaler2")
                .upscalingCrop(true)
                .upscalingResize(0.0)
                .upscaler1("upscaler1")
                .resizeMode(0L)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        ExtraImageOptions deserializedTxt2ImageOptions =
                objectMapper.readValue(objectMapper.writeValueAsString(options), ExtraImageOptions.class);
        assertEquals(options, deserializedTxt2ImageOptions);

    }
}
