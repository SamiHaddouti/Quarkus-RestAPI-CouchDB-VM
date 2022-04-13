package exercise;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static exercise.IsbnChecker.isISBN;

public class CouchDBService {

    static String COUCHDB_ADDRESS = ConfigProvider.getConfig().getValue("couchdb.address",String.class);
    static String COUCHDB_USER = ConfigProvider.getConfig().getValue("couchdb.user",String.class);
    static String COUCHDB_PWORD = ConfigProvider.getConfig().getValue("couchdb.pword",String.class);
    static String BASE_URL = "http://" + COUCHDB_USER + ":" + COUCHDB_PWORD + "@" + COUCHDB_ADDRESS + ":5984/library";

    private static JSONObject executeCurl(String[] command) throws IOException, JSONException {
        ProcessBuilder process = new ProcessBuilder(command);
        Process p;
        try {
            p = process.start();
            BufferedReader reader =  new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ( (line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return new JSONObject(builder.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private static String getBooksFromJSONArray(JSONArray rows) throws JSONException {
        List<Book>books = new ArrayList<>();
        try {
            for(int i = 0 ; i < rows.length() ; i++){
                String bookValue = rows.getJSONObject(i).getString("value");
                JSONObject bookJsonObj = new JSONObject(bookValue);
                Book book = new Book(bookJsonObj.getString("author"),
                bookJsonObj.getString("title"),
                bookJsonObj.getString("lang"),
                bookJsonObj.getString("isbn"));
                books.add(book);
            }
            String library = books.toString();
            library = library.substring(1, library.length() - 1);
            return library;
        } catch (JSONException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Response getAllBooks() throws IOException, JSONException {
        String[] command = {"curl", "-X", "GET", BASE_URL + "/_design/books/_view/byAuthor"};
        JSONObject jsonObj = executeCurl(command);
        if (jsonObj.has("error")) {
            return Response.ok().entity("{\"INFO\": \"The book listing is empty\"}").build();
        } else {
            JSONArray rows = jsonObj.getJSONArray("rows");
            if (rows.length() < 1) {
                return Response.ok().entity("{\"INFO\": \"The book listing is empty\"}").build();
            }
            return Response.ok().entity(getBooksFromJSONArray(rows)).build();
        }
    }

    public static Response getBookByLang(String lang) throws IOException, JSONException {
        String[] command = {"curl", "-X", "GET", BASE_URL + "/_design/books/_view/byLanguage?key=\"" + lang + "\""};
        JSONObject jsonObj = executeCurl(command);
        JSONArray rows = jsonObj.getJSONArray("rows");
        if (rows.length() < 1) {
            return Response.ok().entity("{\"INFO\": \"No book with this language in database\"}").build();
        } else {
            return Response.ok().entity(getBooksFromJSONArray(rows)).build();
        }
    }

    public static Response getBookByISBN(String isbn) throws IOException, JSONException {
        boolean isISBN = isISBN(isbn);
        if (isISBN) {
            String[] command = {"curl", "-X", "GET", BASE_URL + "/_design/books/_view/byISBN?key=\"" + isbn + "\""};
            JSONObject jsonObj = executeCurl(command);
            // Check if library is empty
            if (jsonObj.has("error")) {
                return Response.ok().entity("{\"INFO\": \"The book listing is empty\"}").build();
            } else {
                // Check if rows contains books
                JSONArray rows = jsonObj.getJSONArray("rows");
                if (rows.length() < 1) {
                    return Response.ok().entity("{\"INFO\": \"No book with this ISBN in database\"}").build();
                } else {
                    return Response.ok().entity(getBooksFromJSONArray(rows)).build();
                }
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"detail\": \"Invalid ISBN!\"}").build();
        }
    }

    // check/handle for json parsing
    // error wenn key-value paar fehlt
    // wennn ein key-value zu viel dann kein error (status 200), aber wird auch nicht hinzugefügt
    public static Response createBook(Book newBook) throws IOException, JSONException {
        String new_isbn = newBook.getIsbn();
        Response findResp = getBookByISBN(new_isbn);
        String respAsString = findResp.readEntity(String.class);
        JSONObject jsonObj = new JSONObject(respAsString);
        if (jsonObj.has("INFO")){
            // Add book
            String[] command = {"curl", "-H", "Content-Type: application/json",
                                "-X", "POST", BASE_URL, "-d" + newBook};
            JSONObject createRespObj = executeCurl(command);
            String doc_id = createRespObj.getString("id");
            String doc_rev = createRespObj.getString("rev");
            return Response.ok()
                    .entity("{\"SUCCESS\": {\"doc_id\": \""+ doc_id + "\", \"doc_rev\": \""+ doc_rev + "\"}}").build();
        } else if (jsonObj.has("detail")) {
            return findResp;
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"detail\": \"Book already present in library database\"}").build();
        }
    }
    public static Response getBookCount() throws JSONException, IOException {
        Response allBooksResp = getAllBooks();
        String respAsString = allBooksResp.readEntity(String.class);
        String count = String.valueOf(StringUtils.countMatches(respAsString, "isbn"));
        return Response.ok().entity("{\"count\": \"" + count + "\"}").build();
    }
}