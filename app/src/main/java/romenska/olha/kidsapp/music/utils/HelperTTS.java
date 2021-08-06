package romenska.olha.kidsapp.music.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import romenska.olha.kidsapp.music.R;

public class HelperTTS   {

    public final TextToSpeech TTS;

    private final static String LOG_TAG = "HelperTTS";
    private static final String GOOGLE_TTS_PACKAGE = "com.google.android.tts";

    private final Context context;
    private final Locale locale;

    private OnInitListener onInitListener;
    private boolean isEnabledTTS = false;



    public HelperTTS(Context context, String location) {
        this(context,new Locale(location));
    }

    public HelperTTS(Context context, Locale locale) {
        this.context = context;
        this.locale = locale;
        TTS = new TextToSpeech(context, this::onInit, GOOGLE_TTS_PACKAGE);
        initSpeech();
    }

    private void initSpeech(){
        Log.d(LOG_TAG,"initSpeech "+locale.toString());
        addSound();

    }
    private void addSound() {
        final String appPackageName = context.getPackageName();
        TTS.addSpeech("n_1", appPackageName, R.raw.n_1);
        TTS.addSpeech("n_2", appPackageName, R.raw.n_2);
        TTS.addSpeech("n_3", appPackageName, R.raw.n_3);
        TTS.addSpeech("n_4", appPackageName, R.raw.n_4);
        TTS.addSpeech("n_5", appPackageName, R.raw.n_5);
        TTS.addSpeech("n_6", appPackageName, R.raw.n_6);
        TTS.addSpeech("n_7", appPackageName, R.raw.n_7);


    }



    public void setOnInitListener(OnInitListener onInitListener) {
        this.onInitListener = onInitListener;
    }


    public void onInit(int status) {
        Log.d(LOG_TAG, "TTS status  " + status);

        if (!isGoogleTTSInstalled()) dialogUpdateTTS();

        if (status == TextToSpeech.ERROR) {
            isEnabledTTS = false;
            dialogUpdateTTS();
        } else {

            int result = TTS.setLanguage(locale);
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                dialogUpdateTTS();
               // dialogUpdateLangTTS();
                Log.d(LOG_TAG, "Problem result " + result);

            } else {
                TTS.setPitch(1f);
                TTS.setSpeechRate(1.0f);
                isEnabledTTS = true;
            }

        }
        if (onInitListener != null)
            onInitListener.onInit();
    }


    private boolean isGoogleTTSInstalled() {
        Intent intent = new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> listOfInstalledTTSInfo = pm.queryIntentActivities(intent, PackageManager.GET_META_DATA);
        for (ResolveInfo r : listOfInstalledTTSInfo) {
            String engineName = r.activityInfo.applicationInfo.packageName;
            if (engineName.equals(GOOGLE_TTS_PACKAGE)) {
                return true;
            }
        }
        return false;
    }


    private void installGoogleTTS() {
        Toast.makeText(context, "Installing " + GOOGLE_TTS_PACKAGE, Toast.LENGTH_LONG).show();
        try {
            ((Activity) context).startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + GOOGLE_TTS_PACKAGE)), 1);
        } catch (ActivityNotFoundException e) {
            ((Activity) context).startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + GOOGLE_TTS_PACKAGE)), 1);
        }
    }

    private void dialogUpdateTTS() {
        new AlertDialog.Builder(context)
                .setTitle(R.string.setup_tts)
                .setMessage(R.string.setup_tts_message)
                .setPositiveButton(R.string.setup, (dialog, which) -> installGoogleTTS())
                .setNegativeButton(R.string.cancel, null)
                .setIcon(R.drawable.tts)
                .show();
    }

    public void stop() {
        TTS.stop();
    }

    public void shutdown() {
        TTS.shutdown();
    }

    public void speak(String text) {

        Log.d(LOG_TAG, "speak : " + text);

        if (!isEnabledTTS) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text, TextToSpeech.QUEUE_FLUSH);
        } else {
            ttsUnder20(text, TextToSpeech.QUEUE_FLUSH);
        }
    }

    public void speakAdd(String text) {

        Log.d(LOG_TAG, "+ speak : " + text);

        if (!isEnabledTTS) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text, TextToSpeech.QUEUE_ADD);
        } else {
            ttsUnder20(text, TextToSpeech.QUEUE_ADD);
        }
    }

    private void ttsUnder20(String text, int queueMode) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        TTS.speak(text, queueMode, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text, int queueMode) {
        String utteranceId = this.hashCode() + "";
        TTS.speak(text, queueMode, null, utteranceId);
    }

    public interface OnInitListener {
        void onInit();
    }

}
