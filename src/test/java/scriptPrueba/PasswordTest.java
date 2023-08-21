package scriptPrueba;
import scriptContrasenias.ValidarPassword;
import org.junit.Assert;
import org.junit.Test;

public class PasswordTest {

    @Test
    public void passwordNoSegura(){
        ValidarPassword resultado   = new ValidarPassword();

        Assert.assertFalse(resultado.esValidaPassword("Password123"));
    }
    @Test
    public void passwordSegura(){
        ValidarPassword resultado   = new ValidarPassword();

        Assert.assertTrue(resultado.esValidaPassword("Diseniosistem10"));
    }
}
