/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ailab.smartasset.service.esign;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class EsignAnyWhereRestClient {

    Dictionary<String, String> props = new Hashtable<String, String>();
    static HttpClient httpClientForREST;
    static HttpRequest httpRequestForREST;
    static String gsOrgKey;
    static String gsUserName;
    static WorkstepConfiguration wsc;

    /**
     *
     */
    public EsignAnyWhereRestClient() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     * @throws URISyntaxException
     * @throws ParseException
     */
    public static void main(String[] args)
        throws InterruptedException, ExecutionException, TimeoutException, IOException, URISyntaxException {
        // TODO Auto-generated method stub
        // initialize httpclient
        Scanner scan = new Scanner(System.in);
        String sResponse = ""; // Variable to pass the responses through the steps
        String sAdHocConfig = "";
        String SspFileIds = ""; // Variable to pass the SspFileIds through the steps
        String sEnvelopeId = ""; // Variable to pass the EnvelopeId through the steps

        // ToDo: Set you Filename
        String sFileName = "C:\\\\Program Files (x86)\\\\SIGNificant Kiosk";
        // String sFileName = "C:\\\\Program Files (x86)\\\\SIGNificant Kiosk

        Integer iUseWscFromPrepareCmd = 0; //Mode for which WSC is used

        wsc = new WorkstepConfiguration();

        // ToDo: Set your Org.-Key and E-Mail
        SetUsernameAndOrgKey("LOGIN.NAME@TODO.COM", "xxx-yyyyyyy-zzzzzzz");
        if (CheckCredentials() == false) {
            System.out.println("Please check credentials, authtorisation refused!");
        } else {
            System.out.println("Authorisation succesful!-->UploadTemporary with File: \r\n" + sFileName);
            // "C:\\Program Files (x86)\\SIGNificant Kiosk
            // SDK\\Resources\\Samples\\Contract_Sample.pdf\r\n");

            // Upload a File with UploadTemporary and receive SspFileIds
            SspFileIds = UploadTemporary(sFileName);

            // ToDo: Adopt to your needs (mail, name, lastname)
            wsc.InitCurrentConfig();
            // SetLanguageCode: Ensure that the selected language is active in your organization!
            // (otherwise you will get an error)
            wsc.SetLanguageCode("de");
            if (wsc.SetSenderInformation("SENDER@MAIL.TODO", "NAME", "LASTNAME") == false) {
                System.out.println("Failed to SetSenderInformation. Please ensure to use InitCurrentConfig before!");
                System.out.println("Please press enter to exit the program");
                scan.nextLine();
                System.exit(0);
            }

            if (wsc.SetRecipientInformation("RECIPIENT@MAIL.TODO", "CHARLY", "XYZMO") == false) {
                System.out.println("Failed to SetRecipientInformation. Please ensure to use InitCurrentConfig before!");
                System.out.println("Please press enter to exit the program");
                scan.nextLine();
                System.exit(0);
            }

            wsc.SetSspFileID(SspFileIds);
            // Set general policies-->
            // https://www.esignanywhere.net/esignature-api/api-documentation/signanywhere-viewer-customization-guide/
            if (
                wsc.SetPolicies("AllowAdhocTypewriterAnnotations", "true") == false ||
                wsc.SetPolicies("AllowAdhocSignatures", "true") == false
            ) {
                System.out.println("Failed to SetPolicy. Please check policy name and value!");
                System.out.println("Please press enter to exit the program");
                scan.nextLine();
                System.exit(0);
            }

            // ToDo: Adopt value for iUseWscFromPrepareCmd
            // 0: use default from bottom of this file
            // 1: use default+replace default with AdHoc WSC from prepare Cmd
            // 2: use config from WorkstepConfiguration
            iUseWscFromPrepareCmd = 2;
            //sAdHocConfig = Prepare(SspFileIds);
            wsc.SetAdHocWorkStepConfiguration(""); // wsc.SetAdHocWorkStepConfiguration(sAdHocConfig);

            // Creates and directly sends a new envelope
            // - if prepare is called and the ad hoc WSC is changed: the new config can be
            // used
            // - else with the ad hoc WSC from the file (as it is defined in the sig string
            // and the regarding xml-configs)
            // sEnvelopeId = SendEnvelope(SspFileIds,bUseWscFromPrepareCmd,sAdHocConfig);
            sEnvelopeId = SendEnvelope(SspFileIds, iUseWscFromPrepareCmd, wsc.sCurrentConfig);
            sResponse = GetEnvelopeById(sEnvelopeId);

            // Get redirection Url:
            String sRedirectionUrl = "";
            String[] sParseString = sResponse.split("\",\"|\":\"");
            for (int i = 0; i < sParseString.length; i++) {
                if (sParseString[i].contains("WorkstepRedirectionUrl")) {
                    sRedirectionUrl = sParseString[i + 1];
                    break;
                }
            }

            // Open the url in browser:
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(sRedirectionUrl));
            }
            /*
             * Alternative process (prepare between Upload and Send: WorkstepConfig can be
             * adjusted
             *
             */

            // SspFileIds = UploadTemporary("C:\\Program Files (x86)\\SIGNificant Kiosk
            // SDK\\Resources\\Samples\\Contract_Sample.pdf");

            // Parses the provided file(s) [SspFileIds] for markup fields and sig string and
            // returns the containing elements.
            // NOTE: This step is not necessary, it is only for getting the
            // AdHocWorksStepConfig.
            // sResponse = Prepare(SspFileIds);
            // If you perform the "Prepare"-Command, the AdHoc-WSC can be modified, e.g.
            // policies,
            // signaturetypes, Viewer behavior etc. can be adopted.

            // Creates and directly sends a new envelope
            // - if prepare is called and the ad hoc WSC is changed: the new config can be
            // used
            // - else with the ad hoc WSC from the file (as it is defined in the sig string
            // and the regarding xml-configs)
            // sResponse = SendEnvelope(sResponse,bUseWscFromPrepareCmd);
        }
    }

    /**
     * ManipulateAdHocWSC
     *
     * NOT IMPLEMENTED YET, Method stub!
     *
     * @param adHocConfig
     * @return
     */
    public static String ManipulateAdHocWSC(String adHocConfig) {
        String sTmpAdHocConfig = adHocConfig;
        // Here, JSON-Parsing should happen-->e.g. jdk.nashorn.internal.parser (needs
        // JDK to be installed)
        return sTmpAdHocConfig;
    }

    /**
     * SetUsernameAndOrgKey
     *
     * Set global Username and OrganizationKey for all following API-calls
     *
     * @param sOrgKey
     * @param sUserName
     */
    public static void SetUsernameAndOrgKey(String sUserName, String sOrgKey) {
        gsOrgKey = sOrgKey;
        gsUserName = sUserName;
    }

    /**
     * CheckCredentials
     *
     * For Further details, please have a look into the API documentation: Test if
     * your user token and api token are valid.
     *
     * @param <T>
     *
     * @return
     * @throws TimeoutException
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public static Boolean CheckCredentials() throws InterruptedException, ExecutionException, TimeoutException, IOException {
        httpClientForREST = HttpClient.newBuilder()
            .version(Version.HTTP_1_1)
            .followRedirects(Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        httpRequestForREST = HttpRequest.newBuilder()
            .uri(URI.create("https://demo.xyzmo.com/Api/v4.0/authorization?"))
            .timeout(Duration.ofMinutes(2))
            .header("organizationKey", gsOrgKey)
            .header("userLoginName", gsUserName)
            .GET()
            .build();

        // Samplecode for Errorhandling, needs to be adjusted
        /*
         * BodyHandler<Path> bodyHandler = (rspInfo) -> rspInfo.statusCode() == 200 ?
         * BodySubscribers.ofFile(Paths.get("/tmp/f")) :
         * BodySubscribers.replacing(Paths.get("/NULL")); /*client.sendAsync(request,
         * bodyHandler) .thenApply(HttpResponse::body) .thenAccept(System.out::println);
         */

        HttpResponse<String> response = httpClientForREST.send(httpRequestForREST, BodyHandlers.ofString());

        System.out.println("Authorization answered with code " + response.statusCode());
        System.out.println(response.body());

        if (response.statusCode() == 200) {
            return true;
        } else return false;
    }

    /**
     * GetEnvelopeById
     *
     * For Further details, please have a look into the API documentation:
     *
     * @param sEnvelopeID
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    public static String GetEnvelopeById(String sEnvelopeID)
        throws InterruptedException, ExecutionException, TimeoutException, IOException {
        String[] sSplitString = sEnvelopeID.split(":");
        sSplitString[1] = sSplitString[1].replaceAll("\"", "");
        sSplitString[1] = sSplitString[1].replaceAll("}", "");

        httpClientForREST = HttpClient.newBuilder()
            .version(Version.HTTP_1_1)
            .followRedirects(Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        httpRequestForREST = HttpRequest.newBuilder()
            .uri(URI.create("https://demo.xyzmo.com/Api/v3.0/envelope/" + sSplitString[1]))
            .timeout(Duration.ofMinutes(2))
            .header("organizationKey", gsOrgKey)
            .header("userLoginName", gsUserName)
            .GET()
            .build();

        // Samplecode for Errorhandling, needs to be adjusted
        /*
         * BodyHandler<Path> bodyHandler = (rspInfo) -> rspInfo.statusCode() == 200 ?
         * BodySubscribers.ofFile(Paths.get("/tmp/f")) :
         * BodySubscribers.replacing(Paths.get("/NULL")); /*client.sendAsync(request,
         * bodyHandler) .thenApply(HttpResponse::body) .thenAccept(System.out::println);
         */

        HttpResponse<String> response = httpClientForREST.send(httpRequestForREST, BodyHandlers.ofString());

        System.out.println("EnvelopeID: \r\n");
        System.out.println(response.body());

        return response.body();
    }

    /**
     * Prepare
     *
     * For Further details, please have a look into the API documentation:
     *
     * @param sSspFileIds
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    public static String Prepare(String sSspFileIds) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        String sResponse = "";
        String[] sSplitString = sSspFileIds.split(":");

        httpClientForREST = HttpClient.newBuilder()
            .version(Version.HTTP_1_1)
            .followRedirects(Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        String sBody =
            "{ \r\n" +
            "  \"SspFileIds\": [ \r\n" +
            "    \"##SSpFileIDS##\" \r\n" +
            "  ], \r\n" +
            "  \"AdHocWorkstepConfiguration\": { \r\n" +
            "    \"WorkstepLabel\": \"RSGtestWorkstepForSample\" \r\n" +
            "        } \r\n" +
            "} ";

        if (sSplitString[0].contains("SspFileId")) {
            for (int i = 1; i < sSplitString.length; i++) {
                sSplitString[i] = sSplitString[i].replaceAll("\"", "");
                sSplitString[i] = sSplitString[i].replaceAll("}", "");
                if (sSplitString.length > 2 && i < sSplitString.length - 1) {
                    sBody = sBody.replaceFirst("##SSpFileIDS##", sSplitString[i] + ",\r\n\"##SSpFileIDS##\"");
                } else {
                    sBody = sBody.replaceFirst("##SSpFileIDS##", sSplitString[i]);
                }
            }
        }

        httpRequestForREST = HttpRequest.newBuilder()
            .uri(URI.create("https://demo.xyzmo.com/Api/v2.0/envelope/prepare"))
            .timeout(Duration.ofMinutes(2))
            .header("Content-Type", "application/json")
            .header("accept", "application/json")
            .header("organizationKey", gsOrgKey)
            .header("userLoginName", gsUserName)
            .POST(BodyPublishers.ofString(sBody))
            .build();

        // ********** Samplecode for Errorhandling, needs to be adjusted *************
        /*
         * BodyHandler<Path> bodyHandler = (rspInfo) -> rspInfo.statusCode() == 200 ?
         * BodySubscribers.ofFile(Paths.get("/tmp/f")) :
         * BodySubscribers.replacing(Paths.get("/NULL")); /*client.sendAsync(request,
         * bodyHandler) .thenApply(HttpResponse::body) .thenAccept(System.out::println);
         */

        CompletableFuture<String> f = httpClientForREST
            .sendAsync(httpRequestForREST, BodyHandlers.ofString())
            .thenApply(HttpResponse::body);

        sResponse = f.get(30, TimeUnit.SECONDS);
        String sRespToPrint = sResponse.replaceAll(",", ",\r\n");
        System.out.println("Prepare successful. ad hoc workstepconfig:\r\n");
        System.out.println(sRespToPrint);
        // save config for debugging to file
        FileWriter fWrite = new FileWriter("Tutorial AdHocWSC.txt", true);
        fWrite.write(sRespToPrint);
        fWrite.close();

        // return ad hoc WSC
        return sResponse;
    }

    /**
     * UploadTemporary
     *
     * For Further details, please have a look into the API documentation: Uploads a
     * file for further processing/using. Content-Type must be multipart/form-data!
     * Here, it is not choosen explicit, but seems to be choosen automatically by
     * the application.
     *
     * @param sFilename
     * @return SSPFileId
     * @throws FileNotFoundException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public static String UploadTemporary(String sFilename)
        throws FileNotFoundException, InterruptedException, ExecutionException, TimeoutException {
        String sResponse = "";

        httpClientForREST = HttpClient.newBuilder()
            .version(Version.HTTP_1_1)
            .followRedirects(Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        java.io.File fBody = new java.io.File(sFilename);
        httpRequestForREST = HttpRequest.newBuilder()
            .uri(URI.create("https://demo.xyzmo.com/api/v4.0/sspfile/uploadtemporary"))
            .timeout(Duration.ofMinutes(2))
            .header("Content-Disposition", "Attachment;filename=Contract_Sample.pdf")
            .header("organizationKey", gsOrgKey)
            .header("userLoginName", gsUserName)
            .POST(BodyPublishers.ofFile(fBody.toPath()))
            .build();

        // Samplecode for Errorhandling, needs to be adjusted
        /*
         * BodyHandler<Path> bodyHandler = (rspInfo) -> rspInfo.statusCode() == 200 ?
         * BodySubscribers.ofFile(Paths.get("/tmp/f")) :
         * BodySubscribers.replacing(Paths.get("/NULL")); /*client.sendAsync(request,
         * bodyHandler) .thenApply(HttpResponse::body) .thenAccept(System.out::println);
         */

        CompletableFuture<String> f = httpClientForREST
            .sendAsync(httpRequestForREST, BodyHandlers.ofString())
            .thenApply(HttpResponse::body);

        sResponse = f.get(30, TimeUnit.SECONDS);
        System.out.println("Response from UploadTemporary:");
        System.out.println(sResponse + "\r\n");

        return sResponse;
    }

    /**
     * SendEnvelope
     *
     * For Further details, please have a look into the API documentation: Creates
     * and directly sends a new envenlope
     *
     * @param sSspFileIds
     * @param iUseWscFromPrepare 0: use default; 1: use AdHocWSC from Prepare Cmd; 2: Use WSC from WorkstepConfigurationClass
     * @param sAdHocWSC
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws IOException
     */
    public static String SendEnvelope(String sSspFileIds, Integer iUseWscFromPrepare, String sAdHocWSC)
        throws InterruptedException, ExecutionException, TimeoutException, IOException {
        String sResponse = "";

        httpClientForREST = HttpClient.newBuilder()
            .version(Version.HTTP_1_1)
            .followRedirects(Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

        String sBody = "";
        if (iUseWscFromPrepare == 0) {
            sBody = CreateBodyForSendEnvelope(sSspFileIds, "");
        } else if (iUseWscFromPrepare == 1) {
            // ToDo: Use Workstepconfig from Prepare-Command
            sBody = CreateBodyForSendEnvelope(sSspFileIds, sAdHocWSC);
        } else if (iUseWscFromPrepare == 2) {
            sBody = sAdHocWSC;
        }

        FileWriter fWrite = new FileWriter("Tutorial BodyForSendEnvelope.txt", true);
        fWrite.write(sBody);
        fWrite.close();

        httpRequestForREST = HttpRequest.newBuilder()
            .uri(URI.create("https://demo.xyzmo.com/api/v4.0/envelope/send"))
            .timeout(Duration.ofMinutes(2))
            .header("Content-Type", "application/json")
            .header("organizationKey", gsOrgKey)
            .header("userLoginName", gsUserName)
            .POST((BodyPublishers.ofString(sBody)))
            .build();

        // Samplecode for Errorhandling, needs to be adjusted
        /*
         * BodyHandler<Path> bodyHandler = (rspInfo) -> rspInfo.statusCode() == 200 ?
         * BodySubscribers.ofFile(Paths.get("/tmp/f")) :
         * BodySubscribers.replacing(Paths.get("/NULL")); /*client.sendAsync(request,
         * bodyHandler) .thenApply(HttpResponse::body) .thenAccept(System.out::println);
         */

        CompletableFuture<String> f = httpClientForREST
            .sendAsync(httpRequestForREST, BodyHandlers.ofString())
            .thenApply(HttpResponse::body);

        sResponse = f.get(30, TimeUnit.SECONDS);
        System.out.println("Received EnvelopeID: \r\n");
        System.out.print(sResponse);
        return sResponse;
    }

    /**
     * CreateBodyForSendEnvelope
     *
     * DEPRECATED! -->Use WorkstepConfigurationClass!
     *
     * NOTE: THIS IS NOT An API-FUNCTION!
     *
     * Function to create the body for sending the envelope-->replace the generic
     * tag (##SSpFileIDS#) with the parameter sSspFileIds.
     *
     * @param sSspFileIds
     * @return
     */
    public static String CreateBodyForSendEnvelope(String sSspFileIds, String sWorkstepConfig) {
        String[] sSplitString = sSspFileIds.split(":");

        // check, if the given String contains a adHocWorkstepconfiguration, and replace
        // with default if not
        if (sWorkstepConfig.contains("{\"AdHocWorkstepConfigResult\":") == false) {
            sWorkstepConfig =
                "\"WorkstepLabel\": \"Download\"," +
                "\"SmallTextZoomFactorPercent\": 100," +
                "\"FinishAction\": {" +
                "\"ServerActions\": []," +
                "\"ClientActions\": []";
        }

        String sTemplate =
            "{" +
            "\"SspFileIds\": [" +
            "\"##SSpFileIDS##\"" + // This is not a tag of the config, it is
            // simplified tag for String
            // replacement!
            "]," +
            "\"SendEnvelopeDescription\": {" +
            "\"Name\": \"example\"," +
            "\"EmailSubject\": \"Please sign the enclosed envelope\"," +
            "\"EmailBody\": \"Dear #RecipientFirstName# #RecipientLastName#\n#PersonalMessage#\nPlease sign the envelope #EnvelopeName#\n\nEnvelope will expire at #ExpirationDate#\"," +
            "\"DisplayedEmailSender\": \"\"," +
            "\"EnableReminders\": true," +
            "\"FirstReminderDayAmount\": 5," +
            "\"RecurrentReminderDayAmount\": 3," +
            "\"BeforeExpirationDayAmount\": 3," +
            "\"DaysUntilExpire\": 28," +
            "\"CallbackUrl\": \"\"," +
            "\"StatusUpdateCallbackUrl\": \"\"," +
            "\"Steps\": [" +
            "{" +
            "\"OrderIndex\": 1," +
            "\"Recipients\": [" +
            "{" +
            "\"Email\": \"##mail1##\"," +
            "\"FirstName\": \"##Name1##\"," +
            "\"LastName\": \"##Lastname1##\"," +
            "\"LanguageCode\": \"de\"," +
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
            "##WorkStepConfig##" + // sWorkstepConfig +
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
            "\"EMail\": \"##emailSender##\"" +
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
            "\"Email\": \"charly.xyzmo@xyzmo.com\"," +
            "\"FirstName\": \"NAME\"," +
            "\"LastName\": \"LASTNAME\"," +
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

        if (sSplitString[0].contains("SspFileId")) {
            for (int i = 1; i < sSplitString.length; i++) {
                sSplitString[i] = sSplitString[i].replaceAll("\"", "");
                sSplitString[i] = sSplitString[i].replaceAll("}", "");
                if (sSplitString.length > 2 && i < sSplitString.length - 1) {
                    sTemplate = sTemplate.replaceFirst("##SSpFileIDS##", sSplitString[i] + ",\r\n\"##SSpFileIDS##\"");
                } else {
                    sTemplate = sTemplate.replaceFirst("##SSpFileIDS##", sSplitString[i]);
                }
                // sTemplate = sTemplate.replaceFirst("##SSpFileIDS##", sSplitString[i]);
            }
        }

        return sTemplate;
    }
}
