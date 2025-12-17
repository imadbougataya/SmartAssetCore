package com.ailab.smartasset.audit;

import com.ailab.smartasset.domain.enumeration.EntityAuditAction;

@FunctionalInterface
public interface EntityAuditEventWriter {
    public void writeAuditEvent(Object target, EntityAuditAction action);
}
