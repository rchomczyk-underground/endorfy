package moe.rafal.endorfy;

import moe.rafal.endorfy.mapping.MappingService;

public class EndorfyAccessor {

    private static MappingService currentMappingService;

    public static MappingService getCurrentMappingService() {
        return currentMappingService;
    }

    protected static void setCurrentMappingService(MappingService mappingService) {
        currentMappingService = mappingService;
    }
}
