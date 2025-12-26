package com.ailab.smartasset.service.esign;

import com.ailab.smartasset.domain.AssetMovementRequest;
import com.ailab.smartasset.domain.enumeration.EsignStatus;
import com.ailab.smartasset.repository.AssetMovementRequestRepository;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing eSignAnyWhere workflows linked to AssetMovementRequest.
 */
@Service
@Transactional
public class EsignWorkflowService {

    private static final Logger LOG = LoggerFactory.getLogger(EsignWorkflowService.class);

    private final EsignAnyWhereClient esignAnyWhereClient;
    private final AssetMovementRequestRepository assetMovementRequestRepository;

    public EsignWorkflowService(EsignAnyWhereClient esignAnyWhereClient, AssetMovementRequestRepository assetMovementRequestRepository) {
        this.esignAnyWhereClient = esignAnyWhereClient;
        this.assetMovementRequestRepository = assetMovementRequestRepository;
    }

    public void startWorkflow(AssetMovementRequest request) {
        LOG.info("[MOVE-REQ:{}] Starting eSign workflow", request.getId());

        try {
            String workflowId = esignAnyWhereClient.createAndSend(request);

            request.setEsignWorkflowId(workflowId);
            request.setEsignStatus(EsignStatus.IN_PROGRESS);
            request.setEsignLastUpdate(Instant.now());

            assetMovementRequestRepository.save(request);
        } catch (Exception e) {
            request.setEsignStatus(EsignStatus.FAILED);
            request.setEsignLastUpdate(Instant.now());
            assetMovementRequestRepository.save(request);

            LOG.error("[MOVE-REQ:{}] eSign workflow failed", request.getId(), e);
        }
    }
}
