package noticito.itosoft.wilson.com.noticito.models;

/**
 * Created by wilsonurbano on 10/11/16.
 */

public class Noticias {

    private int id;
    private String titulo;
    private String texto;
    private String url;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

    public void setId(int id) {
        this.id = id;
    }
}
