
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HelloPetstore extends JFrame {

    // Une zone de texte et deux boutons
    private final JTextField textHelloPetstore = new JTextField();
    private final JButton buttonHello = new JButton("Hello");
    private final JButton buttonPetstore = new JButton("Petstore");

    public HelloPetstore() {

        // Une action est déclanchée lorsqu'on clique sur le bouton Hello
        buttonHello.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                textHelloPetstore.setText("Hello");
            }
        });

        // Une action est déclanchée lorsqu'on clique sur le bouton Petstore
        buttonPetstore.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                textHelloPetstore.setText("PetStore !");
            }
        });

        // Disposition des éléments graphiques
        getContentPane().setLayout(new GridLayout(3, 1));
        getContentPane().add(textHelloPetstore);
        getContentPane().add(buttonHello);
        getContentPane().add(buttonPetstore);
        pack();
    }

    // Point d'entrée de l'application
    public static void main(String[] args) {
        new HelloPetstore().show();
    }
}
