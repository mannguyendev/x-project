package com.mnguyendev.xproject.common;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class GenerateUUID implements IdentifierGenerator {

    private static final Logger log = LoggerFactory.getLogger(GenerateUUID.class);

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        UUID uuid = UUID.randomUUID();
        log.info(uuid.toString());
        return uuid.toString();
    }
}
