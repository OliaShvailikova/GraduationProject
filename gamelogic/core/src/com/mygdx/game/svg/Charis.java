package com.mygdx.game.svg;

import java.util.HashMap;
import java.util.HashSet;

public class Charis {
    class Point
    {
        public double x;
        public double y;
        public int c;
        Point(double x, double y)
        {
            this.x = x;
            this.y = y;
            this.c = 0;
        }
    }

    public static final byte ID_COMMAND_RIGHT = 1;
    public static final byte ID_COMMAND_LEFT = 2;
    public static final byte ID_COMMAND_DOWN = 4;
    public static final byte ID_COMMAND_UP = 3;
    public static final byte ID_COMMAND_LEFTUP = 7;
    public static final byte ID_COMMAND_LEFTDOWN = 8;
    public static final byte ID_COMMAND_RIGHTUP = 5;
    public static final byte ID_COMMAND_RIGHTDOWN = 6;
    public static final byte ID_COMMAND_CIRCLE = 9;
    public static final byte ID_COMMAND_JUMP = 10;
    public static final byte ID_COMMAND_WORM = 11;
    public static final byte ID_COMMAND_TURTTLE = 12;
    public static final byte ID_COMMAND_BAR = 13;
    public static final byte ID_COMMAND_JUMPDOWN = 14;
    public static final byte ID_COMMAND_JUMPZERO = 15;

    public static final byte ID_COMMAND_POINT = 18;
    public static final byte ID_COMMAND_FLAG = 19;

    HashMap<String, String> params;

    String svgClass;
    boolean shadowElement = false;
    boolean randLines;
    boolean canDiffSinglePoint = false;
    int pointFontSize = 15;
    int numbers = 0;
    String svg = "";



    HashMap<String,Point> xypoints = new HashMap<String,Point>();


    public Charis(HashMap<String,String> hm,String [] color)
    {
        svgClass = " stroke-x=\" "+color[0]+"\" stroke=\""+color[1]+"\" stroke-width=\"1.5\" fill=\"none\" stroke-linejoin=\"round\" stroke-linecap=\"round\" xx-stroke-dasharray=\"1.5,7,1.5,0\" x-stroke-dasharray=\"3,7\";stroke-x=\""+color[0]+"\" stroke=\""+color[2]+"\" stroke-width=\"3.5\" fill=\"none\" stroke-linejoin=\"round\" stroke-linecap=\"round\" xx-stroke-dasharray=\"1.5,7,1.5,0\" x-stroke-dasharray=\"3,7\" ";

        params = hm;

        if(params.containsKey("cellSize"))
        {
            cellSize = Integer.parseInt(params.get("cellSize"));
        }

        if(
                (params.containsKey("drawPointsNumbers") && !("false".equals(params.get("drawPointsNumbers"))) )
                        ||(params.containsKey("simulateDrawPointsNumbers") && !("false".equals(params.get("simulateDrawPointsNumbers"))) )
                ){
            ofsY = 8+pointFontSize*cellSize/50;
            ofsX = ofsY;
        }



        if(params.containsKey("ofs"))
        {
            ofsX = Integer.parseInt(params.get("ofs"));
            ofsY = ofsX;
        }
        if(params.containsKey("ofsX"))
        {
            ofsX = Integer.parseInt(params.get("ofsX"));
        }
        if(params.containsKey("ofsY"))
        {
            ofsY = Integer.parseInt(params.get("ofsY"));
        }

        if(params.containsKey("maxX"))
        {
            maxX = Integer.parseInt(params.get("maxX"));
        }
        if(params.containsKey("maxY"))
        {
            maxY = Integer.parseInt(params.get("maxY"));
        }

        llX = ofsX;
        llY = ofsY;

        llXo = ofsX;
        llYo = ofsY;

        randLines = !params.containsKey("strongLines") || "false".equals(params.get("strongLines"));

        canDiffSinglePoint = params.containsKey("canDiffSinglePoint") && "true".equals(params.get("canDiffSinglePoint"));

        xypoints.put(""+(0.0+ofsX)+"-"+(0.0+ofsY), new Point(ofsX,ofsY));
    }

