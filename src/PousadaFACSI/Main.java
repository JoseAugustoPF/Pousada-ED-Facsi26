package PousadaFACSI;
public class Main {

    public static void main(String[] args) {
        Pousada pousada = new Pousada("Pousada Horizonte", "Marabá");
        Menu menu = new Menu(pousada);
        menu.iniciar();
    }
}