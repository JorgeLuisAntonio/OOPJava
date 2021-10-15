package monapplication;

import magasin.*;

import mesproduits.*;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner entrada=new Scanner(System.in);
        Commande c=new Commande();
        int opt;
        System.out.println("Bienvenue a le magasin QUICKLY");
        do{
            System.out.println("[1]Ajouter article \n [2]Retirer article\n[3]Lister commande" +
                    "\n[4]Quantite du commande\n[5]Montant\n[6]Sortir");
            opt=entrada.nextInt();
            switch(opt){
                case 1:
                    System.out.println("Quantite: ");
                    System.out.println("Produite:");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:

                    break;
                case 5:
                 c.montant();
                    break;
                case 6:
                    System.out.println("Au revoir");
                    break;
                default:
                    System.out.println("Option inconnu");
            }

        }while (opt!=6);


    }
}
