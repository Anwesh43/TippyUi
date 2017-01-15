package com.anwesome.ui.tippyui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 15/01/17.
 */
public class TippyUi extends ImageView {
    private boolean shown = false;
    private int deviceWidth=800;
    private Activity activity;
    private List<MessageBody> messages = new ArrayList<>();
    private Paint messagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private MessageView messageView;
    private int heightOfMessageView;
    private final int font = 50;
    private float prevX = 0,prevY = 0;
    public int getUiColor() {
        return uiColor;
    }

    public void setUiColor(int uiColor) {
        this.uiColor = uiColor;
    }
    public void setMessageText(String text) {
        messagePaint.setTextSize(font);
        String tokens[] = text.split(" ");
        int textMaxWidth = (deviceWidth*9)/10;
        String msg="";
        int x = deviceWidth/20,y = font;
        for(String token:tokens) {
            if(messagePaint.measureText(msg+token)>(9*deviceWidth)/10) {
                messages.add(new MessageBody(msg,x,y));
                y +=font;
                msg = token+" ";
            }
            else {
                msg = msg+token+" ";
            }
        }

        messages.add(new MessageBody(msg,x,y));
        heightOfMessageView = y+=font*3;
        messageView = new MessageView(activity);
        int messageWidth = (messages.size() == 1)?(int)(deviceWidth/5+(messagePaint.measureText(msg))):deviceWidth;
        activity.addContentView(messageView,new ViewGroup.LayoutParams(messageWidth,heightOfMessageView));
        messageView.setX(deviceWidth/10);
        messageView.setY(getY()+font);
        messageView.setVisibility(INVISIBLE);

    }
    private int uiColor = Color.parseColor("#D50000");
    public TippyUi(Context context, AttributeSet attrs) {
        super(context,attrs);
        setImageDrawable(new PlusDrawable());
        activity = (Activity)context;
        initDeviceWidth();
    }
    public TippyUi(Context context) {
        super(context);
        setImageDrawable(new PlusDrawable());
        activity = (Activity)context;
        initDeviceWidth();
    }
    public void initDeviceWidth() {
        DisplayManager displayManager = (DisplayManager)activity.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        if(display!=null) {
            Point size = new Point();
            display.getRealSize(size);
            deviceWidth = (size.x*8)/10;

        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(shown) {
                setRotation(0);
                shown = false;
                messageView.setVisibility(INVISIBLE);
                setX(prevX);
                setY(prevY);
            }
            else {
                prevX = getX();
                prevY = getY();
                setX(messageView.getX()+messageView.getMeasuredWidth()-getMeasuredWidth()/2);
                setY(messageView.getY()+heightOfMessageView);
                setRotation(45);
                shown = true;
                messageView.setVisibility(VISIBLE);
            }
        }
        return true;
    }
    private class CircleDrawable extends Drawable {
        protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        protected int w,h;
        public void draw(Canvas canvas) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            int r = w/2;
            if(h<w) {
                r = h/2;
            }
            paint.setColor(uiColor);
            canvas.drawCircle(w/2,h/2,r,paint);

        }
        public int getOpacity() {
            return 0;
        }
        public void setAlpha(int alpha) {

        }
        public void setColorFilter(ColorFilter colorFilter) {

        }
    }
    private class PlusDrawable extends CircleDrawable {
        public void draw(Canvas canvas) {
            super.draw(canvas);
            drawPlusPath(canvas);
        }
        private void drawPlusPath(Canvas canvas) {
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(10);
            canvas.drawLine(w/2,h/10,w/2,9*h/10,paint);
            canvas.drawLine(w/10,h/2,9*w/10,h/2,paint);
        }
    }
    private class MessageDrawable extends Drawable {
        public void draw(Canvas canvas) {
            messagePaint.setColor(Color.parseColor("#303F9F"));
            int width = canvas.getWidth(),height = canvas.getHeight();
            canvas.drawRect(new RectF(0,0,width,(height*8/10)),messagePaint);
            Path path = new Path();
            path.moveTo(8*width/10,8*height/10);
            path.lineTo(width,height);
            path.lineTo(width,8*height/10);
            canvas.drawPath(path,messagePaint);
            messagePaint.setColor(Color.WHITE);
            for(MessageBody messageBody:messages) {
                canvas.drawText(messageBody.getText(),messageBody.getX(),messageBody.getY(),messagePaint);
            }
        }
        public int getOpacity() {
            return 0;
        }
        public void setAlpha(int alpha) {
            messagePaint.setAlpha(alpha);
        }
        public void setColorFilter(ColorFilter colorFilter) {
            messagePaint.setColorFilter(colorFilter);
        }
    }

    private class MessageView extends ImageView {
        public MessageView(Context context) {
            super(context);
            setImageDrawable(new MessageDrawable());
        }
    }
    private class MessageBody {
        private String text;
        private float x,y;
        public MessageBody(String text,float x,float y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }
        public int hashCode() {
            return text.hashCode()+(int)x+(int)y;
        }
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
