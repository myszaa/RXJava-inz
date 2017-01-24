package abc.json;

/**
 * Created by emicgaj on 2017-01-07.
 */
public class Repo {

    private int id = -1;
    private String name = null;
    private String url = null;

    public Repo(int id, String name) {
        this.id = id;
        this.name = name;
        ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "Repo{" +
                "id=" + id +
                ", name='" + name + '\'' +

                '}';
    }
}
