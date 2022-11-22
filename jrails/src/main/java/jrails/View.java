package jrails;

public class View {

    public static Html html;

    public static Html empty() {
        Html newHtml = new Html();
        newHtml.data = "";
        if (html == null) {
            html = newHtml;
        }
        else {
            html.seq(newHtml);
        }
        return html;
    }

    public static Html br() {
        Html result = new Html();
        result = result.br();
        return result;
    }

    public static Html t(Object o) {
        Html result = new Html();
        result = result.t(o);
        return result;
    }

    public static Html p(Html child) {
        Html result = new Html();
        result = result.p(child);
        return result;
    }

    public static Html div(Html child) {
        Html result = new Html();
        result = result.div(child);
        return result;
    }

    public static Html strong(Html child) {
        Html result = new Html();
        result = result.strong(child);
        return result;
    }

    public static Html h1(Html child) {
        Html result = new Html();
        result = result.h1(child);
        return result;
    }

    public static Html tr(Html child) {
        Html result = new Html();
        result = result.tr(child);
        return result;
    }

    public static Html th(Html child) {
        Html result = new Html();
        result = result.th(child);
        return result;
    }

    public static Html td(Html child) {
        Html result = new Html();
        result = result.td(child);
        return result;
    }

    public static Html table(Html child) {
        Html result = new Html();
        result = result.table(child);
        return result;
    }

    public static Html thead(Html child) {
        Html result = new Html();
        result = result.thead(child);
        return result;
    }

    public static Html tbody(Html child) {
        Html result = new Html();
        result = result.tbody(child);
        return result;
    }

    public static Html textarea(String name, Html child) {
        Html result = new Html();
        result = result.textarea(name, child);
        return result;
    }

    public static Html link_to(String text, String url) {
        Html result = new Html();
        result = result.link_to(text, url);
        return result;
    }

    public static Html form(String action, Html child) {
        Html result = new Html();
        result = result.form(action, child);
        return result;
    }

    public static Html submit(String value) {
        Html result = new Html();
        result = result.submit(value);
        return result;
    }
}