    int xPos = 0;
    int yPos = 0;

    int prevXpos = 0;
    int prevYpos = 0;

    int crc2 = 0;
    int crcTurttle = 0;
    int crcO = 0;
    int crcWorm = 0;

    int FlagCount = 0;

    boolean isWorm = true;

    HashSet<String> points = new HashSet<String>();


    String svgPrev = "";
    String svgSecond = "";

    public int cellSize=60;
    public int ofsX=10;
    public int ofsY=10;

    double llX = 10; // ofsX
    double llY = 10; // ofsY

    double llXo = 10; // ofsX
    double llYo = 10; // ofsY

    public int svgLastX=0;
    public int svgLastY=0;

    public int svgDir = 0;
    public int maxX = 0;
    public int maxY = 0;

    double startPointX = -1;
    double startPointY = 0;


    public String makeGrid()
    {

        Charis ch = this;

        String s =
                "<rect "+
                        "x=\"0\" y=\"0\" "+
                        "width=\""+(ch.cellSize*(ch.maxX)+ofsX*2)+"\" "+
                        "height=\""+(ch.cellSize*(ch.maxY)+ofsY*2)+"\" "+
                        "fill=\"none\" stroke-width=\"0\" stroke=\"pink\" />\n"+

                        "<rect "+
                        "x=\""+ch.ofsX+"\" y=\""+ch.ofsY+"\" "+
                        "width=\""+ch.cellSize*(ch.maxX)+"\" "+
                        "height=\""+ch.cellSize*(ch.maxY)+"\" "+
                        "fill=\"white\" stroke-width=\"0\" stroke=\"pink\" />\n"+

                        "<path d=\"";


        for(int i=0;i<=ch.maxY;i++)
            s+=("M "+ch.ofsX+" "+(i*ch.cellSize+ofsY)+" L "+(ch.cellSize*ch.maxX+ofsX)+" "+(i*ch.cellSize+ofsY)+" ");

        for(int i=0;i<=ch.maxX;i++)
            s+=("M "+(i*ch.cellSize+ofsX)+" "+ch.ofsY+" L "+(i*ch.cellSize+ofsX)+" "+(ofsY+ch.cellSize*(ch.maxY))+" ");

        s+=("\" stroke=\"#CCCCDD\" x-stroke=\"#999999\" xx-stroke=\"#000000\" stroke-width=\"3\"	/> ");
        return s;

    }


    private String getSvgClass()
    {
        if (shadowElement)
            if("doted".equals(params.get("type")))
                return "invisible";
            else
                return "shadow";
        return svgClass;
    }

    void placeSvgClass(String s)
    {
        String r = "";
        String[] ss = getSvgClass().split(";");
        for(int i=0;i<ss.length;i++)
            if(!"".equals(ss[i]))
            {
                if(i==1) svgPrev+=s.replaceAll("svgClass", ss[i]);
                if(i==0) svg+=s.replaceAll("svgClass", ss[i]);
                if(i==2) svgSecond+=s.replaceAll("svgClass", ss[i]);
            }

    }

    void svgAddLine(int x1,int y1,int x2,int y2)
    {
        if(x1==x2 && y1==y2) return;

        if(x1>maxX) maxX=x1;
        if(x2>maxX) maxX=x2;
        if(y1>maxY) maxY=y1;
        if(y2>maxY) maxY=y2;

        x1*=cellSize;
        y1*=cellSize;
        x2*=cellSize;
        y2*=cellSize;

        x1+=ofsX;
        y1+=ofsY;
        x2+=ofsX;
        y2+=ofsY;


        if(x1!=x2 && y1!=y2 && svgDir==0)
            svgDir = x1>x2?1:2;

        svgLastX = x2;
        svgLastY = y2;

    }

