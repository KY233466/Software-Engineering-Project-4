package jrails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.*;

public class ModelTest {

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new Model(){};
    }

    @Test
    public void id() throws IllegalArgumentException, IllegalAccessException, IOException, NoSuchMethodException, SecurityException, InstantiationException, InvocationTargetException {
        // Model.reset();

        // File file = new File("data.csv");
        // boolean result = Files.deleteIfExists(file.toPath());
        // Files.exists(null, null);

        Book b = new Book();
        b.title = "Programming \"Languages\": Build,Prove,  and Compare";
        b.author = "Norman Ramsey";
        b.num_copies = 999;
        b.save();

        System.out.println("----1");

        List<Book> bs = Model.all(Book.class);

        Book b1 = new Book();
        b1.title = "Book n";
        b1.author = "Loln";
        b1.num_copies = 2339;
        b1.save();

        //  throw new IllegalStateException();

        // file = new File("data.csv");
        // result = Files.deleteIfExists(file.toPath());

        // System.out.println("----2");

        bs = Model.all(Book.class);

        // for (Book book : bs) {
        //     System.out.println(book.id());
        // }

        b1.destroy();

        bs = Model.all(Book.class);

        throw new IllegalStateException();

        // System.out.println("----2");

        // bs = Model.all(Book.class);

        // // for (Book book : bs) {
        // //     System.out.println(book.id());
        // // }

        // b.num_copies = 1;
        // b.save();

        // System.out.println("----3");

        // for (Book book : bs) {
        //     System.out.println(book.id());
        // }
    }

    @After
    public void tearDown() throws Exception {
        Model.reset();
        // File file = new File("data.csv");
        // boolean result = Files.deleteIfExists(file.toPath());
    }
}