package 第六章.实例70;
import java.awt.*;
import java.awt.geom.*;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class TextureDemo extends JApplet {
  private BufferedImage bImage;

  public void init(){

    // 获得图片资源
    URL url=null;
    try {
      url = new URL(getCodeBase(),"images\\Winter.jpg");
    } catch(MalformedURLException e) {
        String msg = "Error loading image duke.gif";
        System.err.println(msg);
        showStatus(msg);
        System.exit(0);
    }
 
    Image img = new ImageIcon(url).getImage();

    // 建立一个和image大小相同的BufferedImage
    // 把图片放在上面
    bImage = new BufferedImage(img.getWidth(null),img.getHeight(null),
                   BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = bImage.createGraphics();
    g2.drawImage(img,null,null);
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    // 创建一个和图片大小一样的矩形
    Rectangle2D tr = new Rectangle2D.Double(0, 0,
                      bImage.getWidth(), bImage.getHeight());
    // 创建纹理画笔
    TexturePaint tp = new TexturePaint(bImage, tr);
    // 创建圆角矩形
    RoundRectangle2D r = new RoundRectangle2D.Float
	                             (25, 30, 100, 100, 20, 20);
    // 用纹理填充矩形
    g2.setPaint(tp);
    g2.fill(r);
    // 创建一个椭圆并用纹理填充
    Ellipse2D e = new Ellipse2D.Float(150, 30, 120, 100);
    g2.fill(e);
    //创建一个矩形并设置为thick stroke 使得当前纹理可视
    Rectangle2D r2 = new Rectangle2D.Double(320, 30, 100, 100);
    g2.setStroke(new BasicStroke(20));
    g2.draw(r2);
  }
}
