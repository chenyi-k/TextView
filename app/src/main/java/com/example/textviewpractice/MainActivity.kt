package com.example.textviewpractice

import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.Animatable
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.format.DateUtils
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.text.style.ImageSpan
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.example.textviewpractice.emoji.IconFontSpan
import com.example.textviewpractice.emoji.SpeedSignDrawable
import com.example.textviewpractice.explosion.ExplosionField
import com.example.textviewpractice.fraction.FractionTagHandler
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private var mExplosionField: ExplosionField? = null

    private val mAllSpan: TextView by lazy {
        findViewById(R.id.mAllExampleSpan)
    }

    private val mText by lazy {
        mAllSpan.text.toString()
    }
    private val mSpannableString:SpannableString by lazy {
        SpannableString(mText)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        initView()

        startAnimation()
        customFont()
        gradient()
        picFont()
        html()
        clickableSpan()
        fraction()
        allExampleSpan()
//        allExampleSpan2()

        allExampleSpanParagraphStyle()
        leadingMarginSpan()
        emojiSpan()
        setAnimatedRainbow()
//        explosion(findViewById(R.id.mExplosion))

    }

//    private fun initView() {
////        mAllSpan
//    }

    //Compound Drawable 動畫
    private fun startAnimation() {
        val iAnimation: TextView = findViewById(R.id.mAnimation)
        val iAnimationDrawables = iAnimation.compoundDrawables
        for (iDrawable in iAnimationDrawables) {
            if (iDrawable != null && iDrawable is Animatable) {
                (iDrawable as Animatable).start()
            }
        }
    }

    /***
     * 自定義字體 (將字體放入 assets/fonts/)
     */
    private fun customFont() {
        val iCustomFont: TextView = findViewById(R.id.mCustomFont)
        val iTypeface = Typeface.createFromAsset(assets, "fonts/Ruthie.ttf")
        iCustomFont.typeface = iTypeface
    }

    /***
     * 漸層
     */
    private fun gradient() {
        val iGradientFont: TextView = findViewById(R.id.mGradientFont)

        //漸變起始點座標（x0,y0）, 漸變結束點座標（x1,y1）, 漸變起始點顏色 ,漸變結束點顏色, Shader.TileMode
        iGradientFont.paint.shader = LinearGradient(
            0f, 0f, 0f, iGradientFont.textSize,
            Color.RED, Color.BLUE, Shader.TileMode.MIRROR
        )
    }

    /***
     * 圖片填充文字
     */
    private fun picFont() {
        val iPicFont: TextView = findViewById(R.id.mPicFont)
        val iBitmap = BitmapFactory.decodeResource(resources, R.drawable.cheetah_tile)
        iPicFont.paint.shader =
            BitmapShader(iBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
    }

    /***
     * 多樣式
     */
    @RequiresApi(Build.VERSION_CODES.N)
    private fun html() {
        val iHtmlFont: TextView = findViewById(R.id.mHtmlFont)

        val iHtml = getString(R.string.from_html_text)
//        mHtmlFont.text = Html.fromHtml(html, ResourceImageGetter(this), null) //原本
        iHtmlFont.text = HtmlCompat.fromHtml(iHtml, HtmlCompat.FROM_HTML_MODE_LEGACY, ResourceImageGetter(this), null)
        iHtmlFont.movementMethod = LinkMovementMethod.getInstance()//TextView新增連結
    }

    /***
     * 字符 鏈接（ClickableSpan）--> 連結去設定頁
     * 是要參考：https://blog.csdn.net/zhuhai__yizhi/article/details/53760663
     */
    private fun clickableSpan() {
        val iClickableSpan: TextView = findViewById(R.id.mClickableSpan)
        val iText: String = iClickableSpan.text.toString()

        val iGoToSettings = getString(R.string.goToSetting)
        val iStart = iText.indexOf(iGoToSettings)
        val iEnd = iStart + iGoToSettings.length

        val iSpannableString = SpannableString(iText)
        iSpannableString.setSpan(GoToSettingsSpan(), iStart, iEnd, 0)
        iClickableSpan.text = iSpannableString

        iClickableSpan.movementMethod = LinkMovementMethod()
    }


    /***
     * 自定義 TagHandler
     * 綜合使用 TagHandler 和 MetricAffectingSpan
     */
    private fun fraction() {
        val iFraction: TextView = findViewById(R.id.mFraction)
        val iTypeface: Typeface = Typeface.createFromAsset(assets, "fonts/Nutso2.otf")
        val iHtmlFraction = getString(R.string.fraction)
        iFraction.typeface = iTypeface
        iFraction.text = HtmlCompat.fromHtml(iHtmlFraction, HtmlCompat.FROM_HTML_MODE_LEGACY, null, FractionTagHandler())
    }

    /***
     * underline底線、strikethrough刪除線、subscript字符下沉、superscript字符上浮、
     * backgroundColor字符背景色、foregroundColor文本顏色、image插入圖片、style簡單樣式、typeface自定義字體、
     * textAppearance字體樣式、absoluteSize絕對尺寸、relativeSize相對尺寸、scaleX字體橫向縮放、maskFilter字體蒙板、
     * rainbow彩虹樣式(靜態)
     */
    private fun allExampleSpan() {
//        val mUnderlineSpan:TextView = findViewById(R.id.mAllExampleSpan)
//        val text: String = mUnderlineSpan.text.toString()

//        val underlineText = getString(R.string.underlineText)//要underline的字
//        val start = text.indexOf(underlineText)
//        val end = start + underlineText.length
//
//        val spannableString = SpannableString(text)
//        spannableString.setSpan(UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        mUnderlineSpan.text = spannableString

        //底線
        setSpan(R.string.underlineText, UnderlineSpan())
        //刪除線
        setSpan(R.string.strikethroughText, StrikethroughSpan())
        //字符下沈
        setSpan(R.string.subscriptText, SubscriptSpan())
        //字符上浮
        setSpan(R.string.superscriptText, SuperscriptSpan())
        //字符背景色
        setSpan(R.string.backgroundColorText, BackgroundColorSpan(Color.GREEN))
        //文本顏色
        setSpan(R.string.foregroundColorText, ForegroundColorSpan(Color.RED))
        //插入圖片1
        setSpan(R.string.imageText, ImageSpan(this, R.drawable.ic_wifi_3))
        //簡單樣式StyleSpan
        setSpan(R.string.styleText, StyleSpan(Typeface.BOLD or Typeface.ITALIC))//粗體＋斜體
        //自定義字體
        setSpan(R.string.typefaceText, TypefaceSpan("serif"))
        //字體樣式
        setSpan(R.string.textAppearanceText, TextAppearanceSpan(this, R.style.SpecialTextAppearance))
        //絕對尺寸
        setSpan(R.string.absoluteSizeText, AbsoluteSizeSpan(24, true))
        //相對尺寸
        setSpan(R.string.relativeSizeText, RelativeSizeSpan(2.0f))
        //字體橫向縮放
        setSpan(R.string.scaleXText, ScaleXSpan(3.0f))
        //字體蒙板  模糊(BlurMaskFilter)、浮雕(EmbossMaskFilter)
        setSpan(R.string.maskFilterBlurMaskFilterText, MaskFilterSpan(BlurMaskFilter(5f, BlurMaskFilter.Blur.NORMAL)))
//        setSpan(R.string.maskFilterEmbossMaskFilterText, MaskFilterSpan(EmbossMaskFilter(floatArrayOf(1f, 1f, 1f), 0.4f, 6f, 3.5f))) //這沒在用了
        //彩虹樣式(靜態)
        setSpan(R.string.rainbowText, RainbowSpan(this))
        //自定義 Span
        setSpan(R.string.spanText, Span())

        // 彩色字體與霓虹燈字體 https://blog.csdn.net/Jack__Frost/article/details/52279374
    }


    private fun setSpan(pResId: Int, pSpan: CharacterStyle) {

        val iSpanText = getString(pResId)
        val iSpanTextStart = mText.indexOf(iSpanText)
        val iSpanTextEnd = iSpanTextStart + iSpanText.length

        mSpannableString.setSpan(pSpan, iSpanTextStart, iSpanTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mAllSpan.text = mSpannableString
    }


//    private fun allExampleSpan2() {
//        val iAllSpan: TextView = findViewById(R.id.mAllExampleSpan2)
//        val iText: String = iAllSpan.text.toString()
//        val iSpannableString = SpannableString(iText)
//
//        //底線
//        iSpannableString.setSpan(UnderlineSpan(), 10, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //刪除線
//        iSpannableString.setSpan(StrikethroughSpan(), 22, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //字符下沈
//        iSpannableString.setSpan(SubscriptSpan(), 39, 52, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //字符上浮
//        iSpannableString.setSpan(SuperscriptSpan(), 53, 68, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //字符背景色
//        iSpannableString.setSpan(BackgroundColorSpan(Color.GREEN), 70, 90, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //文本顏色
//        iSpannableString.setSpan(ForegroundColorSpan(Color.RED), 91, 110, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //插入圖片1
//        iSpannableString.setSpan(ImageSpan(this, R.drawable.ic_wifi_3), 111, 120, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //簡單樣式StyleSpan
//        iSpannableString.setSpan(StyleSpan(Typeface.BOLD or Typeface.ITALIC), 121, 130, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)//粗體＋斜體
//        //自定義字體
//        iSpannableString.setSpan(TypefaceSpan("serif"), 131, 144, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //字體樣式
//        iSpannableString.setSpan(TextAppearanceSpan(this, R.style.SpecialTextAppearance), 146, 164, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //絕對尺寸
//        iSpannableString.setSpan(AbsoluteSizeSpan(24, true), 165, 181, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //相對尺寸
//        iSpannableString.setSpan(RelativeSizeSpan(2.0f), 182, 198, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //字體橫向縮放
//        iSpannableString.setSpan(ScaleXSpan(3.0f), 199, 211, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //字體蒙板  模糊(BlurMaskFilter)
//        iSpannableString.setSpan(MaskFilterSpan(BlurMaskFilter(5f, BlurMaskFilter.Blur.NORMAL)), 212, 242, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
////        iSpannableString.setSpan(MaskFilterSpan(EmbossMaskFilter(floatArrayOf(100f, 1f, 1f), 0.4f, 888f, 3.5f)), 212, 242, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //彩虹樣式(靜態)
//        iSpannableString.setSpan(RainbowSpan(this), 277, 292, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //自定義 Span
//        iSpannableString.setSpan(Span(), 303, 308, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        iAllSpan.text = iSpannableString
//    }

    /***
     * 帶橫線的 EditText 在xml、LineEditText.kt
     */



    @SuppressLint("ObjectAnimatorBinding")
    fun setAnimatedRainbow() {
        val iAllSpan: TextView = findViewById(R.id.mRainBowTest)
        val iText = iAllSpan.text.toString()
        val spannableString = SpannableString(iText)

        val iSpanText = getString(R.string.rainbowAnimatedText)
        val iSpanTextStart = iText.indexOf(iSpanText)
        val iSpanTextEnd = iSpanTextStart + iSpanText.length
        val iAnimatedRainbowSpan = AnimatedRainbowSpan(this)

        spannableString.setSpan(iAnimatedRainbowSpan, iSpanTextStart, iSpanTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 透明度動畫
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(iAnimatedRainbowSpan, ANIMATED_COLOR_SPAN_FLOAT_PROPERTY, 0f, 100f)
        objectAnimator.setEvaluator(FloatEvaluator())
        objectAnimator.addUpdateListener { iAllSpan.text = spannableString }
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.duration = DateUtils.MINUTE_IN_MILLIS * 3
        objectAnimator.repeatCount = ValueAnimator.INFINITE //
        objectAnimator.start()
    }

    private val ANIMATED_COLOR_SPAN_FLOAT_PROPERTY: Property<AnimatedRainbowSpan, Float> =
        object : Property<AnimatedRainbowSpan, Float>(
            Float::class.java, "ANIMATED_COLOR_SPAN_FLOAT_PROPERTY"
        ) {
            override fun set(span: AnimatedRainbowSpan, value: Float?) {
                value?.let { span.setTranslateXPercentage(it) }
            }

            override fun get(span: AnimatedRainbowSpan): Float {
                return span.getTranslateXPercentage()
            }
        }


    /***
     * 自定義 Emoji
     */

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun emojiSpan() {
        val iEmojiSpan: TextView = findViewById(R.id.mEmojiSpan)
        val iText: String = iEmojiSpan.text.toString()
        val iSpannableString = SpannableString(iText)

        // Icon font
        var iPattern = Pattern.compile("\u26F7") // 滑雪Emoji
        var iMatcher = iPattern.matcher(iText)
        while (iMatcher.find()) {
            val iForegroundColorSpan = ForegroundColorSpan(getColor(R.color.black))
            val iIconFontSpan = IconFontSpan(iEmojiSpan.context)
            iSpannableString.setSpan(iIconFontSpan, iMatcher.start(), iMatcher.end(), 0)
            iSpannableString.setSpan(iForegroundColorSpan, iMatcher.start(), iMatcher.end(), 0)
        }

        // 文字間放圖
        iPattern = Pattern.compile(":octopus:")
        iMatcher = iPattern.matcher(iText)
        var iPicture: Bitmap? = null
        val iSize = (-iEmojiSpan.paint.ascent()).toInt()
        while (iMatcher.find()) {
            if (iPicture == null) {
                val iBitmap = BitmapFactory.decodeResource(resources, R.drawable.octopus)
                iPicture = Bitmap.createScaledBitmap(iBitmap, iSize, iSize, true)
                iBitmap.recycle()
            }
            val span = ImageSpan(this, iPicture!!, ImageSpan.ALIGN_BASELINE)
            iSpannableString.setSpan(span, iMatcher.start(), iMatcher.end(), 0)
        }

        // 用 drawable 來劃出圖 裡面有文字
        iPattern = Pattern.compile(":speed_(\\d\\d\\d?):")
        iMatcher = iPattern.matcher(iText)
        while (iMatcher.find()) {
            val iDrawable = SpeedSignDrawable(iEmojiSpan, iMatcher.group(1))
            val iImageSpan = ImageSpan(iDrawable, ImageSpan.ALIGN_BASELINE)
            iSpannableString.setSpan(iImageSpan, iMatcher.start(), iMatcher.end(), 0)
        }
        iEmojiSpan.text = iSpannableString
    }


    //TODO: 感覺可以在跟 setSpan 做整理
    private fun setSpanParagraphStyle(pAllSpanId: Int, pSpan: ParagraphStyle) {
        val iAllSpan: TextView = findViewById(pAllSpanId)
        val iText: String = iAllSpan.text.toString()

        val iSpanTextEnd = iText.length

        val iSpannableString = SpannableString(iText)
        iSpannableString.setSpan(pSpan, 0, iSpanTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        iAllSpan.text = iSpannableString
    }


    /***
     * 段落 簡單項目符號（BulletSpan）
     * 引用（QuoteSpan）
     * 對齊方式（AlignmentSpan.Standard）
     */
    private fun allExampleSpanParagraphStyle() {
        //段落 簡單項目符號（BulletSapn）
        setSpanParagraphStyle(R.id.mBulletSpan, BulletSpan(15, Color.BLACK))
        //引用（QuoteSpan）
        setSpanParagraphStyle(R.id.mQuoteSpan, QuoteSpan(Color.RED))
        //對齊方式（AlignmentSpan.Standard）
        setSpanParagraphStyle(
            R.id.mAlignmentSpan,
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
        )
    }


    /***
     * 項目符號（LeadingMarginSpan）
     */
    private fun leadingMarginSpan() {
        val iLeadingMarginSpan: TextView = findViewById(R.id.mLeadingMarginSpan)
        val iBullets = arrayOf("1.", "2.", "3.", "4.")

        val iItemContents = arrayOf(
            "那一天，閉目在經殿香霧中，驀然聽見，你誦經中的真言；",
            "那一月，我搖動所有的經筒，不為超度，只為觸摸你的指尖；",
            "那一年，磕長頭匍匐在山路，不為覲見，只為貼著你的溫暖；",
            "那一世，轉山轉水轉佛塔呀，不為修來生，只為途中與你相見。"
        )

        var iAllText: CharSequence? = ""
        for (iI in iBullets.indices) {
            val iBulletDrawText = iBullets[iI]
            val iT = iItemContents[iI].trim { it <= ' ' }

            // 註意此處的換行, 如果沒有換行符, 則系統當做只有一個項目處理
            val iSpannableString = SpannableString(
                """
                $iT
              
              """.trimIndent()
            )
            iSpannableString.setSpan(object : LeadingMarginSpan {
                override fun getLeadingMargin(pFirst: Boolean): Int {
                    // 項目符號和正文的縮進距離, 單位 px
                    // 我們可以根據 first 來改變第1行和其餘行的縮進距離
                    return 100
                }

                override fun drawLeadingMargin(
                    pCanvas: Canvas, pPaint: Paint, pX: Int, pDir: Int,
                    pTop: Int, pBaseline: Int, pBottom: Int,
                    pText: CharSequence, pStart: Int,
                    pEnd: Int, pFirst: Boolean, pLayout: Layout,
                ) {
                    if (pFirst) {
                        val iOrgStyle = pPaint.style
                        pPaint.style = Paint.Style.FILL
                        pCanvas.drawText(iBulletDrawText, 0f, pBottom - pPaint.descent(), pPaint)
                        pPaint.style = iOrgStyle
                    }
                }
            }, 0, iT.length, 0)
            iAllText = TextUtils.concat(iAllText, iSpannableString)
        }
        iLeadingMarginSpan.text = iAllText
    }


    //粒子爆炸
    private fun explosion(root: View){

        mExplosionField = ExplosionField.attach2Window(this)
        if (root is ViewGroup) {
            for (i in 0 until root.childCount) {
                explosion(root.getChildAt(i))
            }
        } else {
            root.isClickable = true
            root.setOnClickListener { v ->
                mExplosionField?.explode(v)
                v.setOnClickListener(null)
            }
        }
    }
}