package ascii_art;

import java.io.IOException;

public class Shell {



    private final User user;
    private final AsciiArtAlgorithm asciiArtAlgorithm;

    public Shell() throws IOException {
        user = new User();
        asciiArtAlgorithm = new AsciiArtAlgorithm(user);
    }

    public void run(){
        String response = "";
        while (!response.equals("exit")){
            System.out.println(">>>");
            response = KeyboardInput.readLine();

            switch (response){
                case "chars":
                    break;
                case "res":
                    break;
                case "image":
                        break;
                case "output":
                    break;
                case "asciiArt":
                    break;
                case "add":
                    break;
                case "remove":
                    break;
            }

        }
    }


}

