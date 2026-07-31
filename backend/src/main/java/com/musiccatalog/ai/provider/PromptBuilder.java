package com.musiccatalog.ai.provider;

import com.musiccatalog.library.entity.SavedAlbum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PromptBuilder {

    public String buildPrompt(List<SavedAlbum> library) {
        String librarySummary = library.stream()
                .map(album -> String.format("- Album: %s, Artist: %s, Genre: %s, Rating: %d, Release Year: %s, Notes: %s",
                        album.getTitle(),
                        album.getArtistName(),
                        album.getGenre() != null ? album.getGenre() : "N/A",
                        album.getUserRating() != null ? album.getUserRating() : 0,
                        album.getReleaseDate() != null && album.getReleaseDate().length() >= 4 ? album.getReleaseDate().substring(0, 4) : "N/A",
                        album.getUserNotes() != null ? album.getUserNotes() : "None"))
                .collect(Collectors.joining("\n"));

        return "You are an expert music analyst.\n\n" +
                "Analyze this user's saved music library.\n\n" +
                "The data below contains\n" +
                "Album\nArtist\nGenre\nRating\nRelease Year\nNotes\n\n" +
                librarySummary + "\n\n" +
                "Generate\n" +
                "1. Favorite Genres\n" +
                "2. Favorite Artists\n" +
                "3. Listening Trends\n" +
                "4. Interesting Observations\n" +
                "5. Listening Personality (e.g. Alternative Rock Explorer, Classic Rock Collector)\n" +
                "6. Five Album Recommendations\n\n" +
                "Rules\n" +
                "Do not hallucinate.\n" +
                "Base every statement on the provided data.\n" +
                "If data is insufficient, say so.\n" +
                "Return JSON using this schema.\n" +
                "{\n" +
                "    \"genreSummary\": \"...\",\n" +
                "    \"favouriteArtist\": \"...\",\n" +
                "    \"listeningTrends\": \"...\",\n" +
                "    \"interestingObservations\": \"...\",\n" +
                "    \"listeningPersonality\": \"...\",\n" +
                "    \"albumRecommendations\":[]\n" +
                "}";
    }
}
