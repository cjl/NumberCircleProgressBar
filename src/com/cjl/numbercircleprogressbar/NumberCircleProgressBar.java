package com.cjl.numbercircleprogressbar;

import com.cjl.androidtest.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

public class NumberCircleProgressBar extends View {
	private Context mContext;

	/**
	 * The max progress, default is 100
	 */
	private int mMax = 100;

	/**
	 * current progress, can not exceed the max progress.
	 */
	private int mProgress = 0;

	/**
	 * the progress area bar color,at here is the background color of Sector
	 */
	private int mReachedBarColor;

	/**
	 * the bar unreached area color,at here is the background color of circle
	 */
	private int mUnreachedBarColor;

	/**
	 * the progress text color.
	 */
	private int mTextColor;

	/**
	 * the progress text size
	 */
	private float mTextSize;

	/**
	 * the Radius of the circle
	 */
	private float mCircleRadius;

	/**
	 * the numbercircleProgressBar mode,now have two mode,0:rotate
	 * 1:rising_water
	 */
	private int mFillMode;

	/**
	 * the suffix of the number.
	 */
	private String mSuffix = "%";

	/**
	 * the prefix.
	 */
	private String mPrefix = "";

	/**
	 * when mFillMode=rising_water,the percent Mapping the arc
	 */
	private final static double[] PERCENT_TO_ARC = { 0, 0.364413d, 0.4616d,
			0.530831d, 0.586699d, 0.634474d, 0.676734d, 0.714958d, 0.750081d,
			0.782736d, 0.813377d, 0.842337d, 0.869872d, 0.896184d, 0.921432d,
			0.945747d, 0.969237d, 0.991993d, 1.01409d, 1.0356d, 1.05657d,
			1.07706d, 1.0971d, 1.11674d, 1.13601d, 1.15494d, 1.17356d,
			1.19189d, 1.20996d, 1.22779d, 1.24539d, 1.26279d, 1.27999d,
			1.29702d, 1.31389d, 1.33061d, 1.3472d, 1.36366d, 1.38d, 1.39625d,
			1.4124d, 1.42847d, 1.44446d, 1.46039d, 1.47627d, 1.49209d,
			1.50788d, 1.52364d, 1.53937d, 1.55509d, 0.5 * Math.PI, 1.58651d,
			1.60222d, 1.61796d, 1.63371d, 1.6495d, 1.66533d, 1.6812d, 1.69713d,
			1.71313d, 1.72919d, 1.74535d, 1.76159d, 1.77794d, 1.7944d,
			1.81098d, 1.8277d, 1.84457d, 1.8616d, 1.8788d, 1.8962d, 1.9138d,
			1.93163d, 1.9497d, 1.96803d, 1.98665d, 2.00558d, 2.02485d,
			2.04449d, 2.06454d, 2.08502d, 2.10599d, 2.1275d, 2.1496d, 2.17236d,
			2.19585d, 2.22016d, 2.24541d, 2.27172d, 2.29926d, 2.32822d,
			2.35886d, 2.39151d, 2.42663d, 2.46486d, 2.50712d, 2.55489d,
			2.61076d, 2.67999d, 2.77718d, Math.PI };

