package com.unit.common.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.FileDescriptor;

public class AudioPlayerUtils implements OnCompletionListener, OnPreparedListener,
        OnErrorListener {
    private static final String TAG = AudioPlayerUtils.class.getSimpleName();


    /**
     * 广播ACTION名称的前缀
     */
    private static final String ACTION_PREFFIX = "org.tools.audioplayer";

    /**
     * 一个广播ACTION，歌曲开始播放时发送此广播
     */
    public static final String ACTION_PLAY = ACTION_PREFFIX + ".ACTION_PLAY";

    /**
     * 一个广播ACTION，歌曲暂停播放时发送此广播
     */
    public static final String ACTION_PAUSE = ACTION_PREFFIX + ".ACTION_PAUSE";

    /**
     * 一个广播ACTION，歌曲停止播放发送此广播
     */
    public static final String ACTION_STOP = ACTION_PREFFIX + ".ACTION_STOP";

    /*
     * 歌曲播放的状态(Preparing,Playing,Paused,Stopped)
	 */
    public static final int STATE_STOPPED = 0; // MediaPlayer已经停止工作，不再准备播放
    public static final int STATE_PREPARING = 1; // MediaPlayer正在准备中
    public static final int STATE_PLAYING = 2; // 正在播放（MediaPlayer已经准备好了）
    // （但是当丢失音频焦点时，MediaPlayer在此状态下实际上也许已经暂停了，
    // 但是我们仍然保持这个状态，以表明我们必须在一获得音频焦点时就返回播放状态）
    public static final int STATE_PAUSED = 3; // 播放暂停 (MediaPlayer处于准备好了的状态)

    /**
     * 當前播放狀態
     */
    private int mState = STATE_STOPPED;

    /**
     * 丢失音频焦点，我们为媒体播放设置一个低音量(1.0f为最大)，而不是停止播放
     */
    private static final float DUCK_VOLUME = 0.1f;

    /**
     * 音频焦点辅助类
     */
    private AudioFocusHelper mAudioFocusHelper = null;

    /**
     * 用于音频播放
     */
    private MediaPlayer mMediaPlayer = null;

    /**
     * 本地广播管理器，用于发送本地广播
     */
    private LocalBroadcastManager mLocalBroadcastManager = null;

    /**
     * 需要的上下文环境，引用的是一个ApplicationContext，以防止内存泄漏
     */
    private Context mContext = null;

    /**
     * 构造方法私有化，以便实现单例构造
     */
    private AudioPlayerUtils(Context context) {
        mContext = context.getApplicationContext();
        mAudioFocusHelper = new AudioFocusHelper(mContext, mMusicFocusable);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);
    }

    /**
     * 本类单例
     */
    private static AudioPlayerUtils mController = null;

    private static OnCompletionListener appsOnCompletionListener;
    private static OnErrorListener appsOnErrorListener;
    private static OnPreparedListener appsOnPreparedListener;

    /**
     * 获取音频控制器实例
     *
     * @param context 上下文环境
     */
    public static AudioPlayerUtils getInstance(Context context) {
        if (mController == null) {
            mController = new AudioPlayerUtils(context);
        }
        return mController;
    }

    public static AudioPlayerUtils getInstance(Context context, OnCompletionListener onCompletionListener, OnErrorListener onErrorListener, OnPreparedListener onPreparedListener) {

        appsOnCompletionListener = onCompletionListener;
        appsOnErrorListener = onErrorListener;
        appsOnPreparedListener = onPreparedListener;
        if (mController == null) {
            mController = new AudioPlayerUtils(context);
        }
        return mController;
    }

    /**
     * 请求播放音频
     *
     * @param filePath 音频文件路径
     * @param looping  是否循环播放
     */
    public void processPlayRequest(String filePath, boolean looping) throws Exception {
        processPlayRequest(Uri.parse(filePath), looping);
    }

    /**
     * 请求播放音频
     *
     * @param uri     音频uri
     * @param looping 是否循环播放
     */
    public void processPlayRequest(Uri uri, boolean looping) throws Exception {
        processPlayRequest(uri, null, looping);
    }

    /**
     * 请求播放音频
     *
     * @param fd      音频文件文件描述符,可以播放来自assets下的音频文件
     * @param looping 是否循环播放
     */
    public void processPlayRequest(FileDescriptor fd, boolean looping) throws Exception {
        processPlayRequest(null, fd, looping);
    }

    /**
     * 请求暂停音频播放
     */
    public void processPauseRequest() {
        Log.i(TAG, "processPauseRequest()");

        if (mState == STATE_PLAYING) {
            // 标记当前播放状态为"暂停"
            mState = STATE_PAUSED;

            // 暂停播放
            mMediaPlayer.pause();

            // 发送广播通知音频播放已经暂停
            mLocalBroadcastManager.sendBroadcast(new Intent(ACTION_PAUSE));
        }
    }

    /**
     * 请求停止音频播放
     */
    public void processStopRequest() {
        processStopRequest(false);
    }

    /**
     * 请求停止音频播放
     *
     * @param force 传递true强制停止播放，不管当前处于什么状态
     */
    public void processStopRequest(boolean force) {
        Log.i(TAG, "processStopRequest()");

        if (mState != STATE_STOPPED || force) {
            // 标记当前播放状态为“停止”
            mState = STATE_STOPPED;

            // 释放所有持有的资源
            relaxResources();

            // 放弃音频焦点的控制
            mAudioFocusHelper.giveUpAudioFocus();

            // 发送广播通知音频播放已经停止
            mLocalBroadcastManager.sendBroadcast(new Intent(ACTION_STOP));
        }
    }

    /**
     * 返回当前MediaPlayer的状态
     *
     * @return 当前MediaPlayer的状态
     */
    public int getState() {
        return mState;
    }

    /**
     * 获取当前播放进度
     *
     * @return 当前播放位置（单位：毫秒）。如果当前没有播放，返回0。
     */
    public int getPlayingProgress() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 获取正在播放的音频长度
     *
     * @return 音频长度（单位：毫秒）
     */
    public int getDuration() {
        if (mMediaPlayer != null && mState != STATE_STOPPED) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    /**
     * 跳转到某个指定进度
     *
     * @param newPosition 要跳转到的进度（单位：毫秒）
     */
    public void seekTo(int newPosition) {
        if (mMediaPlayer != null
                && (mState == STATE_PLAYING || mState == STATE_PAUSED)) {
            mMediaPlayer.seekTo(newPosition);
        }
    }

    /**
     * 请求播放音频，提供两个数据源，哪个不为空就用哪个播放
     *
     * @param uri  音频文件的URI，音频可以来自存储卡、ContentProvider或者网络。
     * @param fd   音频文件的文件描述符，音频可以来自原assets目录下。
     * @param loop 是否循环播放
     */
    private void processPlayRequest(Uri uri, FileDescriptor fd, boolean loop) throws Exception {
        Log.i(TAG, "processPlayRequest()");

        if (mState == STATE_PAUSED) {
            mAudioFocusHelper.tryToGetAudioFocus();
            configAndStartMediaPlayer();
            mState = STATE_PLAYING;
        } else if (mState != STATE_PREPARING) {
            mAudioFocusHelper.tryToGetAudioFocus();
            if (uri != null) {
                play(uri, loop);
            } else if (fd != null) {
                play(fd, loop);
            }
        }
    }

    /**
     * 播放音频
     *
     * @param uri     音频来源
     * @param looping 是否循环播放
     */
    private void play(Uri uri, boolean looping) throws Exception {
        play(uri, null, looping);
    }

    /**
     * 播放音频
     *
     * @param fd      音频文件的文件描述符
     * @param looping 是否循环播放
     */
    private void play(FileDescriptor fd, boolean looping) throws Exception {
        play(null, fd, looping);
    }

    /**
     * 播放音频，提供两个音频数据来源，哪个不为空就用哪个
     *
     * @param uri     音频来源
     * @param fd      音频文件的文件描述符
     * @param looping 是否循环播放
     */
    private void play(Uri uri, FileDescriptor fd, boolean looping) throws Exception {
        Log.i(TAG, "play()");

        // 检查数据源是否有效
        if (uri == null && fd == null) {
            Log.d(TAG, "Invalid data source.Failed to play.");
        }

        // 先标示为停止播放状态
        mState = STATE_STOPPED;

        try {
            // 尝试初始化MediaPlayer
            createMediaPlayerIfNeeded();

            // 设置播放数据源
            if (uri != null) {
                boolean isAudio = MediaFileCheckUtils.isAudioFileType(uri.getPath());
                if (isAudio) {
                    mMediaPlayer.setDataSource(mContext, uri);
                } else {
                    throw new Exception("is not a Audio file");
                }
            } else if (fd != null) {
                mMediaPlayer.setDataSource(fd);
            }

            // 设置MediaPlayer
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setLooping(looping);

            mState = STATE_PREPARING;

            // 在后台准备MediaPlayer，准备完成后会调用OnPreparedListener的onPrepared()方法。
            // 在MediaPlayer准备好之前，我们不能调用其start()方法
            mMediaPlayer.prepareAsync();
        } catch (Exception ex) {
            Log.e(TAG, "IOException while request to play.");
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * 释放本服务所使用的资源
     */
    public void relaxResources() {
        // 停止并释放MediaPlayer
        if (mMediaPlayer != null) {
            mState = STATE_STOPPED;
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 确保MediaPlayer存在，并且已经被重置。 这个方法将会在需要时创建一个MediaPlayer，
     * 或者重置一个已存在的MediaPlayer。
     */
    private void createMediaPlayerIfNeeded() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();

            // 确保我们的MediaPlayer在播放时获取了一个唤醒锁，
            // 如果不这样做，当歌曲播放很久时，CPU进入休眠从而导致播放停止
            // 要使用唤醒锁，要确保在AndroidManifest.xml中声明了android.permission.WAKE_LOCK权限
            mMediaPlayer.setWakeMode(mContext, PowerManager.PARTIAL_WAKE_LOCK);

            // 在MediaPlayer在它准备完成时、完成播放时、发生错误时通过监听器通知我们，
            // 以便我们做出相应处理
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnErrorListener(this);
        } else {
            mMediaPlayer.reset();
        }
    }

    /**
     * 根据音频焦点的设置重新设置MediaPlayer的参数，然后启动或者重启它。 如果我们拥有音频焦点，则正常播放;
     * 如果没有音频焦点，根据当前的焦点设置将MediaPlayer切换为“暂停”状态或者低声播放。
     * 这个方法已经假设mPlayer不为空，所以如果要调用此方法，确保正确的使用它。
     */
    private void configAndStartMediaPlayer() {
        if (mAudioFocusHelper.getAudioFocus() == AudioFocusHelper.NoFocusNoDuck) {
            // 如果丢失了音频焦点也不允许低声播放，我们必须让播放暂停，即使mState处于Playing状态。
            // 但是我们并不修改mState的状态，因为我们会在获得音频焦点时返回立即返回播放状态。
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mLocalBroadcastManager.sendBroadcast(new Intent(ACTION_PAUSE));
            }
            return;
        } else if (mAudioFocusHelper.getAudioFocus() == AudioFocusHelper.NoFocusCanDuck)
            // 设置一个较为安静的音量
            mMediaPlayer.setVolume(DUCK_VOLUME, DUCK_VOLUME);
        else {
            // 设置大声播放
            mMediaPlayer.setVolume(1.0f, 1.0f);
        }
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            mLocalBroadcastManager.sendBroadcast(new Intent(ACTION_PLAY));
        }
    }

    @Override
    public final boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.e(TAG, "Error: "
                        + "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK"
                        + ", extra=" + String.valueOf(extra));
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.e(TAG, "Error: " + "MEDIA_ERROR_SERVER_DIED" + ", extra="
                        + String.valueOf(extra));
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.e(TAG,
                        "Error: " + "MEDIA_ERROR_UNKNOWN" + ", extra="
                                + String.valueOf(extra));
                break;
            case -38:
                Log.e(TAG, "Error: what" + what + ", extra=" + extra);
                break;
            default:
                Log.e(TAG, "Error: what" + what + ", extra=" + extra);
                break;
        }

        processStopRequest(true);

        if (appsOnErrorListener != null) {
            appsOnErrorListener.onError(mp, what, extra);
        }

        // true表示我们处理了发生的错误
        return true;
    }

    @Override
    public final void onPrepared(MediaPlayer mp) {
        // 准备完成了，可以播放歌曲了
        mState = STATE_PLAYING;

        configAndStartMediaPlayer();

        mLocalBroadcastManager.sendBroadcast(new Intent(ACTION_PLAY));
        if (appsOnPreparedListener != null) {
            appsOnPreparedListener.onPrepared(mp);
        }
    }

    @Override
    public final void onCompletion(MediaPlayer mp) {
        // 播放完成时，我们释放所有资源，重置播放状态
        processStopRequest(true);
        if (appsOnCompletionListener != null) {
            appsOnCompletionListener.onCompletion(mp);
        }
    }

    private MusicFocusable mMusicFocusable = new MusicFocusable() {

        @Override
        public final void onGainedAudioFocus() {
            Log.i(TAG, "gained audio focus.");

            // 用新的音频焦点状态来重置MediaPlayer
            if (mState == STATE_PLAYING) {
                configAndStartMediaPlayer();
            }
        }

        @Override
        public final void onLostAudioFocus() {
            Log.i(TAG, "lost audio focus.");

            // 以新的焦点参数启动/重启/暂停MediaPlayer
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                configAndStartMediaPlayer();
            }
        }
    };

    public interface MusicFocusable {
        /**
         * Signals that audio focus was gained.
         */
        public void onGainedAudioFocus();

        /**
         * Signals that audio focus was lost.
         */
        public void onLostAudioFocus();
    }

    /**
     * Convenience class to deal with audio focus. This class deals with
     * everything related to audio focus: it can request and abandon focus, and
     * will intercept focus change events and deliver them to a MusicFocusable
     * interface (which, in our case, is implemented by {@link MusicService}).
     * <p/>
     * This class can only be used on SDK level 8 and above, since it uses API
     * features that are not available on previous SDK's.
     */
    @TargetApi(VERSION_CODES.FROYO)
    class AudioFocusHelper implements AudioManager.OnAudioFocusChangeListener {
        /**
         * Represents something that can react to audio focus events. We
         * implement this instead of just using
         * AudioManager.OnAudioFocusChangeListener because that interface is
         * only available in SDK level 8 and above, and we want our application
         * to work on previous SDKs.
         */

        AudioManager mAM;
        MusicFocusable mFocusable;

        // do we have audio focus?
        public static final int NoFocusNoDuck = 0; // we don't have audio focus,
        // and
        // can't duck
        public static final int NoFocusCanDuck = 1; // we don't have focus, but
        // can
        // play at a low volume
        // ("ducking")
        public static final int Focused = 2; // we have full audio focus

        private int mAudioFocus = NoFocusNoDuck;

        public AudioFocusHelper(Context ctx, MusicFocusable focusable) {
            if (android.os.Build.VERSION.SDK_INT >= 8) {
                mAM = (AudioManager) ctx
                        .getSystemService(Context.AUDIO_SERVICE);
                mFocusable = focusable;
            } else {
                mAudioFocus = Focused; // no focus feature, so we always "have"
                // audio focus
            }
        }

        /**
         * Requests audio focus. Returns whether request was successful or not.
         */
        public boolean requestFocus() {
            return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAM
                    .requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN);
        }

        /**
         * Abandons audio focus. Returns whether request was successful or not.
         */
        public boolean abandonFocus() {
            return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == mAM
                    .abandonAudioFocus(this);
        }

        public void giveUpAudioFocus() {
            if (mAudioFocus == Focused && android.os.Build.VERSION.SDK_INT >= 8
                    && abandonFocus())
                mAudioFocus = NoFocusNoDuck;
        }

        public void tryToGetAudioFocus() {
            if (mAudioFocus != Focused && android.os.Build.VERSION.SDK_INT >= 8
                    && requestFocus())
                mAudioFocus = Focused;
        }

        /**
         * Called by AudioManager on audio focus changes. We implement this by
         * calling our MusicFocusable appropriately to relay the message.
         */
        public void onAudioFocusChange(int focusChange) {
            if (mFocusable == null)
                return;
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    mAudioFocus = Focused;
                    mFocusable.onGainedAudioFocus();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mAudioFocus = NoFocusNoDuck;
                    mFocusable.onLostAudioFocus();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mAudioFocus = NoFocusCanDuck;
                    mFocusable.onLostAudioFocus();
                    break;
                default:
            }
        }

        public int getAudioFocus() {
            return mAudioFocus;
        }
    }
}