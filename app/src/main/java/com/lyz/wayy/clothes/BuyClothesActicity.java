package com.lyz.wayy.clothes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lyz.wayy.R;
import com.lyz.wayy.bean.ClothesInfoBean;
import com.lyz.wayy.bean.PkgInfo;
import com.lyz.wayy.horizontalpage.view.HorizontalPageLayoutManager;
import com.lyz.wayy.horizontalpage.view.PagingScrollHelper;
import com.lyz.wayy.main.frame.FragmentPkg;
import com.lyz.wayy.pub.ConstFile;
import com.lyz.wayy.pub.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by helch on 2018/2/23.
 */

public class BuyClothesActicity extends AppCompatActivity implements PagingScrollHelper.onPageChangeListener {
    @BindView(R.id.buydog_gridView)
    RecyclerView recyclerView;
    @BindView(R.id.tab2)
    RadioButton radioButton2;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.frame)
    FrameLayout frame;

    public static final int CHANGE_PKG = 2;
    @BindView(R.id.buydog_back)
    ImageView buydogBack;
    @BindView(R.id.mystar)
    TextView mystar;
    @BindView(R.id.fb)
    TextView fbView;
    @BindView(R.id.left_btn)
    ImageView leftBtn;
    @BindView(R.id.right_btn)
    ImageView rightBtn;
    @BindView(R.id.buydog_main_body)
    RelativeLayout buydogMainBody;
    @BindView(R.id.bottom)
    RelativeLayout bottom;
    @BindView(R.id.radio_group2)
    RadioGroup radioGroup2;
    private FragmentManager fragmentManager = getSupportFragmentManager();


    //    private adapter adapter; //recyclerView的适配器
//    private Handler handler;//传过来的handler
    ArrayList<ClothesInfoBean> dataList = new ArrayList<>();

    int fb;//fb
    int star;//

    PagingScrollHelper scrollHelper = new PagingScrollHelper();

    private AdapterBuyClothes adapter = null;

    int currentPage;
    int totalPage;
    String userJsonStr;//当前用户信息


    ArrayList<ClothesInfoBean> selectDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyclothes);
        ButterKnife.bind(this);
//        radioGroup.setOnCheckedChangeListener(checkedchangelistner);
        Intent intent = getIntent();
        String fbStr = intent.getStringExtra("fb");
        fb = Integer.parseInt(fbStr);
        star = intent.getIntExtra("star", 0);
        mystar.setText(star + "");
        fbView.setText(fb + "");
        userJsonStr=intent.getStringExtra("userJsonStr");

        change2();

        initDogShop();
        scrollHelper.setUpRecycleView(recyclerView);
        scrollHelper.setOnPageChangeListener(this);
//        switchLayout(R.id.rb_horizontal_page);
        recyclerView.setHorizontalScrollBarEnabled(true);
        init();
    }

    private RecyclerView.ItemDecoration lastItemDecoration = null;
    private HorizontalPageLayoutManager horizontalPageLayoutManager = null;
//    private PagingItemDecoration pagingItemDecoration = null;

    private void init() {
        horizontalPageLayoutManager = new HorizontalPageLayoutManager(1, 6);
//        pagingItemDecoration = new PagingItemDecoration(this, horizontalPageLayoutManager);
        if (horizontalPageLayoutManager != null) {
            recyclerView.setLayoutManager(horizontalPageLayoutManager);
            recyclerView.removeItemDecoration(lastItemDecoration);
//            recyclerView.addItemDecoration(pagingItemDecoration);
            scrollHelper.updateLayoutManger();
            scrollHelper.scrollToPosition(0);
//            lastItemDecoration = pagingItemDecoration;
        }

//        radioGroup.bringToFront();
        radioGroup2.setOnCheckedChangeListener(checkedchangelistner);

    }

    private void initDogShop() {

        getDogList();
        adapter = new AdapterBuyClothes(this, selectDataList);
//
//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                BuyDog buyDog=dataList.get(i);
//                showBuyDogDlg(buyDog);
//            }
//        });
        recyclerView.setAdapter(adapter);
        adapter.setOnMyItemClickListener(new AdapterBuyClothes.OnMyItemClickListener() {
            @Override
            public void myClick(ClothesInfoBean boyDog, int pos) {
                showBuyDogDlg(boyDog);
            }
        });

    }


    private void getDogList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=shop&act=getClothesInfo&type=clothes&web_uid=" + ConstFile.uId;
                    String response = example.run(url);