    void finishLongLine(boolean visible, int count)
    {


        double x1 = llX;
        double y1 = llY;

        double x2 = xPos;
        double y2 = yPos;

        x2*=cellSize;
        y2*=cellSize;

        x2+=ofsX;
        y2+=ofsY;

        boolean notSame = (llXo!=x2) || (llYo!=y2);

        String spoint = ""+x2+"-"+y2;

        if(randLines)
        {
            if(!canDiffSinglePoint && xypoints.containsKey(spoint))
            {
                Point p = xypoints.get(spoint);
                x2=p.x;
                y2=p.y;

            }
            else
            {
                // x2+=(Math.random()*2-1)*2; // *count/5
                // y2+=(Math.random()*2-1)*2; // *count/5

                if(!xypoints.containsKey(spoint))
                    xypoints.put(spoint, new Point(x2,y2));

            }


        }
        else
        {
            if(!xypoints.containsKey(spoint))
                xypoints.put(spoint, new Point(x2,y2));
        }

        if(x1!=x2 && y1!=y2 && svgDir==0)
            svgDir = x1>x2?1:2;


        double x3 = ((xPos*cellSize+ofsX)+llXo)/2;
        double y3 = ((yPos*cellSize+ofsY)+llYo)/2;
        if(randLines)
        {

            spoint = ""+x3+"-"+y3;
            if(!canDiffSinglePoint && xypoints.containsKey(spoint))
            {
                Point p = xypoints.get(spoint);
                x3=p.x;
                y3=p.y;
            }
            else
            {
                // x3+=(Math.random()*2-1)*2; // *count/5
                // y3+=(Math.random()*2-1)*2; // *count/5

                if (!canDiffSinglePoint)
                    xypoints.put(spoint, new Point(x3,y3));
            }

        }

        if(visible && startPointX==-1 && notSame)
        {
            startPointX = llXo;
            startPointY = llYo;

        }

        if(params.containsKey("noVisible") && !("false".equals(params.get("noVisible"))) )
        {
            visible=false;
        }


        if(visible)
        {
            if(params.containsKey("drawPoints") && !("false".equals(params.get("drawPoints"))) )
            {

                if(startPointX==-1)
                {
                    svgPrev+=
                            "<circle "+
                                    "cx=\""+llXo+"\" "+
                                    "cy=\""+llYo+"\" "+
                                    "r=\""+(cellSize/12.5)+"\" "+
                                    "stroke=\"#000070\" stroke-width=\"1\" fill=\"#FFFF88\" stroke-linejoin=\"round\" stroke-linecap=\"round\" /> \n";
                }

                svgPrev+=
                        "<circle "+
                                "cx=\""+(xPos*cellSize+ofsX)+"\" "+
                                "cy=\""+(yPos*cellSize+ofsY)+"\" "+
                                "r=\""+(cellSize/12.5)+"\" "+
                                "stroke=\"#000070\" stroke-width=\"1\" fill=\"#FFFF88\" stroke-linejoin=\"round\" stroke-linecap=\"round\" /> \n";

            }

            if(params.containsKey("drawPointsNumbers") && !("false".equals(params.get("drawPointsNumbers"))) )
            {

                numbers++;

                spoint = ""+llXo+"-"+llYo;


                int c = 1;

                if(xypoints.containsKey(spoint))
                    c = ++(xypoints.get(spoint)).c;

                c--;

                svgSecond+=
                        ("<text "+
                                "x=\""+(llXo+(0+2.5)*cellSize/50)+"\" "+
                                "y=\""+(llYo-(5-(pointFontSize+5)*c)*cellSize/50)+"\" "+
                                "font-family=\"Verdana\" font-size=\""+(pointFontSize*cellSize/50)+"\" "+
                                "stroke=\"#000070\" stroke-width=\"0\" fill=\"#000070\">"+
                                numbers+
                                "</text> \n");

                svgSecond+=
                        ("<text "+
                                "x=\""+(llXo+(0+2.5)*cellSize/50)+"\" "+
                                "y=\""+(llYo-(5-(pointFontSize+5)*c)*cellSize/50)+"\" "+
                                "font-family=\"Verdana\" font-size=\""+(pointFontSize*cellSize/50)+"\" "+
                                "stroke=\"#000070\" stroke-width=\"0\" fill=\"#000070\">"+
                                numbers+
                                "</text> \n");
            }

            if((!params.containsKey("drawPoints") || !("only".equals(params.get("drawPoints"))))
                    && (notSame) )
            {


                if(params.containsKey("arrows") && "true".equals(params.get("arrows")) )
                {

                    double dx = (x2-x1)/8;
                    double dy = (y2-y1)/8;

                    double l = Math.sqrt(dx*dx+dy*dy)/cellSize*16;

                    dx/=l;
                    dy/=l;

                    placeSvgClass("<polyline points=\""+
                            x2+","+y2+" "+
                            (x2-dx*4+dy)+","+(y2- dy*4-dx)+" "+
                            (x2-dx*4-dy)+","+(y2-dy*4+dx)+" "+
                            x2+","+y2+" "+
                            "\" class=\"arrowsvgClass\" /> \n");
                }


                placeSvgClass("<polyline points=\""+
                        x1+","+y1+
                        " "+x3+","+y3+
                        " "+x2+","+y2+
                        " \" svgClass /> \n");




                if(params.containsKey("drawLength") && "true".equals(params.get("drawLength")) )
                {
                    double fs = (20*cellSize/50);
                    double tx = ((xPos*cellSize+ofsX)+llXo)/2-(fs/2)*0.6;
                    double ty = ((yPos*cellSize+ofsY)+llYo)/2+fs*0.35;

                    double len = Math.abs((xPos*cellSize+ofsX)-llXo);
                    len = Math.max(Math.abs((yPos*cellSize+ofsY)-llYo),len);
                    len /= cellSize;
                    int lenInt = (int)Math.round(len-0.1);

                    if(lenInt>1)
                    {
                        svgSecond+=
                                ("<text "+
                                        "x=\""+tx+"\" "+
                                        "y=\""+ty+"\" "+
                                        "font-family=\"Verdana\" font-size=\""+fs+"\" "+
                                        "stroke=\"#FFFFFF\" stroke-width=\"8\" fill=\"#000070\" opacity=\"0.70\" stroke-linejoin=\"round\">"+
                                        lenInt+
                                        "</text> \n");
                        svgSecond+=
                                ("<text "+
                                        "x=\""+tx+"\" "+
                                        "y=\""+ty+"\" "+
                                        "font-family=\"Verdana\" font-size=\""+fs+"\" "+
                                        "stroke=\"#FFFFFF\" stroke-width=\"3\" fill=\"#000070\" opacity=\"0.70\" stroke-linejoin=\"round\">"+
                                        lenInt+
                                        "</text> \n");
                        svgSecond+=
                                ("<text "+
                                        "x=\""+tx+"\" "+
                                        "y=\""+ty+"\" "+
                                        "font-family=\"Verdana\" font-size=\""+fs+"\" "+
                                        "stroke=\"#2A52BE\" stroke-width=\"0\" fill=\"#2A52BE\" opacity=\"1.0\">"+
                                        lenInt+
                                        "</text> \n");
                    }

                }



            }
        }

        llX = x2;
        llY = y2;

        llXo=(xPos*cellSize+ofsX);
        llYo=(yPos*cellSize+ofsY);

    }

