/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ailab.smartasset.service.esign;

/**
 *
 * @author imadb
 */
import java.io.FileWriter;
import java.io.IOException;

/**
 * class WorkstepConfiguration
 *
 * A class that bundles WSC configuration methods
 *
 */
public class WorkstepConfiguration {

    protected String sTemplate;
    public String sCurrentConfig;

    /**
     *
     */
    public WorkstepConfiguration() {
        // TODO Auto-generated constructor stub
        SetConfigTemplate();
        InitCurrentConfig();
    }

    /**
     * InitCurrentConfig
     *
     * Once the Currentconfig is set and used e.g. for "SendEnvelope", in can be
     * set to the initial value with this command.
     *
     * NOTE: After it is used and it needs to be filled with new information
     * (e.g. SSP Fileid, recipient, policies, etc.), it MUST be initialized!
     *
     */
    public void InitCurrentConfig() {
        sCurrentConfig = sTemplate;
    }

    /**
     * SetSenderInformation
     *
     * Sets the sender information given in the parameters (if a parameter
     * contains "", it is left default)
     *
     * @param sEMail
     * @param sName
     * @param sLastName
     * @return true: succesful; false: error, sCurrentConfig not initialized!
     */
    public Boolean SetSenderInformation(String sEMail, String sName, String sLastName) {
        if (
            !sCurrentConfig.contains("##EmailSender##") ||
            !sCurrentConfig.contains("##NameSender##") ||
            !sCurrentConfig.contains("##LastnameSender##")
        ) {
            return false;
        }
        if (sEMail.compareTo("") != 0) {
            sCurrentConfig = sCurrentConfig.replace("##EmailSender##", sEMail);
        }

        if (sName.compareTo("") != 0) {
            sCurrentConfig = sCurrentConfig.replace("##NameSender##", sName);
        }

        if (sLastName.compareTo("") != 0) {
            sCurrentConfig = sCurrentConfig.replace("##LastnameSender##", sLastName);
        }

        return true;
    }

    /**
     * SetRecipientInformation
     *
     * @param sEMail
     * @param sName
     * @param sLastName
     * @return
     */
    public Boolean SetRecipientInformation(String sEMail, String sName, String sLastName) {
        if (!sCurrentConfig.contains("##EMail1##") || !sCurrentConfig.contains("##Name1##") || !sCurrentConfig.contains("##Lastname1##")) {
            return false;
        }
        if (sEMail.compareTo("") != 0) {
            sCurrentConfig = sCurrentConfig.replace("##EMail1##", sEMail);
        }

        if (sName.compareTo("") != 0) {
            sCurrentConfig = sCurrentConfig.replace("##Name1##", sName);
        }

        if (sLastName.compareTo("") != 0) {
            sCurrentConfig = sCurrentConfig.replace("##Lastname1##", sLastName);
        }

        return true;
    }

    /**
     * SetSspFileID
     *
     * Sets the file id in the template configuration to a current one
     *
     * @param sSspFileId
     */
    public void SetSspFileID(String sSspFileId) {
        String[] sSplitString = sSspFileId.split(":");

        if (sSplitString[0].contains("SspFileId")) {
            for (int i = 1; i < sSplitString.length; i++) {
                sSplitString[i] = sSplitString[i].replaceAll("\"", "");
                sSplitString[i] = sSplitString[i].replaceAll("}", "");
                if (sSplitString.length > 2 && i < sSplitString.length - 1) {
                    sCurrentConfig = sCurrentConfig.replaceFirst("##SSpFileIDS##", sSplitString[i] + ",\r\n\"##SSpFileIDS##\"");
                } else {
                    sCurrentConfig = sCurrentConfig.replaceFirst("##SSpFileIDS##", sSplitString[i]);
                }
                //sTemplate = sTemplate.replaceFirst("##SSpFileIDS##", sSplitString[i]);
            }
        }
    }