	/**
	 * when mFillMode=rising_water,the percent Mapping the angle
	 */
	private final static double[] PERCENT_TO_ANGLE = { 0.0, 20.87932689970087,
			26.447731823238804, 30.414375934709003, 33.61537654454588,
			36.352682410783395, 38.77400205300625, 40.964075929114315,
			42.9764755929523, 44.847469272952004, 46.60306925301236,
			48.26235502771122, 49.83999431660394, 51.34756086715217,
			52.794164708298474, 54.18731158715907, 55.53318944792137,
			56.837012206521074, 58.103077046421646, 59.33550926374806,
			60.53700176013739, 61.710992282360436, 62.85919970380261,
			63.984488813439555, 65.08857848465665, 66.1731875908393,
			67.24003500537289, 68.29026664384769, 69.32560137964909,
			70.34718512836734, 71.35559084779759, 72.35253741132523,
			73.33802481895025, 74.31377194405803, 75.28035174444373,
			76.23833717790248, 77.1888741600245, 78.13196269080984,
			79.0681757280536, 79.99923214514119, 80.92455898427748,
			81.8453021610527, 82.7614616754669, 83.6741834431103,
			84.58404042177803, 85.49045965367499, 86.39516001218658,
			87.29814149731276, 88.19940410905353, 89.1000937629992, 90.0,
			90.90032715530023, 91.80044385145077, 92.70227942098667,
			93.60468794831772, 94.50938830682928, 95.41638049652137,
			96.325664517394, 97.23838628503741, 98.15511875724673,
			99.07528897622683, 100.00118877315823, 100.9316722324507,
			101.86845822748958, 102.81154675827493, 103.76151078260183,
			104.71949621606056, 105.68607601644626, 106.66182314155404,
			107.64731054917908, 108.64425711270671, 109.65266283213694,
			110.67424658085521, 111.70958131665661, 112.75981295513141,
			113.82666036966499, 114.91126947584766, 116.01535914706473,
			117.1406482567017, 118.28942863593899, 119.46284620036691,
			120.66433869675623, 121.89677091408264, 123.16300764132176,
			124.4670595830395, 125.81293744380183, 127.20579784376484,
			128.65251627647018, 130.1599682354594, 131.73789400324964,
			133.3971797779485, 135.15272246222935, 137.02342966333148,
			139.03565743983094, 141.2260750906161, 143.64739473283896,
			146.3844141201789, 149.5855293215748, 153.5521161372655,
			159.12069294814196, 180.0 };

	private static final int DEFAULT_INITIAL_ANGLE = 270;
	private final int DEFAULT_TEXT_COLOR = Color.rgb(255, 255, 255);
	private final int DEFAULT_REACHED_COLOR = Color.rgb(66, 145, 241);
	private final int DEFAULT_UNREACHED_COLOR = Color.rgb(204, 204, 204);
	private final int DEFAULT_FILL_MODE;
	private final float DEFAULT_TEXT_SIZE;
	private final float DEFAULT_CIRCLE_RADIUS;

	/**
	 * for save and restore instance of progressbar.
	 */
	private static final String INSTANCE_STATE = "saved_instance";
	private static final String INSTANCE_FILL_MODE = "full_mode";
	private static final String INSTANCE_TEXT_COLOR = "text_color";
	private static final String INSTANCE_TEXT_SIZE = "text_size";
	private static final String INSTANCE_CIRCLE_RADIUS = "circle_radius";
	private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
	private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
	private static final String INSTANCE_MAX = "max";
	private static final String INSTANCE_PROGRESS = "progress";
	private static final String INSTANCE_SUFFIX = "suffix";
	private static final String INSTANCE_PREFIX = "prefix";

	private static final int PROGRESS_TEXT_VISIBLE = 0;
	private static final int PROGRESS_TEXT_INVISIBLE = 1;

	/**
	 * circle's X axis
	 */
	private float centerX = 0;

	/**
	 * circle's Y axis
	 */
	private float centerY = 0;

	/**
	 * the width of the text that to be drawn
	 */
	private float mDrawTextWidth;

	/**
	 * the height of the text that to be drawn
	 */
	private float mDrawTextHeight;

	/**
	 * the drawn text start
	 */
	private float mDrawTextStart;

	/**
	 * the drawn text end
	 */
	private float mDrawTextEnd;

	/**
	 * the text that to be drawn in onDraw()
	 */
	private String mCurrentDrawText;

	/**
	 * the Painter of the circle.
	 */
	private Paint mCirclePaint;

	/**
	 * the Painter of the sector.
	 */
	private Paint mSectorPaint;

	/**
	 * the Painter of the progress text.
	 */
	private Paint mTextPaint;

