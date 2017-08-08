import controller.Controller;
import db_con.DatabaseController;
import db_con.JDBCConnection;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import spark.utils.IOUtils;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {

        Controller.deleteFile();

        JDBCConnection.connectProps();
        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist

        staticFiles.externalLocation("upload");

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the memory storage
//        populateData();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(model, "product/index")
            );
        });

        get("/student","multipart/form-data", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(model, "product/student")
            );
    });

        get("/teacher", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(model, "product/teacher")
            );
        });

        post("/upload", (req, res) -> {
            Path tempFile = Files.createTempFile(uploadDir.toPath(),  "", "");
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            try (InputStream input = req.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            List<String> postParams = new ArrayList<>();
            postParams.add(tempFile.toString());
            postParams.add(req.queryParams("theme"));
            postParams.add(req.queryParams("hun"));
            postParams.add(req.queryParams("eng"));
            for(String n:postParams){
                System.out.println(n + " ");
            }
            DatabaseController dBase = new DatabaseController();
            dBase.addNewWordCard(postParams);

            return "<h1>You uploaded this image:<h1><img src='" + tempFile.getFileName() + "'>";
        });


    }
}