    /**
     * SetAdHocWorkStepConfiguration
     *
     * @param sWorkstepConfig
     */
    public void SetAdHocWorkStepConfiguration(String sWorkstepConfig) {
        // check, if the given String contains a adHocWorkstepconfiguration, and replace with default if not
        if (sWorkstepConfig.contains("{\"AdHocWorkstepConfigResult\":") == false) {
            sWorkstepConfig =
                "\"WorkstepLabel\": \"Download\"," +
                "\"SmallTextZoomFactorPercent\": 100," +
                "\"FinishAction\": {" +
                "\"ServerActions\": []," +
                "\"ClientActions\": []";
        }
        //##WorkStepConfig##
        sCurrentConfig = sCurrentConfig.replace("##WorkStepConfig##", sWorkstepConfig);
    }

    /**
     * SetLanguageCode
     *
     * (e.g. "de", "en", etc.)
     *
     * @param sLangCode
     */
    public void SetLanguageCode(String sLangCode) {
        if (sLangCode.compareTo("") != 0) {
            sCurrentConfig = sCurrentConfig.replace("##LangCode##", sLangCode);
        }
    }

    /**
     * SetPolicies
     *
     * Sets the policy specified by the sPoliciName to the value given in
     * sPolicyValue
     *
     * @param sPolicyName
     * @param sPolicyValue
     * @return true: success; false: the policy name given in sPolicyName or
     * value given in sPolicyValue are not applicable
     * @throws IOException
     */
    public Boolean SetPolicies(String sPolicyName, String sPolicyValue) throws IOException {
        String sInverseValue;
        Boolean bWrongValueOrName = false;
        if (sPolicyValue.compareTo("false") == 0) {
            sInverseValue = "true";
        } else if (sPolicyValue.compareTo("true") == 0) {
            sInverseValue = "false";
        } else {
            sInverseValue = "true";
            sPolicyValue = "false";
            bWrongValueOrName = true;
        }

        //"\"AllowSaveDocument\": true,"+
        String sNew = "\"" + sPolicyName + "\": " + sPolicyValue;
        String sTmp = "\"" + sPolicyName + "\": " + sInverseValue;

        FileWriter fWrite = new FileWriter("Tutorial CurrentConfig.txt", true);
        fWrite.write(sCurrentConfig);
        fWrite.close();

        if (sCurrentConfig.contains(sTmp)) {
            if (!sCurrentConfig.contains(sNew)) {
                sCurrentConfig = sCurrentConfig.replace(sTmp, sNew);
                FileWriter fWrite1 = new FileWriter("Tutorial CurrentConfig_New.txt", true);
                fWrite1.write(sCurrentConfig);
                fWrite1.close();
            }
        } else {
            bWrongValueOrName = true;
        }

        return !bWrongValueOrName;
        /*String[] sSplitConfig = sCurrentConfig.split("\": |\",|\n");
		String sConfigTmp = "";*/

        //sResponse.split("\",\"|\":\"");
        //		for(int i = 0; i < sCurrentConfig.length;i++) {
        //			if(sSplitConfig[i].contains(sPolicyName)) {
        //				sSplitConfig[i+1] = sPolicyValue;
        //				break;
        //			}
        //		}
    }