    void svgAddCircle(int x1,int y1)
    {

        if(x1+1>maxX) maxX=x1+1;
        if(y1+1>maxY) maxY=y1+1;

        x1*=cellSize;
        y1*=cellSize;

        x1+=ofsX;
        y1+=ofsY;

        double rx = (15.91549431/50*cellSize);
        double ry = rx;

        String transform = "";
        if(randLines)
        {
            rx-=0.5; // *count/5
            ry-=-0.5; // *count/5
            transform = " transform=\"rotate(45 "+(x1+cellSize/2)+" "+(y1+cellSize/2)+")\" ";
        }

        svgPrev+=
                "<ellipse "+
                        "cx=\""+(x1+cellSize/2)+"\" "+
                        "cy=\""+(y1+cellSize/2)+"\" "+
                        "rx=\""+rx+"\" "+
                        "ry=\""+ry+"\" "+
                        transform+
                        "stroke=\"white\" stroke-width=\"6\" fill=\"none\" stroke-linejoin=\"round\" stroke-linecap=\"round\" /> \n";

        placeSvgClass("<ellipse "+
                "cx=\""+(x1+cellSize/2)+"\" "+
                "cy=\""+(y1+cellSize/2)+"\" "+
                "rx=\""+rx+"\" "+
                "ry=\""+ry+"\" "+
                transform+
                "svgClass /> \n");

    }