	/**
	 * area to draw rect.
	 */
	private RectF mCircleRectF = new RectF(0, 0, 0, 0);

	private boolean mDrawReachedBar = true;

	private boolean mIfDrawText = true;

	public enum ProgressTextVisibility {
		Visible, Invisible
	};

	public NumberCircleProgressBar(Context context) {
		this(context, null);
	}

	public NumberCircleProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.numberProgressBarStyle);
	}

	public NumberCircleProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mContext = context;

		DEFAULT_CIRCLE_RADIUS = dp2px(40.5f);
		DEFAULT_TEXT_SIZE = sp2px(15);
		DEFAULT_FILL_MODE = 0;

		// load styled attributes.
		final TypedArray attributes = context.getTheme()
				.obtainStyledAttributes(attrs,
						R.styleable.NumberCircleProgressBar, defStyleAttr, 0);
		try {
			mFillMode = attributes.getInt(
					R.styleable.NumberCircleProgressBar_progress_fill_mode,
					DEFAULT_FILL_MODE);
			mCircleRadius = attributes.getDimension(
					R.styleable.NumberCircleProgressBar_progress_circle_radius,
					DEFAULT_CIRCLE_RADIUS);

			mReachedBarColor = attributes.getColor(
					R.styleable.NumberCircleProgressBar_progress_reached_color,
					DEFAULT_REACHED_COLOR);
			mUnreachedBarColor = attributes
					.getColor(
							R.styleable.NumberCircleProgressBar_progress_unreached_color,
							DEFAULT_UNREACHED_COLOR);
			mTextColor = attributes.getColor(
					R.styleable.NumberCircleProgressBar_progress_text_color,
					DEFAULT_TEXT_COLOR);
			mTextSize = attributes.getDimension(
					R.styleable.NumberCircleProgressBar_progress_text_size,
					DEFAULT_TEXT_SIZE);

			int textVisible = attributes
					.getInt(R.styleable.NumberCircleProgressBar_progress_text_visibility,
							PROGRESS_TEXT_VISIBLE);
			if (textVisible != PROGRESS_TEXT_VISIBLE) {
				mIfDrawText = false;
			}

			setProgress(attributes.getInt(
					R.styleable.NumberCircleProgressBar_progress, 0));
			setMax(attributes.getInt(R.styleable.NumberCircleProgressBar_max,
					100));
		} finally {
			attributes.recycle();
		}

		initializePainters();

	}

	@Override
	protected int getSuggestedMinimumWidth() {
		return (int) mTextSize;
	}

	@Override
	protected int getSuggestedMinimumHeight() {
		// the view's minWidth(minHeight) is circle's diameter
		return (int) mCircleRadius * 2;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measure(widthMeasureSpec, true),
				measure(heightMeasureSpec, false));

		calculateCircleCenter();
		calculateDrawRectF();
	}

	private int measure(int measureSpec, boolean isWidth) {
		int result;
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		int padding = isWidth ? getPaddingLeft() + getPaddingRight()
				: getPaddingTop() + getPaddingBottom();
		if (mode == MeasureSpec.EXACTLY) {
			result = size;
		} else {
			result = getSuggestedMinimumHeight();
			result += padding;
			if (mode == MeasureSpec.AT_MOST) {
				result = Math.min(result, size);
			}
		}

		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mCirclePaint.setStyle(Paint.Style.FILL);
		mSectorPaint.setStyle(Paint.Style.FILL);

		// draw the circle
		canvas.drawCircle(centerX, centerY, mCircleRadius, mCirclePaint);

		if (mDrawReachedBar) {
			switch (mFillMode) {
			case 0:
				canvas.drawArc(mCircleRectF, DEFAULT_INITIAL_ANGLE,
						getProgress() * 360 / getMax(), true, mSectorPaint);
				break;
			case 1:
				int percent = getProgress() * 100 / getMax();
				float startAngle;
				/**
				 * when mFillMode = rising_water,need draw triangle
				 */
				Path path = new Path();
				if (percent < 50) {
					startAngle = (float) (90 - PERCENT_TO_ANGLE[percent]);
					canvas.drawArc(mCircleRectF, startAngle,
							(float) (PERCENT_TO_ANGLE[percent] * 2), true,
							mSectorPaint);

					float rSin = (float) (mCircleRadius * Math
							.sin(PERCENT_TO_ARC[percent]));
					float rCos = (float) (mCircleRadius * Math
							.cos(PERCENT_TO_ARC[percent]));
					path.moveTo(centerX, centerY);// triangle start
					path.lineTo(centerX + rSin, centerY + rCos);
					path.lineTo(centerX - rSin, centerY + rCos);
					path.lineTo(centerX, centerY);
					path.close(); // siege of triangle
					canvas.drawPath(path, mCirclePaint);
					mCirclePaint.setStyle(Paint.Style.STROKE);
					mCirclePaint.setStrokeWidth(1.0f);
					canvas.drawPath(path, mCirclePaint);
				} else {
					startAngle = (float) (450 - PERCENT_TO_ANGLE[percent]);
					canvas.drawArc(mCircleRectF, startAngle,
							(float) (PERCENT_TO_ANGLE[percent] * 2), true,
							mSectorPaint);
					float rSin = (float) (mCircleRadius * Math
							.sin(PERCENT_TO_ARC[percent]));
					float rCos = (float) (mCircleRadius * Math
							.cos(PERCENT_TO_ARC[percent]));
					path.moveTo(centerX, centerY);// triangle start
					path.lineTo(centerX + rSin, centerY + rCos);
					path.lineTo(centerX - rSin, centerY + rCos);
					path.lineTo(centerX, centerY);
					path.close(); // siege of triangle
					canvas.drawPath(path, mSectorPaint);
					mSectorPaint.setStyle(Paint.Style.STROKE);
					mSectorPaint.setStrokeWidth(1.0f);
					canvas.drawPath(path, mSectorPaint);
				}
				break;
			default:
				break;
			}

		}

		if (mIfDrawText)
			calculateDrawText();
		canvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd,
				mTextPaint);
	}

	/**
	 * calculate the center of circle
	 */
	private void calculateCircleCenter() {
		centerX = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2.0f
				+ getPaddingLeft();
		centerY = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2.0f
				+ getPaddingTop();
	}

	/**
	 * calculate the region of rectangle which can cover circle
	 */
	private void calculateDrawRectF() {
		mCircleRectF.left = centerX - mCircleRadius;
		mCircleRectF.top = centerY - mCircleRadius;
		mCircleRectF.right = centerX + mCircleRadius;
		mCircleRectF.bottom = centerY + mCircleRadius;
	}

	/**
	 * calculate the region of text
	 */
	private void calculateDrawText() {
		mCurrentDrawText = String.format("%d", getProgress() * 100 / getMax());
		mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
		Rect rect = new Rect();
		// 返回包围整个字符串的最小的一个Rect区域
		mTextPaint.getTextBounds(mCurrentDrawText, 0, 1, rect);
		mDrawTextWidth = rect.width();
		mDrawTextHeight = rect.height();
		mDrawTextStart = centerX + mDrawTextWidth / 2.0f;
		mDrawTextEnd = centerY + mDrawTextHeight / 2.0f;
	}

	/**
	 * init all painters
	 */
	private void initializePainters() {
		mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint.setColor(mUnreachedBarColor);
		mCirclePaint.setStyle(Paint.Style.FILL);

		mSectorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mSectorPaint.setStyle(Paint.Style.FILL);
		mSectorPaint.setColor(mReachedBarColor);

		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		mTextPaint.setColor(mTextColor);
		mTextPaint.setTextSize(mTextSize);
	}

	/**
	 * get progress text color
	 * 
	 * @return progress text color
	 */
	public int getTextColor() {
		return mTextColor;
	}

	/**
	 * get progress text size
	 * 
	 * @return progress text size
	 */
	public float getProgressTextSize() {
		return mTextSize;
	}

	public int getUnreachedBarColor() {
		return mUnreachedBarColor;
	}

	public int getReachedBarColor() {
		return mReachedBarColor;
	}

	public int getProgress() {
		return mProgress;
	}

	public int getMax() {
		return mMax;
	}

	public void setMax(int Max) {
		if (Max > 0) {
			this.mMax = Max;
			invalidate();
		}
	}

	public void setProgressTextSize(float TextSize) {
		this.mTextSize = TextSize;
		mTextPaint.setTextSize(mTextSize);
		invalidate();
	}

	public void setProgressTextColor(int TextColor) {
		this.mTextColor = TextColor;
		mTextPaint.setColor(mTextColor);
		invalidate();
	}

	public void setSuffix(String suffix) {
		if (suffix == null) {
			mSuffix = "";
		} else {
			mSuffix = suffix;
		}
	}

	public String getSuffix() {
		return mSuffix;
	}

	public void setPrefix(String prefix) {
		if (prefix == null)
			mPrefix = "";
		else {
			mPrefix = prefix;
		}
	}

	public String getPrefix() {
		return mPrefix;
	}

	public float getCircleRadius() {
		return mCircleRadius;
	}

	public void setCircleRadius(float mCircleRadius) {
		this.mCircleRadius = mCircleRadius;
	}

	public void incrementProgressBy(int by) {
		if (by > 0) {
			int progress = getProgress() + by;
			setProgress(progress > getMax() ? getMax() : progress);
		}
	}

	public boolean isFinished() {
		if (mProgress >= mMax) {
			return true;
		}
		return false;
	}

	public void setProgress(int Progress) {
		if (Progress <= getMax() && Progress >= 0) {
			this.mProgress = Progress;
			invalidate();
		}
	}

	public float dp2px(float dp) {
		final float scale = getResources().getDisplayMetrics().density;
		return dp * scale + 0.5f;
	}

	public float sp2px(float sp) {
		final float scale = getResources().getDisplayMetrics().scaledDensity;
		return sp * scale;
	}

	public void setProgressTextVisibility(ProgressTextVisibility visibility) {
		if (visibility == ProgressTextVisibility.Visible) {
			mIfDrawText = true;
		} else {
			mIfDrawText = false;
		}
		invalidate();
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		final Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
		bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
		bundle.putFloat(INSTANCE_TEXT_SIZE, getProgressTextSize());
		bundle.putFloat(INSTANCE_CIRCLE_RADIUS, getCircleRadius());
		bundle.putInt(INSTANCE_REACHED_BAR_COLOR, getReachedBarColor());
		bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR, getUnreachedBarColor());
		bundle.putInt(INSTANCE_MAX, getMax());
		bundle.putInt(INSTANCE_PROGRESS, getProgress());
		bundle.putString(INSTANCE_SUFFIX, getSuffix());
		bundle.putString(INSTANCE_PREFIX, getPrefix());
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			final Bundle bundle = (Bundle) state;
			mFillMode = bundle.getInt(INSTANCE_FILL_MODE);
			mTextColor = bundle.getInt(INSTANCE_TEXT_COLOR);
			mTextSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
			mCircleRadius = bundle.getFloat(INSTANCE_CIRCLE_RADIUS);
			mReachedBarColor = bundle.getInt(INSTANCE_REACHED_BAR_COLOR);
			mUnreachedBarColor = bundle.getInt(INSTANCE_UNREACHED_BAR_COLOR);
			initializePainters();
			setMax(bundle.getInt(INSTANCE_MAX));
			setProgress(bundle.getInt(INSTANCE_PROGRESS));
			setPrefix(bundle.getString(INSTANCE_PREFIX));
			setSuffix(bundle.getString(INSTANCE_SUFFIX));
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
			return;
		}
		super.onRestoreInstanceState(state);
	}

}
