package jrails;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;

public class JRouterTest {

    private JRouter jRouter;

    @Before
    public void setUp() throws Exception {
        jRouter = new JRouter();
    }

    @Test
    public void addRoute() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        jRouter.addRoute("GET", "/", String.class, "index");
        // jRouter.route("GET", "/", null);
        assertThat(jRouter.getRoute("GET", "/"), is("java.lang.String#index"));

        // throw new IllegalStateException();
    }
}