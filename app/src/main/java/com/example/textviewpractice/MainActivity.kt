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

    //Compound Drawable ??????
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
     * ??????????????? (??????????????? assets/fonts/)
     */
    private fun customFont() {
        val iCustomFont: TextView = findViewById(R.id.mCustomFont)
        val iTypeface = Typeface.createFromAsset(assets, "fonts/Ruthie.ttf")
        iCustomFont.typeface = iTypeface
    }

    /***
     * ??????
     */
    private fun gradient() {
        val iGradientFont: TextView = findViewById(R.id.mGradientFont)

        //????????????????????????x0,y0???, ????????????????????????x1,y1???, ????????????????????? ,?????????????????????, Shader.TileMode
        iGradientFont.paint.shader = LinearGradient(
            0f, 0f, 0f, iGradientFont.textSize,
            Color.RED, Color.BLUE, Shader.TileMode.MIRROR
        )
    }

    /***
     * ??????????????????
     */
    private fun picFont() {
        val iPicFont: TextView = findViewById(R.id.mPicFont)
        val iBitmap = BitmapFactory.decodeResource(resources, R.drawable.cheetah_tile)
        iPicFont.paint.shader =
            BitmapShader(iBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
    }

    /***
     * ?????????
     */
    @RequiresApi(Build.VERSION_CODES.N)
    private fun html() {
        val iHtmlFont: TextView = findViewById(R.id.mHtmlFont)

        val iHtml = getString(R.string.from_html_text)
//        mHtmlFont.text = Html.fromHtml(html, ResourceImageGetter(this), null) //??????
        iHtmlFont.text = HtmlCompat.fromHtml(iHtml, HtmlCompat.FROM_HTML_MODE_LEGACY, ResourceImageGetter(this), null)
        iHtmlFont.movementMethod = LinkMovementMethod.getInstance()//TextView????????????
    }

    /***
     * ?????? ?????????ClickableSpan???--> ??????????????????
     * ???????????????https://blog.csdn.net/zhuhai__yizhi/article/details/53760663
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
     * ????????? TagHandler
     * ???????????? TagHandler ??? MetricAffectingSpan
     */
    private fun fraction() {
        val iFraction: TextView = findViewById(R.id.mFraction)
        val iTypeface: Typeface = Typeface.createFromAsset(assets, "fonts/Nutso2.otf")
        val iHtmlFraction = getString(R.string.fraction)
        iFraction.typeface = iTypeface
        iFraction.text = HtmlCompat.fromHtml(iHtmlFraction, HtmlCompat.FROM_HTML_MODE_LEGACY, null, FractionTagHandler())
    }

    /***
     * underline?????????strikethrough????????????subscript???????????????superscript???????????????
     * backgroundColor??????????????????foregroundColor???????????????image???????????????style???????????????typeface??????????????????
     * textAppearance???????????????absoluteSize???????????????relativeSize???????????????scaleX?????????????????????maskFilter???????????????
     * rainbow????????????(??????)
     */
    private fun allExampleSpan() {
//        val mUnderlineSpan:TextView = findViewById(R.id.mAllExampleSpan)
//        val text: String = mUnderlineSpan.text.toString()

//        val underlineText = getString(R.string.underlineText)//???underline??????
//        val start = text.indexOf(underlineText)
//        val end = start + underlineText.length
//
//        val spannableString = SpannableString(text)
//        spannableString.setSpan(UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        mUnderlineSpan.text = spannableString

        //??????
        setSpan(R.string.underlineText, UnderlineSpan())
        //?????????
        setSpan(R.string.strikethroughText, StrikethroughSpan())
        //????????????
        setSpan(R.string.subscriptText, SubscriptSpan())
        //????????????
        setSpan(R.string.superscriptText, SuperscriptSpan())
        //???????????????
        setSpan(R.string.backgroundColorText, BackgroundColorSpan(Color.GREEN))
        //????????????
        setSpan(R.string.foregroundColorText, ForegroundColorSpan(Color.RED))
        //????????????1
        setSpan(R.string.imageText, ImageSpan(this, R.drawable.ic_wifi_3))
        //????????????StyleSpan
        setSpan(R.string.styleText, StyleSpan(Typeface.BOLD or Typeface.ITALIC))//???????????????
        //???????????????
        setSpan(R.string.typefaceText, TypefaceSpan("serif"))
        //????????????
        setSpan(R.string.textAppearanceText, TextAppearanceSpan(this, R.style.SpecialTextAppearance))
        //????????????
        setSpan(R.string.absoluteSizeText, AbsoluteSizeSpan(24, true))
        //????????????
        setSpan(R.string.relativeSizeText, RelativeSizeSpan(2.0f))
        //??????????????????
        setSpan(R.string.scaleXText, ScaleXSpan(3.0f))
        //????????????  ??????(BlurMaskFilter)?????????(EmbossMaskFilter)
        setSpan(R.string.maskFilterBlurMaskFilterText, MaskFilterSpan(BlurMaskFilter(5f, BlurMaskFilter.Blur.NORMAL)))
//        setSpan(R.string.maskFilterEmbossMaskFilterText, MaskFilterSpan(EmbossMaskFilter(floatArrayOf(1f, 1f, 1f), 0.4f, 6f, 3.5f))) //???????????????
        //????????????(??????)
        setSpan(R.string.rainbowText, RainbowSpan(this))
        //????????? Span
        setSpan(R.string.spanText, Span())

        // ?????????????????????????????? https://blog.csdn.net/Jack__Frost/article/details/52279374
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
//        //??????
//        iSpannableString.setSpan(UnderlineSpan(), 10, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //?????????
//        iSpannableString.setSpan(StrikethroughSpan(), 22, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????????
//        iSpannableString.setSpan(SubscriptSpan(), 39, 52, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????????
//        iSpannableString.setSpan(SuperscriptSpan(), 53, 68, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //???????????????
//        iSpannableString.setSpan(BackgroundColorSpan(Color.GREEN), 70, 90, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????????
//        iSpannableString.setSpan(ForegroundColorSpan(Color.RED), 91, 110, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????????1
//        iSpannableString.setSpan(ImageSpan(this, R.drawable.ic_wifi_3), 111, 120, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????????StyleSpan
//        iSpannableString.setSpan(StyleSpan(Typeface.BOLD or Typeface.ITALIC), 121, 130, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)//???????????????
//        //???????????????
//        iSpannableString.setSpan(TypefaceSpan("serif"), 131, 144, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????????
//        iSpannableString.setSpan(TextAppearanceSpan(this, R.style.SpecialTextAppearance), 146, 164, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????????
//        iSpannableString.setSpan(AbsoluteSizeSpan(24, true), 165, 181, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????????
//        iSpannableString.setSpan(RelativeSizeSpan(2.0f), 182, 198, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //??????????????????
//        iSpannableString.setSpan(ScaleXSpan(3.0f), 199, 211, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????????  ??????(BlurMaskFilter)
//        iSpannableString.setSpan(MaskFilterSpan(BlurMaskFilter(5f, BlurMaskFilter.Blur.NORMAL)), 212, 242, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
////        iSpannableString.setSpan(MaskFilterSpan(EmbossMaskFilter(floatArrayOf(100f, 1f, 1f), 0.4f, 888f, 3.5f)), 212, 242, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????????(??????)
//        iSpannableString.setSpan(RainbowSpan(this), 277, 292, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        //????????? Span
//        iSpannableString.setSpan(Span(), 303, 308, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        iAllSpan.text = iSpannableString
//    }

    /***
     * ???????????? EditText ???xml???LineEditText.kt
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

        // ???????????????
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
     * ????????? Emoji
     */

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun emojiSpan() {
        val iEmojiSpan: TextView = findViewById(R.id.mEmojiSpan)
        val iText: String = iEmojiSpan.text.toString()
        val iSpannableString = SpannableString(iText)

        // Icon font
        var iPattern = Pattern.compile("\u26F7") // ??????Emoji
        var iMatcher = iPattern.matcher(iText)
        while (iMatcher.find()) {
            val iForegroundColorSpan = ForegroundColorSpan(getColor(R.color.black))
            val iIconFontSpan = IconFontSpan(iEmojiSpan.context)
            iSpannableString.setSpan(iIconFontSpan, iMatcher.start(), iMatcher.end(), 0)
            iSpannableString.setSpan(iForegroundColorSpan, iMatcher.start(), iMatcher.end(), 0)
        }

        // ???????????????
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

        // ??? drawable ???????????? ???????????????
        iPattern = Pattern.compile(":speed_(\\d\\d\\d?):")
        iMatcher = iPattern.matcher(iText)
        while (iMatcher.find()) {
            val iDrawable = SpeedSignDrawable(iEmojiSpan, iMatcher.group(1))
            val iImageSpan = ImageSpan(iDrawable, ImageSpan.ALIGN_BASELINE)
            iSpannableString.setSpan(iImageSpan, iMatcher.start(), iMatcher.end(), 0)
        }
        iEmojiSpan.text = iSpannableString
    }


    //TODO: ?????????????????? setSpan ?????????
    private fun setSpanParagraphStyle(pAllSpanId: Int, pSpan: ParagraphStyle) {
        val iAllSpan: TextView = findViewById(pAllSpanId)
        val iText: String = iAllSpan.text.toString()

        val iSpanTextEnd = iText.length

        val iSpannableString = SpannableString(iText)
        iSpannableString.setSpan(pSpan, 0, iSpanTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        iAllSpan.text = iSpannableString
    }


    /***
     * ?????? ?????????????????????BulletSpan???
     * ?????????QuoteSpan???
     * ???????????????AlignmentSpan.Standard???
     */
    private fun allExampleSpanParagraphStyle() {
        //?????? ?????????????????????BulletSapn???
        setSpanParagraphStyle(R.id.mBulletSpan, BulletSpan(15, Color.BLACK))
        //?????????QuoteSpan???
        setSpanParagraphStyle(R.id.mQuoteSpan, QuoteSpan(Color.RED))
        //???????????????AlignmentSpan.Standard???
        setSpanParagraphStyle(
            R.id.mAlignmentSpan,
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
        )
    }


    /***
     * ???????????????LeadingMarginSpan???
     */
    private fun leadingMarginSpan() {
        val iLeadingMarginSpan: TextView = findViewById(R.id.mLeadingMarginSpan)
        val iBullets = arrayOf("1.", "2.", "3.", "4.")

        val iItemContents = arrayOf(
            "??????????????????????????????????????????????????????????????????????????????",
            "?????????????????????????????????????????????????????????????????????????????????",
            "?????????????????????????????????????????????????????????????????????????????????",
            "????????????????????????????????????????????????????????????????????????????????????"
        )

        var iAllText: CharSequence? = ""
        for (iI in iBullets.indices) {
            val iBulletDrawText = iBullets[iI]
            val iT = iItemContents[iI].trim { it <= ' ' }

            // ?????????????????????, ?????????????????????, ???????????????????????????????????????
            val iSpannableString = SpannableString(
                """
                $iT
              
              """.trimIndent()
            )
            iSpannableString.setSpan(object : LeadingMarginSpan {
                override fun getLeadingMargin(pFirst: Boolean): Int {
                    // ????????????????????????????????????, ?????? px
                    // ?????????????????? first ????????????1??????????????????????????????
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


    //????????????
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