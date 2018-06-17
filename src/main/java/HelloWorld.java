import static spark.Spark.*;

public class HelloWorld {

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello WorldDDDDDDDDD");
        post("/thisIsAPost", (req,res)-> {
            String o = req.attribute("bannerid");

            System.out.println("I got a post");
            return res;
        });
    }
}
