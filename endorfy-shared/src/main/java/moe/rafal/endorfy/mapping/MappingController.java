package moe.rafal.endorfy.mapping;

import java.util.UUID;

public class MappingController {

    private final MappingService mappingService;

    public MappingController(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    public void createOrUpdateMapping(UUID uniqueId, String currentUsername) {
        mappingService.getMappingByUniqueId(uniqueId)
            .thenAccept(mapping -> {
                if (mapping == null) {
                    mappingService.saveMapping(mappingService.createMappingOf(uniqueId, currentUsername));
                    return;
                }

                String expectedUsername = mapping.getUsername();
                if (expectedUsername.equals(currentUsername)) {
                    return;
                }

                mapping.setUsername(currentUsername);
                mappingService.saveMapping(mapping);
            });
    }
}
