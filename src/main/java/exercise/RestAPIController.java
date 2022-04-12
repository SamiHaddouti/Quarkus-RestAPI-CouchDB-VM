package exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static exercise.CouchDBService.*;

/**
 * @Path for accessing and modifying books
 */
@Path("/api/v1")
public class RestAPIController {


  /**
   * @getBooks returns all books in list
   */
  @GET
  @Path("/getall")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getBooks() throws IOException, JSONException {
    return getAllBooks();
  }

  /**
   * @param lang is isbn of a book to find
   * @return return a book with specified isbn
   */
  @GET
  @Path("/get_lang/{lang}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getByLanguage(
          @PathParam("lang") final String lang) throws IOException, JSONException {
    return getBookByLang(lang);
  }

  /**
   * @param isbn is isbn of a book to find
   * @return return a book with specified isbn
   */
  @GET
  @Path("/get_isbn/{isbn}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getByISBN(@PathParam("isbn") final String isbn) throws IOException, JSONException {
    return getBookByISBN(isbn);
  }

  /**
   * @return returns updated books list
   * @createBook add new book to DB
   */
  @PUT
  @Path("/create")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response putBook(final Book newBook) throws IOException, JSONException {
    return createBook(newBook);
  }

  /**
   * @return count of books in DB
   */
  @GET
  @Path("/count")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCount() throws JSONException, IOException {
    return getBookCount();
  }

  /**
   * @return health check for the microservice
   */
  @GET
  @Path("/health")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getHealth() {
    return Response.ok().entity("{\"status\": \"UP\"}").build();
  }
}