    void svgAddFlag(int x1,int y1, int count)
    {

        if(x1>maxX) maxX=x1;
        if(y1>maxY) maxY=y1;


        x1*=cellSize;
        y1*=cellSize;

        x1+=ofsX;
        y1+=ofsY;

        svgSecond+=
                "<polyline points=\""+
                        (x1+0*cellSize/50)+","+(y1)+" "+
                        (x1+0*cellSize/50)+","+(y1-42*cellSize/50)+" "+
                        (x1+0*cellSize/50)+","+(y1-40*cellSize/50)+" "+
                        (x1+35*cellSize/50)+","+(y1-(40)*cellSize/50)+" "+
                        (x1+20*cellSize/50)+","+(y1-(40-15)*cellSize/50)+" "+
                        (x1+35*cellSize/50)+","+(y1-(40-15*2)*cellSize/50)+" "+
                        (x1+0*cellSize/50)+","+(y1-(40-15*2)*cellSize/50)+" "+
                        "Z\" stroke=\"white\" stroke-width=\"6\" fill=\"white\" stroke-linejoin=\"round\" stroke-linecap=\"round\" /> \n";



        svgSecond+=
                "<polyline points=\""+
                        (x1+0*cellSize/50)+","+(y1-(40-15*2)*cellSize/50)+" "+
                        (x1+0*cellSize/50)+","+(y1-40*cellSize/50)+" "+
                        (x1+35*cellSize/50)+","+(y1-(40)*cellSize/50)+" "+
                        (x1+20*cellSize/50)+","+(y1-(40-15)*cellSize/50)+" "+
                        (x1+35*cellSize/50)+","+(y1-(40-15*2)*cellSize/50)+" "+
                        (x1+0*cellSize/50)+","+(y1-(40-15*2)*cellSize/50)+" "+
                        "\" fill=\"#3b83b3\" stroke=\"none\"/> \n";

        svgSecond+=
                "<polyline points=\""+
                        (x1+0*cellSize/50)+","+(y1-(40-15*2)*cellSize/50)+" "+
                        (x1+0*cellSize/50)+","+(y1-40*cellSize/50)+" "+
                        (x1+35*cellSize/50)+","+(y1-(40)*cellSize/50)+" "+
                        (x1+20*cellSize/50)+","+(y1-(40-15)*cellSize/50)+" "+
                        (x1+5*cellSize/50)+","+(y1-(40-15*2)*cellSize/50)+" "+
                        (x1+0*cellSize/50)+","+(y1-(40-15*2)*cellSize/50)+" "+
                        "\" fill=\"#3392d6\" stroke=\"none\"/> \n";

        svgSecond+=
                ("<polyline points=\""+
                        (x1+0*cellSize/50)+","+(y1)+" "+
                        (x1+0*cellSize/50)+","+(y1-42*cellSize/50)+" "+
                        "\" stroke=\"#808080\" fill=\"none\" stroke-width=\"4\" stroke-linecap=\"round\" /> \n");

        svgSecond+=
                ("<polyline points=\""+
                        (x1+0*cellSize/50)+","+(y1-10*cellSize/50)+" "+
                        (x1+0*cellSize/50)+","+(y1-40*cellSize/50)+" "+
                        "\" stroke=\"#3392d6\" fill=\"none\" stroke-width=\"4\" /> \n");


        svgSecond+=
                ("<text "+
                        "x=\""+(x1+(0+2.5)*cellSize/50)+"\" "+
                        "y=\""+(y1-18.5*cellSize/50)+"\" "+
                        "font-family=\"Verdana\" font-size=\""+(18*cellSize/50)+"\" "+
                        "fill=\"white\" stroke=\"lightgary\" "+
                        "stroke-width=\"0.3\">"+
                        count+
                        "</text> \n");

    }


    public void left()
    {
        prepareLine();
        xPos--;
        finishLine();
        checkSize(ID_COMMAND_LEFT);
    }

    public void right()
    {
        prepareLine();
        xPos++;
        finishLine();
        checkSize(ID_COMMAND_RIGHT);
    }

