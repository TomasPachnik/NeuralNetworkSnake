package sk.tomas.ga;

import sk.tomas.neural.exception.FileException;

import java.io.*;

class Util {

    static void writeFile(String filename, Genetic genetic) throws FileException {
        try {
            FileOutputStream f = new FileOutputStream(new File(filename + ".dat"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(genetic);
            o.close();
            f.close();
        } catch (IOException e) {
            throw new FileException(e);
        }
    }

    static Genetic readFile(String filename) throws FileException {
        try {
            FileInputStream fi = new FileInputStream(new File(filename + ".dat"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            Genetic genetic = (Genetic) oi.readObject();
            oi.close();
            fi.close();
            return genetic;
        } catch (IOException | ClassNotFoundException e) {
            throw new FileException(e);
        }
    }

}
