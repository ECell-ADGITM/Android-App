package in.ecelladgitm.modelclasses;

import java.io.Serializable;

import androidx.annotation.Keep;

@Keep
public class Post implements Serializable {
    private String heading;
    private String content;
    private String image;

    public Post() {
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String data) {
        this.content = data;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
