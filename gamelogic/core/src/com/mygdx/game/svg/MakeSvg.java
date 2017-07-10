package com.mygdx.game.svg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.PictureDrawable;

import com.applantation.android.svg.SVG;
import com.applantation.android.svg.SVGParseException;
import com.applantation.android.svg.SVGParser;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;

public class MakeSvg {
    private static String[] colors;
    private String svgpath;

    public MakeSvg() {
    }


    public String makeSvg(String text, String maxX, String maxY, int color, boolean grid) {
        switch (color) {
            case 0:
                colors = new String[]{"#8899DD", "#1E90FF", "#2A52BE"};
                break;
            case 1:
                colors = new String[]{"#FF0000", "#B22222", "#8B0000"};
                break;
            case 2:
                colors = new String[]{"#A9A9A9", "#A9A9A9", "#A9A9A9"};
                break;
        }
        String svg = null;
        try {

            HashMap<String, String> hm = new HashMap<String, String>();

            hm.put("startPoint", "true");

            hm.put("longLines", "true");
            hm.put("arrows", "false");
            hm.put("strongLines", "false");
            hm.put("drawLength", "false");
            hm.put("drawPointsNumbers", "false");
            hm.put("maxX", maxX);
            hm.put("maxY", maxY);

            Charis ch = new Charis(hm, colors);

            ch.draw(text);

            if (hm.containsKey("align8")) {
                if (ch.maxX < 8) ch.maxX = 8;
                else if (ch.maxX < 16) ch.maxX = 16;
                else if (ch.maxX < 24) ch.maxX = 24;
                if (ch.maxY < 8) ch.maxY = 8;
                else if (ch.maxY < 16) ch.maxY = 16;
                else if (ch.maxY < 24) ch.maxY = 24;
            }
            if (grid == true) {
                svg = "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" viewBox=\"" +
                        "0 0 " +
                        (ch.maxX * ch.cellSize + ch.ofsX * 2) + " " + (ch.maxY * ch.cellSize + ch.ofsY * 2) +
                        "\" width=\"800px\" height=\"800px\">";

                svg = svg + "\n" + ch.makeGrid();
            } else {
                svg = "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" viewBox=\"" +
                        "0 0 " +
                        (ch.maxX * ch.cellSize + ch.ofsX * 2) + " " + (ch.maxY * ch.cellSize + ch.ofsY * 2) +
                        "\" width=\"800px\" height=\"800px\">";
            }
            svg = svg + "\n" + ch.svgPrev;
            svg = svg + "\n" + ch.svg;
            svg = svg + "\n" + ch.svgSecond;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return svg;
    }

    public Image drawSVG(Image image, float xX, float yY,String text, String maxX, String maxY, int color, boolean grid) {
        SVG svg = null;
        Pixmap pmap = null;
        if (grid==true){
            svgpath="";
        } else if (grid==false){
            Gdx.app.log("fffff","");
            svgpath="";
        }
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            try {
                svgpath = svgpath+ makeSvg(text, maxX, maxY, color, grid) + "\n" + "</svg>";
                svg = SVGParser.getSVGFromString(svgpath);
            } catch (SVGParseException e) {
                e.printStackTrace();
            }
            PictureDrawable drawable = svg.createPictureDrawable();
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawPicture(drawable.getPicture());
            int size = bitmap.getWidth() * bitmap.getHeight() * 2;
            ByteArrayOutputStream out;
            while (true) {
                out = new ByteArrayOutputStream(size);
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 0, out))
                    break;
                size = size * 3 / 2;
            }
            byte[] img = out.toByteArray();
            pmap = new Pixmap(img, 0, img.length);
        }
        Texture imga = new Texture(pmap);
        image = new Image(imga);
        image.setSize(Gdx.graphics.getWidth() / 2.5f, Gdx.graphics.getHeight() / 1.5f);
        image.setPosition(xX, yY);
        //imga.dispose();
        //Gdx.app.log("fffff", String.valueOf(image.getX())+"     "+image.getY()+
        //         "        "+image.getWidth()+"      "+image.getHeight());
        return image;
    }


}


