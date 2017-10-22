package custom.clock.library.page;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PagePagerAdapter extends PagerAdapter{
    List<Page> pages;

    public PagePagerAdapter(){
        pages = new ArrayList<>();
    }

    public PagePagerAdapter(List<Page> pages){
        this.pages = pages;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        Page page = (Page)object;

        container.removeView(page);
    }

    @Override
    public int getCount() {
        return  pages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Page page = pages.get(position);

        container.addView(page);

        return page;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }

    public void add(int index, Page page){
        pages.add(index, page);
    }

    public void add(Page page){
        pages.add(page);
    }

    public Page get(int index){
        return pages.get(index);
    }

    public boolean remove(Page page){
        return pages.remove(page);
    }

    public Page remove(int index){
        return pages.remove(index);
    }

    public void set(int index, Page page) {
        set(index,page);
    }
}
