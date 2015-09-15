package 第六章.实例74;
// AppletSoundList.java
import javax.swing.*;
import java.applet.*;
import java.net.URL;

//Loads and holds a bunch of audio files whose locations are specified
//relative to a fixed base URL.
class AppletSoundList extends java.util.Hashtable {
    JApplet applet;
    URL baseURL;

    public AppletSoundList(JApplet applet, URL baseURL) {
        super(5); //初始化哈希表的容量为5
        this.applet = applet;
        this.baseURL = baseURL;
    }

    public void startLoading(String relativeURL) {
        new AppletSoundLoader(applet, this,
                              baseURL, relativeURL);
    }

    // 通过相对URL从哈希表中获得AudioClip
    public AudioClip getClip(String relativeURL) {
        return (AudioClip)get(relativeURL);
    }

    // 将AudioClip 对象放进哈希表，键值为relativeURL
    public void putClip(AudioClip clip, String relativeURL) {
        put(relativeURL, clip);
    }
}
