import com.google.gson.Gson;

import controller.Controller;

import db_con.DatabaseController;
import db_con.JDBCConnection;

import model.Wuser;
import spark.ModelAndView;
import spark.Session;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.servlet.MultipartConfigElement;

import java.io.File;
import java.io.InputStream;
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
        externalStaticFileLocation("src/main/resources/public/img/");
        staticFileLocation("/public");
        File uploadDir = new File("src/main/resources/public/img/");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        DatabaseController dBase = new DatabaseController();
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        port(8888);

        // populate some data for the memory storage
//        populateData();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(model, "student")
            );
        });

        get("/student", "multipart/form-data", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(model, "student")
            );
        });

        get("/teacher", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(model, "teacher")
            );
        });

        get("/word_cards", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(model, "wordcard")
            );
        });

        get("/wordcards", (res, req) -> {
            Gson json = new Gson();
            return json.toJson(dBase.getAll());
        });

        get("/exam_cards", (req, res) -> {
            Gson json = new Gson();
            Wuser wuser =  new Wuser();
            req.session().attribute("user",wuser);
            wuser.setCards(dBase.getExamCards());
            return json.toJson(wuser.getCards());
        });

        post("/exercise", (req, res) -> {
            Gson json = new Gson();
            Wuser wuser =  new Wuser();
            String theme = req.queryParams("theme");
            req.session().attribute("user",wuser);
            wuser.setCards(dBase.getCardsByTheme(theme));
            return json.toJson(wuser.getCards());
        });

        get("/getcard", (req,res) -> {
           Wuser wuser = req.session().attribute("user");
           Gson json = new Gson();
           if(wuser.isLastIndex()){
               return json.toJson("OK");
           }
           return json.toJson(wuser.getCard());
        });

        post("/upload", (req, res) -> {
            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".jpg");
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            try (InputStream input = req.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            List<String> postParams = new ArrayList<>();
            String fileName = tempFile.toString().replace("src/main/resources/public", "");
            postParams.add(fileName);
            postParams.add(req.queryParams("theme"));
            postParams.add(req.queryParams("hun"));
            postParams.add(req.queryParams("eng"));
            dBase.addNewWordCard(postParams);

            res.redirect("/teacher");
            return "OK";
        });



    }
}