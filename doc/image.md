一种是：(图片在本地可以预览到)，方法如下：
BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
int width = bufferedImage.getWidth();
int height = bufferedImage.getHeight();
这里的imagePath形如：F:\\aa\\a.jpg

Java代码  收藏代码
另一种是：图片属于网络上的地址(比如http://www.baidu.com/img/baidu_logo.gif) 代码如下：
Image img = Image.getInstance(new URL(“http://www.baidu.com/img/baidu_logo.gif”));
System.out.println("img.width="+img.width()+" img.hight="+img.height());
不过这里要加入下面的包才可以的，包在附件里面。