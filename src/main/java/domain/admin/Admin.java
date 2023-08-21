package domain.admin;

public class Admin {
    private String usuario;
    private String password;
    private Login sistemaLogueo;
    private Double FE_CF;
    private Double FE_CM;
    private Double FE_EAC;
    private Double FE_LPR;
    private Double K;

    public void loguear(){
        // TODO: 23/6/2022
    }
    public Double factorDeEmision(String actividad){
        Double fe = null;

        switch(actividad){
            case "COMBUSTION_FIJA":
                fe = FE_CF;
                break;
            case "COMBUSTION_MOVIL":
                fe = FE_CM;
                break;
            case "ELECTRICIDAD_ADQUIRIDA_Y_CONSUMIDA":
                fe = FE_EAC;
                break;
            case "LOGISTICA_PRODUCTOS_Y_RESIDUOS":
                fe = FE_LPR;
                break;
        }

        return fe;
    }

    //Getters y setters
        //Usuario
        public String getUsuario () {
        return usuario;
    }

        public void setUsuario (String usuario){
        this.usuario = usuario;
    }
        //Password
        public String getPassword () {
        return password;
    }

        public void setPassword (String password){
        this.password = password;
    }
        //FE_CF
        public Double getFE_CF () {
        return FE_CF;
    }

        public void setFE_CF (Double FE_CF){
        this.FE_CF = FE_CF;
    }
        //FE_CM
        public Double getFE_CM () {
        return FE_CM;
    }

        public void setFE_CM (Double FE_CM){
        this.FE_CM = FE_CM;
    }
        //FE_EAC
        public Double getFE_EAC () {
        return FE_EAC;
    }

        public void setFE_EAC (Double FE_EAC){
        this.FE_EAC = FE_EAC;
    }
        //FE_LPR
        public Double getFE_LPR () {
        return FE_LPR;
    }

        public void setFE_LPR (Double FE_LPR){
        this.FE_LPR = FE_LPR;
    }

        public Double getK() { return K; }

        public void setK (Double k) {
        this.K = k;
    }
    }

