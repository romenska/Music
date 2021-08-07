package romenska.olha.kidsapp.music;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.view.animation.Animation;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import static android.view.animation.AnimationUtils.loadAnimation;
public class MainActivity extends AppCompatActivity {
private static final String TAG = "MainActivity";
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCatSound, mChickenSound, mCowSound, mDogSound, mDuckSound, mSheepSound;
    private ArrayList<Integer>  noteList = new ArrayList<>();
    private ArrayList<ImageButton>  keyList = new ArrayList<>();
    private ArrayList<Boolean>  keyPressList = new ArrayList<>();
    private int mStreamID;
    LinearLayout sensor;
    int lastStep=-1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            // Для устройств до Android 5
//            createOldSoundPool();
//        } else {
//            // Для новых устройств
//            createNewSoundPool();
//        }

//        mAssetManager = getAssets();
//
//        // получим идентификаторы
//        mCatSound = loadSound("cat.ogg");
//        mChickenSound = loadSound("chicken.ogg");
//        mCowSound = loadSound("cow.ogg");
//        mDogSound = loadSound("dog.ogg");
//        mDuckSound = loadSound("duck.ogg");
//        mSheepSound = loadSound("sheep.ogg");




        ImageView cowImageButton = (ImageView) findViewById(R.id.imageButtonCow);
       cowImageButton.setOnClickListener(onClickListener);


        ImageView chickenImageButton = (ImageView) findViewById(R.id.imageButtonChicken);
        chickenImageButton.setOnClickListener(onClickListener);


        ImageView catImageButton = (ImageView) findViewById(R.id.imageButtonCat);
        catImageButton.setOnClickListener(onClickListener);


        ImageView duckImageButton = (ImageView) findViewById(R.id.imageButtonDuck);
        duckImageButton.setOnClickListener(onClickListener);


        ImageView sheepImageButton = (ImageView) findViewById(R.id.imageButtonSheep);
        sheepImageButton.setOnClickListener(onClickListener);


        ImageView dogImageButton = (ImageView) findViewById(R.id.imageButtonDog);
        dogImageButton.setOnClickListener(onClickListener);

        keyList.add(findViewById(R.id.button1));
        keyList.add(findViewById(R.id.button2));
        keyList.add(findViewById(R.id.button3));
        keyList.add(findViewById(R.id.button4));
        keyList.add(findViewById(R.id.button5));
        keyList.add(findViewById(R.id.button6));
        keyList.add(findViewById(R.id.button7));



        sensor = findViewById(R.id.sensor);



        sensor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                if (eventAction == MotionEvent.ACTION_DOWN) {
                    lastStep=-1;
                }
                float x = event.getX();
                float y = event.getY();
                Log.d(TAG,"sound ");
                Log.d(TAG,"x= "+x);
                Log.d(TAG,"y= "+y);
                Log.d(TAG,"sensor x= "+sensor.getWidth());
                Log.d(TAG,"sensor y= "+sensor.getHeight());
                float xx = sensor.getWidth();
                xx=xx/7;

                int step = (int) (x/xx);
                step=(step>=7)?6:step;
                if(lastStep!=step) {
                    lastStep=step;
                    playSound(noteList.get(step));
                    keyList.get(step).startAnimation(loadAnimation(MainActivity.this, R.anim.button_press));
                }


                /*
                if (x<xx){

                } else if (x<xx*2){
                    playSound(noteList.get( 1 ));
                    button2.startAnimation(loadAnimation(MainActivity.this, R.anim.button_press));
                } else if (x<xx*3){
                    playSound(noteList.get( 2 ));
                    button3.startAnimation(loadAnimation(MainActivity.this, R.anim.button_press));
                } else if (x<xx*4){
                    playSound(noteList.get( 3 ));
                    button4.startAnimation(loadAnimation(MainActivity.this, R.anim.button_press));
                } else if (x<xx*5){
                    playSound(noteList.get( 4 ));
                    button5.startAnimation(loadAnimation(MainActivity.this, R.anim.button_press));
                }else if (x<xx*6){
                    playSound(noteList.get( 5 ));
                    button6.startAnimation(loadAnimation(MainActivity.this, R.anim.button_press));
                }else if (x<xx*7){
                    playSound(noteList.get( 6 ));
                    button7.startAnimation(loadAnimation(MainActivity.this, R.anim.button_press));
                }
*/
                return true;
            }
        });

/*
        cowImageButton.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                if (eventAction == MotionEvent.ACTION_UP) {
                    // Отпускаем палец
                    if (mStreamID > 0)
                        mSoundPool.stop(mStreamID);
                }
                if (eventAction == MotionEvent.ACTION_DOWN) {
                    // Нажимаем на кнопку
                    mStreamID = playSound(mCowSound);
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    mSoundPool.stop(mStreamID);
                }
                return true;
            }
        });*/
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            v.startAnimation(loadAnimation(MainActivity.this, R.anim.bounce));

            switch (v.getId()) {
                case R.id.imageButtonCow:
                    playSound(mCowSound);
                    break;
                case R.id.imageButtonChicken:
                    playSound(mChickenSound);
                    break;
                case R.id.imageButtonCat:
                    playSound(mCatSound);
                    break;
                case R.id.imageButtonDuck:
                    playSound(mDuckSound);
                    break;
                case R.id.imageButtonSheep:
                    playSound(mSheepSound);
                    break;
                case R.id.imageButtonDog:
                    playSound(mDogSound);
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(30, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        /*mAssetManager = getAssets();

        // получим идентификаторы
        mCatSound = loadSound("cat.mp3");
        mChickenSound = loadSound("chicken.mp3");
        mCowSound = loadSound("cow.mp3");
        mDogSound = loadSound("dog.mp3");
        mDuckSound = loadSound("duck.mp3");
        mSheepSound = loadSound("sheep.mp3");
*/

        mCatSound =  mSoundPool.load(MainActivity.this, R.raw.cat,1);
        mChickenSound = mSoundPool.load(MainActivity.this, R.raw.chicken,1);
        mCowSound = mSoundPool.load(MainActivity.this, R.raw.cow,1);
        mDogSound = mSoundPool.load(MainActivity.this, R.raw.dog,1);
        mDuckSound = mSoundPool.load(MainActivity.this, R.raw.duck,1);
        mSheepSound = mSoundPool.load(MainActivity.this, R.raw.sheep,1);

        noteList.add(mSoundPool.load(MainActivity.this, R.raw.n_1,1));
        noteList.add(mSoundPool.load(MainActivity.this, R.raw.n_2,1));
        noteList.add(mSoundPool.load(MainActivity.this, R.raw.n_3,1));
        noteList.add(mSoundPool.load(MainActivity.this, R.raw.n_4,1));
        noteList.add(mSoundPool.load(MainActivity.this, R.raw.n_5,1));
        noteList.add(mSoundPool.load(MainActivity.this, R.raw.n_6,1));
        noteList.add(mSoundPool.load(MainActivity.this, R.raw.n_7,1));


    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }

    public void onClickKey (View v){
       int sound =  noteList.get( Integer.parseInt((String)  v.getTag()));
        playSound(sound);
        v.startAnimation(loadAnimation(MainActivity.this, R.anim.button_press));
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //if (hasFocus) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //}
    }
}