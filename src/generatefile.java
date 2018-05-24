import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class generatefile {


    byte[] getbytes (String song){
        byte[] bytes =  Base64.getDecoder().decode(song.getBytes(StandardCharsets.UTF_8));
        return bytes;
    }

    void writeBytesToFile(byte[] bFile, String fileDest) {

        try (FileOutputStream fileOuputStream = new FileOutputStream(fileDest)) {
            fileOuputStream.write(bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        songtobase64 s1 = new songtobase64();
        String information = s1.Songtobase64("/home/racso/Project#2/Bella.mp3");
        generatefile g1 = new generatefile();
        g1.writeBytesToFile(g1.getbytes(information),"/home/racso/Odessey/Test/Song.mp3");
    }
}
