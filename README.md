NumberCircleProgressBar
=======================

The NumberCircleProgressBar is a bar, slim and sexy.  I decided to do this after I saw [@daimajia/NumberProgressBar](https://github.com/daimajia/NumberProgressBar). And I made some change for the progressbar's shape and style.

---

### Illustration
---
![NumberCircleProgressBar1](https://github.com/cjl/NumberCircleProgressBar/blob/master/illustration/rotate_1.gif)
![NumberCircleProgressBar2](https://github.com/cjl/NumberCircleProgressBar/blob/master/illustration/rotate_2.png)
![NumberCircleProgressBar3](https://github.com/cjl/NumberCircleProgressBar/blob/master/illustration/rising_water_1.gif)
![NumberCircleProgressBar4](https://github.com/cjl/NumberCircleProgressBar/blob/master/illustration/rising_water_2.png)

### Attributes
![NumberCircleProgressBar5](https://github.com/cjl/NumberCircleProgressBar/blob/master/illustration/zoning.png)

There are several attributes you can set:

**The Circle**
*	circle_radius
* fill_mode(name="rotate" value="0",name="rising_water" value="1" )

The **reached area** and **unreached area**:

* color
* height 

The **text area**:

* color
* text size
* visibility

The **bar**:

* max progress
* current progress


### Usage
----
This widget is small,so I didn't build it as a library.You just copy the src/NumberCircleProgressBar.java and res/values/attrs.xml then:

#### In the layout file,you can write as this:

```xml
<com.cjl.numbercircleprogressbar.NumberCircleProgressBar
        android:id="@+id/numbercircleprogress_bar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:padding="10dp"
        custom:max="100"
        custom:progress="0"
        custom:progress_circle_radius="50dp"
        custom:progress_fill_mode="rotate"
        custom:progress_reached_color="#3498DB"
        custom:progress_text_color="@android:color/black"
        custom:progress_text_size="15sp"
        custom:progress_text_visibility="visible"
        custom:progress_unreached_color="#CCCCCC" />
```

#### You can also define the NumberCircleProgressBar's style like this:

```xml
<style name="NumberCircleProgressBar_Default">
        <item name="android:layout_height">wrap_content</item>
        <item name="android:layout_width">match_parent</item>
        <item name="max">100</item>
        <item name="progress">10</item>
        <item name="progress_unreached_color">#CCCCCC</item>
        <item name="progress_reached_color">#3498DB</item>
        <item name="progress_circle_radius">20dp</item>
        <item name="progress_text_size">10sp</item>
        <item name="progress_text_color">#000000</item>
        <item name="progress_fill_mode">rotate</item>
</style>
```
#### When you use the style,just do this:
```xml
<com.cjl.numbercircleprogressbar.NumberCircleProgressBar
            style="@style/NumberCircleProgressBar_Default"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="10dp" />
```

#### In my sample demo ,you can use NumberCircleBar in your Activity:

```java
public void setTheNumberProgressBar() {

	final NumberCircleProgressBar bnp = (NumberCircleProgressBar) findViewById(R.id.numbercircleprogress_bar);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (!isFinish) {
							bnp.incrementProgressBy(2);
							if (bnp.isFinished()) {
								isFinish = false;
							}
						}
					}
				});
			}
		}, 1000, 100);
	}
```

How to accomplish this view,you can look my [Blog](http://blog.csdn.net/cjllife/article/details/39102243)
