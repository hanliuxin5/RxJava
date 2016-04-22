package com.vvsai.rxjava.excel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.vvsai.rxjava.R;
import com.vvsai.rxjava.retrofit.MyRetrofit;
import com.vvsai.rxjava.retrofit.VenuesBean;
import com.vvsai.rxjava.utils.LogUtil;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lychee on 2016/4/22.
 */
public class ExcelActivity extends AppCompatActivity {
    @Bind(R.id.button3)
    Button button3;
    //标题行
    String title[] = {"名称", "地址", "Logo"};
    WritableWorkbook book;
    WritableSheet sheet;
    int page = 1;
    int pageSize = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);
        ButterKnife.bind(this);
        try {
            //t.xls为要新建的文件名
            WorkbookSettings s = new WorkbookSettings();
            s.setUseTemporaryFileDuringWrite(true);
            book = Workbook.createWorkbook(new File(this.getFilesDir(), "j.xls"), s);
            //生成名为“第一页”的工作表，参数0表示这是第一页
            sheet = book.createSheet("第一页", 0);
            for (int i = 0; i < 3; i++)    //title
                sheet.addCell(new Label(i, 0, title[i]));
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
        Observable.range(page, 15)
                .observeOn(Schedulers.io())
                .flatMap(new Func1<Integer, Observable<VenuesBean>>() {
                    @Override
                    public Observable<VenuesBean> call(Integer integer) {
                        page = integer;
                        return MyRetrofit.getApiService().getArenaList("51", "", page, pageSize);
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<VenuesBean>() {
                    @Override
                    public void call(VenuesBean venuesBean) {

                        write2Excel(venuesBean, page);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VenuesBean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.e("完成全部EXCEL输出！");
                        try {
                            //写入数据
                            book.write();
                            //关闭文件
                            book.close();
                        } catch (IOException | WriteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("error: " + e);
                    }

                    @Override
                    public void onNext(VenuesBean venuesBean) {
                        LogUtil.e("完成 " + page + " 页EXCEL输出！");
                    }
                });

//        MyRetrofit.getApiService().getArenaList("", 1, 104222)
//                .subscribeOn(Schedulers.io());
    }

    private void write2Excel(VenuesBean vb, int page) {
        //操作执行
        try {
            //写入内容

            int p = (page - 1) * pageSize + 1;
            for (int i = 0; i < vb.getResult().getArenas().size(); i++) {
                for (int j = 0; j < 2; j++) {
                    VenuesBean.ResultEntity.ArenasEntity arenasEntity = vb.getResult().getArenas().get(i);
                    if (j == 0)
                        //名称
                        sheet.addCell(new Label(j, p, arenasEntity.getName()));
                    if (j == 1)
                        //地址
                        sheet.addCell(new Label(j, p, arenasEntity.getAddress()));
//                    if (j == 2)
//                        //Logo
//                        sheet.addCell(new Label(j, p, arenasEntity.getIcon()));
                }
                p++;
            }
//            //写入数据
//            book.write();
//            //关闭文件
//            book.close();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
