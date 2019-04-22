package stepDefinition;

import FileReader.pojo.VehicleDetails;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import FileReader.pojo.FileDetails;
import FileReader.service.FileReaderService;
import FileReader.service.FileReaderServiceImpl;
import framework.pages.ErrorPage;
import framework.pages.Page;
import framework.pages.RegEnterPage;
import framework.pages.VehicleEnquiry;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Filereadersteps {

    private FileReaderService fileReaderService;
    private List<FileDetails> fileDetailsList;
    private VehicleEnquiry vehicleEnquiry;
    private Page page;
    private String[] vehicleInfo;
    private String fileName;
    private VehicleDetails vehicleDetails;
    private String[] info;

    private static String DIRECTORY = System.getProperty("user.dir") + "/testfiles";

    @Before
    public void init() {
        System.setProperty(
                "webdriver.gecko.driver",
                "geckodriver"
        );
    }

    @Given("^the service has (-?\\d+) files to read in directory (.*)$")
    public void testServiceReadsDirectory(int number, String path) throws Exception {
        fileReaderService = new FileReaderServiceImpl();
        fileDetailsList = fileReaderService.getFileContents(DIRECTORY);
    }

    @Given("^the service reads files from directory testfiles$")
    public void testServiceReadsDirectoryTestfiles1() throws Exception {
        fileReaderService = new FileReaderServiceImpl();
        fileDetailsList = fileReaderService.getFileContents(DIRECTORY);
    }

    @When("^the service is evoked$")
    public void testServiceIsEvoked() throws Exception {
        vehicleEnquiry = new VehicleEnquiry();
    }

    @And("^the registration number (.*) from (.*) is entered into the website$")
    public void testCorrectNumberEntered(String plate, String file) throws Exception {
        vehicleInfo = vehicleEnquiry.checkDetails(plate);
    }

    @And("^registration numbers from each file with (.*) is entered into the website inturn$")
    public void testRegistrationNumbersFromEachFile(String name) throws Exception {
        this.fileName = name;
    }

    @Then("^the service will produce a list of size (-?\\d+)$")
    public void testServiceProduceListSize(int result) throws Exception {
        assertEquals(fileDetailsList.size(), result);
    }

    @Then("^the website details will match the service with make of BMW with a colour of BLUE$")
    public void testWebsiteDetails() throws Exception {
        assertEquals(vehicleInfo[1], "BMW");
        assertEquals(vehicleInfo[2], "BLUE");
    }

    @Then("^the website details for registration (.*) will be (.*) the exact vehicle make (.*) with colour (.*)$")
    public void testWebsiteFoundOrNot(String plate, String foundOrNot, String make, String colour) throws Exception {
        switch (foundOrNot) {
            case "found":
                vehicleInfo = vehicleEnquiry.checkDetails(plate);
                assertEquals(make, vehicleInfo[1]);
                assertEquals(colour, vehicleInfo[2]);
                break;
            case "not found":
                page = vehicleEnquiry.incorrectRegNo(plate);
                ErrorPage errorPage = vehicleEnquiry.incorrectRegNo(plate);
                assertEquals(Page.ErrorPage_CONTACT_INFO, errorPage.getErrorContactInfoString());
                break;
            case "error":
                page = vehicleEnquiry.enterInvalidFormatRegNo(plate);
                RegEnterPage regEnterPage = (RegEnterPage) page;
                assertEquals(Page.RegEnterPage_ERROR_HEADING, regEnterPage.getErrorStringHeading());
                assertEquals(Page.RegEnterPage_ERROR_STRING, regEnterPage.getErrorString());
                break;
        }
    }

    @Then("^the website details for registration (.*) with make of (.*) with a colour of (.*)  will (.*) " +
            "the service with make of (.*) with a colour of (.*)$")
    public void testWebsiteMatchOrNot(String plate, String make, String colour, String matchOrNot,
                                      String make1, String colour1) throws Exception {
        switch (matchOrNot) {
            case "match":
                vehicleInfo = vehicleEnquiry.checkDetails(plate);
                assertEquals(make, vehicleInfo[1]);
                assertEquals(colour, vehicleInfo[2]);
                vehicleDetails = fileReaderService.getVehicleDetailsFromFile(fileName);
                vehicleEnquiry.takeScreenshot();
                assertEquals(make1, vehicleDetails.getMake());
                assertEquals(colour1, vehicleDetails.getColour());
                break;
            case "not match":
                page = vehicleEnquiry.incorrectRegNo(plate);
                ErrorPage errorPage = (ErrorPage) page;
                assertEquals(errorPage.getErrorContactInfoString(), Page.ErrorPage_CONTACT_INFO);
                vehicleDetails = fileReaderService.getVehicleDetailsFromFile(fileName);
                vehicleEnquiry.takeScreenshot();
                assertEquals(make1, vehicleDetails.getMake());
                assertEquals(colour1, vehicleDetails.getColour());
                break;
            case "not be found":
                vehicleInfo = vehicleEnquiry.checkDetails(plate);
                assertEquals(make, vehicleInfo[1]);
                assertEquals(colour, vehicleInfo[2]);
                vehicleDetails = fileReaderService.getVehicleDetailsFromFile(fileName);
                vehicleEnquiry.takeScreenshot();
                assertEquals(make1, vehicleDetails.getMake());
                assertEquals(colour1, vehicleDetails.getColour());
                break;
            case "error":
                page = vehicleEnquiry.enterInvalidFormatRegNo(plate);
                RegEnterPage regEnterPage = (RegEnterPage) page;
                assertEquals(Page.RegEnterPage_ERROR_HEADING, regEnterPage.getErrorStringHeading());
                assertEquals(Page.RegEnterPage_ERROR_STRING, regEnterPage.getErrorString());
                vehicleDetails = fileReaderService.getVehicleDetailsFromFile(fileName);
                vehicleEnquiry.takeScreenshot();
                assertEquals(make1, vehicleDetails.getMake());
                assertEquals(colour1, vehicleDetails.getColour());
                break;
        }
    }

    @Given("^I am a user of the website$")
    public void testUserWebsite() throws Exception {
        vehicleEnquiry = new VehicleEnquiry();
        assertNotNull(vehicleEnquiry);
    }

    @When("^I enter a correct registration plate of YS59ABZ")
    public void testCorrectRegistration() throws Exception {
        info = vehicleEnquiry.checkDetails("YS59ABZ");
    }

    @When("^I enter a incorrectly formatted registraton plate of AB343467$")
    public void testIncorrectlyFormatRegistration() throws Exception {
        page = vehicleEnquiry.enterInvalidFormatRegNo("AB343467");
    }

    @When("^I enter a correctly formatted invalid registration plate of V765DPR$")
    public void testCorrectFormatedInvalidRegistration() throws Exception {
        page = vehicleEnquiry.incorrectRegNo("V765DPR");
    }

    @And("^press continue$")
    public void testPressContinue() throws Exception {

    }

    @Then("^I will receive the correct make as BMW and colour of car as BLUE$")
    public void testCorrect() throws Exception {
        assertEquals(3, info.length);
        assertEquals(info[1], "BMW");
        assertEquals(info[2], "BLUE");
    }

    @Then("^I will receive an error messsage on the same page$")
    public void testErrorMessageSamePage() throws Exception {
        RegEnterPage regEnterPage = (RegEnterPage) page;
        assertEquals(regEnterPage.getErrorString(), Page.RegEnterPage_ERROR_STRING);
    }

    @Then("^I will be taken to an error page$")
    public void testErrorPage() throws Exception {
        ErrorPage errorPage = (ErrorPage) page;
        assertEquals(errorPage.getErrorContactInfoString(), Page.ErrorPage_CONTACT_INFO);
    }

    @After
    public void tearDown() {
        if (vehicleEnquiry != null) {
            vehicleEnquiry.quit();
        }
    }

}