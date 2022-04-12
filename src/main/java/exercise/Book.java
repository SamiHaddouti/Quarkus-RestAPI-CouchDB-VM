package exercise;

/**
 * @Book object that contains information about author, title, language and ISBN
 */

public class Book {

  private String author;
  private String title;
  private String lang;
  private String isbn;

  public Book(String author, String title, String lang, String isbn) {
    this.author = author;
    this.title = title;
    this.lang = lang;
    this.isbn = isbn;
  }

  // Default constructor for REST API Consumer
  public Book() {
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
