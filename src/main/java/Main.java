import scriptContrasenias.ValidarPassword;

public class Main {

    public static void main(String[] args)
    {
        ValidarPassword validarPassword = new ValidarPassword();
        System.out.println(validarPassword.esValidaPassword("Password123"));
    }

}
