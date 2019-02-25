package com.theabsolutecompany.stickercentral;import android.animation.ObjectAnimator;import android.graphics.Bitmap;import android.os.Build;import android.os.Bundle;import android.support.annotation.RequiresApi;import android.support.v7.widget.CardView;import android.transition.Transition;import android.util.DisplayMetrics;import android.widget.FrameLayout;import android.widget.ImageView;import com.theabsolutecompany.stickercentral.utils.DecodeBitmapTask;public class DetailsActivity extends AddStickerPackActivity implements DecodeBitmapTask.Listener {    static final String BUNDLE_IMAGE_ID = "BUNDLE_IMAGE_ID";    private ImageView imageView;    private DecodeBitmapTask decodeBitmapTask;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_details);        final int smallResId = getIntent().getIntExtra(BUNDLE_IMAGE_ID, -1);        if (smallResId == -1) {            finish();            return;        }	    FrameLayout addbutton=findViewById(R.id.add_to_whatsapp_button);        imageView = findViewById(R.id.image);        imageView.setImageResource(smallResId);        addbutton.setOnClickListener(v -> finish());        imageView.setOnClickListener(view -> DetailsActivity.super.onBackPressed());        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {            loadFullSizeBitmap(smallResId);        } else {            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {                private boolean isClosing = false;                @Override public void onTransitionPause(Transition transition) {}                @Override public void onTransitionResume(Transition transition) {}                @Override public void onTransitionCancel(Transition transition) {}                @Override public void onTransitionStart(Transition transition) {                    if (isClosing) {                        addCardCorners();                    }                }                @Override public void onTransitionEnd(Transition transition) {                    if (!isClosing) {                        isClosing = true;                        removeCardCorners();                        loadFullSizeBitmap(smallResId);                    }                }            });        }    }    @Override    protected void onPause() {        super.onPause();        if (isFinishing() && decodeBitmapTask != null) {            decodeBitmapTask.cancel(true);        }    }    private void addCardCorners() {        final CardView cardView = findViewById(R.id.card);        cardView.setRadius(25f);    }    private void removeCardCorners() {        final CardView cardView = findViewById(R.id.card);        ObjectAnimator.ofFloat(cardView, "radius", 0f).setDuration(50).start();    }    private void loadFullSizeBitmap(int smallResId) {        int bigResId;        switch (smallResId) {            case R.drawable.val5:bigResId=R.drawable.sl13;break;            case R.drawable.porn:bigResId=R.drawable.sl14;break;            case R.drawable.spidey: bigResId=R.drawable.sl0;break;            case R.drawable.pubg: bigResId = R.drawable.sl1; break;            case R.drawable.ariana: bigResId = R.drawable.sl2; break;            case R.drawable.bby: bigResId = R.drawable.sl3; break;            case R.drawable.got: bigResId = R.drawable.sl4; break;            case R.drawable.dead25: bigResId = R.drawable.sl5; break;            case R.drawable.elon: bigResId = R.drawable.sl6; break;            case R.drawable.harley: bigResId = R.drawable.sl7; break;            case R.drawable.homer: bigResId = R.drawable.sl8; break;            case R.drawable.rage: bigResId = R.drawable.sl9; break;            case R.drawable.scream: bigResId = R.drawable.sl10; break;            case R.drawable.tuziki: bigResId = R.drawable.sl11; break;            case R.drawable.love: bigResId = R.drawable.sl12; break;            case R.drawable.wolv:bigResId=R.drawable.sl15;break;            default: bigResId = R.drawable.webh;        }        final DisplayMetrics metrics = new DisplayMetrics();        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);        final int w = metrics.widthPixels;        final int h = metrics.heightPixels;        decodeBitmapTask = new DecodeBitmapTask(getResources(), bigResId, w, h, this);        decodeBitmapTask.execute();    }    @Override    public void onPostExecuted(Bitmap bitmap) {        imageView.setImageBitmap(bitmap);    }}