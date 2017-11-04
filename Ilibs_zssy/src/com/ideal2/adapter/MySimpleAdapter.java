package com.ideal2.adapter;



import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ideal2.demo.R;
import com.ideal2.util.ReflectUtil;

public class MySimpleAdapter extends BaseAdapter implements Filterable {
    private int[] mTo;
    private String[] mFrom;
    private ViewBinder mViewBinder;

    private List mData;

    private int mResource;
    private int mDropDownResource;
    private LayoutInflater mInflater;

    private SimpleFilter mFilter;
    private ArrayList mUnfilteredData;
    
    private Context mContext;
    private OnLongClickListener onLongClickListener;
    private OnClickListener onClickListener;
    
    private ListView listView;
     
    public ListView getListView() {
		return listView;
	}


	public void setListView(ListView listView) {
		this.listView = listView;
	}


	public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
		this.onLongClickListener = onLongClickListener;
	}


	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	public Context getContext() {
		return mContext;
	}



    public MySimpleAdapter(Context context, List data,
            int resource, String[] from, int[] to) {
        mData = data;
        mResource = mDropDownResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

 
    public int getCount() {
        return mData.size();
    }

   
    public Object getItem(int position) {
        return mData.get(position);
    }

   
    public long getItemId(int position) {
        return position;
    }

   
    public View getView(int position, View convertView, ViewGroup parent) {
    	Log.d("getView", "position:"+position);
        return createViewFromResource(position, convertView, parent, mResource);
    }
   
	private int position;
	private Builder dialog;
	private View view;
	private boolean isOnClick = true;
	private Handler onLongHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isOnClick = false;
			dialog = new AlertDialog.Builder(getContext());
			if (null != onLongClickListener) {
				onLongClickListener.onLongClick(view, mData.get(position), mTo);
			} else {
				String str = "";
				for (int i = 0; i < mTo.length; i++) {
					View v = view.findViewById(mTo[i]);
					if (v instanceof TextView) {
						TextView txt = (TextView) v;
						str = str + txt.getText() + "\n";
					}
				}
				dialog.setMessage(str);
				dialog.setNegativeButton("确认", null);
				dialog.create().show();
			}
//			Log.d("asdasd", "asdsa");
		}

	};
    
    private View createViewFromResource(int position, View convertView,
            ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resource, parent, false);
            
          
        } else {
            v = convertView;
        }
        v.setClickable(true);
        v.setLongClickable(true);
      /*  final Builder dialog = new AlertDialog.Builder(getContext());
        v.setOnLongClickListener(new View.OnLongClickListener() {
			int position;
			public View.OnLongClickListener setPosition(int p){
				this.position=p;
				return this;
			}
			@Override
			public boolean onLongClick(View v) {
				
				if(null!=onLongClickListener){
					onLongClickListener.onLongClick(v,mData.get(position),mTo);
				}else{
					String str = "";
					for(int i =0;i<mTo.length;i++){
		            	View view = v.findViewById(mTo[i]);
		            	if(view instanceof TextView){
		            		 TextView txt = (TextView)view;
		            		str = str+txt.getText()+"\n";
		            	}
					}
					dialog.setMessage(str);
 					dialog.setNegativeButton("确认", null);
 					dialog.create().show();
				}
				return false;
			}
		}.setPosition(position));
        v.setOnClickListener(new View.OnClickListener() {
        	int position;
			public View.OnClickListener setPosition(int p){
				this.position=p;
				return this;
			}
			@Override
			public void onClick(View v) {
				if(null!=onClickListener){
					onClickListener.onClick(v,mData.get(position),mTo);
				}
			}
		}.setPosition(position));*/
       
		
        v.setOnTouchListener(new View.OnTouchListener() {
        	int pos;
			public View.OnTouchListener setPosition(int p){
				this.pos=p;
				return this;
			}
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MySimpleAdapter.this.position = pos;
				MySimpleAdapter.this.view = v;
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					onLongHandler.sendEmptyMessageDelayed(0, 1000);
//					#00c0ff
					
					v.setBackgroundResource(R.color.btn_down);
					break;
				case MotionEvent.ACTION_UP:
					onLongHandler.removeMessages(0);
					if(null!=onClickListener&&isOnClick){
						onClickListener.onClick(v,mData.get(pos),mTo);
					}
					isOnClick = true;
					v.setBackgroundResource(R.color.transparent);
					break;
				case MotionEvent.ACTION_CANCEL:
					isOnClick = true;
					onLongHandler.removeMessages(0);
					v.setBackgroundResource(R.color.transparent);
					break;
				}
				return false;
			}
		}.setPosition(position));
        try {
			bindView(position, v);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
//        try {
//			if(null!=v.findViewById(R.id.image)){
//				ImageView image = (ImageView)v.findViewById(R.id.image);
//				String strSex = ReflectUtil.getter(this.mData.get(position), sexName)+"";
//				strSex = strSex.trim();
//				if("男".equals(strSex)){
//					image.setBackgroundResource(R.drawable.m_tx);
//				}else{
//					image.setBackgroundResource(R.drawable.w_tx);
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        return v;
    }
    
    private String sexName;
    
    public String getSexName() {
		return sexName;
	}


	public void setSexName(String sexName) {
		this.sexName = sexName;
	}


	public interface OnClickListener{
    	public void onClick(View v,Object obj,int[] mTo);
    }
    
    public interface OnLongClickListener{
    	public boolean onLongClick(View v,Object obj,int[] mTo);
    }

    public void setDropDownViewResource(int resource) {
        this.mDropDownResource = resource;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mDropDownResource);
    }

    private void bindView(int position, View view) {
        final Object dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }

        final ViewBinder binder = mViewBinder;
        final String[] from = mFrom;
        final int[] to = mTo;
        final int count = to.length;

        for (int i = 0; i < count; i++) {
            final View v = view.findViewById(to[i]);
            if (v != null) {
//                final Object data = dataSet.get(from[i]);
            	String fromVal = from[i];
            	fromVal = fromVal.substring(0, 1).toUpperCase()+fromVal.substring(1);
            	final Object data = getter(dataSet,fromVal);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }

                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, data, text);
                }

                if (!bound) {
                    if (v instanceof Checkable) {
                        if (data instanceof Boolean) {
                            ((Checkable) v).setChecked((Boolean) data);
                        } else if (v instanceof TextView) {
                            // Note: keep the instanceof TextView check at the bottom of these
                            // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                            setViewText((TextView) v, text);
                        } else {
                            throw new IllegalStateException(v.getClass().getName() +
                                    " should be bound to a Boolean, not a " +
                                    (data == null ? "<unknown type>" : data.getClass()));
                        }
                    } else if (v instanceof TextView) {
                        // Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        setViewText((TextView) v, text);
                    } else if (v instanceof ImageView) {
                        if (data instanceof Integer) {
                            setViewImage((ImageView) v, (Integer) data);                            
                        } else {
                            setViewImage((ImageView) v, text);
                        }
                    } else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                " view that can be bounds by this MySimpleAdapter");
                    }
                }
            }
        }
    }
    
    public static Object getter(Object obj, String att) {
		try {
			Method method = obj.getClass().getMethod("get" + att);
			return method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

   
    public ViewBinder getViewBinder() {
        return mViewBinder;
    }

   
    public void setViewBinder(ViewBinder viewBinder) {
        mViewBinder = viewBinder;
    }

    
    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }

  
    public void setViewImage(ImageView v, String value) {
        try {
            v.setImageResource(Integer.parseInt(value));
        } catch (NumberFormatException nfe) {
            v.setImageURI(Uri.parse(value));
        }
    }

   
    public void setViewText(TextView v, String text) {
        v.setText(text);
    }

    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new SimpleFilter();
        }
        return mFilter;
    }

    
    public static interface ViewBinder {
      
        boolean setViewValue(View view, Object data, String textRepresentation);
    }

    
    private class SimpleFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList(mData);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList newValues = new ArrayList(count);

                for (int i = 0; i < count; i++) {
                    Object h = unfilteredValues.get(i);
                    if (h != null) {
                        
                        int len = mTo.length;

                        for (int j=0; j<len; j++) {
                        	String fromVal = mFrom[j];
                        	fromVal = fromVal.substring(0, 1).toUpperCase()+fromVal.substring(1);
                        	final Object data = getter(h,fromVal);
                            String str =  (String)data;
                            
                            String[] words = str.split(" ");
                            int wordCount = words.length;
                            
                            for (int k = 0; k < wordCount; k++) {
                                String word = words[k];
                                
                                if (word.toLowerCase().startsWith(prefixString)) {
                                    newValues.add(h);
                                    break;
                                }
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            mData = (List) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

	public List getmData() {
		return mData;
	}


	public void setmData(List mData) {
		this.mData = mData;
	}
    
}
