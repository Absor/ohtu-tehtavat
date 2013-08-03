/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olutopas.command;

import olutopas.IO;

public class QuitKomento implements Komento {

    IO io;

    public QuitKomento(IO io) {
        this.io = io;
    }

    @Override
    public void suorita() {
        io.println("bye");
        System.exit(0);
    }

    @Override
    public String komennonNimi() {
        return "quit";
    }
}
