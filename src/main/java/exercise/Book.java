package exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Book object that contains information about author, title, language and ISBN
 */
public class Book {

  final private String author;
  final private String title;
  final private String lang;
  final private String isbn;

  @JsonCreator
  public Book(@JsonProperty("author") String author, @JsonProperty("title") String title,
              @JsonProperty("lang")String lang, @JsonProperty("isbn")String isbn) {
    this.author = author;
    this.title = title;
    this.lang = lang;
    this.isbn = isbn;
  }

  // Setter and Getter for attributes
  public String getAuthor() {
    return author;
  }

  public String getTitle() {
    return title;
  }

  public String getLanguage() { return lang; }

  public String getIsbn() { return isbn; }

  @Override
  public String toString() {
    return "{" +
            "\"author\": \"" + author + "\", " +
            "\"title\": \"" + title + "\", " +
            "\"lang\": \"" + lang + "\", " +
            "\"isbn\": \"" + isbn + "\"" +
            "}";
  }
}
