package sparkjava;

import controllers.UserController;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;

import static spark.Spark.*;

public class SparkJavaExample {
    public static void main(String[] args) {
        // Set the port to listen on

        // Configure Thymeleaf
        try {
            port(8080);

            ThymeleafTemplateEngine templateEngine = new ThymeleafTemplateEngine();

            // Define a route
            get("/hello", (req, res) -> {
                ModelAndView modelAndView = new ModelAndView(new HashMap<>(), "hello");
                return templateEngine.render(modelAndView);
            });


            UserController.userRoutes();
        }
        catch (Exception e){
            e.printStackTrace();
            StackTraceElement[] stackTrace = e.getStackTrace();

            // Print the stack trace
            for (StackTraceElement element : stackTrace) {
                System.out.println(element.toString());
            }

        }

    }
}

