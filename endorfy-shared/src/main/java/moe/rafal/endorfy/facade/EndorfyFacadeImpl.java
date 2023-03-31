package moe.rafal.endorfy.facade;

import moe.rafal.endorfy.mapping.EndorfyMapping;
import moe.rafal.endorfy.mapping.MappingService;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class EndorfyFacadeImpl implements EndorfyFacade {

    private final MappingService mappingService;

    public EndorfyFacadeImpl(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    @Override
    public CompletableFuture<EndorfyMapping> getMappingByUniqueId(UUID uniqueId) {
        return mappingService.getMappingByUniqueId(uniqueId);
    }

    @Override
    public CompletableFuture<EndorfyMapping> getMappingByUsername(String username) {
        return mappingService.getMappingByUsername(username);
    }
}
