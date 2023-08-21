package scriptContrasenias;

public class Main {

    public static void main(String [] args){
        String password = args[0];
        ValidarPassword validador = new ValidarPassword();

        validador.esValidaPassword(password);

    }
}
