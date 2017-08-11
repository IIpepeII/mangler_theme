import com.google.gson.Gson;
import controller.ImageReader;
import db_con.DatabaseController;
import model.Wuser;
import spark.ModelAndView;
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

public class Main {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        externalStaticFileLocation("upload");
        staticFileLocation("/public");
        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        DatabaseController dBase = new DatabaseController();
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        //port(8888);
        port(getHerokuAssignedPort());

        get("/", (req, res) -> {
            req.session().attribute("user", new Wuser());
            Map<String, Object> model = new HashMap<>();
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(model, "student")
            );
        });

        get("/student", "multipart/form-data", (req, res) -> {
            req.session().attribute("user", new Wuser());
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

        get("/wordcards", (req, res) -> {
            Gson json = new Gson();
            return json.toJson(dBase.getAllWordCards());
        });

        get("/results", (req, res) -> {
            Gson json = new Gson();
            return json.toJson(dBase.getAllResults());
        });

        get("/exam_cards", (req, res) -> {
            Gson json = new Gson();
            Wuser wuser = req.session().attribute("user");
            wuser.setResultToZero();
            wuser.setCurrentCardIndexToZero();
            wuser.setCards(dBase.getExamCards());
            return json.toJson(wuser.getCards());
        });

        post("/exercise", (req, res) -> {
            Gson json = new Gson();
            Wuser wuser = req.session().attribute("user");
            wuser.setResultToZero();
            wuser.setCurrentCardIndexToZero();
            String theme = req.queryParams("theme");
            wuser.setCards(dBase.getCardsByTheme(theme));
            return json.toJson(wuser.getCards());
        });

        get("/getcard", (req, res) -> {
            Gson json = new Gson();
            Wuser wuser = req.session().attribute("user");
            if (wuser.isLastIndex()) {
                return json.toJson("OK");
            }
            return json.toJson(wuser.getCard());
        });

        post("/evaluate", (req, res) -> {
            String hun = req.queryParams("hun");
            String eng = req.queryParams("eng");
            Wuser wuser = req.session().attribute("user");
            if(wuser.getCardListSize() == 0){
                return "OK";
            }
            else if(wuser.getCurrentIndex() == 0) {
                if (wuser.evalCardWithIndexZero(hun, eng)) {
                    wuser.setResult();
                }
            } else if (wuser.evalCard(hun, eng)) {
                wuser.setResult();
            }
            return "OK";
        });

        get("/result_to_zero", (req, res) -> {
            Wuser wuser = req.session().attribute("user");
            wuser.setResultToZero();
            return "Ok";
        });

        get("/get_result", (req, res) -> {
            Wuser wuser = req.session().attribute("user");
            return String.valueOf(wuser.getResult());
        });

        post("/del_wordcard", (req, res) -> {
            Integer id = Integer.parseInt(req.queryParams("id"));
            dBase.delWordCard(id);
            return "OK";
        });

        post("/del_result", (req, res) -> {
            System.out.println("segg");
            Integer id = Integer.parseInt(req.queryParams("id"));
            dBase.delResult(id);
            return "OK";
        });

        post("/getimage", (req, res) -> {
            String picLocation = req.queryParams("picLocation");
            return ImageReader.base64image(picLocation);
        });

        post("/save_result", (req, res) -> {
            String firstName = req.queryParams("fname");
            String lastName = req.queryParams("lname");
            Wuser wuser = req.session().attribute("user");
            String result = String.valueOf(wuser.getResult() * 10);
            dBase.addNewResult(firstName,lastName,result);
            res.redirect("/student");
            return "OK";
        });

        post("/upload", (req, res) -> {
            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".jpg");
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            try (InputStream input = req.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            List<String> postParams = new ArrayList<>();
            String fileName = tempFile.toString();
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