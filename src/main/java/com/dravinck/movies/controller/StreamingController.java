package com.dravinck.movies.controller;

import com.dravinck.movies.controller.request.StreamingRequest;
import com.dravinck.movies.controller.response.StreamingResponse;
import com.dravinck.movies.entity.Streaming;
import com.dravinck.movies.mapper.StreamingMapper;
import com.dravinck.movies.service.StreamingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/movies/streaming")
@RequiredArgsConstructor
@Tag(name = "Streaming", description = "Endpoints de streamings")
public class StreamingController {


    private final StreamingService streamingService;


    @GetMapping
    public ResponseEntity<List<StreamingResponse>> getAllStreaming() {
        List<StreamingResponse> streamings = streamingService.findAll()
                .stream() //steam é uma sequencia de elementos que suporta operações sequenciais e paralelas
                .map(streaming -> StreamingMapper.toStreamingResponse(streaming)) // mapea cada objeto Category para um objeto CategoryResponse
                .toList();
        return ResponseEntity.ok(streamings);


    }

    @PostMapping
    public ResponseEntity<StreamingResponse> saveStreaming(@Valid @RequestBody StreamingRequest request) {
        Streaming savedStreaming = streamingService.save(StreamingMapper.toStreaming(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(StreamingMapper.toStreamingResponse(savedStreaming));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> getByStreamingId(@PathVariable Long id) {
        return streamingService.findById(id)
                .map(Streaming -> ResponseEntity.ok(StreamingMapper.toStreamingResponse(Streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByStreamingId(@PathVariable Long id) {
        streamingService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
