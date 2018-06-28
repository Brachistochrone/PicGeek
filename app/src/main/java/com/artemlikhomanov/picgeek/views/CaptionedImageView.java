package com.artemlikhomanov.picgeek.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.artemlikhomanov.picgeek.R;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/*Класс кастомного виджета. Заполняет макет, обрабатывает события изменения макета, создает область размытия под TextView*/
public class CaptionedImageView extends FrameLayout implements View.OnLayoutChangeListener {

    @BindView(R.id.item_title)
    TextView mTextView;
    @BindView(R.id.image)
    SquareImageView mImageView;

    @BindColor(R.color.list_item_scrim)
    int mScrimColor;

    private Drawable mDrawable;

    public CaptionedImageView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CaptionedImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CaptionedImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CaptionedImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public SquareImageView getImageView() {
        return mImageView;
    }

    /*Метод вызывается при каждом изменении размера TextView
    * Метод получает виджет, изменивший размер, а так же его новую и прежнюю позицию*/
    @Override
    public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        /*проверить видим ли виджет*/
        if (view.getVisibility() != VISIBLE) {
            return;
        }

        int height = bottom - top;
        int width = right - left;

        /*если высота или ширина виджета нулевая*/
        if (height == 0 || width == 0) {
            return;
        }

        updateBlur();
    }

    /*Метод обновляет ImageView, вызывает размытие и сохраняет ссылку на картинку в переменной*/
    public void setImageResource(@DrawableRes int drawableResourceId) {
        mDrawable = getResources().getDrawable(drawableResourceId);
        mImageView.setImageDrawable(mDrawable);
        updateBlur();
    }

    /*Метод создает эффект размытия под TextView*/
    private void updateBlur() {

        /*проверить является ли холст ImageView наследником BitmapDrawable, потому что можно размывать
        * только растровые изображения*/
        if (!(mDrawable instanceof BitmapDrawable)) {
            return;
        }

        int textViewHeight = mTextView.getHeight();
        if (textViewHeight == 0) {
            return;
        }

        /*вычислить отношение высоты TextView к высоте ImageView, чтобы определить долю изображения
        * которую нужно размыть*/
        float ratio = (float) textViewHeight / mImageView.getHeight();

        /*получить Bitmap*/
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mDrawable;
        Bitmap originalBitmap = bitmapDrawable.getBitmap();

        /*определить высоту области размытия*/
        int height = (int) (ratio * originalBitmap.getHeight());

        /*определить Y координату первого пикселя в исходном Bitmap с которого начнется создание
        * Bitmap для размытия*/
        int y = originalBitmap.getHeight() - height;

        /*создать новый Bitmap равный области размытия из исходного Bitmap
        * аргументы:
        * SourceBitmap, X & Y - координаты первого пикселя - точка начала нового Bitmap,
        * width - количество пикселей в каждом ряду,
        * height - количество рядов*/
        Bitmap portionToBlur = Bitmap.createBitmap(originalBitmap, 0, y, originalBitmap.getWidth(), height);

        /*создать копию Bitmap, но с новыми настройками:
        * Bitmap.Config.ARGB_8888 - хранить каждый пиксель в 4 байтах (ARGB)
        * isMutable true - разрешить модифицировать пиксели*/
        Bitmap blurredBitmap = portionToBlur.copy(Bitmap.Config.ARGB_8888, true);

        /*выполнить размытие пикселей*/
        /*RenderScript - оболочка для проведения интенсивных математических вычислений
        * использует параллельное вычисление на всех доступных ядрах CPU и GPU*/
        RenderScript renderScript = RenderScript.create(getContext());
        /*ScriptIntrinsicBlur - Gaussian blur filter. Applies a gaussian blur of the specified radius to all elements of an allocation.*/
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        /*создать фрагмент памяти Allocation с исходными данными для RenderScript*/
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, portionToBlur);
        /*создать фрагмент памяти Allocation с выходными данными для RenderScript*/
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, blurredBitmap);
        /*установить радиус размытия*/
        theIntrinsic.setRadius(10f);
        /*задать для фильтра источник для размытия*/
        theIntrinsic.setInput(tmpIn);
        /*применить фильтр ко входным данным и сохранить результат в выходных данных*/
        theIntrinsic.forEach(tmpOut);
        /*скопировать полученные данные в Bitmap*/
        tmpOut.copyTo(blurredBitmap);

        /*затенить полученную размытую область, окрасив ее в заданный цвет*/
        new Canvas(blurredBitmap).drawColor(mScrimColor);

        /*создать новый Bitmap из исходного Bitmap и размытого Bitmap*/
        /*копировать исходный Bitmap с новыми настройками*/
        Bitmap newBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        /*наложить размытый Bitmap на новый Bitmap по координатам X & Y*/
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(blurredBitmap, 0, y, new Paint());

        /*отобразить новый Bitmap в ImageView*/
        mImageView.setImageBitmap(newBitmap);
    }

    /*метод отрисовывает виджет и заполняет его*/
    private void init(Context context) {
        View view = inflate(context, R.layout.captioned_image_view, this);
        ButterKnife.bind(this, view);
        mTextView.addOnLayoutChangeListener(this);
    }
}