    public void down()
    {
        prepareLine();
        yPos++;
        finishLine();
        checkSize(ID_COMMAND_DOWN);
    }

    public void up()
    {
        prepareLine();
        yPos--;
        finishLine();
        checkSize(ID_COMMAND_UP);
    }

    public void leftUp()
    {
        prepareLine();
        xPos--;
        yPos--;
        finishLine();
        checkSize(ID_COMMAND_LEFTUP);
    }

    public void upLeft(){ leftUp(); };

    public void leftDown()
    {
        prepareLine();
        xPos--;
        yPos++;
        finishLine();
        checkSize(ID_COMMAND_LEFTDOWN);
    }

    public void downLeft(){ leftDown(); };

    public void rightUp()
    {
        prepareLine();
        yPos--;
        xPos++;
        finishLine();
        checkSize(ID_COMMAND_RIGHTUP);
    }

    public void upRight(){ rightUp(); };

    public void rightDown()
    {
        prepareLine();
        yPos++;
        xPos++;
        finishLine();
        checkSize(ID_COMMAND_RIGHTDOWN);
    }

    public void downRight(){ rightDown(); };


    public void lefts(int count)
    {
        for(int i=0;i<count;i++) left();
        finishLongLine(true, count);

    }

    public void rights(int count)
    {
        for(int i=0;i<count;i++) right();
        finishLongLine(true, count);

    }

    public void ups(int count)
    {
        for(int i=0;i<count;i++) up();
        finishLongLine(true, count);

    }

    public void downs(int count)
    {
        for(int i=0;i<count;i++) down();
        finishLongLine(true, count);

    }

    public void leftUps(int count)
    {
        for(int i=0;i<count;i++) leftUp();
        finishLongLine(true, count);

    }

    public void leftDowns(int count)
    {
        for(int i=0;i<count;i++) leftDown();
        finishLongLine(true, count);

    }

    public void rightUps(int count)
    {
        for(int i=0;i<count;i++) rightUp();
        finishLongLine(true, count);

    }

    public void rightDowns(int count)
    {
        for(int i=0;i<count;i++) rightDown();
        finishLongLine(true, count);

    }

    public void jumps(int count)
    {
        for(int i=0;i<count;i++) jump();
        finishLongLine(false, count);
    }

    public void jumpDowns(int count)
    {
        for(int i=0;i<count;i++) jumpDown();
        finishLongLine(false, count);

    }


    public void turttle()
    {
        synchronized(this)
        {
            isWorm = false;
        }

    }

    public void worm()
    {
        synchronized(this)
        {
            isWorm = true;
        }

    }

    public void circle()
    {

        prepareLine();
        finishLine();

        int a1 = prevXpos*100+prevYpos;
        int a2 = xPos*100+yPos;

        svgAddCircle(xPos,yPos);

        crcO = crcO + (a1+7)*(a2+7)+a1+a2+1;

        checkSize(ID_COMMAND_CIRCLE);
    }

    public void flag()
    {

        prepareLine();
        finishLine();
        FlagCount = FlagCount+1;

        int a1 = xPos*100+yPos;

        svgAddFlag(xPos,yPos,FlagCount);

        crcTurttle = crcTurttle + (a1+13)*FlagCount;
        crcWorm = crcWorm + (a1+13)*FlagCount;
        crc2 = crc2 + a1*FlagCount;

        checkSize(ID_COMMAND_FLAG);
    }

    public void jump()
    {

        synchronized(this)
        {
            boolean save = isWorm;
            prepareLine();
            finishLine();
            isWorm = save;
        }
        xPos++;
        checkSize(ID_COMMAND_JUMP);
    }

    public void jumpDown()
    {

        synchronized(this)
        {
            boolean save = isWorm;
            prepareLine();
            finishLine();
            isWorm = save;
        }
        yPos++;
        checkSize(ID_COMMAND_JUMPDOWN);
    }


    public void bar()
    {

    }

    public void subA(int count)
    {
        for(int i=0;i<count;i++) subA();

    }
    public void subB(int count)
    {
        for(int i=0;i<count;i++) subB();

    }
    public void subC(int count)
    {
        for(int i=0;i<count;i++) subC();

    }
    public void subA()
    {

    }
    public void subB()
    {

    }
    public void subC()
    {

    }