//                    JSONArray arr=new JSONArray(response);
                    dataList = ClothesInfoBean.arrayClothesInfoBeanFromData(response);
                    setSelectDataList("10");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.reSetDatalist(selectDataList);
                            updateData();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //做一下分类
    private void setSelectDataList(String sort) {
        selectDataList.clear();
        for (ClothesInfoBean pp : dataList) {
            if (pp.getSort().equalsIgnoreCase(sort)) {
                selectDataList.add(pp);
            }
        }
    }

    private void updateData() {
        adapter.notifyDataSetChanged();
        //滚动到第一页
        scrollHelper.scrollToPosition(0);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                totalPage = scrollHelper.getPageCount();
                currentPage = 0;
                leftBtn.setVisibility(View.GONE);
                if (totalPage > 0) {
                    rightBtn.setVisibility(View.VISIBLE);
                } else {
                    rightBtn.setVisibility(View.GONE);
                }
//                tv_page_total.setText("共" + scrollHelper.getPageCount() + "页");
            }
        });
    }


    private void change2() {
        radioButton2.setPressed(true);
        radioButton2.setChecked(true);
        radioButton2.setBackgroundResource(R.drawable.i_tool);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FragmentPkg fragment2 = new FragmentPkg();
        fragment2.setHandle(handler);
        fragment2.setContext(this);
        ft.replace(R.id.frame, fragment2);
        ft.commit();
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_PKG://背包
                    Bundle bundle2 = msg.getData();
                    PkgInfo pkg = (PkgInfo) bundle2.getSerializable("pkg");
                    break;
                default:
                    break;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener checkedchangelistner = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {//tab页切换
            switch (checkedId) {
                case R.id.tab2:
                    change2();
                    break;
                case R.id.shangyi:
                    setChg("10",checkedId);
//                    updateData();
                    break;
                case R.id.kuqun:
                    setChg("8",checkedId);
                    break;
                case R.id.nianxin:
                    setChg("14",checkedId);
                    break;
                case R.id.faxin:
                    setChg("19",checkedId);
                    break;
                case R.id.maoz:
                    setChg("20",checkedId);
                    break;
                case R.id.shiping:
                    setChg("17",checkedId);
                    break;
                case R.id.zhuangshi:
                    setChg("23",checkedId);
                    break;
                case R.id.zhuangshi2:
                    setChg("24",checkedId);
                    break;
                case R.id.chibang:
                    setChg("3",checkedId);
                    break;
                default:
                    break;
            }
        }
    };

    private void setChg(String type,int btnId){
//        int id = getResources().getIdentifier("animal" + DogId + "_0" + step, "drawable", getPackageName());

        setSelectDataList(type);
        adapter.reSetDatalist(selectDataList);
        adapter.notifyDataSetChanged();
//        scrollHelper.scrollToPosition(0);
        updateData();
    }

    @OnClick(R.id.buydog_back)
    public void onViewClicked() {
        finish();
    }

    /////////
    private void showBuyDogDlg(final ClothesInfoBean clothes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BuyClothesActicity.this, R.style.AlertDialogStyle);
        final View dlgView = View
                .inflate(this, R.layout.dlg_buyclothes, null);
        builder.setView(dlgView);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        dialog.show();

        ImageView touxiang = dlgView.findViewById(R.id.bugdog_touxiang);

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(userJsonStr);
            String vImgStr = jsonObj.getString("virtualimage");
            String[] vImgArr = Utils.getQQShowData(vImgStr);
            loadQQshowImg(vImgArr, touxiang,clothes);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        TextView title = dlgView.findViewById(R.id.youxiaoqi);
//        title.setText(clothes.getStock());

        TextView desc = dlgView.findViewById(R.id.naijiudu);
        desc.setText(clothes.getEndurance());

        TextView price = dlgView.findViewById(R.id.price);
        String priceStr = clothes.getCost();
        price.setText(priceStr);


        //设置背景透明
