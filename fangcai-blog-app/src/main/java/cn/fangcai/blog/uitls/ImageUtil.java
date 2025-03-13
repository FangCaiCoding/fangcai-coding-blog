package cn.fangcai.blog.uitls;

import org.apache.commons.imaging.Imaging;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ImageUtil {

    public static void main(String[] args) {
        handleOne() ;

    }
    public static void handleOne(){
            try {
                String path = "./rk_images_2/1898727353690435584.png";
                processImage(path, path, "公众号：方才编程");
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    public static void handleALl(){
        // 示例：读取指定目录下的所有图片文件路径
        String directoryPath = "./rk_images_2"; // 替换为目标目录路径
        java.util.List<String> imagePaths = getImageFilePaths(directoryPath);

        // 输出结果
        System.out.println("找到的图片文件路径：");
        for (String path : imagePaths) {
            System.out.println(path);
            try {
                processImage(path, path, "公众号：方才编程");
            } catch (Exception e) {
                System.out.printf("图片处理失败：%s\n", path);
                e.printStackTrace();
            }
        }
    }

    /**
     * 图片压缩+水印处理
     *
     * @param srcPath  源文件路径
     * @param destPath 输出路径
     */
    public static void processImage(String srcPath, String destPath, String waterText)
            throws Exception {
        BufferedImage originalImage;
        // 1. 读取原始图片
        try {
            originalImage = readImage(srcPath);
        } catch (Exception e) {
            System.out.println("重试读取图片：" + srcPath);
            originalImage = ImageIO.read(new File(srcPath));
        }
        if (originalImage == null) {
            System.out.println("失败！！！！！！！" + srcPath);
            return;
        }

        // 3. 添加文字水印
        addTextWatermark(originalImage, waterText, Color.green, 45, 0.2f);

        // 4. 保存处理后的图片
        ImageIO.write(originalImage, "png", new File(destPath));
    }


    /**
     * @param image
     * @param text
     * @param color
     * @param fontSize
     * @param alpha
     */
    private static void addTextWatermark(BufferedImage image, String text, Color color,
                                         int fontSize, float alpha) {
        Graphics2D g = image.createGraphics();

        try {
            // 设置绘图上下文参数
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 设置透明度（alpha范围0.0-1.0）
            AlphaComposite alphaComposite = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER,
                    Math.min(1.0f, Math.max(0.0f, alpha))  // 确保值在0-1之间
            );
            g.setComposite(alphaComposite);
            // 动态计算字体大小，根据图片宽度的1/15作为字体大小
            fontSize = Math.min(fontSize, image.getWidth() / 15);
            fontSize = Math.min(fontSize, image.getHeight() / 5);

            // 设置字体样式
            Font font = new Font("Microsoft YaHei", Font.PLAIN, fontSize);
            g.setFont(font);
            g.setColor(color);


            // 计算水印位置（居中）
            FontMetrics metrics = g.getFontMetrics();
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getHeight();

            // 计算居中坐标（考虑基线对齐）
            int x = (image.getWidth() - textWidth) / 2;
            int y = (image.getHeight() - textHeight) / 2 + metrics.getAscent();

            // 4. 计算中心点坐标（旋转锚点）
            int centerX = image.getWidth() / 2;
            int centerY = image.getHeight() / 2;
            g.rotate(Math.toRadians(30), centerX, centerY);
            // 绘制主文字
            g.setColor(color);
            g.drawString(text, x, y);
        } finally {
            // 确保资源释放
            g.dispose();
        }
    }


    /**
     * 遍历指定目录及其子目录，获取所有图片文件路径
     *
     * @param directoryPath 指定目录路径
     *
     * @return 图片文件路径列表
     */
    public static java.util.List<String> getImageFilePaths(String directoryPath) {
        java.util.List<String> imagePaths = new ArrayList<>();
        File directory = new File(directoryPath);

        // 检查目录是否存在且是文件夹
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("指定路径无效或不是目录：" + directoryPath);
            return imagePaths;
        }

        // 支持的图片文件扩展名
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".webp"};

        // 递归遍历目录
        traverseDirectory(directory, imagePaths, imageExtensions);

        return imagePaths;
    }

    /**
     * 递归遍历目录并收集图片文件路径
     *
     * @param file            当前文件或目录
     * @param imagePaths      存储图片路径的列表
     * @param imageExtensions 支持的图片扩展名
     */
    private static void traverseDirectory(File file, java.util.List<String> imagePaths, String[] imageExtensions) {
        if (file.isDirectory()) {
            // 如果是目录，递归遍历子文件
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File subFile : subFiles) {
                    traverseDirectory(subFile, imagePaths, imageExtensions);
                }
            }
        } else if (file.isFile()) {
            // 如果是文件，检查是否为图片
            String fileName = file.getName().toLowerCase();
            for (String ext : imageExtensions) {
                if (fileName.endsWith(ext)) {
                    imagePaths.add(file.getPath());
                    break;
                }
            }
        }
    }


    public static BufferedImage readImage(String srcPath) throws Exception {
        File file = new File(srcPath);
        if (!file.exists() || !file.canRead()) {
            throw new IllegalArgumentException("文件不存在或不可读：" + srcPath);
        }
        return Imaging.getBufferedImage(file);
    }


}