    /**
     * SetConfigTemplate
     *
     * Resets the tepmplate of the workstepconfiguration with the default one.
     * The default configuration contains placeholder tags like ##Mail# and so
     * on.
     */
    private void SetConfigTemplate() {
        sTemplate =
            "{" +
            "\"SspFileIds\": [" +
            "\"##SSpFileIDS##\"" + // This is not a tag of the config, it is simplified tag for String replacement!
            "]," +
            "\"SendEnvelopeDescription\": {" +
            "\"Name\": \"example\"," +
            "\"EmailSubject\": \"Please sign the enclosed envelope\"," +
            "\"EmailBody\": \"Dear #RecipientFirstName# #RecipientLastName#\n#PersonalMessage#\nPlease sign the envelope #EnvelopeName#\n\nEnvelope will expire at #ExpirationDate#\"," +
            "\"DisplayedEmailSender\": \"\"," +
            "\"EnableReminders\": true," +
            "\"FirstReminderDayAmount\": 1," +
            "\"RecurrentReminderDayAmount\": 1," +
            "\"BeforeExpirationDayAmount\": 2," +
            "\"DaysUntilExpire\": 3," +
            "\"CallbackUrl\": \"\"," +
            "\"StatusUpdateCallbackUrl\": \"\"," +
            "\"Steps\": [" +
            "{" +
            "\"OrderIndex\": 1," +
            "\"Recipients\": [" +
            "{" +
            "\"Email\": \"##EMail1##\"," +
            "\"FirstName\": \"##Name1##\"," +
            "\"LastName\": \"##Lastname1##\"," +
            "\"LanguageCode\":\"##LangCode##\"," +
            "\"EmailBodyExtra\": \"\"," +
            "\"DisableEmail\": false," +
            "\"AddAndroidAppLink\": false," +
            "\"AddIosAppLink\": false," +
            "\"AddWindowsAppLink\": false," +
            "\"AllowDelegation\": true," +
            "\"SkipExternalDataValidation\": false," +
            "\"AuthenticationMethods\": []" +
            "}" +
            "]," +
            "\"EmailBodyExtra\": \"\"," +
            "\"RecipientType\": \"Signer\"," +
            "\"WorkstepConfiguration\": {" +
            "##WorkStepConfig##" + //sWorkstepConfig +
            "}," +
            "\"ReceiverInformation\": {" +
            "\"UserInformation\": {" +
            "\"FirstName\": \"NAME\"," +
            "\"LastName\": \"LASTNAME\"," +
            "\"EMail\": \"EMAILADDRESS\"" +
            "}" +
            "}," +
            "\"SenderInformation\": {" +
            "\"UserInformation\": {" +
            "\"FirstName\": \"##NameSender##\"," +
            "\"LastName\": \"##LastnameSender##\"," +
            "\"EMail\": \"##EmailSender##\"" +
            "}" +
            "}," +
            "\"TransactionCodeConfigurations\": [" +
            "{" +
            "\"Id\": \"smsAuthTransactionCodeId\"," +
            "\"HashAlgorithmIdentifier\": \"Sha256\"," +
            "\"Texts\": [" +
            "{" +
            "\"Value\": \"Please authenticate yourself for the access to the envelope with the transactionId {tId}. Your code is: {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"bg\"," +
            "\"Value\": \"Моля, удостоверете се за достъп до плика с Id {tId} на трансакцията. Вашият код е: {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"de\"," +
            "\"Value\": \"Bitte authentifizieren Sie sich für den Zugriff auf die Dokumentenmappe der Transaktion {tId}. Ihre TAN lautet: {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"el\"," +
            "\"Value\": \"Παρακαλούμε ταυτοποιηθείτε για την πρόσβαση στον φάκελο με τον αναγνωριστικό κωδικό συναλλαγής {tId}. Ο κωδικός σας είναι: {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"en\"," +
            "\"Value\": \"Please authenticate yourself for the access to the envelope with the transactionId {tId}. Your code is: {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"es\"," +
            "\"Value\": \"Autentíquese para acceder al sobre con el ID de transacción {tId}. Su código es: {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"fr\"," +
            "\"Value\": \"Veuillez vous authentifier afin d’accéder à l’enveloppe avec l’identifiant de transaction {tId}. Votre code est : {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"it\"," +
            "\"Value\": \"Con riferimento alla transazione {tId}, per autenticarsi si prega di inserire il seguente CODICE {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"nl\"," +
            "\"Value\": \"U dient zich te authenticeren voor toegang tot enveloppe met transactie-ID {tId}. Uw code is: {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"pl\"," +
            "\"Value\": \"Dokonaj uwierzytelnienia, aby uzyskać dostęp do koperty z identyfikatorem IDTransakcji {tId}. Twój kod to: {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"pt-br\"," +
            "\"Value\": \"Por favor autentique-se para acessar ao envelope com o ID: {tId}. Seu código é: {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"pt-pt\"," +
            "\"Value\": \"Autentique-se para aceder ao envelope com a ID de transação {tId}. O seu código é: {Token}\"" +
            "}," +
            "{" +
            "\"Language\": \"ro\"," +
            "\"Value\": \"Va rugam sa va autentificati pentru accesul la documentul cu numarul de tranzactie {tId}. Codul este: {Token}. \"" +
            "}," +
            "{" +
            "\"Language\": \"zh-cn\"," +
            "\"Value\": \"请使用事务标识号{tId}验证自己的身分以便访问此信封。您的密码是：{Token}\"" +
            "}" +
            "]" +
            "}," +
            "{" +
            "\"Id\": \"disposableCertificateEnrolAndSignSmsText\"," +
            "\"HashAlgorithmIdentifier\": \"Sha256\"," +
            "\"Texts\": [" +
            "{" +
            "\"Value\": \"Please confirm the issuance of your disposable certificate and signature, referenced by transactionId {tId}, with the OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"bg\"," +
            "\"Value\": \"Моля, потвърдете издаването на Вашия сертификат за еднократна употреба, отнасящ се до Id {tId} на трансакцията, подпишете с OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"de\"," +
            "\"Value\": \"Bitte bestätigen Sie die Ausstellung des Einwegzertifikats und die Signatur für Transaktion {tId} mit dem Einmalpasswort: \"" +
            "}," +
            "{" +
            "\"Language\": \"el\"," +
            "\"Value\": \"Παρακαλούμε επιβεβαιώστε την έκδοση του πιστοποιητικού και υπογραφής μίας χρήσης, με αναφορά το Id συναλλαγής {tId}, με το OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"en\"," +
            "\"Value\": \"Please confirm the issuance of your disposable certificate and signature, referenced by transactionId {tId}, with the OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"es\"," +
            "\"Value\": \"Confirme la emisión de su firma y certificado desechable, a los que se hace referencia por el ID de transacción {tId}, con el OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"fr\"," +
            "\"Value\": \"Veuillez confirmer la délivrance de votre signature et certificat référencé par l’identifiant {tId} avec le mot de passe à usage unique : \"" +
            "}," +
            "{" +
            "\"Language\": \"it\"," +
            "\"Value\": \"Conferma l’emissione del tuo certificato disposable, con riferimento alla transazione {tId}, e della firma con l´OTP \"" +
            "}," +
            "{" +
            "\"Language\": \"nl\"," +
            "\"Value\": \"Bevestig de afgifte van uw eenmalig certificaat en handtekening, met transactie-ID {tId} en het eenmalig wachtwoord: \"" +
            "}," +
            "{" +
            "\"Language\": \"pl\"," +
            "\"Value\": \"Potwierdź wydanie jednorazowego certyfikatu i podpisu w związku z transakcją o numerze {tId}, korzystając z OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"pt-br\"," +
            "\"Value\": \"Por favor, confirme a emissão do seu certificado e assinatura descartáveis, referenciados por {tId}, com o OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"pt-pt\"," +
            "\"Value\": \"Confirme a emissão do seu certificado disponível e a assinatura, referenciado pela ID de transação {tId}, com o OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"ro\"," +
            "\"Value\": \"Va rugam sa confirmati emiterea certificatului de unica folosinta si a semnaturii, referitor la numarul de tranzactie {tId}, cu OTP-ul: \"" +
            "}," +
            "{" +
            "\"Language\": \"zh-cn\"," +
            "\"Value\": \"请确认签发单次性证书和签名，请引用事务标识号{tId}，与OTP：\"" +
            "}" +
            "]" +
            "}," +
            "{" +
            "\"Id\": \"remoteCertificateSignSmsText\"," +
            "\"HashAlgorithmIdentifier\": \"Sha256\"," +
            "\"Texts\": [" +
            "{" +
            "\"Value\": \"Please sign the document, referenced by transactionId {tId}, using the OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"bg\"," +
            "\"Value\": \"Моля, подпишете документа, отнасящ се до Id {tId} на трансакцията, като използвате OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"de\"," +
            "\"Value\": \"Bitte signieren Sie das Dokument, referenziert durch die Transaktions {tId}, mit dem Einmalpasswort: \"" +
            "}," +
            "{" +
            "\"Language\": \"el\"," +
            "\"Value\": \"Παρακαλούμε υπογράψτε το έγγραφο με αναφορά τον αναγνωριστικό κωδικό συναλλαγής {tId}, χρησιμοποιώντας το OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"en\"," +
            "\"Value\": \"Please sign the document, referenced by transactionId {tId}, using the OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"es\"," +
            "\"Value\": \"Firme el documento, al que se hace referencia por el ID de transacción {tId}, usando el OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"fr\"," +
            "\"Value\": \"Veuillez signer le document, référencé par l’identifiant de transaction {tId}, en utilisant le mot de passe à usage unique : \"" +
            "}," +
            "{" +
            "\"Language\": \"it\"," +
            "\"Value\": \"Firma il documento, con riferimento alla transazione {tId}, usando l´OTP \"" +
            "}," +
            "{" +
            "\"Language\": \"nl\"," +
            "\"Value\": \"Onderteken het document met transactie-ID {tId}, met behulp van het eenmalig wachtwoord: \"" +
            "}," +
            "{" +
            "\"Language\": \"pl\"," +
            "\"Value\": \"Podpisz dokument związany z identyfikatorem IDTransakcji {tId}, korzystając z OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"pt-br\"," +
            "\"Value\": \"Por favor, assine o documento de ID {tId} utilizando o OTP (One Time Password): \"" +
            "}," +
            "{" +
            "\"Language\": \"pt-pt\"," +
            "\"Value\": \"Assine o documento, referenciado pela ID de transação {tId}, usando o OTP: \"" +
            "}," +
            "{" +
            "\"Language\": \"ro\"," +
            "\"Value\": \"Va rugam sa semnati documentul, la care se face referinta prin ID-ul de tranzactie {tId}, folosind OTP-ul: \"" +
            "}," +
            "{" +
            "\"Language\": \"zh-cn\"," +
            "\"Value\": \"请签署文件，请引用事务标识号{tId}，并使用OTP：\"" +
            "}" +
            "]" +
            "}," +
            "{" +
            "\"Id\": \"otpSignatureSmsText\"," +
            "\"HashAlgorithmIdentifier\": \"Sha256\"," +
            "\"Texts\": [" +
            "{" +
            "\"Value\": \"Please sign the document with the transactionId {tId} with the code: {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"bg\"," +
            "\"Value\": \"Моля, подпишете документа с Id {tId} на транзакцията с код: {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"de\"," +
            "\"Value\": \"Bitte signieren Sie das Dokument, referenziert durch die Transaktions {tId}, mit dem Einmalpasswort: {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"el\"," +
            "\"Value\": \"Παρακαλούμε υπογράψτε το έγγραφο με αναφορά τον αναγνωριστικό κωδικό συναλλαγής {tId}, χρησιμοποιώντας τον κωδικό: {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"en\"," +
            "\"Value\": \"Please sign the document with the transactionId {tId} with the code: {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"es\"," +
            "\"Value\": \"Firme el documento con ID de transacción {tId}, usando el código: {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"fr\"," +
            "\"Value\": \"Veuillez signer le document avec l’identifiant de transaction {tId} avec le code : {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"it\"," +
            "\"Value\": \"Firma il documento, con riferimento alla transazione {tId}, usando il codice OTP {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"nl\"," +
            "\"Value\": \"Onderteken het document met transactie-ID {tId}, met code: {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"pl\"," +
            "\"Value\": \"Podpisz dokument związany z identyfikatorem IDTransakcji {tId}, korzystając z kodu: {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"pt-br\"," +
            "\"Value\": \"Por favor, assine o documento de ID {tId} utilizando o código: {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"pt-pt\"," +
            "\"Value\": \"Assine o documento com a ID de transação {tId} com o código: {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"ro\"," +
            "\"Value\": \"Va rugam sa semnati documentul ce contine transactionId {tId} cu codul {Token} \"" +
            "}," +
            "{" +
            "\"Language\": \"zh-cn\"," +
            "\"Value\": \"请使用事务标识号{tId}与密码：{Token}签署文件。\"" +
            "}" +
            "]" +
            "}," +
            "{" +
            "\"Id\": \"swissComSign\"," +
            "\"HashAlgorithmIdentifier\": \"Sha256\"," +
            "\"Texts\": [" +
            "{" +
            "\"Value\": \"Please confirm to sign the document\"" +
            "}," +
            "{" +
            "\"Language\": \"bg\"," +
            "\"Value\": \"Моля, потвърдете, за да подпишете документа\"" +
            "}," +
            "{" +
            "\"Language\": \"de\"," +
            "\"Value\": \"Bitte bestätigen sie die Unterschrift des Dokuments\"" +
            "}," +
            "{" +
            "\"Language\": \"el\"," +
            "\"Value\": \"Παρακαλούμε επιβεβαιώστε για να υπογράψετε το έγγραφο\"" +
            "}," +
            "{" +
            "\"Language\": \"en\"," +
            "\"Value\": \"Please confirm to sign the document\"" +
            "}," +
            "{" +
            "\"Language\": \"es\"," +
            "\"Value\": \"Confirme la firma del documento\"" +
            "}," +
            "{" +
            "\"Language\": \"fr\"," +
            "\"Value\": \"Veuillez confirmer de signer le document\"" +
            "}," +
            "{" +
            "\"Language\": \"it\"," +
            "\"Value\": \"Si prega di confermare per firmare il documento\"" +
            "}," +
            "{" +
            "\"Language\": \"nl\"," +
            "\"Value\": \"Gelieve te bevestigen om het document te ondertekenen\"" +
            "}," +
            "{" +
            "\"Language\": \"pl\"," +
            "\"Value\": \"Potwierdź w celu podpisania dokumentu\"" +
            "}," +
            "{" +
            "\"Language\": \"pt-br\"," +
            "\"Value\": \"Por favor confirme a assinatura do documento\"" +
            "}," +
            "{" +
            "\"Language\": \"pt-pt\"," +
            "\"Value\": \"Confirme para assinar o documento\"" +
            "}," +
            "{" +
            "\"Language\": \"ro\"," +
            "\"Value\": \"Confirmați să semnați documentul\"" +
            "}," +
            "{" +
            "\"Language\": \"zh-cn\"," +
            "\"Value\": \"请确认以签署此文档\"" +
            "}" +
            "]" +
            "}" +
            "]," +
            "\"SignatureConfigurations\": []," +
            "\"ViewerPreferences\": {" +
            "\"FinishWorkstepOnOpen\": false," +
            "\"VisibleAreaOptions\": {" +
            "\"AllowedDomain\": \"*\"," +
            "\"Enabled\": false" +
            "}" +
            "}," +
            "\"ResourceUris\": {" +
            "\"DelegationUri\": \"https://demo3.local//Resource/Delegate\"" +
            "}," +
            "\"AuditingToolsConfiguration\": {" +
            "\"WriteAuditTrail\": true," +
            "\"NotificationConfiguration\": {}" +
            "}," +
            "\"Policy\": {" +
            "\"GeneralPolicies\": {" +
            "\"AllowSaveDocument\": true," +
            "\"AllowSaveAuditTrail\": true," +
            "\"AllowRotatingPages\": false," +
            "\"AllowEmailDocument\": true," +
            "\"AllowPrintDocument\": true," +
            "\"AllowFinishWorkstep\": true," +
            "\"AllowRejectWorkstep\": true," +
            "\"AllowRejectWorkstepDelegation\": true," +
            "\"AllowUndoLastAction\": false," +
            "\"AllowAdhocPdfAttachments\": false," +
            "\"AllowAdhocSignatures\": false," +
            "\"AllowAdhocStampings\": false," +
            "\"AllowAdhocFreeHandAnnotations\": false," +
            "\"AllowAdhocTypewriterAnnotations\": false," +
            "\"AllowAdhocPictureAnnotations\": false," +
            "\"AllowAdhocPdfPageAppending\": false" +
            "}," +
            "\"WorkstepTasks\": {" +
            "\"PictureAnnotationMinResolution\": 0," +
            "\"PictureAnnotationMaxResolution\": 0," +
            "\"PictureAnnotationColorDepth\": \"Color16M\"," +
            "\"SequenceMode\": \"NoSequenceEnforced\"," +
            "\"PositionUnits\": \"PdfUnits\"," +
            "\"ReferenceCorner\": \"Lower_Left\"," +
            "\"Tasks\": [" +
            "{" +
            "\"PositionPage\": 1," +
            "\"Position\": {" +
            "\"PositionX\": 61.0," +
            "\"PositionY\": 586.0" +
            "}," +
            "\"Size\": {" +
            "\"Height\": 80.0," +
            "\"Width\": 191.0" +
            "}," +
            "\"AdditionalParameters\": [" +
            "{" +
            "\"Key\": \"enabled\"," +
            "\"Value\": \"1\"" +
            "}," +
            "{" +
            "\"Key\": \"positioning\"," +
            "\"Value\": \"onPage\"" +
            "}," +
            "{" +
            "\"Key\": \"req\"," +
            "\"Value\": \"1\"" +
            "}," +
            "{" +
            "\"Key\": \"fd\"," +
            "\"Value\": \"\"" +
            "}," +
            "{" +
            "\"Key\": \"fd_dateformat\"," +
            "\"Value\": \"dd-MM-yyyy HH:mm:ss\"" +
            "}," +
            "{" +
            "\"Key\": \"fd_timezone\"," +
            "\"Value\": \"datetimeutc\"" +
            "}" +
            "]," +
            "\"AllowedSignatureTypes\": [" +
            "{" +
            "\"AllowedCapturingMethod\": \"Click2Sign\"," +
            "\"StampImprintConfiguration\": {" +
            "\"DisplayExtraInformation\": true," +
            "\"DisplayEmail\": true," +
            "\"DisplayIp\": true," +
            "\"DisplayName\": true," +
            "\"DisplaySignatureDate\": true," +
            "\"FontFamily\": \"Times New Roman\"," +
            "\"FontSize\": 11.0" +
            "}," +
            "\"Id\": \"4657c69c-b2e6-4bfc-9118-4aff655418b5\"," +
            "\"DiscriminatorType\": \"SigTypeClick2Sign\"," +
            "\"Preferred\": false" +
            "}" +
            "]," +
            "\"IsRequired\": true," +
            "\"Id\": \"1#XyzmoDuplicateIdSeperator#Signature_7bfa129e-53d6-0e91-e919-71f756277154\"," +
            "\"DisplayName\": \"\"," +
            "\"DocRefNumber\": 1," +
            "\"DiscriminatorType\": \"Signature\"" +
            "}" +
            "]" +
            "}" +
            "}" +
            "}," +
            "\"DocumentOptions\": [" +
            "{" +
            "\"DocumentReference\": \"1\"," +
            "\"IsHidden\": false" +
            "}" +
            "]," +
            "\"UseDefaultAgreements\": false" +
            "}," +
            "{" +
            "\"OrderIndex\": 2," +
            "\"Recipients\": [" +
            "{" +
            "\"Email\": \"Charly.xyzmo@xyzmo.com\"," +
            "\"FirstName\": \"Charly\"," +
            "\"LastName\": \"Xyzmo\"," +
            "\"LanguageCode\": \"de\"," +
            "\"EmailBodyExtra\": \"\"," +
            "\"DisableEmail\": false," +
            "\"AddAndroidAppLink\": false," +
            "\"AddIosAppLink\": false," +
            "\"AddWindowsAppLink\": false," +
            "\"AllowDelegation\": false," +
            "\"SkipExternalDataValidation\": false," +
            "\"AuthenticationMethods\": []" +
            "}" +
            "]," +
            "\"EmailBodyExtra\": \"\"," +
            "\"RecipientType\": \"Cc\"," +
            "\"DocumentOptions\": [" +
            "{" +
            "\"DocumentReference\": \"1\"," +
            "\"IsHidden\": false" +
            "}" +
            "]," +
            "\"UseDefaultAgreements\": false" +
            "}" +
            "]," +
            "\"AddFormFields\": {" +
            "\"Forms\": {}" +
            "}," +
            "\"OverrideFormFieldValues\": {" +
            "\"Forms\": {}" +
            "}," +
            "\"AttachSignedDocumentsToEnvelopeLog\": false" +
            "}" +
            "}";
    }
}
