
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
        final HashSet<String> attributes = new HashSet<String>((Arrays.asList("wrapperType", "kind", "artistId", "collectionId", "trackId", "artistName", "collectionName", "trackName", "collectionCensoredName", "trackCensoredName", "artistViewUrl", "collectionViewUrl", "trackViewUrl", "previewUrl", "artworkUrl30", "artworkUrl60", "artworkUrl100", "collectionPrice", "trackPrice", "releaseDate", "collectionExplicitness", "trackExplicitness", "discCount", "discNumber", "trackCount", "trackNumber", "trackTimeMillis", "country", "currency", "primaryGenreName", "isStreamable")));     
        //Comparing expected and actual key attributes of Song Item
        Assert.assertTrue(item.keySet().containsAll(attributes), "Song Key fields are missing");   
        
    }
    
    public void validateAudioBookItem() {
        
        final HashSet<String> audioBookatAttributes = new HashSet<String>((Arrays.asList("wrapperType", "collectionId", "artistName", "collectionName", "collectionCensoredName", "artistViewUrl", "collectionViewUrl", "artworkUrl60", "artworkUrl100", "collectionPrice", "trackCount", "releaseDate", "collectionExplicitness", "country", "currency", "primaryGenreName", "previewUrl", "description")));    
        Assert.assertTrue(item.keySet().containsAll(audioBookatAttributes), "AudioBook Key fields are missing");
        
    }
    
    public void validateFeatureMovieItem() {
         
        final HashSet<String> featureMovieattributes = new HashSet<String>((Arrays.asList("wrapperType", "kind", "trackId", "trackName", "artistName", "country", "currency", "primaryGenreName","trackCensoredName","previewUrl","releaseDate","collectionExplicitness","trackExplicitness","shortDescription", "longDescription")));       
        Assert.assertTrue(item.keySet().containsAll(featureMovieattributes), "FeatureMovie Key fields are missing");
        
    }
    public void validateEBookItem() {
        
        final HashSet<String> ebookAttributes = new HashSet<String>((Arrays.asList("artistId", "kind", "price", "artistName","description", "trackId", "trackId", "releaseDate", "currency")));           
        Assert.assertTrue(item.keySet().containsAll(ebookAttributes), "EBookItem Key fields are missing");
     
    }
    public void validatesoftwareItem() {
        
        final HashSet<String> softwareAttributes = new HashSet<String>((Arrays.asList("trackId", "kind", "trackName", "minimumOsVersion", "version", "currentVersionReleaseDate", "features", "currency")));    
        Assert.assertTrue(item.keySet().containsAll(softwareAttributes), "software Key fields are missing");
       
    }
    public void validatePodcastItem() {
        
        final HashSet<String> podCastattributes = new HashSet<String>((Arrays.asList("wrapperType", "kind", "collectionId", "trackId", "artistName", "collectionName", "country", "currency", "primaryGenreName")));    
        Assert.assertTrue(item.keySet().containsAll(podCastattributes), "Podcast Key fields are missing");        
        
       
    }
    public void validateTVEpisodeItem() {
        
        final HashSet<String> tvEpisideAttributes = new HashSet<String>((Arrays.asList("wrapperType", "kind", "collectionId", "artistId", "artistName","collectionName", "trackId", "trackName", "collectionCensoredName", "country", "currency")));       
        Assert.assertTrue(item.keySet().containsAll(tvEpisideAttributes), "TVEpisode Key fields are missing");
       
    }
    
    public void validateMusicVideoItem() {
        
        final HashSet<String> musicVideoattributes = new HashSet<String>((Arrays.asList("wrapperType", "kind", "trackId", "artistId", "artistName", "country", "trackName", "currency")));   
        Assert.assertTrue(item.keySet().containsAll(musicVideoattributes), "MusicVideo Key fields are missing");
        
       
        
       
    }


    
    

}
