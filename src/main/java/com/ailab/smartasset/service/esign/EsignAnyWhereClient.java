package com.ailab.smartasset.service.esign;

import com.ailab.smartasset.domain.AssetMovementRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class EsignAnyWhereClient {

    private static final Logger LOG = LoggerFactory.getLogger(EsignAnyWhereClient.class);

    @Value("${esign.base-url:https://demo.esignanywhere.net}")
    private String baseUrl;

    @Value("${esign.api-token}")
    private String apiToken;

    @Value("${esign.move-request-template-id}")
    private String templateId;

    private final RestTemplate restTemplate = new RestTemplate();

    /* ============================================================
       PUBLIC API
     ============================================================ */
    public String createAndSend(AssetMovementRequest request) {
        String draftId = createDraft(templateId, request);
        //        uploadPdfAndGetFileId(Path.of("C:\\Java\\Projets\\OCP\\SmartAssetCore\\Modele_Demande_Transfert_Actif_SmartAssets_v2.pdf"));
        Map<String, Object> templateElements = getTemplateElements(templateId);
        //        sendDraft(draftId);
        //        setFormFields(draftId, request);
        Map<String, Object> applyFormValuesToTemplateElements = applyFormValuesToTemplateElements(templateElements, request);
        //        configureDraftFields(draftId, applyFormValuesToTemplateElements);
        //        return sendDraft(draftId, request);
        return sendDraft(draftId);
    }

    //    public String createAndSend(AssetMovementRequest request) {
    //
    //        // 1️⃣ Upload PDF → FileId
    //        String fileId = uploadPdfAndGetFileId(
    //                Path.of("C:\\Java\\Projets\\OCP\\SmartAssetCore\\Modele_Demande_Transfert_Actif_SmartAssets_v2.pdf")
    //        );
    //
    //        // 2️⃣ Create draft AVEC valeurs
    //        String draftId = createDraftWithValues(fileId, request);
    //
    //        // 3️⃣ Send draft (sans override)
    //        return sendDraft(draftId,request);
    //    }

    private String sendDraft(String draftId) {
        String url = baseUrl + "/api/v6/draft/send";

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("DraftId", draftId);

        persistPayload("send-draft", body);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers()), Map.class);

        Map<String, Object> result = response.getBody();

        // ✅ eSign peut ne PAS retourner EnvelopeId
        if (result == null || !result.containsKey("EnvelopeId")) {
            LOG.info("[eSign] Draft sent successfully (no EnvelopeId returned, draftId={})", draftId);
            return draftId; // ✅ fallback propre
        }

        String envelopeId = String.valueOf(result.get("EnvelopeId"));

        LOG.info("[eSign] Draft sent successfully (envelopeId={})", envelopeId);
        return envelopeId;
    }

    //    private String sendDraft(String draftId, AssetMovementRequest r) {
    //
    //    String url = baseUrl + "/api/v6/draft/send";
    //
    //
    //    Map<String, Object> body = new LinkedHashMap<>();
    //    body.put("DraftId", draftId);
    //
    //
    //    persistPayload("send-draft", body);
    //
    //    ResponseEntity<Map> response = restTemplate.exchange(
    //        url,
    //        HttpMethod.POST,
    //        new HttpEntity<>(body, headers()),
    //        Map.class
    //    );
    //
    //    return response.getBody().get("EnvelopeId").toString();
    //}
    //    private String sendDraft(String draftId, AssetMovementRequest r) {
    //
    //        LOG.info("[eSign] Sending draft (draftId={})", draftId);
    //
    //        String url = baseUrl + "/api/v6/draft/send";
    //
    //        Map<String, Object> body = new LinkedHashMap<>();
    //        body.put("DraftId", draftId);
    //
    //        // 🔎 Log payload exact envoyé
    //        persistPayload("send-draft", body);
    //
    //        ResponseEntity<Map> response = restTemplate.exchange(
    //                url,
    //                HttpMethod.POST,
    //                new HttpEntity<>(body, headers()),
    //                Map.class
    //        );
    //
    //        Map<String, Object> result = response.getBody();
    //
    //        if (result == null || !result.containsKey("EnvelopeId")) {
    //            throw new IllegalStateException("EnvelopeId not returned by eSignAnyWhere");
    //        }
    //
    //        String envelopeId = result.get("EnvelopeId").toString();
    //
    //        LOG.info("[eSign] Draft sent successfully (envelopeId={})", envelopeId);
    //        return envelopeId;
    //    }
    private Map<String, Object> textField(String elementId, int documentNumber, String value) {
        Map<String, Object> field = new LinkedHashMap<>();
        field.put("ElementId", elementId);
        field.put("DocumentNumber", documentNumber);
        field.put("Value", value != null ? value : "");
        return field;
    }

    @SuppressWarnings("unchecked")
    private String createDraftWithValues(String fileId, AssetMovementRequest r) {
        String url = baseUrl + "/api/v6/draft/create";

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Name", "Demande_Transfert_Actif");
        body.put("ShareWithTeam", false);

        // 🔥 CRITIQUE : empêcher héritage template
        body.put("UseTemplateRecipients", false);

        /* ===========================
       DOCUMENT
       =========================== */
        body.put("Documents", List.of(Map.of("FileId", fileId, "DocumentNumber", 1)));

        /* ===========================
       SIGNER UNIQUE
       =========================== */
        Map<String, Object> signer = new LinkedHashMap<>();
        signer.put("RecipientId", "SIGNER_1");
        signer.put("Email", "imad@ailab.ma");
        signer.put("FirstName", r.getApprovedBy().getFirstName());
        signer.put("LastName", r.getApprovedBy().getLastName());
        signer.put("LanguageCode", "fr");

        /* ===========================
       SIGNATURE FIELD
       =========================== */
        List<Map<String, Object>> signatureFields = List.of(signatureField("SIGNATURE_1", 1, 350, 120, 180, 50));

        /* ===========================
       TEXT FIELDS
       =========================== */
        List<Map<String, Object>> textFields = List.of(
            tf("REQUEST_REF", 1, "SA-MOVE-" + r.getId(), 260, 650, 160, 18),
            tf("REQUEST_TYPE", 1, "Transfert d’actif", 260, 600, 160, 18),
            tf("REQUESTER_NAME", 1, r.getRequestedBy().getFirstName() + " " + r.getRequestedBy().getLastName(), 260, 545, 200, 18),
            tf("REQUESTER_EMAIL", 1, safe(r.getRequestedBy().getEmail()), 260, 520, 200, 18)
        );

        Map<String, Object> predefined = new LinkedHashMap<>();
        predefined.put("TextFields", textFields);
        predefined.put("SignatureFields", signatureFields);

        Map<String, Object> elements = new LinkedHashMap<>();
        elements.put("PredefinedFieldElements", predefined);

        Map<String, Object> sign = new LinkedHashMap<>();
        sign.put("SequenceMode", "NoSequenceEnforced");
        sign.put("SigningGroup", "1");
        sign.put("Signers", List.of(signer));
        sign.put("Elements", elements);

        body.put("Activities", List.of(Map.of("Action", Map.of("Sign", sign))));

        persistPayload("create-draft", body);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers()), Map.class);

        return response.getBody().get("DraftId").toString();
    }

    private Map<String, Object> signatureField(String elementId, int page, double x, double y, double width, double height) {
        Map<String, Object> position = new LinkedHashMap<>();
        position.put("PageNumber", page);
        position.put("X", x);
        position.put("Y", y);

        Map<String, Object> size = new LinkedHashMap<>();
        size.put("Width", width);
        size.put("Height", height);

        Map<String, Object> fieldDefinition = new LinkedHashMap<>();
        fieldDefinition.put("Position", position);
        fieldDefinition.put("Size", size);

        Map<String, Object> clickToSign = new LinkedHashMap<>();
        clickToSign.put("Preferred", true);
        clickToSign.put("UseExternalSignatureImage", "Disabled");

        Map<String, Object> allowedSignatureTypes = new LinkedHashMap<>();
        allowedSignatureTypes.put("ClickToSign", clickToSign);

        Map<String, Object> signature = new LinkedHashMap<>();
        signature.put("ElementId", elementId);
        signature.put("DocumentNumber", 1);
        signature.put("AssignedTo", "SIGNER_1"); // 🔥 CRITIQUE
        signature.put("Required", true);
        signature.put("AllowedSignatureTypes", allowedSignatureTypes);
        signature.put("FieldDefinition", fieldDefinition);
        signature.put("UseExternalTimestampServer", false);

        return signature;
    }

    private Map<String, Object> textField(String elementId, int page, String value, double x, double y, double w, double h) {
        Map<String, Object> field = new LinkedHashMap<>();
        field.put("ElementId", elementId);
        field.put("Value", value);
        field.put("DocumentNumber", page);

        Map<String, Object> fieldDef = new LinkedHashMap<>();
        fieldDef.put("Position", Map.of("PageNumber", page, "X", x, "Y", y));
        fieldDef.put("Size", Map.of("Width", w, "Height", h));

        field.put("FieldDefinition", fieldDef);
        return field;
    }

    private Map<String, Object> tf(String elementId, int page, String value, double x, double y, double width, double height) {
        Map<String, Object> field = new LinkedHashMap<>();
        field.put("ElementId", elementId);
        field.put("DocumentNumber", page);
        field.put("Value", value == null ? "" : value);

        Map<String, Object> position = new LinkedHashMap<>();
        position.put("PageNumber", page);
        position.put("X", x);
        position.put("Y", y);

        Map<String, Object> size = new LinkedHashMap<>();
        size.put("Width", width);
        size.put("Height", height);

        Map<String, Object> fieldDef = new LinkedHashMap<>();
        fieldDef.put("Position", position);
        fieldDef.put("Size", size);

        field.put("FieldDefinition", fieldDef);
        return field;
    }

    /**
     * Helper: construit un TextField (PredefinedFieldElements.TextFields item)
     */
    //    private Map<String, Object> textField(String elementId, int documentNumber, String value) {
    //        Map<String, Object> m = new LinkedHashMap<>();
    //        m.put("ElementId", elementId);
    //        m.put("DocumentNumber", documentNumber);
    //        m.put("Value", safe(value));
    //        return m;
    //    }
    /**
     * Helper: évite les null dans Map.of / et sécurise les concat
     */
    private String safe(String s) {
        return s == null ? "TOTO" : s;
    }

    /**
     * Debug: log/stocke le payload en JSON propre. - si tu as Jackson
     * (ObjectMapper), c’est idéal. - sinon je te donne un fallback.
     */
    private void persistPayload(String label, Object payload) {
        try {
            // Si tu as déjà un ObjectMapper dans la classe, utilise-le directement.
            com.fasterxml.jackson.databind.ObjectMapper om = new com.fasterxml.jackson.databind.ObjectMapper();
            String json = om.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
            LOG.debug("[eSign][PAYLOAD:{}]\n{}", label, json);
            // si tu veux aussi écrire dans un fichier, fais-le ici
        } catch (Exception e) {
            LOG.debug("[eSign][PAYLOAD:{}] {}", label, String.valueOf(payload));
        }
    }

    public String uploadPdfAndGetFileId(Path pdfPath) {
        String url = baseUrl + "/api/v6/file/upload";

        LOG.info("[eSign] Uploading PDF file: {}", pdfPath.getFileName());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(pdfPath.toFile()));

        HttpHeaders headers = headers();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        String fileId = (String) response.getBody().get("FileId");

        if (fileId == null) {
            throw new IllegalStateException("FileId not returned after upload");
        }

        LOG.info("[eSign] File uploaded successfully (fileId={})", fileId);
        return fileId;
    }

    public void configureDraftFields(String draftId, Map<String, Object> configuredTemplateElements) {
        String url = baseUrl + "/api/v6/draft/" + draftId + "/configuration";

        LOG.info("[eSign] Configuring draft fields (draftId={})", draftId);

        try {
            // 🔎 Dump EXACT du payload envoyé (avant POST)
            persistPayload("draft-configuration-" + draftId, configuredTemplateElements.toString());

            HttpHeaders headers = headers();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(configuredTemplateElements, headers);

            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

            LOG.info("[eSign] Draft configuration applied successfully (draftId={}, status={})", draftId, response.getStatusCode());
        } catch (Exception e) {
            LOG.error("[eSign] ERROR while configuring draft fields (draftId={})", draftId, e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> applyFormValuesToTemplateElements(Map<String, Object> templateElements, AssetMovementRequest r) {
        Map<String, String> values = new LinkedHashMap<>();

        values.put("REQUEST_REF", "SA-MOVE-" + r.getId());
        values.put("REQUEST_TYPE", "Transfert d’actif");
        values.put("REQUESTER_NAME", r.getRequestedBy().getFirstName() + " " + r.getRequestedBy().getLastName());

        values.put("REQUESTER_EMAIL", r.getRequestedBy().getEmail() != null ? r.getRequestedBy().getEmail() : "");

        values.put("ASSET_CODE", r.getAsset().getAssetCode());
        values.put("ASSET_NAME", r.getAsset().getSerialNumber());
        values.put("ASSET_CRITICALITY", r.getAsset().getCriticality().name());
        values.put("FROM_SITE", r.getFromLocationLabel());
        values.put("TO_SITE", r.getToLocationLabel());
        values.put("TRANSFER_REASON", r.getReason());

        Object activitiesObj = templateElements.get("Activities");
        if (!(activitiesObj instanceof List)) {
            return templateElements;
        }

        List<Map<String, Object>> activities = (List<Map<String, Object>>) activitiesObj;

        for (Map<String, Object> activity : activities) {
            Map<String, Object> action = (Map<String, Object>) activity.get("Action");
            if (action == null) {
                continue;
            }

            Map<String, Object> sign = (Map<String, Object>) action.get("Sign");
            if (sign == null) {
                continue;
            }

            Map<String, Object> elements = (Map<String, Object>) sign.get("Elements");
            if (elements == null) {
                continue;
            }

            List<Map<String, Object>> textBoxes = (List<Map<String, Object>>) elements.get("TextBoxes");

            if (textBoxes == null) {
                continue;
            }

            for (Map<String, Object> textBox : textBoxes) {
                String elementId = (String) textBox.get("ElementId");

                if (elementId != null && values.containsKey(elementId)) {
                    textBox.put("Value", values.get(elementId));
                }
            }
        }

        return templateElements;
    }

    public void downloadTemplateFiles(String templateId) {
        String url = baseUrl + "/api/v6/template/" + templateId + "/files";

        LOG.info("[eSign] Downloading template files (templateId={})", templateId);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers()), byte[].class);

        byte[] fileContent = response.getBody();

        if (fileContent == null || fileContent.length == 0) {
            throw new IllegalStateException("Template file is empty");
        }

        try {
            Path dir = Paths.get("esign-logs", "templates");
            Files.createDirectories(dir);

            Path file = dir.resolve("template-" + templateId + ".pdf");
            Files.write(file, fileContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            LOG.info("[eSign] Template PDF saved → {}", file.toAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException("Failed to save template file", e);
        }
    }

    public Map<String, Object> getTemplate(String templateId) {
        String url = baseUrl + "/api/v6/template/" + templateId;

        LOG.info("[eSign] Fetching template metadata (templateId={})", templateId);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers()), Map.class);

        Map<String, Object> body = response.getBody();

        persistPayload("template-" + templateId, body.toString());

        LOG.info("[eSign] Template metadata retrieved (templateId={})", templateId);

        return body;
    }

    public Map<String, Object> getTemplateElements(String templateId) {
        String url = baseUrl + "/api/v6/template/" + templateId + "/elements";

        LOG.info("[eSign] Fetching template elements (templateId={})", templateId);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers()), Map.class);

        Map<String, Object> body = response.getBody();

        if (body == null) {
            throw new IllegalStateException("Empty response from /template/{id}/elements");
        }

        // Persist raw response for deep inspection
        persistPayload("template-elements-" + templateId, body.toString());

        LOG.info("[eSign] Template elements retrieved successfully (templateId={})", templateId);

        return body;
    }

    private void configureDraftFields(String draftId, AssetMovementRequest r) {
        String url = baseUrl + "/api/v6/draft/" + draftId + "/configuration";

        // ===== Forms values =====
        Map<String, Object> forms = new LinkedHashMap<>();

        forms.put("REQUEST_REF", "SA-MOVE-" + r.getId());
        forms.put("REQUEST_TYPE", "Transfert d’actif");
        forms.put("REQUESTER_NAME", r.getRequestedBy().getFirstName() + " " + r.getRequestedBy().getLastName());
        forms.put("REQUESTER_EMAIL", r.getRequestedBy().getEmail());
        forms.put("ASSET_CODE", r.getAsset().getAssetCode());
        forms.put("ASSET_NAME", r.getAsset().getSerialNumber());
        forms.put("ASSET_CRITICALITY", r.getAsset().getCriticality().name());
        forms.put("FROM_SITE", r.getFromLocationLabel());
        forms.put("TO_SITE", r.getToLocationLabel());
        forms.put("TRANSFER_REASON", r.getReason());

        // ===== OverrideFormFieldValues =====
        Map<String, Object> override = new LinkedHashMap<>();
        override.put("Forms", forms);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("OverrideFormFieldValues", override);

        // ===== LOG PAYLOAD =====
        persistPayload("02-draft-configuration-" + draftId, body.toString());

        LOG.info("[eSign] Configuring draft fields (draftId={})", draftId);

        restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers()), Void.class);

        LOG.info("[eSign] Draft fields configured successfully (draftId={})", draftId);
    }

    /* ============================================================
       STEP 1 – CREATE DRAFT
     ============================================================ */
    private String createDraft(String templateId, AssetMovementRequest r) {
        String url = baseUrl + "/api/v6/template/createdraft";

        String body =
            "{\n" +
            "  \"TemplateId\": \"" +
            templateId +
            "\",\n" +
            "  \"Name\": \"Demande de transfert d’actif\",\n" +
            "  \"UnassignedElements\": {\n" +
            "    \"PredefinedFieldElements\": {\n" +
            "      \"TextFields\": [\n" +
            "        { \"ElementId\": \"REQUEST_REF\", \"DocumentNumber\": 1, \"Value\": \"SA-MOVE-" +
            r.getId() +
            "\" },\n" +
            "        { \"ElementId\": \"REQUEST_TYPE\", \"DocumentNumber\": 1, \"Value\": \"Transfert d’actif\" },\n" +
            "        { \"ElementId\": \"REQUESTER_NAME\", \"DocumentNumber\": 1, \"Value\": \"" +
            r.getRequestedBy().getFirstName() +
            " " +
            r.getRequestedBy().getLastName() +
            "\" },\n" +
            "        { \"ElementId\": \"REQUESTER_EMAIL\", \"DocumentNumber\": 1, \"Value\": \"" +
            r.getRequestedBy().getEmail() +
            "\" },\n" +
            "        { \"ElementId\": \"ASSET_CODE\", \"DocumentNumber\": 1, \"Value\": \"" +
            r.getAsset().getAssetCode() +
            "\" },\n" +
            "        { \"ElementId\": \"ASSET_NAME\", \"DocumentNumber\": 1, \"Value\": \"" +
            r.getAsset().getSerialNumber() +
            "\" },\n" +
            "        { \"ElementId\": \"ASSET_CRITICALITY\", \"DocumentNumber\": 1, \"Value\": \"" +
            r.getAsset().getCriticality().name() +
            "\" },\n" +
            "        { \"ElementId\": \"FROM_SITE\", \"DocumentNumber\": 1, \"Value\": \"" +
            r.getFromLocationLabel() +
            "\" },\n" +
            "        { \"ElementId\": \"TO_SITE\", \"DocumentNumber\": 1, \"Value\": \"" +
            r.getToLocationLabel() +
            "\" },\n" +
            "        { \"ElementId\": \"TRANSFER_REASON\", \"DocumentNumber\": 1, \"Value\": \"" +
            r.getReason() +
            "\" }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";

        persistPayload("createdraft", body);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers()), Map.class);

        Map<String, Object> result = response.getBody();
        String draftId = (String) result.get("DraftId");

        LOG.info("[eSign] Draft created (draftId={})", draftId);
        return draftId;
    }

    //    private void persistPayload(String prefix, String json) {
    //        try {
    //            Path dir = Paths.get("esign-logs");
    //            Files.createDirectories(dir);
    //
    //            String ts = OffsetDateTime.now().toString().replace(":", "-");
    //            Path file = dir.resolve(prefix + "-" + ts + ".json");
    //
    //            Files.writeString(file, json, StandardCharsets.UTF_8);
    //            LOG.info("[eSign] {} payload saved → {}", prefix, file.toAbsolutePath());
    //        } catch (Exception e) {
    //            LOG.error("[eSign] Failed to persist raw JSON", e);
    //        }
    //    }

    /* ============================================================
       STEP 2 – SET FORM FIELDS
     ============================================================ */
    private void setFormFields(String draftId, AssetMovementRequest r) {
        String url = baseUrl + "/api/V6/draft/setformfields";

        Map<String, Object> body = new HashMap<>();
        body.put("DraftId", draftId);
        body.put("FormFields", buildFormFields(r));

        restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers()), Void.class);

        LOG.info("[eSign] Form fields set (draftId={})", draftId);
    }

    private List<Map<String, String>> buildFormFields(AssetMovementRequest r) {
        return List.of(
            field("REQUEST_REF", "SA-MOVE-" + r.getId()),
            //                field("REQUEST_DATE", DateTimeFormatter.ISO_DATE.format(r.getRequestedAt())),
            field("REQUEST_TYPE", "Transfert d’actif"),
            field("REQUESTER_NAME", r.getRequestedBy().getFirstName() + " " + r.getRequestedBy().getLastName()),
            field("REQUESTER_EMAIL", r.getRequestedBy().getEmail()),
            field("ASSET_CODE", r.getAsset().getAssetCode()),
            field("ASSET_NAME", r.getAsset().getSerialNumber()),
            field("ASSET_CRITICALITY", r.getAsset().getCriticality().name()),
            field("FROM_SITE", r.getFromLocationLabel()),
            field("TO_SITE", r.getToLocationLabel()),
            field("TRANSFER_REASON", r.getReason())
        );
    }

    private Map<String, String> field(String id, String value) {
        return Map.of("ElementId", id, "Value", value != null ? value : "");
    }

    //    /* ============================================================
    //       STEP 3 – SEND DRAFT
    //     ============================================================ */
    //    private String sendDraft(String draftId, AssetMovementRequest r) {
    //
    //        String url = baseUrl + "/api/V6/draft/send";
    //
    //        String body
    //                = "{\n"
    //                + "  \"DraftId\": \"" + draftId + "\",\n"
    //                + "  \"OverrideFormFieldValues\": {\n"
    //                + "    \"Forms\": {\n"
    //                + "      \"REQUEST_REF\": { \"Value\": \"SA-MOVE-" + r.getId() + "\" },\n"
    //                + "      \"REQUEST_TYPE\": { \"Value\": \"Transfert d’actif\" },\n"
    //                + "      \"REQUESTER_NAME\": { \"Value\": \""
    //                + r.getRequestedBy().getFirstName() + " " + r.getRequestedBy().getLastName()
    //                + "\" },\n"
    //                + "      \"REQUESTER_EMAIL\": { \"Value\": \"" + r.getRequestedBy().getEmail() + "\" },\n"
    //                + "      \"ASSET_CODE\": { \"Value\": \"" + r.getAsset().getAssetCode() + "\" },\n"
    //                + "      \"ASSET_NAME\": { \"Value\": \"" + r.getAsset().getSerialNumber() + "\" },\n"
    //                + "      \"ASSET_CRITICALITY\": { \"Value\": \"" + r.getAsset().getCriticality().name() + "\" },\n"
    //                + "      \"FROM_SITE\": { \"Value\": \"" + r.getFromLocationLabel() + "\" },\n"
    //                + "      \"TO_SITE\": { \"Value\": \"" + r.getToLocationLabel() + "\" },\n"
    //                + "      \"TRANSFER_REASON\": { \"Value\": \"" + r.getReason() + "\" }\n"
    //                + "    }\n"
    //                + "  }\n"
    //                + "}";
    //
    //        ResponseEntity<Map> response
    //                = restTemplate.exchange(
    //                        url,
    //                        HttpMethod.POST,
    //                        new HttpEntity<>(body, headers()),
    //                        Map.class
    //                );
    //
    //        Map<String, Object> result = response.getBody();
    //        String envelopeId = (String) result.get("EnvelopeId");
    //
    //        LOG.info("[eSign] Draft sent (envelopeId={})", envelopeId);
    //        return envelopeId;
    //    }

    /* ============================================================
       HEADERS
     ============================================================ */
    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("apiToken", apiToken);
        return headers;
    }
}
