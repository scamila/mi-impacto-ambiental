package scriptContrasenias;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.io.FileReader;

public class ValidarPassword {

    public boolean noEstaEnElArchivo(String password) {
        try(BufferedReader br = new BufferedReader(new FileReader("./topPeoresContrasenias.txt"))) {

            String lineaLeida;

            while ((lineaLeida = br.readLine()) != null) {

                if (Objects.equals(password, lineaLeida))
                    return false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo");
        } catch (IOException e) {
            e.printStackTrace();
        }
            return true;
    }
    public boolean esValidaPassword(String password){

        String expresionRegular = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,}$";

        Pattern p = Pattern.compile(expresionRegular);
        Matcher m = p.matcher(password);

        if(m.matches() && this.noEstaEnElArchivo(password)) {
           System.out.println("Contraseña Segura");
           return true;
        }
        else {
            System.out.println("Contraseña Insegura");
            return false;
        }
        }
}



