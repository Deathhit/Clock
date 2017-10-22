package custom.clock.library.page;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class Page extends FrameLayout{
    public static final int DEFAULT_ID = -1;

    protected int id = DEFAULT_ID;

    public Page(Context context){
        super(context,null);
        init();
    }

    public Page(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Page(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onAttachedToWindow (){
        super.onAttachedToWindow();
        onShow();
    }


    @Override
    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        onHide();
    }

    /***Initialize the page here. Includes inflating, referencing, and page-registering. Use View.inflate() to inflate page by XML layout. Use findViewById to reference child view of page.
     * Use pageContainer.registerPage() to register page if there is page contained by pageContainer in XML layout.***/
    protected void init(){}

    /***This is invoked after bringing page to the front. Override to use it.***/
    public void onShow(){}

    /***This is invoked before bringing page to background. Override to use it.***/
    public void onHide(){}
}
