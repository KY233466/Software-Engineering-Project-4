package jrails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.*;

public class ViewTest {

    @Test
    public void empty() {
        assertThat(View.empty().toString(), isEmptyString());

        Book b = new Book();
        b.title = "Programming Languages: Build, Prove, and Compare";
        b.author = "Norman Ramsey";
        b.num_copies = 999;
        String s = BookView.show(b).toString();

        System.out.println("-------------");
        System.out.println("BookView test");
        System.out.println(s);
        System.out.println("-------------");

        // throw new IllegalStateException();
    }
}