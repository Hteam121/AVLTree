import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

class Book {
    double isbn; // had to use double instead of Int due to numberFormat error
    String title;
    String author;

    public Book(double isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public String toString() { //Formats it properly to output later on in pretty print
        DecimalFormat format = new DecimalFormat("0");

        return String.format("\t %s %s %s", format.format(isbn), title, author);
    }
}

public class Runner {
    public static void main(String[] args) throws IOException {

        //Import File via Scanner
        File file = new File("/Users/hatim/Downloads/CS/Assignments/AVL Trees/untitled/src/booklist.txt");
        BufferedReader br = new BufferedReader(new FileReader(file)); //Used bufferedReader to improve efficiency

        //Create an arrayList of book objects
        ArrayList<Book> books = new ArrayList<>();
        String line = "";
        while((line = br.readLine()) != null) {
            double isbn = Double.parseDouble(line);
            String title = br.readLine();
            String author = br.readLine();

            Book temp = new Book(isbn, title, author);
            books.add(temp);
        }

        //Create the bst
        AVLTree bst = new AVLTree();
        for(int i = 0; i < books.size(); i++) {
            bst.insert(books.get(i));
        }

        //final output of the tree
        System.out.println();
        bst.prettyPrint();
    }
}
