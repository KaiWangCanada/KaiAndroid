package kai.kaiprivate.thirdparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lidroid.xutils.http.client.multipart.content.StringBody;

import kai.kaiprivate.R;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class KaiRxJava extends AppCompatActivity {

    Observable<String> mObservable;
    Subscriber<String> mSubscriber;
    Subscription mSubscription;

    Action1<String> mAction1;
    Func1<String, String> mFunc1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_rx_java);
    }

    public void click(View view) {
//        standardRx();
        simplifiedRx();
    }

    private void simplifiedRx() {
        mObservable =
                Observable.just("hello, world!");

        mAction1 = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        };

        mFunc1 = new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s + " - kai";
            }
        };

        mSubscription = mObservable
                .map(mFunc1)
                .subscribe(mAction1);

        mSubscription.unsubscribe();
    }

    private void standardRx() {
        mObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("hello, world!");
                        subscriber.onCompleted();
                    }
                }
        );

        mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        mSubscription = mObservable.subscribe(mSubscriber);

        mSubscription.unsubscribe();
    }
}






















