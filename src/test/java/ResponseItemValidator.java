
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.testng.Assert;

public class ResponseItemValidator {
    LinkedHashMap<String, String> item;
    String entityType;

    public ResponseItemValidator(LinkedHashMap<String, String> item, String entityType) {
        this.item = item;
        this.entityType = entityType;
    }

    public void validateItem() {

        switch (this.entityType) {

        case "song":
            validateSongItem();
            break;
        case "audiobook":
            validateAudioBookItem();
            break;
        case "feature-movie":
            validateFeatureMovieItem();
            break;
        case "ebook":
            validateEBookItem();
            break;
        case "software":
            validatesoftwareItem();
            break;
        case "podcast":
            validatePodcastItem();
            break;
        case "tv-episode":
            validateTVEpisodeItem();
            break;
        case "music-video":
            validateMusicVideoItem();
            break;
        default:
            throw new IllegalArgumentException("Invalid media type");
        }
    }

    public void validateSongItem() {

        // CHECKING IF ALL KEYS ARE PRESENT.
        final HashSet<String> songAttributes = new HashSet<String>((Arrays.asList("wrapperType", "kind", "artistId",
                "collectionId", "trackId", "artistName", "collectionName", "trackName", "collectionCensoredName",
                "trackCensoredName", "artistViewUrl", "collectionViewUrl", "trackViewUrl", "previewUrl", "artworkUrl30",
                "artworkUrl60", "artworkUrl100", "collectionPrice", "trackPrice", "releaseDate",
                "collectionExplicitness", "trackExplicitness", "discCount", "discNumber", "trackCount", "trackNumber",
                "trackTimeMillis", "country", "currency", "primaryGenreName", "isStreamable")));
        
        // Comparing expected and actual key attributes
        Assert.assertTrue(item.keySet().containsAll(songAttributes), "Song Key fields are missing");

        // Validating key fields does not have null value
        for (String key : songAttributes) {
            Assert.assertTrue(item.get(key) != null, key + "value is empty");
        }

        // Validating key fields return string value
        Assert.assertTrue(item.get("wrapperType") instanceof String, "wrapperType value is not string");
        Assert.assertTrue(item.get("kind") instanceof String, "kind value is not string");
        Assert.assertTrue(item.get("artistName") instanceof String, "artistName value is not string");
        Assert.assertTrue(item.get("collectionCensoredName") instanceof String,
                "collectionCensoredName value is not string");
        Assert.assertTrue(item.get("country") instanceof String, "country value is not string");
        Assert.assertTrue(item.get("currency") instanceof String, "currency value is not string");

    }

    public void validateAudioBookItem() {

        final HashSet<String> audioBookatAttributes = new HashSet<String>((Arrays.asList("wrapperType", "collectionId",
                "artistName", "collectionName", "collectionCensoredName", "artistViewUrl", "collectionViewUrl",
                "artworkUrl60", "artworkUrl100", "collectionPrice", "trackCount", "releaseDate",
                "collectionExplicitness", "country", "currency", "primaryGenreName", "previewUrl", "description")));
       
        // Comparing expected and actual key attributes
        Assert.assertTrue(item.keySet().containsAll(audioBookatAttributes), "AudioBook Key fields are missing");

        // Validating key fields does not have null value
        for (String key : audioBookatAttributes) {
            Assert.assertTrue(item.get(key) != null, key + "value is empty");
        }

        // Validating key fields return string value
        Assert.assertTrue(item.get("wrapperType") instanceof String, "wrapperType value is not string");
        Assert.assertTrue(item.get("artistName") instanceof String, "artistName value is not string");
        Assert.assertTrue(item.get("collectionName") instanceof String, "collectionName value is not string");
        Assert.assertTrue(item.get("collectionCensoredName") instanceof String,
                "collectionCensoredName value is not string");
        Assert.assertTrue(item.get("country") instanceof String, "country value is not string");
        Assert.assertTrue(item.get("currency") instanceof String, "currency value is not string");

    }

    public void validateFeatureMovieItem() {

        final HashSet<String> featureMovieattributes = new HashSet<String>(
                (Arrays.asList("wrapperType", "kind", "trackId", "trackName", "artistName", "country", "currency",
                        "primaryGenreName", "trackCensoredName", "previewUrl", "releaseDate", "collectionExplicitness",
                        "trackExplicitness", "shortDescription", "longDescription")));
        
        // Comparing expected and actual key attributes
        Assert.assertTrue(item.keySet().containsAll(featureMovieattributes), "FeatureMovie Key fields are missing");

        // Validating key fields does not have null value
        for (String key : featureMovieattributes) {
            Assert.assertTrue(item.get(key) != null, key + "value is empty");
        }

        // Validating key fields return string value
        Assert.assertTrue(item.get("wrapperType") instanceof String, "wrapperType value is not string");
        Assert.assertTrue(item.get("kind") instanceof String, "kind value is not string");
        Assert.assertTrue(item.get("trackName") instanceof String, "trackName value is not string");
        Assert.assertTrue(item.get("artistName") instanceof String, "artistName value is not string");
        Assert.assertTrue(item.get("country") instanceof String, "country value is not string");
        Assert.assertTrue(item.get("currency") instanceof String, "currency value is not string");

    }

