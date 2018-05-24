
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Base64;


public class songtobase64 {


        public String Songtobase64(String ruta) throws IOException {
        Path path = Paths.get(ruta);
        byte[] data = Files.readAllBytes(path);
        String song = Base64.getEncoder().encodeToString(data);
        return song;
    }

    public static void main(String[] args) throws IOException {
        songtobase64 s1 = new songtobase64();
        String information = s1.Songtobase64("/home/racso/Project#2/Bella.mp3");
        System.out.println(information);
    }

}