package net.vorson.muhammadsufwan.prayertimesformuslim.quran;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import net.vorson.muhammadsufwan.prayertimesformuslim.App;
import net.vorson.muhammadsufwan.prayertimesformuslim.R;
import net.vorson.muhammadsufwan.prayertimesformuslim.ShowPrayAlarmActivity;
import net.vorson.muhammadsufwan.prayertimesformuslim.customWidget.mtDialog.MaterialDialog;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.adapters.AlQuranDisplayListViewAdapterNew;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.data.DataManager;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.data.DatabaseHandler;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.data.WordMeaningDataManager;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.interfaces.AlQuranDisplayListViewAdapterListener;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.interfaces.OnTextWrapcomplete;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.BookmarkObj;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.BookmarksManager;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.CustomTypefaceSpan;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.models.WordOfQuran;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets.CommonMethods;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets.JustifierNew;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets.LastReadingPositionManager;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets.NewQuranMarks;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets.QuranMarks;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets.TextWrapper;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets.VerticalTextView;
import net.vorson.muhammadsufwan.prayertimesformuslim.quran.servicesAndWidgets.WordMeaningPopup;
import net.vorson.muhammadsufwan.prayertimesformuslim.settingsAndPreferences.AppSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class ScrollingStyleQuranActivity extends AppCompatActivity implements OnTextWrapcomplete,
        View.OnTouchListener, QuranMediaPlayerListener, AlQuranDisplayListViewAdapterListener, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = ScrollingStyleQuranActivity.class.getSimpleName();
    public static int AJZAA_MODE = 1;
    private static final int MIN_DRAGE_Path = 150;
    public static int Majd_MODE = 2;
    public static int PAGING_MODE = 1;
    public static int QUARTER_MODE = 2;
    private static final int REQUEST_TAFSIR_CODE = 123;
    private static int RIGHT_LEFT_MARGIN = 35;
    public static int SCROLL_MODE;
    public static int SURAS_MODE;
    private static Bitmap cacheAnimationBitmap;
    private AlQuranDisplayListViewAdapterNew alQuranDisplayListViewAdapter;
    private int ayahNumberFrom;
    private int ayahNumberTo;
    private String ayatRepeatNumber;
    @Nullable
    private CountDownTimer countDownTimerForAutoScrolling;
    @NonNull
    private String currentFontNameForDrawSura;
    private int currentFromLineIndex;
    private Reciter currentReciter;
    private int fontColor;
    private FontManager fontManager;
    private String fromClassName;
    private boolean isScrolling;
    private List<List<Integer>> lines;
    private ListView listView;
    private Boolean neightReadingMode;
    private NewQuranMarks newQuranMarks;
    private int numberOfAyas;
    private int numberOfRowsInt;
    @NonNull
    View.OnClickListener onClickListinerForNavigation;
    private Boolean repeatAyatEnable;
    private Boolean repeatSesionEnable;
    private LinearLayout rootcontainer;
    private int startDragX;
    private int strokeColor;
    private Boolean strokeEnabled;
    private SurasAjzaaManager surasAjzaaManager;
    private Boolean tafsirEnabled;
    private int tafsirFontSize;
    private String tafsirId;
    private boolean textBold;
    private TextWrapper textWrapper;
    private Toolbar toolbarBottom;
    private TextView tvGozaNum;
    private TextView tvPageNumber;
    private TextView tvSuraName;
    private Typeface typefaceSura2;
    private VerticalTextView verticalTextView;
    private ProgressDialog waitDialog;
    private boolean wordMeaingEnabled;
    private Thread wrappingThread;
    private AppSettings appSettings;

    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        reset();
        if (App.browsingMethod == ScrollingStyleQuranActivity.SCROLL_MODE) {
            setContentView(R.layout.scrolling_quran_activity);
        } else if (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE) {
            setContentView(R.layout.pagging_quran_activity);
        }
        final Toolbar supportActionBar = findViewById(R.id.toolbarViewQuranTowSameLayout);
//        App.applyMyTheme(this, supportActionBar);
//        if (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE) {
//            App.applyMyTheme(this, (Toolbar) findViewById(R.id.toolbarViewQuranTowSameLayout));
//        }
        setSupportActionBar(supportActionBar);
        toolbarBottom = findViewById(R.id.bottom_tool_bar);
        tvSuraName = supportActionBar.findViewById(R.id.tv_sura_name);
        tvGozaNum = supportActionBar.findViewById(R.id.tv_goza);
        rootcontainer = findViewById(R.id.rootContainer);
        final SettingsManager settingsManager = new SettingsManager(this);
        appSettings = AppSettings.getInstance();
        boolean backLight = true;
        final float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        try {
            Integer.parseInt((String) settingsManager.getSettings(SettingsManager.FONT_SIZE, String.class));
            fontColor = appSettings.getInt(AppSettings.Key.FONT_COLOR,-16777216);
            strokeColor = appSettings.getInt(AppSettings.Key.FONT_COLOR_STROKE,-65536);
            strokeEnabled = appSettings.getBoolean(AppSettings.Key.STROKE_ENABLED,false);
            textBold = appSettings.getBoolean(AppSettings.Key.TEXT_BOLD,false);
            wordMeaingEnabled = appSettings.getBoolean(AppSettings.Key.WORD_MEANING,true);;
            neightReadingMode = appSettings.getBoolean(AppSettings.Key.NEIGHT_READING_MODE,false);
            repeatSesionEnable = appSettings.getBoolean(AppSettings.Key.SESSION_REPEAT,false);
            repeatAyatEnable = appSettings.getBoolean(AppSettings.Key.AYAHAT_REPEAT,false);
            backLight = appSettings.getBoolean(AppSettings.Key.BACKLIGHT,false);
            tafsirEnabled = appSettings.getBoolean(AppSettings.Key.TAFSIR_ENABLED);
            tafsirId = appSettings.getString(AppSettings.Key.DEFAULT_TAFSIR);
            tafsirFontSize = CommonMethods.spToPixels(Integer.parseInt((String) settingsManager.getSettings(SettingsManager.TAFSIR_FONT_SIZE, String.class)),
                    getResources().getDisplayMetrics());
            ayatRepeatNumber = appSettings.getString(AppSettings.Key.AYAHAT_REPEAT_NUM);

        } catch (Exception ex) {
            Log.d(TAG, "onCreate: "+ex.getMessage());
        }
