package com.zsy.words;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.zsy.words.adapter.MyAdapter;
import com.zsy.words.bean.Person;
import com.zsy.words.view.WordsNavigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        WordsNavigation.onWordsChangeListener, AbsListView.OnScrollListener {

    private Handler handler;
    private List<Person> list;
    private TextView tv;
    private ListView listView;
    private WordsNavigation word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        word = (WordsNavigation) findViewById(R.id.words);
        listView = (ListView) findViewById(R.id.list);
        //初始化数据
        initData();
        //初始化列表
        initListView();

        //设置列表点击滑动监听
        handler = new Handler();
        word.setOnWordsChangeListener(this);
    }

    private void initListView() {
        MyAdapter adapter = new MyAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
    }

    /**
     * 初始化联系人列表信息
     */
    private void initData() {
        list = new ArrayList<>();
        list.add(new Person("Dave"));
        list.add(new Person("张晓飞"));
        list.add(new Person("杨光福"));
        list.add(new Person("阿钟"));
        list.add(new Person("胡继群"));
        list.add(new Person("徐歌阳"));
        list.add(new Person("钟泽兴"));
        list.add(new Person("宋某人"));
        list.add(new Person("刘某人"));
        list.add(new Person("Tony"));
        list.add(new Person("老刘"));
        list.add(new Person("隔壁老王"));
        list.add(new Person("安传鑫"));
        list.add(new Person("温松"));
        list.add(new Person("成龙"));
        list.add(new Person("那英"));
        list.add(new Person("刘甫"));
        list.add(new Person("沙宝亮"));
        list.add(new Person("张猛"));
        list.add(new Person("张大爷"));
        list.add(new Person("张哥"));
        list.add(new Person("张娃子"));
        list.add(new Person("樟脑丸"));
        list.add(new Person("吴亮"));
        list.add(new Person("Tom"));
        list.add(new Person("阿三"));
        list.add(new Person("肖奈"));
        list.add(new Person("贝微微"));
        list.add(new Person("赵二喜"));
        list.add(new Person("曹光"));
        list.add(new Person("姜宇航"));

        //对集合排序
        Collections.sort(list, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                //根据拼音进行排序
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
    }

    //手指按下字母改变监听回调
    @Override
    public void wordsChange(String words) {
        updateWord(words);
        updateListView(words);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //当滑动列表的时候，更新右侧字母列表的选中状态
        word.setTouchIndex(list.get(firstVisibleItem).getHeaderWord());
    }

    /**
     * @param words 首字母
     */
    private void updateListView(String words) {
        for (int i = 0; i < list.size(); i++) {
            String headerWord = list.get(i).getHeaderWord();
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words.equals(headerWord)) {
                //将列表选中哪一个
                listView.setSelection(i);
                //找到开头的一个即可
                return;
            }
        }
    }

    /**
     * 更新中央的字母提示
     *
     * @param words 首字母
     */
    private void updateWord(String words) {
        tv.setText(words);
        tv.setVisibility(View.VISIBLE);
        //清空之前的所有消息
        handler.removeCallbacksAndMessages(null);
        //1s后让tv隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setVisibility(View.GONE);
            }
        }, 500);
    }


}
