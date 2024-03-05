package io.github.robothy.sdwebui.sdk;


import io.github.robothy.sdwebui.sdk.models.options.ExtraImageOptions;
import io.github.robothy.sdwebui.sdk.models.results.ExtraImageResult;

public interface ExtraImage {

    ExtraImageResult extraImage(ExtraImageOptions options);
}
