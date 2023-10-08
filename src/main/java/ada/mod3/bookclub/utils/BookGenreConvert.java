package ada.mod3.bookclub.utils;

import java.util.ArrayList;
import java.util.List;

import ada.mod3.bookclub.controller.dto.BookGenreRequest;
import ada.mod3.bookclub.controller.dto.BookGenreResponse;
import ada.mod3.bookclub.model.BookGenre;

public class BookGenreConvert {

    public static BookGenre toEntity(BookGenreRequest genreDTO) {
        BookGenre genre = new BookGenre();
        genre.setName(genreDTO.getName());
        return genre;
    }

    public static BookGenreResponse toResponse(BookGenre genre) {
        BookGenreResponse bookGenreResponse = new BookGenreResponse();
        bookGenreResponse.setId(genre.getId());
        bookGenreResponse.setName(genre.getName());
        return bookGenreResponse;
    }

    public static List<BookGenreResponse> toResponseList(List<BookGenre> genres){
        List<BookGenreResponse> genreResponses = new ArrayList<>();
        for (BookGenre genre : genres) {
            BookGenreResponse genreResponse = BookGenreConvert.toResponse(genre);
            genreResponses.add(genreResponse);
        }
        return genreResponses;
    }
}
