package custom.clock.pages.about;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import custom.clock.R;
import custom.clock.library.page.Page;

public class AboutPage extends Page {
    public AboutPage(Context context) {
        super(context);
    }

    public AboutPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AboutPage(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(){
        View.inflate(getContext(), R.layout.page_about, this);
    }
}
