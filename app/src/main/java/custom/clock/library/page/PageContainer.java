package custom.clock.library.page;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Stack;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/***A single page container that can only show one page at one time. It keeps track of each page in stack, and is able to support page switching.
 * MotionEvent is intercepted and passed down to child by default.***/
public class PageContainer extends FrameLayout{
    private static final ViewGroup.LayoutParams LAYOUT_PARAMS = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

    private Stack<Page> stack = new Stack<>();

    public PageContainer(Context context){
        super(context);
    }

    public PageContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PageContainer(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    /***Multi views are not allowed.***/
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        try{
            goToNext((Page)child);
        }catch (Exception e){
            Page page = new Page(getContext());

            page.addView(child,index,params);

            goToNext(page);
        }
    }

    /***Returns current page. ***/
    public Page current(){
        return (Page)(super.getChildAt(0));
    }

    /***Move to input page if it is in stack. Returns replaced page.***/
    public Page goBackTo(Page page){
        if(super.getChildAt(0) == page)
            return null;
        else if(stack.contains(page)){
            Page currentPage = (Page)(this.getChildAt(0));

            super.removeView(currentPage);
            super.addView(page,0,LAYOUT_PARAMS);

            while(stack.pop() != page){}

            return currentPage;
        }else
            return null;
    }

    /***Move to root page. Returns replaced page.***/
    public Page goBackToRoot(){
        if(stack.size()>0)
            return goBackTo(stack.get(0));
        else
            return null;
    }

    /***Add input page to stack and move to it . Returns replaced page.***/
    public Page goToNext(Page page){
        //If there is at least one page at front.
        if(super.getChildCount() > 0) {
            Page currentPage = (Page)(super.getChildAt(0));

            next(page);

            return currentPage;
        }else{
            first(page);

            return null;
        }
    }

    /***Move to previous page. Returns replaced page.***/
    public Page goToPrevious(){
        //If there is a previous page.
        if(stack.size() > 0) {
            Page currentPage = (Page)super.getChildAt(0);

            previous();

            return currentPage;
        }else
            return null;
    }

    /***Return number of pages. Null is considered one.***/
    public int size(){return stack.size()+1;}

    /***Switch to previous page.***/
    private void previous(){
        super.removeViewAt(0);

        Page page = stack.pop();

        super.addView(page,0,LAYOUT_PARAMS);
    }

    /***Replace current page with input page. Returns replaced page.***/
    public Page replace(Page page){
        //If there is at least one page in front.
        if(super.getChildCount() > 0) {
            Page currentPage = (Page)(super.getChildAt(0));

            super.removeViewAt(0);
            super.addView(page,0,LAYOUT_PARAMS);

            return currentPage;
        }else{
            first(page);

            return null;
        }
    }

    /***Switch to next page.***/
    private void next(Page page){
        Page currentPage  = (Page)(super.getChildAt(0));

        stack.push(currentPage);

        super.removeViewAt(0);
        super.addView(page,0,LAYOUT_PARAMS);
    }

    /***Add first page.***/
    private void first(Page page){
        super.addView(page,0,LAYOUT_PARAMS);
    }
}
