package kai.kaiprivate.kai.imagegridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import kai.kaiprivate.R;
import kai.kaiprivate.dataforuse.Constants;

/**
 * Created by Administrator on 2015/8/27.
 */
public class ImageGridviewAdapter extends BaseAdapter {

    private static final String[] IMAGE_URLS = Constants.IMAGES;

    private Context context;
    private LayoutInflater inflater;

    BitmapUtils bitmapUtils;

    ImageGridviewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return IMAGE_URLS.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_grid_image, parent, false);
            holder = new ViewHolder();
            assert view != null;
            holder.imageView = (ImageView) view.findViewById(R.id.iv);
            holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        bitmapUtils.display(holder.imageView, IMAGE_URLS[position]);

        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
}