//        WindowManager m = getWindowManager();
//        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
//        p.height = Utils.dp2px(300,MainActivity.this);
//        p.width = Utils.dp2px(260,MainActivity.this);
//        dialog.getWindow().setAttributes(p);//设置生效

        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.buydog_cancel);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ImageView okBtn = (ImageView) dialog.findViewById(R.id.buydog_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyDogFunc(clothes);
                dialog.dismiss();
            }
        });

    }

    //设置qq形象
    private void loadQQshowImg(final String[] arr, final ImageView image, final ClothesInfoBean clothes) throws IOException {
        final ArrayList<Drawable> drawableArr = new ArrayList<Drawable>();
//        Drawable[] array = new Drawable[26];
        final int sort=Integer.parseInt(clothes.getSort());
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    if (!arr[i].equalsIgnoreCase("0")||(sort==i)) {
                        Utils.OkHttps example = new Utils.OkHttps();
//                 Drawable bitmap1 = Utils.getImageFromAsserts(this,"virtualimage/"+i+"/"+arr[i]+".gif");
                        String url = ConstFile.serverUrl + "images/virtualimage/" + i + "/" + arr[i] + ".gif";
                        if(sort==i){
                            url = ConstFile.serverUrl + "images/virtualimage/" + i + "/" + clothes.getGraphic();
                        }

                        ResponseBody response = null;
                        try {
                            response = example.run2(url);
                            byte[] bytes = response.bytes();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            Drawable drawable = new BitmapDrawable(bitmap);
                            drawableArr.add(drawable);
                            GifDrawable gifFromBytes = new GifDrawable(bytes);
                            drawableArr.add(gifFromBytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //
//        Bitmap bitmap2 = ((BitmapDrawable) getResources().getDrawable(
//                R.drawable.go)).getBitmap();
                        Drawable[] array = drawableArr.toArray(new Drawable[drawableArr.size()]);
                        LayerDrawable la = new LayerDrawable(array);
//        // 其中第一个参数为层的索引号，后面的四个参数分别为left、top、right和bottom
                        la.setLayerInset(0, 0, 0, 0, 0);
////        la.setLayerInset(1, 20, 20, 20, 20);
                        image.setImageDrawable(la);
                    }
                });
            }
        }).start();

    }

    private void buyDogFunc(final ClothesInfoBean clothes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=shop&act=buyClothes&type=buy&suitable="+clothes.getSuitable()+"&leibie="+clothes.getSort()+"&classid="+clothes.getSort()+"&picid=" + clothes.getPicid() + "&web_uid=" + ConstFile.uId;
                    final String response = example.run(url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.trim().length() == 0) {
                                Toast.makeText(BuyClothesActicity.this, "购买失败", Toast.LENGTH_SHORT).show();
                            } else {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    if (jsonObject.getInt("code") == 1) {
                                        Toast.makeText(BuyClothesActicity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                        change2();
                                        int fbs = Integer.parseInt(fbView.getText() + "");
                                        fbs -= Integer.parseInt(clothes.getCost());
                                        fbView.setText(fbs + "");
                                    } else {
                                        Toast.makeText(BuyClothesActicity.this, "购买失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(BuyClothesActicity.this, "购买失败", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onPageChange(int index) {
        currentPage = index;
        if (currentPage == 0) {
            leftBtn.setVisibility(View.GONE);
        }
        if (totalPage > 1) {
            rightBtn.setVisibility(View.VISIBLE);
        }
        if (currentPage == totalPage - 1) {
            rightBtn.setVisibility(View.GONE);
        }
        if (currentPage > 0) {
            leftBtn.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.left_btn, R.id.right_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_btn:
                currentPage--;
                scrollHelper.scrollToPosition(currentPage);
                if (currentPage == 0) {
                    leftBtn.setVisibility(View.GONE);
                }
                if (totalPage > 1) {
                    rightBtn.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.right_btn:
                currentPage++;
                scrollHelper.scrollToPosition(currentPage);
                if (currentPage == totalPage - 1) {
                    rightBtn.setVisibility(View.GONE);
                }
                if (currentPage > 0) {
                    leftBtn.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
