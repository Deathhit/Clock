package custom.clock.library.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import custom.clock.library.page.Page;

public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter{
    List<View> views;

    public ViewPagerAdapter(){
        views = new ArrayList<>();
    }

    public ViewPagerAdapter(List<View> views){
        this.views = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        Page page = (Page)object;

        container.removeView(page);
    }

    @Override
    public int getCount() {
        return  views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);

        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }

    public void add(int index, View view){
        views.add(index, view);
    }

    public void add(View page){
        views.add(page);
    }

    public View get(int index){
        return views.get(index);
    }

    public boolean remove(View view){
        return views.remove(view);
    }

    public View remove(int index){
        return views.remove(index);
    }

    public void set(int index, View view) {
        set(index,view);
    }
}