    public void jumpZero()
    {

        synchronized(this)
        {
            boolean save = isWorm;
            prepareLine();
            finishLine();
            isWorm = save;
        }
        xPos=0;
        yPos=0;
        checkSize(ID_COMMAND_JUMP);
    }

    private void prepareLine()
    {
        prevXpos = xPos;
        prevYpos = yPos;
    }

    private void finishLine()
    {
        int a1 = prevXpos*100+prevYpos;
        int a2 = xPos*100+yPos;

        if(a2<a1)
        {
            int i=a1;
            a1=a2;
            a2=i;
        }

        if(isWorm)
        {
            String s = ""+Integer.toString(a1)+"-"+Integer.toString(a2);
            if(a1!=a2 && !points.contains(s))
            {
                points.add(s);

                svgAddLine(prevXpos,prevYpos,xPos,yPos);

                crcWorm = crcWorm + (a1+7)*(a2+7)+a1+a2;
                crc2 =  crc2 + a1 + a2;
            }
        }



    }

    private void checkSize(byte type)
    {
        prevXpos = xPos;
        prevYpos = yPos;
    }

    public int getCRC()
    {
        int i = ((crcTurttle+crcWorm) % 90000) + 10000;

        int j= ((i+crcO) % 9)+1;

        if(crcO==0) j = i % 10;

        i = (i / 10)*10 + j;

        return i;

    }

    int getCRC2()
    {
        return ((crcTurttle+crcWorm+crcO + crc2) % 10000000+47)*31;
    }


    private String repeatString(String s, int count)
    {
        String r = "";
        while (count-- > 0)
        {
            r+=s;
        }
        return r;
    }

