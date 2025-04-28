package br.com.cefet.activehub.utils;

import java.security.SecureRandom;

public class RandomUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int MATRICULA_LENGTH = 20;
    private static SecureRandom random = new SecureRandom();

    public static String generateMatricula() {
        StringBuilder matricula = new StringBuilder(MATRICULA_LENGTH);
        for (int i = 0; i < MATRICULA_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            matricula.append(CHARACTERS.charAt(randomIndex));
        }
        return matricula.toString();
    }
}
