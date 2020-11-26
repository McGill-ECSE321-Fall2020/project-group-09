package ca.mcgill.ecse321.artgallerysystem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;
public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    List<String> description;
    private ImageView[] images;
    LayoutInflater mLayoutInfalter;

    public ViewPagerAdapter(Context context, List<String> description){
        this.context=context;
        this.description=description;
        mLayoutInfalter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < description.size(); i++) {
            Glide.with(ViewPagerAdapter.this.context).asBitmap().load(description.get(i)).into(images[i]);
        }

    }
    @Override
    public int getCount(){
        return images.length;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view,@NonNull Object object){
        return view == ((LinearLayout)object);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position){
        //todo:Set position here
        //View itemView = mLayoutInfalter.inflate(R.layout.item,container,false);
        //ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);
        images[position].setScaleType(ImageView.ScaleType.FIT_CENTER);
        images[position] = new ImageView(ViewPagerAdapter.this.context);
        container.addView(images[position],position);
        return images[position];
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((ImageView)object);
    }
    }
