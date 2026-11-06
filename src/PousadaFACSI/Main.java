package PousadaFACSI;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        File arquivo = new File("dados/pousada.txt");
        Pousada pousada;
        if (arquivo.exists()) {
            LeitorArquivo leitor = new LeitorArquivo();
            pousada = leitor.carregar("dados/pousada.txt");
        } else {
            pousada = new Pousada("Pousada FACSI", "Marabá");
        }
        Menu menu = new Menu(pousada);
        menu.iniciar();
    }
}