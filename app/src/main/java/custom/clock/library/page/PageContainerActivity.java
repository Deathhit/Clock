package custom.clock.library.page;

import android.support.v7.app.AppCompatActivity;

/***An activity class that supports the functions of PageContainer. Set up container by invoking setContainer() to support page switching onBackPressed().***/
public abstract class PageContainerActivity extends AppCompatActivity {
    protected static PageContainer container;

    @Override
    public void onBackPressed() {
        if(container == null || container.goToPrevious() == null)
            super.onBackPressed();
    }

    /***Set currentContainer to input container. Returns previous container.***/
    public static PageContainer setContainer(PageContainer container){
        PageContainerActivity.container = container;

        return container;
    }

    /***Get current front page.***/
    public static Page current(){return container.current();}

    /***Make container go back to target page.***/
    public static Page goBackTo(Page page){
        return container.goBackTo(page);
    }

    /***Make container go back to root.***/
    public static Page goBackToRoot(){return container.goBackToRoot();}

    /***Make container go to target page.***/
    public static Page goToNext(Page page){
        return container.goToNext(page);
    }

    /***Make container go to previous page.***/
    public static Page goToPrevious(){
        return container.goToPrevious();
    }

    /***Make container replace current page to target page.***/
    public static Page replace(Page page){
        return container.replace(page);
    }

    /***Return number of pages in container.***/
    public static int size(){return container.size();}
}