//        fontColor = -16777216;
//        strokeColor = -65536;
//        strokeEnabled = false;
//        textBold = false;
//        wordMeaingEnabled = true;
//        neightReadingMode = false;
//        repeatSesionEnable = false;
//        repeatAyatEnable = false;
        tafsirFontSize = 11;
        ayatRepeatNumber = "3";


        if (backLight) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        initAndLoadFonts();
        listView = findViewById(R.id.listView1);
        currentReciter = new AudioSettingsManager(this).getCurrentReciter();
        loadContent();
        if (neightReadingMode) {
            applyNightMode();
        }
        if (App.browsingMethod == ScrollingStyleQuranActivity.SCROLL_MODE) {
            ((SeekBar) findViewById(R.id.seekBar1)).setOnSeekBarChangeListener(this);
        } else if (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE) {
            tvPageNumber = toolbarBottom.findViewById(R.id.textView3);
            final Button button = findViewById(R.id.button_next);
            final Button button2 = findViewById(R.id.button_prev);
            button.setOnClickListener(onClickListinerForNavigation);
            button2.setOnClickListener(onClickListinerForNavigation);
            listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    int height = listView.getHeight();
                    if (getResources().getConfiguration().orientation == 1) {
                        App.listOfQuranHeight[0] = height;
                    } else {
                        App.listOfQuranHeight[1] = height;
                    }
                    numberOfRowsInt = getNumberOfRows();
                    invalidateDisplayInfo();
                    listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
        verticalTextView = findViewById(R.id.vertical_text_View_hezb);
        verticalTextView.setTextColor(-16777216);
        newQuranMarks = new NewQuranMarks();
        fromClassName = getIntent().getStringExtra("FromClassName");
    }

    private void initAndLoadFonts() {
        fontManager = new FontManager();
        final SettingsManager settingsManager = new SettingsManager(this);
        int int1;
        try {
            int1 = Integer.parseInt((String) settingsManager.getSettings(SettingsManager.FONT_SIZE, String.class));
        } catch (Exception ex) {
            ex.printStackTrace();
            int1 = 11;
        }
        fontManager.loadFonts(this, CommonMethods.spToPixels(int1, getResources().getDisplayMetrics()));
    }

    private void loadContent() {
        final PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isInteractive;
        if (Build.VERSION.SDK_INT >= 20) {
            isInteractive = powerManager.isInteractive();
        } else {
            isInteractive = powerManager.isScreenOn();
        }
        if (isInteractive) {
            waitDialog = ProgressDialog.show(this, getResources().getString(R.string.loading), getText(R.string.please_wait), true);
        }
        surasAjzaaManager = new SurasAjzaaManager(this);
        if (App.browsingContent == ScrollingStyleQuranActivity.SURAS_MODE) {
            StaticObjects.contentWords = surasAjzaaManager.getSuraContentNew(App.currentSuraId);
        } else if (App.browsingContent == ScrollingStyleQuranActivity.AJZAA_MODE) {
            StaticObjects.contentWords = surasAjzaaManager.getJozaHezbContentNew(App.browsingContent, App.currentJozaId);
        } else if (App.browsingContent == ScrollingStyleQuranActivity.QUARTER_MODE) {
            StaticObjects.contentWords = surasAjzaaManager.getJozaHezbContentNew(App.browsingContent, App.currentQuarterId);
        }
        new QuranMarks(App.currentSuraId);
        textWrapper = new TextWrapper(fontManager);
        textWrapper.setRequiredWordIndex(App.currentHighlightWordIndexDurngSura);
        textWrapper.setOnTextWrapComplete(this);
        Log.d(TAG, "loadContent: Before Wrapping");
        wrappingThread = new Thread(new Runnable() {
            public void run() {
                Log.d(TAG, "run: wrapping");
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                int height = displayMetrics.heightPixels;
                TextWrapper textWrapper2 = textWrapper;
                double widthInPixels = (double) width;
                double margin = (double) RIGHT_LEFT_MARGIN;
                margin *= 2.3d;
                double density = (double) displayMetrics.density;
                margin *= density;
                width = (int) (widthInPixels - margin);
                height = (int) (((float) height) - (density * 155.0f));
                int i3 = (App.browsingMethod == SCROLL_MODE && tafsirEnabled) ? 1 : 0;
                textWrapper2.wrapNowWords(width, height, i3);
            }
        });
        wrappingThread.start();
    }

    private void displaypage() {
        if (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE && numberOfRowsInt <= 0) {
            numberOfRowsInt = getNumberOfRows();
        }
        Log.d(TAG, "displaypage: Run");
        lines = textWrapper.getAllLinesOfContent();
        final JustifierNew justifierNew = new JustifierNew(this, fontManager);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        final int densityDpi = displayMetrics.densityDpi;
        final int widthPixels = displayMetrics.widthPixels;
        final float density = displayMetrics.density;
        final List<List<Integer>> lines = this.lines;
        final double n = displayMetrics.widthPixels;
        final double n2 = ScrollingStyleQuranActivity.RIGHT_LEFT_MARGIN;
        final double n3 = n2 * 2.3;
        final double n4 = displayMetrics.density;
        final double n5 = n3 * n4;
        final int n6 = (int) (n - n5);
        int mode = (tafsirEnabled && App.browsingMethod == SCROLL_MODE) ? 1 : 0;
        justifierNew.justifyLines(lines, n6, mode);
        loadWordMeaningsIndexesOfSura();
        Log.d(TAG, "displaypage: after Justifire");
        if (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE) {
            while (this.lines.size() % numberOfRowsInt != 0) {
                this.lines.add(new ArrayList<Integer>());
            }
            this.lines.add(new ArrayList<Integer>());
        }
        alQuranDisplayListViewAdapter = new AlQuranDisplayListViewAdapterNew(this, fontManager, fontColor, strokeEnabled, strokeColor,
                textBold, displayMetrics, this.lines, ScrollingStyleQuranActivity.RIGHT_LEFT_MARGIN, wordMeaingEnabled, tafsirEnabled, tafsirId, neightReadingMode,
                textWrapper, tafsirFontSize);
        listView.setAdapter(alQuranDisplayListViewAdapter);
        if (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE) {
            currentFromLineIndex = 0;
            alQuranDisplayListViewAdapter.setFirstVisibleItem(currentFromLineIndex);
            alQuranDisplayListViewAdapter.setVisibleRowsCount(numberOfRowsInt);
        }
        alQuranDisplayListViewAdapter.notifyDataSetChanged();
        alQuranDisplayListViewAdapter.setAlQuranDisplayListViewAdapterListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == 0) {
                    isScrolling = false;
                    if (listView.getLastVisiblePosition() != -1) {
                        invalidateDisplayInfo();
                        if (waitDialog != null && waitDialog.isShowing()) {
                            waitDialog.dismiss();
                        }
                    }
                }
            }

            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                if (listView.getLastVisiblePosition() != -1) {
                    if (waitDialog != null && waitDialog.isShowing()) {
                        waitDialog.dismiss();
                    }
                    if (App.haveToPlayReciter && !App.isSoundPlaying) {
                        if (App.currentWordIndexHilightForSoundPlay < 0) {
                            App.currentWordIndexHilightForSoundPlay = 0;
                        }
                        if ((StaticObjects.contentWords.get(0)).getWordIndexOfSura() == -1 && (StaticObjects.contentWords.get(1)).getWordIndexOfSura() > 1 && App.currentWordIndexHilightForSoundPlay <= 0) {
                            App.currentWordIndexHilightForSoundPlay = 1;
                        }
                    }
                    if (((List) textWrapper.getAllLinesOfContent().get(listView.getFirstVisiblePosition())).size() > 0) {
                        App.currentWordIndexDurngSuraForRotationScrolling = (Integer) ((List) textWrapper.getAllLinesOfContent().get(listView.getFirstVisiblePosition())).get(0);
                    }
                }
            }
        });
        new DataManager(listView.getContext());
        (StaticObjects.quranMediaPlayer = new QuranMediaPlayer(this, StaticObjects.contentWords.get(0).getSuraId(), alQuranDisplayListViewAdapter,
                textWrapper.getAllLinesOfContent(), listView, surasAjzaaManager, repeatAyatEnable, -1 + Integer.parseInt(ayatRepeatNumber)))
                .setquranMediaPlayerListener(this);
        if (!App.haveToPlayReciter && App.currentWordIndexDurngSuraForRotationScrolling > 0) {
            final int lineIndexOfWord = StaticObjects.contentWords.get(App.currentWordIndexDurngSuraForRotationScrolling).getLineIndexOfWord();
            if (App.browsingMethod == ScrollingStyleQuranActivity.SCROLL_MODE) {
                listView.setSelection(lineIndexOfWord);
            } else if (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE) {
                final int correctFirstLineIndex = correctFirstLineIndex(lineIndexOfWord);
                currentFromLineIndex = correctFirstLineIndex;
                alQuranDisplayListViewAdapter.setFirstVisibleItem(correctFirstLineIndex);
                listView.setSelection(correctFirstLineIndex);
            }
        } else if (App.currentHighlightWordIndexDurngSura > 0) {
            final int lineIndexOfWord2 = StaticObjects.contentWords.get(App.currentHighlightWordIndexDurngSura).getLineIndexOfWord();
            if (App.browsingMethod == ScrollingStyleQuranActivity.SCROLL_MODE) {
                listView.setSelection(lineIndexOfWord2);
            } else if (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE) {
                final int correctFirstLineIndex2 = correctFirstLineIndex(lineIndexOfWord2);
                currentFromLineIndex = correctFirstLineIndex2;
                alQuranDisplayListViewAdapter.setFirstVisibleItem(correctFirstLineIndex2);
                listView.setSelection(correctFirstLineIndex2);
            }
        }
        if (App.haveToPlayReciter && !App.isSoundPlaying && StaticObjects.quranMediaPlayer != null && !StaticObjects.quranMediaPlayer.isPaused()) {
            StaticObjects.quranMediaPlayer.playSound(App.currentWordIndexHilightForSoundPlay);
        }
        if (App.displayLastPageOnLoad && App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE) {
            final int correctFirstLineIndex3 = correctFirstLineIndex(StaticObjects.contentWords.get(StaticObjects.contentWords.size() - 1).getLineIndexOfWord());
            currentFromLineIndex = correctFirstLineIndex3;
            alQuranDisplayListViewAdapter.setFirstVisibleItem(correctFirstLineIndex3);
            listView.setSelection(correctFirstLineIndex3);
            App.displayLastPageOnLoad = false;
        }
        new Thread(new Runnable() {
            public void run() {
                do {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "displayPage Thread.sleep" + e.getMessage());
                    }
                } while (listView.getLastVisiblePosition() <= -1);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d(TAG, "run: invalidateDisplayInfo");
                        invalidateDisplayInfo();
                    }
                });
            }
        }).start();
        Log.d(TAG, "displaypage: End");
    }

    public ScrollingStyleQuranActivity() {
        ayahNumberFrom = 1;
        ayahNumberTo = 1;
        numberOfAyas = 1;
        currentFontNameForDrawSura = "";
        fromClassName = "";
        onClickListinerForNavigation = new View.OnClickListener() {
            public void onClick(@NonNull View view) {
                switch (view.getId()) {
                    case R.id.button_next:
                        doNextPage();
                        return;
                    case R.id.button_prev:
                        doPrevPage();
                        return;
                    default:
                        return;
                }
            }
        };
    }

    private void addBookmark() {
        final BookmarksManager bookmarksManager = new BookmarksManager(this);
        final int intValue = lines.get(listView.getFirstVisiblePosition()).get(0);
        bookmarksManager.addBookmarks(new BookmarkObj((StaticObjects.contentWords.get(intValue)).getAyahNumber(),
                (StaticObjects.contentWords.get(intValue)).getSuraId(), (StaticObjects.contentWords.get(intValue)).getWordIndexOfSura()));
        Toast.makeText(this, R.string.add_bookmark, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint({"NewApi"})
    private void applyNightMode() {
        if (Build.VERSION.SDK_INT >= 16) {
            rootcontainer.setBackground(null);
        } else {
            rootcontainer.setBackgroundDrawable(null);
        }
        rootcontainer.setBackgroundColor(-16777216);
        fontColor = -1;
//        App.applyNightReadingTheme((ViewGroup)findViewById(16908290));
    }

    private void autoScrolling(final int i, final int i2) {
        listView.post(new Runnable() {
            public void run() {
                countDownTimerForAutoScrolling = new CountDownTimer(Long.MAX_VALUE, (long) i2) {
                    public void onFinish() {
                    }

                    public void onTick(long j) {
                        listView.smoothScrollBy(i, i2);
                    }
                }.start();
            }
        });
    }

    private int correctFirstLineIndex(int n) {
        while (n % numberOfRowsInt != 0) {
            --n;
        }
        return n;
    }

    private void doPrevPage() {
        gotoPage(-1 + alQuranDisplayListViewAdapter.getFirstVisibleItem() / numberOfRowsInt);
    }

    private void downloadAudio() {
        finish();
        startActivity(new Intent(this, PrefsActivity.class));
    }

    private void drawGozaaName() {
        if (StaticObjects.contentWords != null && alQuranDisplayListViewAdapter != null && listView.getLastVisiblePosition() != -1) {
            int i;
            if (App.browsingContent == AJZAA_MODE) {
                i = App.currentJozaId;
            } else {
                i = listView.getLastVisiblePosition();
                if (i == -1) {
                    i = 0;
                }
                if (i >= textWrapper.getAllLinesOfContent().size()) {
                    i = textWrapper.getAllLinesOfContent().size() - 1;
                }
                if (i >= 0) {
                    while ((textWrapper.getAllLinesOfContent().get(i)).size() == 0) {
                        i--;
                    }
                }
                if (i > -1) {
                    try {
                        if (i < textWrapper.getAllLinesOfContent().size() && (textWrapper.getAllLinesOfContent().get(i)).size() > 0) {
                            double hezpIndex = (double) (newQuranMarks.getHezpIndex((StaticObjects.contentWords.get((Integer) ((List) textWrapper.getAllLinesOfContent().get(i)).get(0))).getSuraId(),
                                    (StaticObjects.contentWords.get((Integer) ((List) textWrapper.getAllLinesOfContent().get(i)).get(0))).getWordIndexOfSura()) / 4);
                            i = ((int) (hezpIndex / 2.0d)) + 1;
                        }
                    } catch (IndexOutOfBoundsException unused) {
                        i = 0;
                    }
                }
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("");
            String str = i <= 16 ? "jozaa1" : "jozaa2";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Character.toString((char) (i + 61440)));
            stringBuilder.append(Character.toString((char) 61440));
            spannableStringBuilder.append(stringBuilder.toString());
            try {
                Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "fonts/jozaa1.ttf");
                AssetManager assets = getAssets();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("fonts/");
                stringBuilder2.append(str);
                stringBuilder2.append(".ttf");
                spannableStringBuilder.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(assets, stringBuilder2.toString())), 0, 1, 33);
                spannableStringBuilder.setSpan(new CustomTypefaceSpan("", createFromAsset), 1, 2, 33);
                tvGozaNum.setTextSize(32.0f);
                tvGozaNum.setTextColor(-1);
                tvGozaNum.setText(spannableStringBuilder);
            } catch (Exception unused2) {
                Log.e(TAG, "drawGozaaName: " + unused2.getMessage());
            }
        }
    }

    private void drawHezp() {
        if (StaticObjects.contentWords == null || alQuranDisplayListViewAdapter == null) {
            return;
        }
        if (listView.getLastVisiblePosition() == -1) {
            return;
        }
        int i = listView.getLastVisiblePosition();
        if (i == -1) {
            i = 0;
        }
        if (i > -1 && i < textWrapper.getAllLinesOfContent().size()) {
            while (i > -1) {
                if ((textWrapper.getAllLinesOfContent().get(i)).size() > 0 && (textWrapper.getAllLinesOfContent().get(i)).get(0) < StaticObjects.contentWords.size()) {
                    verticalTextView.setText(newQuranMarks.getHezpText((StaticObjects.contentWords.get((textWrapper.getAllLinesOfContent().get(i)).get(0))).getSuraId(),
                            (StaticObjects.contentWords.get((textWrapper.getAllLinesOfContent().get(i)).get(0))).getWordIndexOfSura()));
                    return;
                }
                verticalTextView.setText("");
                --i;
            }
        }
    }

    private void drawPageNumber() {
        if (StaticObjects.contentWords == null || alQuranDisplayListViewAdapter == null) {
            return;
        }
        if (listView.getLastVisiblePosition() == -1) {
            return;
        }
        if (numberOfRowsInt <= 0) {
            finish();
            return;
        }
        final int n = 1 + alQuranDisplayListViewAdapter.getFirstVisibleItem() / numberOfRowsInt;
        final int n2 = lines.size() / numberOfRowsInt;
        final TextView tvPageNumber = this.tvPageNumber;
        final StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append(" : ");
        sb.append(n2);
        tvPageNumber.setText(sb.toString());
        toolbarBottom.invalidate();
    }

    private void drawSuraName() {
        Log.d(TAG, "drawSuraName: Start");
        if (StaticObjects.contentWords == null || alQuranDisplayListViewAdapter == null) {
            return;
        }
        if (listView.getLastVisiblePosition() == -1) {
            return;
        }
        SpannableStringBuilder text = new SpannableStringBuilder("");
        String currentFontNameForDrawSura = "";
        int lastVisiblePosition;
        if (listView.getLastVisiblePosition() == -1) {
            lastVisiblePosition = 0;
        } else {
            lastVisiblePosition = listView.getLastVisiblePosition();
        }
        if (lastVisiblePosition >= textWrapper.getAllLinesOfContent().size()) {
            lastVisiblePosition = -1 + textWrapper.getAllLinesOfContent().size();
        }
        int n;
        if ((textWrapper.getAllLinesOfContent().get(lastVisiblePosition)).size() == 0) {
            while (true) {
                n = 0;
                if (lastVisiblePosition < 0) {
                    break;
                }
                if ((textWrapper.getAllLinesOfContent().get(lastVisiblePosition)).size() > 0 && (textWrapper.getAllLinesOfContent()
                        .get(lastVisiblePosition)).get(0) < StaticObjects.contentWords.size()) {
                    n = StaticObjects.contentWords.get(textWrapper.getAllLinesOfContent().get(lastVisiblePosition).get(0)).getSuraId();
                    break;
                }
                --lastVisiblePosition;
            }
        } else {
            final int intValue = textWrapper.getAllLinesOfContent().get(lastVisiblePosition).get(0);
            final int size = StaticObjects.contentWords.size();
            n = 0;
            if (intValue < size) {
                n = StaticObjects.contentWords.get(textWrapper.getAllLinesOfContent().get(lastVisiblePosition).get(0)).getSuraId();
            }
        }
        if (n <= 23) {
            currentFontNameForDrawSura = "suras1";
        } else if (n <= 47) {
            currentFontNameForDrawSura = "suras2";
        } else if (n <= 69) {
            currentFontNameForDrawSura = "suras3";
        } else if (n <= 92) {
            currentFontNameForDrawSura = "suras4";
        } else if (n <= 114) {
            currentFontNameForDrawSura = "suras5";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(Character.toString((char) (n + 61440)));
        sb.append(Character.toString('\uf0c8'));
        text.append(sb.toString());
        if (!this.currentFontNameForDrawSura.equals(currentFontNameForDrawSura)) {
            final AssetManager assets = getAssets();
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("fonts/");
            sb2.append(currentFontNameForDrawSura);
            sb2.append(".ttf");
            typefaceSura2 = Typeface.createFromAsset(assets, sb2.toString());
            this.currentFontNameForDrawSura = currentFontNameForDrawSura;
        }
        tvSuraName.setTypeface(typefaceSura2);
        tvSuraName.setTextSize(28.0f);
        tvSuraName.setText(text);
        tvSuraName.setTextColor(-1);
        Log.d(TAG, "drawSuraName: Complete");
    }

    public static void getBitmapFromView(@NonNull final View view) {
        ScrollingStyleQuranActivity.cacheAnimationBitmap = null;
        try {
            if (view.getWidth() > 0 && view.getHeight() > 0) {
                ScrollingStyleQuranActivity.cacheAnimationBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
            } else {
                ScrollingStyleQuranActivity.cacheAnimationBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
            }
            final Canvas canvas = new Canvas(ScrollingStyleQuranActivity.cacheAnimationBitmap);
            final Drawable background = view.getBackground();
            if (background != null) {
                background.draw(canvas);
            } else {
                canvas.drawColor(0);
            }
            view.draw(canvas);
        } catch (OutOfMemoryError outOfMemoryError) {
            Log.d("GenaricQuran", outOfMemoryError.getMessage());
            ScrollingStyleQuranActivity.cacheAnimationBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
        }
    }

    private int getValidLastWordIndexGenaric() {
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        if (firstVisiblePosition >= textWrapper.getAllLinesOfContent().size()) {
            firstVisiblePosition = -1 + textWrapper.getAllLinesOfContent().size();
        }
        return (textWrapper.getAllLinesOfContent().get(firstVisiblePosition)).get(0);
    }

    private void gotoAyah(int i) {
        i = (StaticObjects.contentWords.get(Collections.binarySearch(StaticObjects.contentWords, new WordOfQuran(0, 0, 0, null, null, 0, 0.0f, i), new Comparator<WordOfQuran>() {
            public int compare(WordOfQuran wordOfQuran, WordOfQuran wordOfQuran2) {
                return wordOfQuran.getAyahNumber() - wordOfQuran2.getAyahNumber();
            }
        }))).getLineIndexOfWord();
        if (listView.getLastVisiblePosition() <= i) {
            int lastVisiblePosition = listView.getLastVisiblePosition() - listView.getFirstVisiblePosition();
            if (App.browsingMethod == SCROLL_MODE) {
                listView.smoothScrollToPositionFromTop(listView.getFirstVisiblePosition() + lastVisiblePosition, 0, 1000);
            } else {
                onGotoPage(i / alQuranDisplayListViewAdapter.getNumberOfRowsInt());
                onPageInvalidated();
            }
        }
        if (listView.getFirstVisiblePosition() <= i) {
            return;
        }
        if (App.browsingMethod == SCROLL_MODE) {
            listView.smoothScrollToPositionFromTop(i, 0, 1000);
        } else if (alQuranDisplayListViewAdapter.getNumberOfRowsInt() > 0) {
            onGotoPage(i / alQuranDisplayListViewAdapter.getNumberOfRowsInt());
            onPageInvalidated();
        }
    }

    private void gotoAyahOption() {

        new MaterialDialog.Builder(this).title(R.string.gotoAyah).content(R.string.gotoAyah_content).inputType(3).input(R.string.gotoAyah_content, R.string.prefill, new MaterialDialog.InputCallback() {
            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                try {
                    int parseInt = Integer.parseInt(charSequence.toString());
                    if (parseInt <= 0 || parseInt > (StaticObjects.contentWords.get(StaticObjects.contentWords.size() - 1)).getAyahNumber()) {
                        Toast.makeText(ScrollingStyleQuranActivity.this, R.string.ncorrect_num, Toast.LENGTH_SHORT).show();
                    } else {
                        gotoAyah(parseInt);
                    }
                } catch (Exception unused) {
                    Toast.makeText(ScrollingStyleQuranActivity.this, R.string.ncorrect_num, Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
    }

    private void gotoPageOption() {
        new MaterialDialog.Builder(this).title(R.string.gotoAyah).content(R.string.gotoAyah_content).inputType(3).input(R.string.gotoAyah_content, R.string.prefill, new MaterialDialog.InputCallback() {
            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                try {
                    int parseInt = Integer.parseInt(charSequence.toString()) - 1;
                    int size = lines.size() / numberOfRowsInt;
                    if (parseInt < 0 || parseInt > size) {
                        Toast.makeText(ScrollingStyleQuranActivity.this, R.string.ncorrect_num, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    onGotoPage(parseInt);
                    onPageInvalidated();
                } catch (Exception unused) {
                    Toast.makeText(ScrollingStyleQuranActivity.this, R.string.ncorrect_num, Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
    }

    private void invalidateDisplayInfo() {
        drawGozaaName();
        drawSuraName();
        drawHezp();
        if (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE) {
            drawPageNumber();
        }
    }

    private void loadWordMeaningsIndexesOfSura() {
        final SQLiteDatabase db = new DatabaseHandler(this).getDB();
        new WordMeaningDataManager(db).getWordMeaningVectorOfJoza();
        db.close();
    }

    private void nextJozaa() {
        if (App.currentJozaId <= 30) {
            App.currentWordIndexDurngSuraForRotationScrolling = 0;
            App.currentHighlightWordIndexDurngJoza = 0;
            App.currentWordIndexDurngJozaForRotationScrolling = 0;
            App.currentWordIndexForSoundPlaying = 0;
            App.currentWordIndexHilightForSoundPlay = 0;
            if (StaticObjects.quranMediaPlayer != null) {
                StaticObjects.quranMediaPlayer.stopPlayer();
                StaticObjects.quranMediaPlayer.setNull();
            }
            ++App.currentJozaId;
            startActivity(new Intent(this, ScrollingStyleQuranActivity.class));
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
            finish();
            return;
        }
        App.haveToPlayReciter = false;
    }

    private void nextQuarter() {
        if (App.currentQuarterId < 239) {
            App.currentWordIndexDurngSuraForRotationScrolling = 0;
            App.currentHighlightWordIndexDurngJoza = 0;
            App.currentWordIndexDurngJozaForRotationScrolling = 0;
            App.currentWordIndexForSoundPlaying = 0;
            App.currentWordIndexHilightForSoundPlay = 0;
            App.highLight = false;
            App.isSoundPlaying = false;
            if (StaticObjects.quranMediaPlayer != null) {
                StaticObjects.quranMediaPlayer.stopPlayer();
                StaticObjects.quranMediaPlayer.setNull();
            }
            StaticObjects.contentWords = null;
            ++App.currentQuarterId;
            startActivity(new Intent(this, ScrollingStyleQuranActivity.class));
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
            finish();
            return;
        }
        App.haveToPlayReciter = false;
    }

    private void nextSura() {
        if (App.currentSuraId < 114) {
            if (StaticObjects.quranMediaPlayer != null) {
                StaticObjects.quranMediaPlayer.stopPlayer();
                StaticObjects.quranMediaPlayer.setNull();
            }
            App.currentHighlightWordIndexDurngSura = 0;
            App.currentWordIndexDurngSuraForRotationScrolling = 0;
            App.currentWordIndexForSoundPlaying = 0;
            App.currentWordIndexHilightForSoundPlay = 0;
            ++App.currentSuraId;
            final StringBuilder sb = new StringBuilder();
            sb.append("nextSura() new App.currentSuraId=");
            sb.append(App.currentSuraId);
            Log.d("GenaricQuran", sb.toString());
            startActivity(new Intent(this, ScrollingStyleQuranActivity.class));
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
            finish();
            return;
        }
        App.haveToPlayReciter = false;
    }

    private void openNextJoza() {
        if (App.currentSuraId > 1) {
            --App.currentSuraId;
            startActivity(new Intent(this, ScrollingStyleQuranActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            finish();
        }
    }

    private void openPrevJoza() {
        if (App.currentSuraId > 1) {
            --App.currentSuraId;
            startActivity(new Intent(this, ScrollingStyleQuranActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            finish();
        }
    }

    private void pauseSound() {
        StaticObjects.quranMediaPlayer.pausePlayer();
        invalidateOptionsMenu();
        App.haveToPlayReciter = false;
    }

    private void playNextSura() {
    }

    private void playSound() {
        if (StaticObjects.quranMediaPlayer == null) {
            return;
        }
        if (StaticObjects.quranMediaPlayer.isPaused()) {
            StaticObjects.quranMediaPlayer.resumePlayer();
        } else {
            StaticObjects.quranMediaPlayer.playSound(textWrapper.getAllLinesOfContent().get(listView.getFirstVisiblePosition()).get(0));
            App.currentWordIndexHilightForSoundPlay = textWrapper.getAllLinesOfContent().get(listView.getFirstVisiblePosition()).get(0);
        }
        App.isSoundPlaying = true;
        App.haveToPlayReciter = true;
        invalidateOptionsMenu();
    }

    private void prevJozaa() {
        if (App.currentJozaId > 1) {
            App.currentWordIndexDurngSuraForRotationScrolling = 0;
            if (StaticObjects.quranMediaPlayer != null) {
                StaticObjects.quranMediaPlayer.stopPlayer();
                StaticObjects.quranMediaPlayer.setNull();
            }
            App.currentWordIndexHilightForSoundPlay = 0;
            --App.currentJozaId;
            startActivity(new Intent(this, ScrollingStyleQuranActivity.class));
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
            finish();
            return;
        }
        App.haveToPlayReciter = false;
    }

    private void prevQuarter() {
        if (App.currentQuarterId > 0) {
            App.currentWordIndexDurngSuraForRotationScrolling = 0;
            App.currentHighlightWordIndexDurngJoza = 0;
            App.currentWordIndexDurngJozaForRotationScrolling = 0;
            App.currentWordIndexForSoundPlaying = 0;
            App.currentWordIndexHilightForSoundPlay = 0;
            if (StaticObjects.quranMediaPlayer != null) {
                StaticObjects.quranMediaPlayer.stopPlayer();
                StaticObjects.quranMediaPlayer.setNull();
            }
            --App.currentQuarterId;
            startActivity(new Intent(this, ScrollingStyleQuranActivity.class));
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
            finish();
            return;
        }
        App.haveToPlayReciter = false;
    }

    private void prevSura() {
        if (App.currentSuraId > 1) {
            App.currentWordIndexDurngSuraForRotationScrolling = 0;
            if (StaticObjects.quranMediaPlayer != null) {
                StaticObjects.quranMediaPlayer.stopPlayer();
                StaticObjects.quranMediaPlayer.setNull();
            }
            App.currentWordIndexHilightForSoundPlay = 0;
            --App.currentSuraId;
            startActivity(new Intent(this, ScrollingStyleQuranActivity.class));
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
            finish();
            return;
        }
        App.haveToPlayReciter = false;
    }

    private void reset() {
        if (wrappingThread != null && wrappingThread.isAlive()) {
            wrappingThread.interrupt();
        }
        if (StaticObjects.quranMediaPlayer != null) {
            StaticObjects.quranMediaPlayer.stopPlayer();
            StaticObjects.quranMediaPlayer.setNull();
            StaticObjects.quranMediaPlayer = null;
        }
    }

    private void saveListReadingposition() {
        if (textWrapper == null) {
            return;
        }
        if (textWrapper.getAllLinesOfContent() == null) {
            return;
        }
        new LastReadingPositionManager((Context) this);
    }

    private void stopSound() {
        StaticObjects.quranMediaPlayer.stopPlayer();
        invalidateOptionsMenu();
        App.haveToPlayReciter = false;
    }

    private void turnNightReadingModeOff() {
        new SettingsManager(this).setSettings(SettingsManager.NEIGHT_READING_MODE, false);
        finish();
        startActivity(getIntent());
    }

    private void turnNightReadingModeOn() {
        new SettingsManager(this).setSettings(SettingsManager.NEIGHT_READING_MODE, true);
        finish();
        startActivity(getIntent());
    }

    private void turnWordMeaningModeOff() {
        new SettingsManager(this).setSettings(SettingsManager.WORD_MEANING, false);
        finish();
        startActivity(getIntent());
    }

    private void turnWordMeaningModeOn() {
        new SettingsManager(this).setSettings(SettingsManager.WORD_MEANING, true);
        finish();
        startActivity(getIntent());
    }

    public boolean dispatchGenericMotionEvent(final MotionEvent motionEvent) {
        return App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE || super.dispatchGenericMotionEvent(motionEvent);
    }

    public boolean dispatchTouchEvent(@NonNull final MotionEvent motionEvent) {
        if (App.browsingMethod == ScrollingStyleQuranActivity.SCROLL_MODE) {
            return super.dispatchTouchEvent(motionEvent);
        }
        final int action = motionEvent.getAction();
        final int startDragX = (int) motionEvent.getX();
        switch (action) {
            case 2: {
                if (this.startDragX == 0) {
                    return super.dispatchTouchEvent(motionEvent);
                }
                if (Math.abs(this.startDragX - startDragX) > 150) {
                    if (this.startDragX - startDragX < 0) {
                        this.doNextPage();
                    } else {
                        this.doPrevPage();
                    }
                    this.startDragX = 0;
                    break;
                }
                break;
            }
            case 1: {
                this.startDragX = 0;
                break;
            }
            case 0: {
                this.startDragX = startDragX;
                break;
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void doNext() {
        if (App.browsingMethod == ScrollingStyleQuranActivity.AJZAA_MODE) {
            nextJozaa();
            return;
        }
        if (App.browsingMethod == ScrollingStyleQuranActivity.QUARTER_MODE) {
            nextQuarter();
            return;
        }
        if (App.browsingMethod == ScrollingStyleQuranActivity.SURAS_MODE) {
            nextSura();
        }
    }

    public void doNextPage() {
        gotoPage(1 + alQuranDisplayListViewAdapter.getFirstVisibleItem() / numberOfRowsInt);
    }

    public void doPrev() {
        if (App.browsingMethod == ScrollingStyleQuranActivity.AJZAA_MODE) {
            prevJozaa();
            return;
        }
        if (App.browsingMethod == ScrollingStyleQuranActivity.QUARTER_MODE) {
            prevQuarter();
            return;
        }
        if (App.browsingMethod == ScrollingStyleQuranActivity.SURAS_MODE) {
            prevSura();
        }
    }

    public int getNumberOfRows() {
        int n;
        if (getResources().getConfiguration().orientation == 1) {
            n = App.listOfQuranHeight[0];
        } else {
            n = App.listOfQuranHeight[1];
        }
        final float applyDimension = TypedValue.applyDimension(1, 3.0f, getResources().getDisplayMetrics());
        final double n2 = this.fontManager.getFontSize();
        final double n3 = n2 * 2.4;
        final double n4 = applyDimension;
        final double n5 = n3 + n4;
        final double n6 = n;
        return (int) (n6 / n5);
    }

    public void gotoPage(final int n) {
        final int firstVisibleItem = alQuranDisplayListViewAdapter.getFirstVisibleItem() / numberOfRowsInt;
        final int size = lines.size() / numberOfRowsInt;
        if (n == -1) {
            App.displayLastPageOnLoad = true;
            if (App.browsingContent == ScrollingStyleQuranActivity.AJZAA_MODE) {
                prevJozaa();
                return;
            }
            if (App.browsingContent == ScrollingStyleQuranActivity.SURAS_MODE) {
                prevSura();
                return;
            }
            if (App.browsingContent == ScrollingStyleQuranActivity.QUARTER_MODE) {
                prevQuarter();
            }
        } else if (n == size) {
            App.displayLastPageOnLoad = false;
            if (App.browsingContent == ScrollingStyleQuranActivity.AJZAA_MODE) {
                nextJozaa();
                return;
            }
            if (App.browsingContent == ScrollingStyleQuranActivity.SURAS_MODE) {
                nextSura();
                return;
            }
            if (App.browsingContent == ScrollingStyleQuranActivity.QUARTER_MODE) {
                nextQuarter();
            }
        } else {
            String s;
            if (firstVisibleItem < n) {
                s = "next";
            } else {
                s = "prev";
            }
            final ImageView imageView = new ImageView(this);
            final FrameLayout frameLayout = findViewById(android.R.id.content);
            final LinearLayout linearLayout = frameLayout.findViewById(R.id.rootContainer);
            getBitmapFromView(frameLayout);
            if (ScrollingStyleQuranActivity.cacheAnimationBitmap.getHeight() > 10) {
                imageView.setImageBitmap(ScrollingStyleQuranActivity.cacheAnimationBitmap);
                frameLayout.addView(imageView, 1, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                Animation animation;
                if (s.equals("next")) {
                    animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
                } else {
                    animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
                }
                animation.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                        if (StaticObjects.quranMediaPlayer != null) {
                            StaticObjects.quranMediaPlayer.pauseListInvalidate = true;
                        }
                        currentFromLineIndex = n * numberOfRowsInt;
                        alQuranDisplayListViewAdapter.setFirstVisibleItem(currentFromLineIndex);
                        alQuranDisplayListViewAdapter.notifyDataSetChanged();
                        alQuranDisplayListViewAdapter.notifyDataSetInvalidated();
                        currentFromLineIndex = alQuranDisplayListViewAdapter.getFirstVisibleItem();
                        listView.setSelection(alQuranDisplayListViewAdapter.getFirstVisibleItem());
                    }

                    public void onAnimationEnd(Animation animation) {
                        frameLayout.removeView(imageView);
                        if (StaticObjects.quranMediaPlayer != null) {
                            StaticObjects.quranMediaPlayer.pauseListInvalidate = false;
                        }
                        invalidateDisplayInfo();
                        cacheAnimationBitmap = null;
                    }
                });
                imageView.startAnimation(animation);
                Animation animation2;
                if (s.equals("next")) {
                    animation2 = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
                } else {
                    animation2 = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
                }
                linearLayout.startAnimation(animation2);
                return;
            }
            if (StaticObjects.quranMediaPlayer != null) {
                StaticObjects.quranMediaPlayer.pauseListInvalidate = true;
            }
            currentFromLineIndex = n * numberOfRowsInt;
            alQuranDisplayListViewAdapter.setFirstVisibleItem(currentFromLineIndex);
            alQuranDisplayListViewAdapter.notifyDataSetChanged();
            alQuranDisplayListViewAdapter.notifyDataSetInvalidated();
            currentFromLineIndex = alQuranDisplayListViewAdapter.getFirstVisibleItem();
            listView.setSelection(alQuranDisplayListViewAdapter.getFirstVisibleItem());
            if (StaticObjects.quranMediaPlayer != null) {
                StaticObjects.quranMediaPlayer.pauseListInvalidate = false;
            }
            invalidateDisplayInfo();
            ScrollingStyleQuranActivity.cacheAnimationBitmap = null;
        }
    }

    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 123) {
            final SettingsManager settingsManager = new SettingsManager(this);
            final Boolean tafsirEnabled = (Boolean) settingsManager.getSettings(SettingsManager.TAFSIR_ENABLED, Boolean.class);
            final String tafsirId = (String) settingsManager.getSettings(SettingsManager.DEFAULT_TAFSIR, String.class);
            final int spToPixels = CommonMethods.spToPixels(Integer.parseInt((String) settingsManager.getSettings(SettingsManager.TAFSIR_FONT_SIZE, String.class)),
                    getResources().getDisplayMetrics());
            if (tafsirEnabled != this.tafsirEnabled || tafsirId != this.tafsirId || spToPixels != tafsirFontSize
                    || (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE && tafsirEnabled)) {
                final Intent intent2 = getIntent();
                if (App.browsingMethod == ScrollingStyleQuranActivity.PAGING_MODE) {
                    Toast.makeText(getApplicationContext(), R.string.cant_display_tafsir_in_pagging, Toast.LENGTH_LONG).show();
                    App.browsingMethod = ScrollingStyleQuranActivity.SCROLL_MODE;
                }
                this.tafsirEnabled = tafsirEnabled;
                this.tafsirId = tafsirId;
                finish();
                startActivity(intent2);
            }
        }
    }

    public void onBackPressed() {
        if (countDownTimerForAutoScrolling != null) {
            countDownTimerForAutoScrolling.cancel();
            countDownTimerForAutoScrolling = null;
        }
        final LastReadingPositionManager lastReadingPositionManager = new LastReadingPositionManager(this);
        final int browsingMethod = App.browsingMethod;
        final int browsingContent = App.browsingContent;
        int n;
        if (App.browsingContent == ScrollingStyleQuranActivity.SURAS_MODE) {
            n = App.currentSuraId;
        } else {
            n = App.currentJozaId;
        }
        lastReadingPositionManager.addBookmark(browsingMethod, browsingContent, n, getValidLastWordIndexGenaric());
        if (StaticObjects.quranMediaPlayer != null) {
            if (App.isSoundPlaying) {
                StaticObjects.quranMediaPlayer.stopPlayer();
            }
            StaticObjects.quranMediaPlayer.setNull();
        }
        if (StaticObjects.mediaService != null && StaticObjects.mediaService.myNotificationManager != null) {
            StaticObjects.mediaService.myNotificationManager.cancel(MediaService.NOTIFICATION_ID);
        }
        if (fromClassName != null && fromClassName.equals(ShowPrayAlarmActivity.class.getName())) {
            finish();
            final Intent intent = new Intent(this, QuranActivity.class);
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//            intent.addFlags(0x10008000);
            startActivity(intent);
            return;
        }
        super.onBackPressed();
        StaticObjects.contentWords = null;
    }

    public void onConfigurationChanged(@NonNull final Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (wrappingThread.isAlive()) {
            wrappingThread.interrupt();
        }
        if (configuration.orientation == 2) {
            wrappingThread.isAlive();
        } else if (configuration.orientation == 1) {
            wrappingThread.isAlive();
        }
        if (StaticObjects.quranMediaPlayer != null && App.isSoundPlaying) {
            StaticObjects.quranMediaPlayer.stopPlayer();
            StaticObjects.quranMediaPlayer.setNull();
            StaticObjects.quranMediaPlayer = null;
        }
    }

    public boolean onCreateOptionsMenu(@Nullable final Menu menu) {
        if (menu == null) {
            return true;
        }
        if (StaticObjects.contentWords == null) {
            return true;
        }
        final AudioSettingsManager audioSettingsManager = new AudioSettingsManager(this);
        currentReciter = audioSettingsManager.getCurrentReciter();
        final Reciter currentReciter = this.currentReciter;
        boolean soundFilesExists = false;
        if (currentReciter != null) {
            final List contentWords = StaticObjects.contentWords;
            soundFilesExists = false;
            if (contentWords != null) {
                final int currentWordIndexHilightForSoundPlay = App.currentWordIndexHilightForSoundPlay;
                soundFilesExists = false;
                if (currentWordIndexHilightForSoundPlay >= 0) {
                    if (App.currentWordIndexHilightForSoundPlay >= StaticObjects.contentWords.size()) {
                        App.currentWordIndexHilightForSoundPlay = 0;
                    }
                    soundFilesExists = audioSettingsManager.isSoundFilesExists(this.currentReciter.getReciterId(), StaticObjects.contentWords.get(App.currentWordIndexHilightForSoundPlay).getSuraId());
                }
            }
        }
        menu.clear();
        getMenuInflater().inflate(R.menu.scrolling_quran_activity_menu, menu);
        if (!soundFilesExists) {
            menu.removeItem(R.id.play_sound_menu_item);
            menu.removeItem(R.id.stop_sound_menu_item);
            menu.removeItem(R.id.pause_sound_menu_item);
        } else if (StaticObjects.quranMediaPlayer != null && StaticObjects.quranMediaPlayer.getMediaPlayerObj() != null && StaticObjects.quranMediaPlayer.getMediaPlayerObj().isPlaying()) {
            menu.removeItem(R.id.play_sound_menu_item);
        } else {
            menu.removeItem(R.id.stop_sound_menu_item);
            menu.removeItem(R.id.pause_sound_menu_item);
        }
        if (soundFilesExists) {
            menu.removeItem(R.id.download_audio_menu_item);
        }
        final SettingsManager settingsManager = new SettingsManager(this);
        neightReadingMode = (Boolean) settingsManager.getSettings(SettingsManager.NEIGHT_READING_MODE, Boolean.class);
        if (!neightReadingMode) {
            menu.removeItem(R.id.night_reading_off);
        } else {
            menu.removeItem(R.id.night_reading_on);
        }
        if (!(wordMeaingEnabled = (boolean) settingsManager.getSettings(SettingsManager.WORD_MEANING, Boolean.class))) {
            menu.removeItem(R.id.word_meaning_off);
        } else {
            menu.removeItem(R.id.word_meaning_on);
        }
        if (App.browsingContent == ScrollingStyleQuranActivity.AJZAA_MODE) {
            menu.findItem(R.id.next).setTitle(R.string.next_Joza);
            menu.findItem(R.id.prev).setTitle(R.string.prev_Joza);
        } else if (App.browsingContent == ScrollingStyleQuranActivity.SURAS_MODE) {
            menu.findItem(R.id.next).setTitle(R.string.next_sura);
            menu.findItem(R.id.prev).setTitle(R.string.prev_sura);
        } else if (App.browsingContent == ScrollingStyleQuranActivity.QUARTER_MODE) {
            menu.findItem(R.id.next).setTitle(R.string.next_quarter);
            menu.findItem(R.id.prev).setTitle(R.string.prev_quarter);
        }
        if (App.browsingMethod == ScrollingStyleQuranActivity.SCROLL_MODE) {
            menu.removeItem(R.id.gotoPage);
        }
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.word_meaning_on: {
                turnWordMeaningModeOn();
                break;
            }
            case R.id.word_meaning_off: {
                turnWordMeaningModeOff();
                break;
            }
//            case R.id.tahfiz_by_typing: {
//                tahfeezOptions();
//                break;
//            }
//            case R.id.tafsir_options: {
//                startActivityForResult(new Intent(this, TafsirPrefsActivity.class), 123);
//                break;
//            }
            case R.id.stop_sound_menu_item: {
                stopSound();
                break;
            }
            case R.id.prev: {
                if (App.browsingContent == ScrollingStyleQuranActivity.AJZAA_MODE) {
                    prevJozaa();
                    break;
                }
                if (App.browsingContent == ScrollingStyleQuranActivity.SURAS_MODE) {
                    prevSura();
                    break;
                }
                if (App.browsingContent == ScrollingStyleQuranActivity.QUARTER_MODE) {
                    prevQuarter();
                    break;
                }
                break;
            }
            case R.id.play_sound_menu_item: {
                playSound();
                break;
            }
            case R.id.pause_sound_menu_item: {
                pauseSound();
                break;
            }
            case R.id.night_reading_on: {
                turnNightReadingModeOn();
                break;
            }
            case R.id.night_reading_off: {
                turnNightReadingModeOff();
                break;
            }
            case R.id.next: {
                if (App.browsingContent == ScrollingStyleQuranActivity.AJZAA_MODE) {
                    nextJozaa();
                    break;
                }
                if (App.browsingContent == ScrollingStyleQuranActivity.SURAS_MODE) {
                    nextSura();
                    break;
                }
                if (App.browsingContent == ScrollingStyleQuranActivity.QUARTER_MODE) {
                    nextQuarter();
                    break;
                }
                break;
            }
            case R.id.gotoPage: {
                gotoPageOption();
                break;
            }
            case R.id.gotoAyah: {
                gotoAyahOption();
                break;
            }
            case R.id.download_audio_menu_item: {
                downloadAudio();
                break;
            }
            case R.id.add_bookmark_menu_item: {
                addBookmark();
                break;
            }
        }
        return true;
    }

    public void onGotoPage(final int n) {
        gotoPage(n);
    }

    public boolean onKeyDown(final int n, final KeyEvent keyEvent) {
        if (n == 4) {
            if (wrappingThread != null && wrappingThread.isAlive()) {
                wrappingThread.interrupt();
            }
            if (StaticObjects.quranMediaPlayer != null) {
                StaticObjects.quranMediaPlayer.stopPlayer();
                StaticObjects.quranMediaPlayer.setNull();
            }
            App.haveToPlayReciter = false;
            saveListReadingposition();
        }
        return super.onKeyDown(n, keyEvent);
    }

    public void onNextPagePlaying() {
        doNextPage();
    }

    public void onPageInvalidated() {
        invalidateDisplayInfo();
    }

    protected void onPause() {
        saveListReadingposition();
        super.onPause();
    }

    public boolean onPrepareOptionsMenu(final Menu menu) {
        onCreateOptionsMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    public void onPrevPagePlaying() {
        doPrevPage();
    }

    public void onProgressChanged(final SeekBar seekBar, final int n, final boolean b) {
        final int abs = Math.abs(n - 100);
        if (countDownTimerForAutoScrolling != null) {
            countDownTimerForAutoScrolling.cancel();
            countDownTimerForAutoScrolling = null;
        }
        if (abs <= 95) {
            final int i2 = abs < 4 ? 2 : abs * 2;
            countDownTimerForAutoScrolling = new CountDownTimer(Long.MAX_VALUE, (long) i2) {
                public void onFinish() {
                }

                public void onTick(long j) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            listView.smoothScrollBy(13, i2);
                        }
                    });
                }
            }.start();
        }
    }

    public void onRequiredPageReady() {
    }

    protected void onRestoreInstanceState(final Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    public void onSoundFilePlayingComplete() {
        if (StaticObjects.quranMediaPlayer == null) {
            return;
        }
        final int n = 1 + StaticObjects.quranMediaPlayer.getSuraId();
        if (n > 114) {
            App.haveToPlayReciter = false;
            return;
        }
        final AudioSettingsManager audioSettingsManager = new AudioSettingsManager((Context) this);
        currentReciter = audioSettingsManager.getCurrentReciter();
        if (audioSettingsManager.isSoundFilesExists(currentReciter.getReciterId(), n)) {
            playNextSura();
            return;
        }
        App.haveToPlayReciter = false;
    }

    public void onSoundPlayingComplete() {
        if (repeatSesionEnable) {
            if (App.browsingContent == ScrollingStyleQuranActivity.AJZAA_MODE) {
                --App.currentJozaId;
            } else if (App.browsingContent == ScrollingStyleQuranActivity.SURAS_MODE) {
                --App.currentSuraId;
            } else if (App.browsingContent == ScrollingStyleQuranActivity.QUARTER_MODE) {
                --App.currentQuarterId;
            }
        }
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        if (App.browsingContent == ScrollingStyleQuranActivity.AJZAA_MODE) {
            nextJozaa();
            return;
        }
        if (App.browsingContent == ScrollingStyleQuranActivity.SURAS_MODE) {
            nextSura();
            return;
        }
        if (App.browsingContent == ScrollingStyleQuranActivity.QUARTER_MODE) {
            nextQuarter();
        }
    }

    public void onStartTrackingTouch(final SeekBar seekBar) {
    }

    protected void onStop() {
        saveListReadingposition();
        super.onStop();
    }

    public void onStopTrackingTouch(final SeekBar seekBar) {
    }

    public void onTextWrapComplete(final boolean b) {
        runOnUiThread(new Runnable() {
            public void run() {
                Log.d(TAG, "run: displaypage");
                displaypage();
            }
        });
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return false;
    }

    public void onWordClick(final int i) {
        runOnUiThread(new Runnable() {
            public void run() {
                WordMeaningPopup wordMeaningPopup = new WordMeaningPopup(ScrollingStyleQuranActivity.this, 1);
                if (StaticObjects.contentWords != null && StaticObjects.contentWords.size() != 0 && i < StaticObjects.contentWords.size()) {
                    WordOfQuran wordOfQuran = StaticObjects.contentWords.get(i);
                    if (wordOfQuran != null && wordOfQuran.getWordMeaning() != null && wordOfQuran.getWordMeaning() != "") {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(wordOfQuran.getPlanTextForMeaning());
                        stringBuilder.append(":");
                        wordMeaningPopup.addText(stringBuilder.toString());
                        wordMeaningPopup.addText(wordOfQuran.getWordMeaning().replace(" و ", " و"));
                        wordMeaningPopup.show(findViewById(android.R.id.content));
                        wordMeaningPopup.setAnimStyle(4);
                    }
                }
            }
        });
    }

    public void onWordClick(final int n, final int n2) {
    }
}
