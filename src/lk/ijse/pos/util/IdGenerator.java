package lk.ijse.pos.util;

import java.util.Random;

public class IdGenerator {

    private static final String RESOURCE
            ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String getId(){
        String id="C-";
        int rand=new Random().nextInt(20);
        for (int i = 0; i < rand; i++) {
            id=id.concat(String.valueOf(RESOURCE.charAt(new Random().nextInt(61))));

        }
        return id;
    }

}
