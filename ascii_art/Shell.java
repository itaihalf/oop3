package ascii_art;

import java.io.IOException;

public class Shell {



    private final User user;
    public Shell() throws IOException {
        user = new User();
    }

    public void run(){
        String response = "";
        while (!response.equals("exit")){
            System.out.println(">>>");
            response = KeyboardInput.readLine();

            String [] args= response.split(" ");

            switch (args[0]){
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
                    add(args[1]);
                case "remove":
                    break;
            }

        }
    }

    private void add(String arg){
        user.getSubImgCharMatcher().addChar();
    }


}

