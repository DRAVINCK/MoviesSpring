package com.dravinck.movies.service;

import com.dravinck.movies.entity.Streaming;
import com.dravinck.movies.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StreamingService {

    private final StreamingRepository repository;

    public List<Streaming> findAll(){
        return repository.findAll();
    }

    public Streaming save(Streaming streaming){
        return repository.save(streaming);
    }

    public Optional<Streaming> findById(Long id){
        return repository.findById(id);
    }

    public void delete (Long id){
        repository.deleteById(id);
    }
}
