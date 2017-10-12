package nativelevel.titulos;

public enum Sexo {

    HOMEM, MULHER;

    public String getPrefix() {
        if (this == HOMEM) {
            return "o";
        } else {
            return "a";
        }

    }

    public String feminiza(String palavra) {
        if(palavra==null || palavra.length()<3)
            return palavra;
        if (this == MULHER) {
            if (palavra.equalsIgnoreCase("Patrão")) {
                return "Patroa";
            }
            if (palavra.equalsIgnoreCase("Papai Noel")) {
                return "Mamãe Noel";
            }
             if (palavra.equalsIgnoreCase("Rei do Gado")) {
                return "Rainha do Gado";
            }
            String u = palavra.charAt(palavra.length() - 1) + "";
            String p = palavra.charAt(palavra.length() - 2) + "";
            String build = palavra.substring(0, palavra.length() - 2);

            if (u.equalsIgnoreCase("o")) {
                if (p.equalsIgnoreCase("ã")) {
                    build += "ã";
                } else {
                    build += p + "a";
                }

            } else if (u.equalsIgnoreCase("a") || u.equalsIgnoreCase("e")) {
                build += p + u;
            } else {
                build += p + u + "a";
            }
            return build;
        }
        return palavra;
    }

}