    public void validateEBookItem() {

        final HashSet<String> ebookAttributes = new HashSet<String>((Arrays.asList("artistId", "kind", "price",
                "artistName", "description", "trackId", "releaseDate", "currency")));
        
        // Comparing expected and actual key attributes
        Assert.assertTrue(item.keySet().containsAll(ebookAttributes), "EBookItem Key fields are missing");

        // Validating key fields does not have null value
        for (String key : ebookAttributes) {
            Assert.assertTrue(item.get(key) != null, key + "value is empty");
        }

        // Validating key fields return string value
        Assert.assertTrue(item.get("kind") instanceof String, "kind value is not string");
        Assert.assertTrue(item.get("artistName") instanceof String, "artistName value is not string");
        Assert.assertTrue(item.get("description") instanceof String, "description value is not string");
        Assert.assertTrue(item.get("releaseDate") instanceof String, "releaseDate value is not string");
        Assert.assertTrue(item.get("currency") instanceof String, "currency value is not string");

    }

    public void validatesoftwareItem() {

        final HashSet<String> softwareAttributes = new HashSet<String>((Arrays.asList("trackId", "kind", "trackName",
                "minimumOsVersion", "version", "currentVersionReleaseDate", "features", "currency")));
        
        // Comparing expected and actual key attributes
        Assert.assertTrue(item.keySet().containsAll(softwareAttributes), "software Key fields are missing");

        // Validating key fields does not have null value
        for (String key : softwareAttributes) {
            Assert.assertTrue(item.get(key) != null, key + "value is empty");
        }

        // Validating key fields return string value
        Assert.assertTrue(item.get("currentVersionReleaseDate") instanceof String,
                "currentVersionReleaseDate value is not string");
        Assert.assertTrue(item.get("kind") instanceof String, "kind value is not string");
        Assert.assertTrue(item.get("trackName") instanceof String, "trackName value is not string");
        Assert.assertTrue(item.get("version") instanceof String, "version value is not string");
        Assert.assertTrue(item.get("releaseDate") instanceof String, "releaseDate value is not string");
        Assert.assertTrue(item.get("currency") instanceof String, "currency value is not string");

    }

    public void validatePodcastItem() {

        final HashSet<String> podCastattributes = new HashSet<String>((Arrays.asList("wrapperType", "kind",
                "collectionId", "trackId", "artistName", "collectionName", "country", "currency", "primaryGenreName")));
        
        // Comparing expected and actual key attributes
        Assert.assertTrue(item.keySet().containsAll(podCastattributes), "Podcast Key fields are missing");

        // Validating key fields does not have null value
        for (String key : podCastattributes) {
            Assert.assertTrue(item.get(key) != null, key + "value is empty");
        }

        // Validating key fields return string value
        Assert.assertTrue(item.get("wrapperType") instanceof String, "wrapperType value is not string");
        Assert.assertTrue(item.get("kind") instanceof String, "kind value is not string");
        Assert.assertTrue(item.get("artistName") instanceof String, "artistName value is not string");
        Assert.assertTrue(item.get("collectionName") instanceof String, "collectionName value is not string");
        Assert.assertTrue(item.get("country") instanceof String, "country value is not string");
        Assert.assertTrue(item.get("currency") instanceof String, "currency value is not string");

    }

    public void validateTVEpisodeItem() {

        final HashSet<String> tvEpisideAttributes = new HashSet<String>(
                (Arrays.asList("wrapperType", "kind", "collectionId", "artistId", "artistName", "collectionName",
                        "trackId", "trackName", "collectionCensoredName", "country", "currency")));
        
        // Comparing expected and actual key attributes
        Assert.assertTrue(item.keySet().containsAll(tvEpisideAttributes), "TVEpisode Key fields are missing");

        // Validating key fields does not have null value
        for (String key : tvEpisideAttributes) {
            Assert.assertTrue(item.get(key) != null, key + "value is empty");
        }

        // Validating key fields return string value
        Assert.assertTrue(item.get("wrapperType") instanceof String, "wrapperType value is not string");
        Assert.assertTrue(item.get("kind") instanceof String, "kind value is not string");
        Assert.assertTrue(item.get("artistName") instanceof String, "artistName value is not string");
        Assert.assertTrue(item.get("collectionName") instanceof String, "collectionName value is not string");
        Assert.assertTrue(item.get("country") instanceof String, "country value is not string");
        Assert.assertTrue(item.get("currency") instanceof String, "currency value is not string");

    }

    public void validateMusicVideoItem() {

        final HashSet<String> musicVideoattributes = new HashSet<String>((Arrays.asList("wrapperType", "kind",
                "trackId", "artistId", "artistName", "country", "trackName", "currency")));
        
        // Comparing expected and actual key attributes
        Assert.assertTrue(item.keySet().containsAll(musicVideoattributes), "MusicVideo Key fields are missing");

        // Validating key fields does not have null value
        for (String key : musicVideoattributes) {
            Assert.assertTrue(item.get(key) != null, key + "value is empty");
        }

        // Validating key fields return string value
        Assert.assertTrue(item.get("wrapperType") instanceof String, "wrapperType value is not string");
        Assert.assertTrue(item.get("kind") instanceof String, "kind value is not string");
        Assert.assertTrue(item.get("artistName") instanceof String, "artistName value is not string");
        Assert.assertTrue(item.get("country") instanceof String, "country value is not string");
        Assert.assertTrue(item.get("trackName") instanceof String, "trackName value is not string");
        Assert.assertTrue(item.get("currency") instanceof String, "currency value is not string");

    }

}