    public void draw(String s)
    {


        if((params.containsKey("longLines") && "false".equals(params.get("longLines")) )
                ||(params.containsKey("longLines") && "true".equals(params.get("longLines")) ))

            for(int i=9;i>1;i--)
            {
                s = s.replaceAll("R"+i,repeatString("R", i));
                s = s.replaceAll("D"+i,repeatString("D", i));
                s = s.replaceAll("L"+i,repeatString("L", i));
                s = s.replaceAll("U"+i,repeatString("U", i));
                s = s.replaceAll( "\\(RD\\)"+i,repeatString("(RD)", i));
                s = s.replaceAll( "\\(RU\\)"+i,repeatString("(RU)", i));
                s = s.replaceAll( "\\(LD\\)"+i,repeatString("(LD)", i));
                s = s.replaceAll( "\\(LU\\)"+i,repeatString("(LU)", i));
                s = s.replaceAll( "J"+i,repeatString("J", i));
                s = s.replaceAll( "\\(JD\\)"+i,repeatString("(JD)", i));
            }

        if(params.containsKey("longLines") && "true".equals(params.get("longLines")) )
            for(int i=9;i>1;i--)
            {
                s = s.replaceAll(repeatString("R", i), "R"+i);
                s = s.replaceAll(repeatString("D", i), "D"+i);
                s = s.replaceAll(repeatString("L", i), "L"+i);
                s = s.replaceAll(repeatString("U", i), "U"+i);
                s = s.replaceAll(repeatString("\\(RD\\)", i), "(RD)"+i);
                s = s.replaceAll(repeatString("\\(RU\\)", i), "(RU)"+i);
                s = s.replaceAll(repeatString("\\(LD\\)", i), "(LD)"+i);
                s = s.replaceAll(repeatString("\\(LU\\)", i), "(LU)"+i);
                s = s.replaceAll(repeatString("J", i), "J"+i);
                s = s.replaceAll(repeatString("\\(JD\\)", i), "(JD)"+i);
            }

        if(params.containsKey("longLines") && "false".equals(params.get("longLines")) )
            for(int i=9;i>1;i--)
            {
                s = s.replaceAll("R"+i,repeatString("R", i));
                s = s.replaceAll("D"+i,repeatString("D", i));
                s = s.replaceAll("L"+i,repeatString("L", i));
                s = s.replaceAll("U"+i,repeatString("U", i));
                s = s.replaceAll( "\\(RD\\)"+i,repeatString("(RD)", i));
                s = s.replaceAll( "\\(RU\\)"+i,repeatString("(RU)", i));
                s = s.replaceAll( "\\(LD\\)"+i,repeatString("(LD)", i));
                s = s.replaceAll( "\\(LU\\)"+i,repeatString("(LU)", i));
                s = s.replaceAll( "J"+i,repeatString("J", i));
                s = s.replaceAll( "\\(JD\\)"+i,repeatString("(JD)", i));
            }

        String d = "";
        while(s.length()>0)
        {

            int count = 1;
            d=""+s.charAt(0);

            shadowElement = false;
            if("?".equals(d))
            {
                shadowElement = true;
                s=s.substring(1);
                d=""+s.charAt(0);

            }

            if((s.charAt(0)=='(') && (s.length()>3) && (s.charAt(3)==')'))
            {
                d =  ""+s.charAt(1)+s.charAt(2);
                s=s.substring(4);
            }
            else
            if((s.charAt(0)=='(') && (s.length()>4) && (s.charAt(3)=='s') && (s.charAt(4)==')'))
            {
                d =  ""+s.charAt(1)+s.charAt(2);
                s=s.substring(5);
            }
            else
            {
                s=s.substring(1);
            }
            if((s.length()>0) && (s.charAt(0)>='0') && (s.charAt(0)<='9'))
            {
                count = 0;
                while((s.length()>0) && (s.charAt(0)>='0') && (s.charAt(0)<='9'))
                {
                    count += count*10+(char)s.charAt(0)-(char)'0';
                    s=s.substring(1);
                }
            }


            if("A".equals(d)) subA(count);
            if("B".equals(d)) subB(count);
            if("C".equals(d)) subC(count);


            if("O".equals(d)) circle();
            if("F".equals(d)) flag();
            if("J".equals(d)) jumps(count);
            if("R".equals(d)) rights(count);
            if("L".equals(d)) lefts(count);
            if("U".equals(d)) ups(count);
            if("D".equals(d)) downs(count);
            if("RU".equals(d)) rightUps(count);
            if("RD".equals(d)) rightDowns(count);
            if("LU".equals(d)) leftUps(count);
            if("LD".equals(d)) leftDowns(count);
            if("UR".equals(d)) rightUps(count);
            if("DR".equals(d)) rightDowns(count);
            if("UL".equals(d)) leftUps(count);
            if("DL".equals(d)) leftDowns(count);
            if("W".equals(d)) worm();
            if("T".equals(d)) turttle();
            if("P".equals(d)) bar();
            if("B".equals(d)) bar();
            if("JD".equals(d)) jumpDowns(count);
            if("JZ".equals(d)) jumpZero();
        }


        // for draw lat point
        if(llXo!=startPointX || llYo!=startPointY)
            finishLongLine(true, 0);

    }



    void draw(byte[] s)
    {
        for(int i=0;i<s.length;i++)
            switch (s[i])
            {
                case ID_COMMAND_CIRCLE: circle(); break;
                case ID_COMMAND_FLAG: flag(); break;
                case ID_COMMAND_POINT: break;
                case ID_COMMAND_JUMP: jump(); break;
                case ID_COMMAND_RIGHT: right(); break;
                case ID_COMMAND_LEFT: left(); break;
                case ID_COMMAND_UP: up(); break;
                case ID_COMMAND_DOWN: down(); break;
                case ID_COMMAND_RIGHTUP: rightUp(); break;
                case ID_COMMAND_RIGHTDOWN: rightDown(); break;
                case ID_COMMAND_LEFTUP: leftUp(); break;
                case ID_COMMAND_LEFTDOWN: leftDown(); break;
                case ID_COMMAND_WORM: worm(); break;
                case ID_COMMAND_TURTTLE: turttle(); break;
                case ID_COMMAND_BAR: bar(); break;
                case ID_COMMAND_JUMPDOWN: jumpDown(); break;
                case ID_COMMAND_JUMPZERO: jumpZero(); break;

            }
    }

}
