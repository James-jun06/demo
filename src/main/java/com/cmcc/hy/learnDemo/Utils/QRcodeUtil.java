package com.cmcc.hy.learnDemo.Utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.org.apache.bcel.internal.generic.PUSH;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * Project:  learnDemo
 * File Created by James on 2018/6/28
 *
 * Copyright 2018 CMCC Corporation Limited.
 * All rights reserved.
 */
public class QRcodeUtil {

    public static String showQRCode(HttpServletResponse response){
        String token = RandomUtil.makeToken();
        try {
            createQrCode(response, token, 900, "jpg");
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return token;
    }

    public static void createQrCode(HttpServletResponse response, String content, int qrCodeSize, String imageFormat)
        throws WriterException, IOException {
        //设置二维码纠错级别ＭＡＰ
        /*Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 矫错级别
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth-200, matrixWidth-200, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++){
            for (int j = 0; j < matrixWidth; j++){
                if (byteMatrix.get(i, j)){
                    graphics.fillRect(i-100, j-100, 1, 1);
                }
            }
        }*/
        try {
            BufferedImage image1 = QrCodeGenWrapper.createQrCodeConfig().setMsg("111").setH(400).setW(500).asBufferedImage();
            ImageIO.write(image1, imageFormat, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
