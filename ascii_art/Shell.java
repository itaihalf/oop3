package ascii_art;

import image.DimensionException;

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
                    remove(args[1]);
            }

        }
    }


    private static char[] charsFromRange(char start, char end){
        if (start > end){
            char temp = start;
            start = end;
            end = temp;
        }
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
        } else if (arg.split("-").length==2) {
            char start = arg.split("-")[0].charAt(0);
            char end = arg.split("-")[1].charAt(0);
            user.addCharsToDB(charsFromRange(start, end));
        } else{
            System.out.println("Did not add due to incorrect format.");
        }
    }

    private void remove(String arg){
        if (arg.length() == 1){
            user.removeCharFromDB(arg.charAt(0));
        }
        else if (arg.equals("all")){
            user.removeCharsFromDB(charsFromRange(' ', '~'));
        } else if (arg.split("-").length==2) {
            char start = arg.split("-")[0].charAt(0);
            char end = arg.split("-")[1].charAt(0);
            user.removeCharsFromDB(charsFromRange(start, end));
        } else{
            System.out.println("Did not add due to incorrect format.");
        }
    }

    private void res(String arg){
        try{
            switch (arg){
                case "down":
                    user.setResolution(user.getResolution()/2);
                    break;
                case "up":
                    user.setResolution(user.getResolution()*2);
                    break;
                default:
                    System.out.println("Did not change resolution due to incorrect format.");
            }
        } catch (DimensionException e){
            System.out.println("Did not change resolution due to incorrect format.");
        }

    }


}

