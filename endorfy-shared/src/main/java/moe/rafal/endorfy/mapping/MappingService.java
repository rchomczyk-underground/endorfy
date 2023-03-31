package moe.rafal.endorfy.mapping;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MappingService {

    private final AsyncLoadingCache<String, EndorfyMapping> mappingCache;
    private final MappingRepository mappingRepository;

    public MappingService(MappingRepository mappingRepository) {
        this.mappingCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(30))
            .buildAsync(mappingRepository::findMappingByUsername);
        this.mappingRepository = mappingRepository;
    }

    public EndorfyMapping createMappingOf(UUID uniqueId, String username) {
        return new EndorfyMapping(uniqueId, username);
    }

    public CompletableFuture<EndorfyMapping> getMappingByUsername(String username) {
        return mappingCache.get(username)
            .exceptionally(exception -> {
                exception.printStackTrace();
                return null;
            });
    }

    public CompletableFuture<EndorfyMapping> getMappingByUniqueId(UUID uniqueId) {
        return CompletableFuture.supplyAsync(() ->
            mappingCache.synchronous().asMap().values()
                .stream()
                .filter(mapping -> mapping.getUniqueId().equals(uniqueId))
                .findFirst()
                .orElseGet(() -> mappingRepository.findMappingByUniqueId(uniqueId)))
            .exceptionally(exception -> {
                exception.printStackTrace();
                return null;
            });
    }

    public void saveMapping(EndorfyMapping mapping) {
        CompletableFuture.runAsync(() -> mappingRepository.saveMapping(mapping))
            .exceptionally(exception -> {
                exception.printStackTrace();
                return null;
            });
    }
}
