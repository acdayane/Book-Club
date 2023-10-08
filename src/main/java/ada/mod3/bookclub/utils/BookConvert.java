package ada.mod3.bookclub.utils;

import java.util.ArrayList;
import java.util.List;

import ada.mod3.bookclub.controller.dto.BookRequest;
import ada.mod3.bookclub.controller.dto.BookResponse;
import ada.mod3.bookclub.model.Book;
import ada.mod3.bookclub.model.BookGenre;

public class BookConvert {
    public static Book toEntity(BookRequest bookDTO, BookGenre genre) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(genre);
        System.out.println(book);
        return book;
    }

    public static BookResponse toResponse(Book book) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setGenre(book.getGenre());
        return bookResponse;
    }

    public static List<BookResponse> toResponseList(List<Book> books){
        List<BookResponse> bookResponses = new ArrayList<>();
        for (Book book : books) {
            BookResponse bookResponse = BookConvert.toResponse(book);
            bookResponses.add(bookResponse);
        }
        return bookResponses;
    }
}
