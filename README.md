# BallsLoadingView
A loading view that includes four balls for animating.

### [中文](https://github.com/samlss/BallsLoadingView/blob/master/README-ZH.md)

### [More](https://github.com/samlss/FunnyViews)

 <br/>

[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/BallsLoadingView)  [![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://github.com/samlss/BallsLoadingView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)

### The default 'translate' animation effect
![gif1](https://github.com/samlss/BallsLoadingView/blob/master/screenshots/screenshot1.gif)

### The default 'scale' animation effect
![gif2](https://github.com/samlss/BallsLoadingView/blob/master/screenshots/screenshot2.gif)

### The 'scale' animation but custom colors effect
![gif3](https://github.com/samlss/BallsLoadingView/blob/master/screenshots/screenshot3.gif)

### Use<br>
Add it in your root build.gradle at the end of repositories：
```java
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Add it in your app build.gradle at the end of repositories:
```java
dependencies {
    implementation 'com.github.samlss:BallsLoadingView:1.0'
}
```


in layout.xml：
```java
 <com.iigo.library.BallsLoadingView
          android:layout_marginTop="50dp"
          app:pointRadius="6dp"
          app:animType="scale"
          app:firstPointColor="@android:color/holo_green_dark"
          app:secondPointColor="@android:color/holo_red_dark"
          app:thirdPointColor="@android:color/holo_orange_dark"
          app:fourthPointColor="@android:color/holo_blue_dark"
          android:layout_width="100dp"
          android:layout_height="40dp" />

```

<br>

in java code：
```java
  ballsLoadingView.setAnimType(BallsLoadingView.ANIM_TYPE_SCALE);// Set the animation type.
  
  ballsLoadingView.setBallRadius(6); //Set the ball radius in pixel
  
  ballsLoadingView.setFirstBallColor(Color.RED); //Set the first ball color
  ballsLoadingView.setSecondBallColor(Color.BLACK); //Set the second ball color
  ballsLoadingView.setThirdBallColor(Color.GREEN); //Set the third ball color
  ballsLoadingView.setFourthBallColor(Color.BLUE); //Set the fourth ball color
  
  ballsLoadingView.start(); //start animation
  ballsLoadingView.stop(); //stop animation
  
  ballsLoadingView.release(); //release when you do net need the view anyway.
```
<br>

Attributes description：

| attr      |              description              |
| --------- | :-----------------------------------: |
| firstBallColor | the first ball color |
| secondBallColor | the second ball color |
| thirdBallColor | the third ball color |
| fourthBallColor | the fourth ball color |
| ballRadius | the ball radius |
| animType | the animation type(translate, scale) |

<br>

# Note

I don't have open the  animation interpolator and animation Interpolator setting interface. If you need to set it, you can download the source code to modify it.

## [LICENSE](https://github.com/samlss/BallsLoadingView/blob/master/LICENSE)
