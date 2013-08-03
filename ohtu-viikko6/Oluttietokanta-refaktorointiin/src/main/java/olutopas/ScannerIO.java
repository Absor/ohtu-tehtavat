package olutopas;

import java.util.Scanner;

class ScannerIO implements IO {

    private Scanner scanner;

    public ScannerIO() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public void println(String text) {
        System.out.println(text);
    }
    
    @Override
    public void print(String text) {
        System.out.print(text);
    }
}
