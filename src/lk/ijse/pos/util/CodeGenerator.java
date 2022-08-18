package lk.ijse.pos.util;

import java.util.Random;

public class CodeGenerator {

    private static final String RESOURCE
            ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String getCode(){
        String code="P-";
        int rand=new Random().nextInt(20);
        for (int i = 0; i < rand; i++) {
            code=code.concat(String.valueOf(RESOURCE.charAt(new Random().nextInt(61))));

        }
        return code;
    }

}
