package kai.kaiprivate.thirdparty.rxjava;

import com.lidroid.xutils.http.client.multipart.content.StringBody;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2015/8/12.
 */
public class KaiObservable {
    public Observable<String> getObversable(final String str) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(str + " - class");
            }
        });
    }
}
