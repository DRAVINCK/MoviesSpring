package com.dravinck.movies.mapper;

import com.dravinck.movies.controller.request.CategoryRequest;
import com.dravinck.movies.controller.request.StreamingRequest;
import com.dravinck.movies.controller.response.CategoryResponse;
import com.dravinck.movies.controller.response.StreamingResponse;
import com.dravinck.movies.entity.Category;
import com.dravinck.movies.entity.Streaming;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamingMapper {
    public static Streaming toStreaming(StreamingRequest streaming) {
        return Streaming
                .builder()
                .name(streaming.name())
                .build();

    }

    public static StreamingResponse toStreamingResponse(Streaming streaming) {
        return StreamingResponse
                .builder()
                .id(streaming.getId())
                .name(streaming.getName())
                .build();
    }
}

