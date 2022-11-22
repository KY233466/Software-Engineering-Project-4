package jrails;

public class Html {

    public String data;

    public String toString() {
        return this.data;
    }

    public Html seq(Html h) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + h.toString();
        return newH;
    }

    public Html br() {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<br/>";
        return newH;
    }

    public Html t(Object o) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + o.toString();
        return newH;
    }

    public Html p(Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<p>" + child.toString() + "</p>";
        return newH;
    }

    public Html div(Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<div>" + child.toString() + "</div>";
        return newH;
    }

    public Html strong(Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<strong>" + child.toString() + "</strong>";
        return newH;
    }

    public Html h1(Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<h1>" + child.toString() + "</h1>";
        return newH;
    }

    public Html tr(Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<tr>" + child.toString() + "</tr>";
        return newH;
    }

    public Html th(Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<th>" + child.toString() + "</th>";
        return newH;
    }

    public Html td(Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<td>" + child.toString() + "</td>";
        return newH;
    }

    public Html table(Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<table>" + child.toString() + "</table>";
        return newH;
    }

    public Html thead(Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<thead>" + child.toString() + "</thead>";
        return newH;
    }

    public Html tbody(Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<tbody>" + child.toString() + "</tbody>";
        return newH;
    }

    public Html textarea(String name, Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<textarea name=\"" + name + "\">" + child.toString() + "</textarea>";
        return newH;
    }

    public Html link_to(String text, String url) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<a href=\"" + url + "\">" + text + "</a>";
        return newH;
    }

    public Html form(String action, Html child) {
        Html newH = new Html();
        newH.data = (this.data == null ? "" : this.data) + "<form action=\"" + action + "\" accept-charset=\"UTF-8\" method=\"post\">" + child.toString() + "</form>";
        return newH;
    }

    public Html submit(String value) {
        Html btn = new Html();
        btn.data = (this.data == null ? "" : this.data) + "<input type=\"submit\" value=\"" + value + "\"/>";
        return btn;
    }
}