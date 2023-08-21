package domain.admin;

public class Login {
    private AdapterLogin adapter;
    public boolean validacionLogueo(String password){
        return adapter.validacionLogueo(password);
    }
}
