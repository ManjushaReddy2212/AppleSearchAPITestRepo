import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class AppleSearchAPIAutomation {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = Constants.URI_DOMAIN;
    }

    @DataProvider(name = "searchTermsAndMediaTypes")
    public Object[][] getSearchTermsAndMediaTypes() {
        return new Object[][] { 
                { "Shreya Goshal", "music" }, 
                { "Baahubali", "movie" }, 
                { "Podcast", "podcast" },
                { "Taylor Swift", "musicVideo" }, 
                { "Knowledge", "audiobook" }, 
                { "Friends", "tvShow" },
                { "Java", "software" }, 
                { "Business", "ebook" },
        };
    }

    @Test(testName = "Case1", description = "Verify valid entity type is retured for the given media type && Validate key fields for the search results",
                                             dataProvider = "searchTermsAndMediaTypes")
    public void searchByTermAndMediaType(String term, String mediaType) {

        RestAssured.baseURI = Constants.URI_DOMAIN;
        Response response = given().queryParam("term", term).queryParam("media", mediaType).when().get(Constants.API);

        // Validating status code
        Assert.assertEquals(response.getStatusCode(), Constants.HTTP_OK_STATUS, "Status code does not match");

        JsonPath path = new JsonPath(response.asString());
        int resultCount = path.getInt("resultCount");

        // Validating result count range is between 0 and 50
        Assert.assertTrue(resultCount > 0, "No search results found for the term: " + term);
        assertTrue(resultCount < 51,
                "Default result count should not be more than 50. But the actual count is " + resultCount);

        // Verifying valid entity type is returned for the given media-type
        HashSet<String> supportedEntities = getValidEntityTypesForMedia(mediaType);

        for (int i = 0; i < resultCount; i++) {

            LinkedHashMap<String, String> item = path.get("results[" + i + "]");
            String entityType;

            // For audio-book it needs special handling as kind key is absent, rather entity
            // info is present in wrapperType key
            if (mediaType.equals("audiobook")) {
                entityType = item.get("wrapperType").toString();
            }

            else {
                entityType = item.get("kind").toString();
            }
            Assert.assertTrue(supportedEntities.contains(entityType),
                    "Invalid entity type is returned " + "media: " + mediaType + " entityType " + entityType);

            // Validate the response of each item contains mandatory fields.
            ResponseItemValidator validator = new ResponseItemValidator(item, entityType);
            validator.validateItem();

        }
    }

    // Method which returns valid entity types for a media type
    private HashSet<String> getValidEntityTypesForMedia(String mediaType) {

        // Table:
        // https://developer.apple.com/library/archive/documentation/AudioVideo/Conceptual/iTuneSearchAPI/Searching.html#//apple_ref/doc/uid/TP40017632-CH5-SW2
        HashSet<String> entities;
        switch (mediaType) {

        case "movie":
            entities = new HashSet<>(Arrays.asList("movieArtist", "movie", "feature-movie"));
            break;
        case "podcast":
            entities = new HashSet<>(Arrays.asList("podcastAuthor", "podcast"));
            break;
        case "music":
            entities = new HashSet<>(Arrays.asList("musicArtist", "musicTrack", "album", "musicVideo", "mix", "song"));
            break;
        case "musicVideo":
            entities = new HashSet<>(Arrays.asList("musicArtist", "musicVideo", "music-video"));
            break;
        case "audiobook":
            entities = new HashSet<>(Arrays.asList("audiobookAuthor", "audiobook"));
            break;
        case "shortFilm":
            entities = new HashSet<>(Arrays.asList("shortFilmArtist", "shortFilm"));
            break;
        case "tvShow":
            entities = new HashSet<>(Arrays.asList("tvEpisode", "tvSeason", "tv-episode"));
            break;
        case "software":
            entities = new HashSet<>(Arrays.asList("software", "iPadSoftware", "macSoftware"));
            break;
        case "ebook":
            entities = new HashSet<>(Arrays.asList("ebook"));
            break;
        case "all":
            entities = new HashSet<>(Arrays.asList("movie", "album", "allArtist", "podcast", "musicVideo", "mix",
                    "audiobook", "tvSeason", "allTrack"));
            break;
        default:
            throw new IllegalArgumentException("Invalid media type");
        }
        return entities;
    }

    @Test(testName = "Case2", description = "Verify various status codes")
    public void testStatusCodesForApI() {

        // Test response OK
        RestAssured.baseURI = Constants.URI_DOMAIN;
        Response response = given().log().all().queryParam("term", "Test").when().get(Constants.API);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, Constants.HTTP_OK_STATUS, "Expected status code is 200");

        // Test status code 404 as the API is no existent
        response = given().log().all().get("/nonexistent");
        statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, Constants.HTTP_NOT_FOUND, "Expected status code is 404");

        // Test status code 400 as the media type is invalid.
        response = given().log().all().queryParam("term", "test").queryParam("media", "InvalidMediaType")
                .get(Constants.API);
        statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, Constants.HTTP_INVALID_INPUT, "Expected status code is 400");

        // Test status code 400 as the country type is invalid.
        response = given().log().all().queryParam("term", "test").queryParam("country", "InvalidCountryType")
                .get(Constants.API);
        statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, Constants.HTTP_INVALID_INPUT, "Expected status code is 400");

    }

    @Test(testName = "Case3", description = "Validate invalid text search gives zero results")
    public void testResponseForInvalidText() {

        // Test response OK
        RestAssured.baseURI = Constants.URI_DOMAIN;
        Response response = given().log().all().queryParam("term", "InvalidText").when().get(Constants.API);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, Constants.HTTP_OK_STATUS, "Expected status code is 200");

        // CHECK SIZE OF RESULTS IS ZERO.
        JsonPath path = new JsonPath(response.asString());
        int resultCount = path.getInt("resultCount");
        Assert.assertEquals(resultCount, 0, "No search results should be found for the term:InvalidText ");
        List<String> list = path.get("results");
        Assert.assertEquals(list.size(), 0, "Results returned are invalid");

    }

    @DataProvider(name = "countryAndPricePoints")
    public Object[][] getCountryAndPricePoints() {
        return new Object[][] { 
                          { "IN", "INR", "IND" }, 
                          { "US", "USD", "USA" }, 
                          { "CA", "CAD", "CAN" },
                          { "GB", "GBP", "GBR" }, 
                          { "JP", "JPY", "JPN" }
        };
    }

    @Test(testName = "Case4", description = "Verify valid currency is displayed for the respective country field", dataProvider = "countryAndPricePoints")
    public void testPricePointsAndCountryForAPI(String country, String currency, String countryCode) {

        RestAssured.baseURI = Constants.URI_DOMAIN;
        String response = given().log().all().queryParam("term", "Jim jones").queryParam("country", country)
                .queryParam("limit", 1).when().get(Constants.API).then().statusCode(Constants.HTTP_OK_STATUS).extract()
                .response().asString();

        JsonPath path = new JsonPath(response);

        // CHECK CURRENCY AND COUNTRY ARE EQUAL
        switch (country) {

        case "IN":
            // Replace all is used here as response returned is enclosed in square braces []
            Assert.assertEquals(path.getString("results.currency").replaceAll("[\\[\\]]", ""), currency,
                    "Currency is invalid");
            Assert.assertEquals(path.getString("results.country").replaceAll("[\\[\\]]", ""), countryCode,
                    "Country is Invalid");
            break;
        case "US":
            Assert.assertEquals(path.getString("results.currency").replaceAll("[\\[\\]]", ""), currency,
                    "Currency is invalid");
            Assert.assertEquals(path.getString("results.country").replaceAll("[\\[\\]]", ""), countryCode,
                    "Country is Invalid");
            break;
        case "CA":
            Assert.assertEquals(path.getString("results.currency").replaceAll("[\\[\\]]", ""), currency,
                    "Currency is invalid");
            Assert.assertEquals(path.getString("results.country").replaceAll("[\\[\\]]", ""), countryCode,
                    "Country is Invalid");
            break;
        case "GB":
            Assert.assertEquals(path.getString("results.currency").replaceAll("[\\[\\]]", ""), currency,
                    "Currency is invalid");
            Assert.assertEquals(path.getString("results.country").replaceAll("[\\[\\]]", ""), countryCode,
                    "Country is Invalid");
            break;
        case "JP":
            Assert.assertEquals(path.getString("results.currency").replaceAll("[\\[\\](){}]", ""), currency,
                    "Currency is invalid");
            Assert.assertEquals(path.getString("results.country").replaceAll("[\\[\\](){}]", ""), countryCode,
                    "Country is Invalid");
            break;
        default:
            throw new IllegalArgumentException("Invalid country ");

        }

    }

    @Test(testName = "Case5", description = "Verify limit field is returning valid results count", dataProvider = "searchTermsAndMediaTypes")
    public void validateLimit(String term, String mediaType) {

        // GIVE DIFFERENT LIMITS AND SEE IF IT WORKS.
        String response = given().log().all().queryParam("term", term).queryParam("media", mediaType)
                .queryParam("limit", 1).when().get(Constants.API).then().statusCode(Constants.HTTP_OK_STATUS).extract()
                .response().asString();

        JsonPath path = new JsonPath(response);
        Assert.assertEquals(path.getInt("resultCount"), 1, "Result Count mismatch");
        List<String> list = path.get("results");
        Assert.assertEquals(list.size(), 1, "Results returned are invalid");

        String response1 = given().log().all().queryParam("term", term).queryParam("media", mediaType)
                .queryParam("limit", 4).when().get(Constants.API).then().statusCode(Constants.HTTP_OK_STATUS).extract()
                .response().asString();

        JsonPath path1 = new JsonPath(response1);
        Assert.assertEquals(path1.getInt("resultCount"), 4, "Result Count mismatch");
        List<String> list1 = path1.get("results");
        Assert.assertEquals(list1.size(), 4, "Results returned are invalid");

    }

    // None of the API response fields match to Attribute type. So this method
    // cannot be used for validations
    private HashSet<String> getValidAttributeTypesForMedia(String mediaType) {

        // Table:
        // https://developer.apple.com/library/archive/documentation/AudioVideo/Conceptual/iTuneSearchAPI/Searching.html#//apple_ref/doc/uid/TP40017632-CH5-SW3
        HashSet<String> attributes;
        switch (mediaType) {

        case "movie":
            attributes = new HashSet<>(Arrays.asList("actorTerm", "genreIndex", "artistTerm", "shortFilmTerm",
                    "producerTerm", "ratingTerm", "directorTerm", "releaseYearTerm", "featureFilmTerm",
                    "movieArtistTerm", "movieTerm", "ratingIndex", "descriptionTerm"));
            break;
        case "podcast":
            attributes = new HashSet<>(Arrays.asList("titleTerm", "languageTerm", "authorTerm", "genreIndex",
                    "artistTerm", "ratingIndex", "keywordsTerm", "descriptionTerm"));
            break;
        case "music":
            attributes = new HashSet<>(Arrays.asList("mixTerm", "genreIndex", "artistTerm", "composerTerm", "albumTerm",
                    "ratingIndex", "songTerm"));
            break;
        case "musicVideo":
            attributes = new HashSet<>(
                    Arrays.asList("genreIndex", "artistTerm", "albumTerm", "ratingIndex", "songTerm"));
            break;
        case "audiobook":
            attributes = new HashSet<>(Arrays.asList("titleTerm", "authorTerm", "genreIndex", "ratingIndex"));
            break;
        case "shortFilm":
            attributes = new HashSet<>(
                    Arrays.asList("genreIndex", "artistTerm", "shortFilmTerm", "ratingIndex", "descriptionTerm"));
            break;
        case "tvShow":
            attributes = new HashSet<>(Arrays.asList("genreIndex", "tvEpisodeTerm", "showTerm", "tvSeasonTerm",
                    "ratingIndex", "descriptionTerm"));
            break;
        case "software":
            attributes = new HashSet<>(Arrays.asList("softwareDeveloper"));
            break;
        case "all":
            attributes = new HashSet<>(Arrays.asList("actorTerm", "languageTerm", "allArtistTerm", "tvEpisodeTerm",
                    "shortFilmTerm", "directorTerm", "releaseYearTerm", "titleTerm", "featureFilmTerm", "ratingIndex",
                    "keywordsTerm", "descriptionTerm", "authorTerm", "genreIndex", "mixTerm", "allTrackTerm",
                    "artistTerm", "composerTerm", "tvSeasonTerm", "producerTerm", "ratingTerm", "songTerm",
                    "movieArtistTerm", "showTerm", "movieTerm", "albumTerm"));
            break;
        default:
            throw new IllegalArgumentException("Invalid media type");
        }
        return attributes;
    }

    // public void validateExplicit() {}
    // "collectionExplicitness":"notExplicit", "trackExplicitness":"notExplicit"
    // API response includes both explicit and not explicit results irrespective of
    // explicit flag

    // public void validateVersion(String term, String mediaType) {}
    // API response includes all results irrespective of version field

    // public void validateLanguage(String term, String mediaType) {}
    //// API response is always in English irrespective of language field,
    // zh_CN,ja_JP

}
