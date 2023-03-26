import java.io.File;
import java.io.FileNotFoundException;
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

    public String toString() {
        DecimalFormat format = new DecimalFormat("0");

        return String.format("\t %s %s %s", format.format(isbn), title, author);
    }
}

public class Runner {
    public static void main(String[] args) throws FileNotFoundException {

        //Import File via Scanner
        File file = new File("/Users/hatim/Downloads/CS/Assignments/AVL Trees/untitled/src/booklist.txt");
        Scanner bookList = new Scanner(file);

        //Create an arrayList of book objects
        ArrayList<Book> books = new ArrayList<>();
        while(bookList.hasNextLine()) {
            double isbn = Double.parseDouble(bookList.nextLine());
            String title = bookList.nextLine();
            String author = bookList.nextLine();

            Book temp = new Book(isbn, title, author);
            books.add(temp);
        }

        AVLTree bst = new AVLTree();
        for(int i = 0; i < books.size(); i++) {
            bst.insert(books.get(i));
        }

        System.out.println();
        bst.prettyPrint();
    }
}
