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


    private static char[] charsFromRange(char start, char end){
        char [] res = new char[end - start + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = (char) (start + i);
        }
        return res;
    }
    private void add(String arg){
        if (arg.length() == 1){
            user.addCharToDB(arg.charAt(0));
        }
        else if (arg.equals("all")){
            user.addCharsToDB(charsFromRange(' ', '~'));
        }
        else{
            System.out.println("Invalid input");
        }
    }


}

